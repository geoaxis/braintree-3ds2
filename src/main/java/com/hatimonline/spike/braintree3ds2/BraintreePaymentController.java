package com.hatimonline.spike.braintree3ds2;

import com.braintreegateway.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class BraintreePaymentController {
	
	private BraintreePaymentRepository braintreePaymentRepository;
	
	public BraintreePaymentController(BraintreePaymentRepository braintreePaymentRepository) {
		this.braintreePaymentRepository = braintreePaymentRepository;
	}
	
	@Value("${braintree.merchant-id}")
	public String braintreeMerchantId;
	
	@Value("${braintree.public-key}")
	public String braintreePublicKey;
	
	@Value("${braintree.private-key}")
	public String braintreePrivateKey;
	
	private BraintreeGateway gateway;
	
	
	@PostConstruct
	private void createGateway() {
		gateway = new BraintreeGateway(
				Environment.SANDBOX,
				braintreeMerchantId,
				braintreePublicKey,
				braintreePrivateKey
		);
	}
	
	@GetMapping("/client_token")
	@ResponseBody
	public ResponseEntity<?> getClientToken() {
		ClientTokenRequest clientTokenRequest = new ClientTokenRequest();
		// pass clientToken to your front-end
		String clientToken = gateway.clientToken().generate(clientTokenRequest);
		
		
		return ResponseEntity.status(201).body(Collections.singletonMap("client_token", clientToken));
	}
	
	@PostMapping(value = "/pay")
	public ResponseEntity<?>  postForm(
			@RequestBody Map<String, String> payload) {
		
		String amount = payload.get("amount");
		
		String nonce = payload.get("payment_method_nonce");
		
		BigDecimal decimalAmount;
		try {
			decimalAmount = new BigDecimal(amount);
		} catch (NumberFormatException e) {
			return ResponseEntity.badRequest().build();
		}
		
		TransactionRequest request = new TransactionRequest()
				.amount(decimalAmount)
				.paymentMethodNonce(nonce)
				.options()
				.submitForSettlement(true)
				.done();
		
		Result<Transaction> result = gateway.transaction().sale(request);
		
		if (result.isSuccess()) {
			Transaction transaction = result.getTarget();
			
			BraintreePayment bp = new BraintreePayment(transaction.getId(),
					transaction.getGraphQLId(),
					transaction.getCreatedAt().toInstant(),
					transaction.getAmount(),
					transaction.getCurrencyIsoCode());
			braintreePaymentRepository.save(bp);
			
			return ResponseEntity.accepted().build();
		} else if (result.getTransaction() != null) {
			Transaction transaction = result.getTransaction();
			Map<String, String> map = new HashMap<>();
			map.put("transactionId", transaction.getId());
			
			return ResponseEntity.ok(map);
			
		} else {
			StringBuilder errorString = new StringBuilder();
			for (ValidationError error : result.getErrors().getAllDeepValidationErrors()) {
				errorString.append("Error: ").append(error.getCode()).append(": ").append(error.getMessage()).append("\n");
			}
			Map<String, String> map = new HashMap<>();
			map.put("errors", errorString.toString());
			
			return ResponseEntity.status(402).body(map);
		}
		
	}
}
