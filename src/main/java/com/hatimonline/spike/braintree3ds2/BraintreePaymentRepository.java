package com.hatimonline.spike.braintree3ds2;

import org.springframework.data.jpa.repository.JpaRepository;

public interface  BraintreePaymentRepository extends JpaRepository<BraintreePayment, Long> {
}
