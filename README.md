# Pre-Req
 - JDK 8
 - Braintree sandbox account. See details [here](https://developer.paypal.com/braintree/articles/get-started/try-it-out)
 - Gateway credentials (merchant id, private and public keys). See details [here](https://developer.paypal.com/braintree/articles/control-panel/important-gateway-credentials)

# How to test 
 - Set the properties for your merchantid and secret from braintree sandbox account
 - run `./mvnw`
 - check the status of payments current payments in [`http://localhost:8080/braintreePayments`](http://localhost:8080/braintreePayments)
 - browse to [`http://localhost:8080/payment.html`](http://localhost:8080/payment.html)
 - Do the payments as per instructions in UI (it's probably a good idea to see the network trraffic)
 - check payments in braintree as well in the database using the link mentioned before.
 - Try out different scenarios using test cards (see testing and go live link).

# Related
- [Documentation: Set Up Your Server , Braintree](https://developer.paypal.com/braintree/docs/start/hello-server/java)
- [Documentation: Client authorization overview, Braintree](https://developer.paypal.com/braintree/docs/guides/authorization/overview) . Shows that client token is needed for 3ds. 
- [Documentation: Testing and Go Live, Braintree](https://developer.paypal.com/braintree/docs/guides/3d-secure/testing-go-live/java)
- [Documentation: 3D secure , client side implementation, Braintree](https://developer.paypal.com/braintree/docs/guides/3d-secure/client-side/javascript/v3) . This is where the hosted fields code is copied from 
- [Blog : Integration of braintree payment gateway in spring boot](https://www.oodlestechnologies.com/blogs/integration-of-braintree-payment-gateway-in-spring-boot/)
