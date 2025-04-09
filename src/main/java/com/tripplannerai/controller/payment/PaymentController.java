package com.tripplannerai.controller.payment;

import com.tripplannerai.annotation.Username;
import com.tripplannerai.dto.request.payment.PaymentRequest;
import com.tripplannerai.dto.response.ConfirmResponse;
import com.tripplannerai.repository.impotency.ImpotencyRepository;
import com.tripplannerai.repository.payment.PaymentRepository;
import com.tripplannerai.service.payment.PaymentService;
import lombok.RequiredArgsConstructor;
import net.minidev.json.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    @PostMapping("api/confirm")
    public ResponseEntity<?> confirm(@RequestHeader("Idempotency-Key") String impotencyKey,
                                     @RequestBody PaymentRequest paymentRequest, @Username String email) throws IOException, ParseException {
        ConfirmResponse confirmResponse = paymentService.confirm(impotencyKey,paymentRequest,email);
        return new ResponseEntity<>(confirmResponse, HttpStatus.OK);
    }
}
