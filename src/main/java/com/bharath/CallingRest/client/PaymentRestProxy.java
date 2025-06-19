package com.bharath.CallingRest.client;

import com.bharath.CallingRest.Models.Payment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;



@Component
public class PaymentRestProxy {
    private final RestTemplate restTemplate;

    @Value("${name.service.url}")
    private String paymentServiceUrl;

    public PaymentRestProxy(RestTemplate restTemplate){
        this.restTemplate=restTemplate;
    }
    public Payment createPayment(Payment payment){
        String uri=paymentServiceUrl+"/payment";
        HttpHeaders httpHeaders=new org.springframework.http.HttpHeaders();
        httpHeaders.add("requestId",payment.Id);
        HttpEntity<Payment> entity=new HttpEntity<Payment>(payment,httpHeaders);
        var response= restTemplate.exchange(uri, HttpMethod.POST,entity,Payment.class);
        return response.getBody();
    }

}
