package com.bharath.CallingRest.Controllers;

import com.bharath.CallingRest.Models.Payment;
import com.bharath.CallingRest.client.PaymentProxy;
import com.bharath.CallingRest.client.PaymentRestProxy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.logging.Logger;

@RestController
public class PaymentsController {

    private static Logger logger=Logger.getLogger(PaymentsController.class.getName());
    private final PaymentProxy paymentProxy;
    private final PaymentRestProxy paymentRestProxy;

    public PaymentsController(PaymentProxy paymentProxy,PaymentRestProxy prp){
        this.paymentProxy=paymentProxy;
        this.paymentRestProxy=prp;
    }


    @RequestMapping(value="/payments",method= RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Payment> createPayment(@RequestHeader String requestId,@RequestBody Payment payment){

        logger.info("Received request with ID"+requestId+ ";Payment Amount:"+payment.amount);
        payment.Id= UUID.randomUUID().toString();
        Payment retPay=paymentRestProxy.createPayment(payment);
        return ResponseEntity
                .status(HttpStatus.OK)
                .header("request ID",requestId)
                .body(retPay);
    }


}
