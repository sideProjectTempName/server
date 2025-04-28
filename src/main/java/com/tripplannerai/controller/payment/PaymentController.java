package com.tripplannerai.controller.payment;

import com.tripplannerai.common.annotation.Id;
import com.tripplannerai.dto.request.payment.PaymentRequest;
import com.tripplannerai.dto.request.payment.TempPaymentRequest;
import com.tripplannerai.dto.response.payment.ConfirmResponse;
import com.tripplannerai.dto.response.payment.CheckTempResponse;
import com.tripplannerai.dto.response.payment.SaveTempResponse;
import com.tripplannerai.service.payment.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    @PostMapping("/api/confirm")
    public ResponseEntity<?> confirm(@RequestHeader("Idempotency-Key") String impotencyKey,
                                     @RequestBody PaymentRequest paymentRequest, @Id Long id){
        ConfirmResponse confirmResponse = paymentService.confirm(impotencyKey,paymentRequest,id);
        return new ResponseEntity<>(confirmResponse, HttpStatus.OK);
    }

    @PostMapping("/api/temp")
    public ResponseEntity<?> saveTemp(@RequestBody TempPaymentRequest tempPaymentRequest, @Id Long id){
        SaveTempResponse saveTempResponse = paymentService.saveTemp(tempPaymentRequest,id);
        return new ResponseEntity<>(saveTempResponse, HttpStatus.OK);
    }

    @PostMapping("/api/temp/check")
    public ResponseEntity<?> checkTemp(@RequestBody TempPaymentRequest tempPaymentRequest, @Id Long id){
        CheckTempResponse checkTempResponse = paymentService.checkTemp(tempPaymentRequest,id);
        return new ResponseEntity<>(checkTempResponse, HttpStatus.OK);
    }
}
