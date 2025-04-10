package com.tripplannerai.controller.payment;

import com.tripplannerai.annotation.Username;
import com.tripplannerai.dto.request.payment.PaymentRequest;
import com.tripplannerai.dto.request.payment.TempPaymentRequest;
import com.tripplannerai.dto.response.payment.ConfirmResponse;
import com.tripplannerai.dto.response.payment.CheckTempResponse;
import com.tripplannerai.dto.response.payment.SaveTempResponse;
import com.tripplannerai.service.payment.PaymentService;
import lombok.RequiredArgsConstructor;
import net.minidev.json.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    @PostMapping("/api/confirm")
    public ResponseEntity<?> confirm(@RequestHeader("Idempotency-Key") String impotencyKey,
                                     @RequestBody PaymentRequest paymentRequest, @Username String email) throws IOException, ParseException {
        ConfirmResponse confirmResponse = paymentService.confirm(impotencyKey,paymentRequest,email);
        return new ResponseEntity<>(confirmResponse, HttpStatus.OK);
    }

    @PostMapping("/api/temp")
    public ResponseEntity<?> saveTemp(@RequestBody TempPaymentRequest tempPaymentRequest, @Username String email){
        SaveTempResponse saveTempResponse = paymentService.saveTemp(tempPaymentRequest,email);
        return new ResponseEntity<>(saveTempResponse, HttpStatus.OK);
    }

    @GetMapping("/api/temp")
    public ResponseEntity<?> checkTemp(@RequestBody TempPaymentRequest tempPaymentRequest, @Username String email){
        CheckTempResponse checkTempResponse = paymentService.checkTemp(tempPaymentRequest,email);
        return new ResponseEntity<>(checkTempResponse, HttpStatus.OK);
    }
}
