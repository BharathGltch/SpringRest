package com.bharath.CallingRest.Controllers;

import com.bharath.CallingRest.Models.Payment;
import com.bharath.CallingRest.Models.Purchase;
import com.bharath.CallingRest.Repositories.PurchaseRepository;
import com.bharath.CallingRest.client.PaymentProxy;
import com.bharath.CallingRest.client.PaymentRestProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@RestController
public class PaymentsController {

    private static Logger logger=Logger.getLogger(PaymentsController.class.getName());
    private final PaymentProxy paymentProxy;
    private final PaymentRestProxy paymentRestProxy;
    @Autowired
    private PurchaseRepository purchaseRepository;

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

    @PostMapping("/purchase")
    public ResponseEntity purchase(@RequestBody Purchase purchase){
        purchaseRepository.storePurchase(purchase);
        return null;
    }

    @GetMapping("/purchase")
    public ResponseEntity<List<Purchase>> Purchases(){
        var purchaseList=purchaseRepository.getPurchases();
        return  ResponseEntity.status(HttpStatus.OK).body(purchaseList);
    }


}
