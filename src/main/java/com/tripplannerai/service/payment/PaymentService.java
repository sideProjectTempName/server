package com.tripplannerai.service.payment;

import com.tripplannerai.dto.request.payment.PaymentRequest;
import com.tripplannerai.dto.request.payment.TempPaymentRequest;
import com.tripplannerai.dto.response.payment.ConfirmResponse;
import com.tripplannerai.dto.response.payment.CheckTempResponse;
import com.tripplannerai.dto.response.payment.SaveTempResponse;
import com.tripplannerai.entity.impotency.Impotency;
import com.tripplannerai.entity.member.Member;
import com.tripplannerai.entity.payment.TempPayment;
import com.tripplannerai.exception.member.NotFoundMemberException;
import com.tripplannerai.exception.payment.AlreadyPaymentRequestException;
import com.tripplannerai.exception.payment.NotFoundTempPaymentException;
import com.tripplannerai.mapper.payment.PaymentFactory;
import com.tripplannerai.repository.impotency.ImpotencyRepository;
import com.tripplannerai.repository.member.MemberRepository;
import com.tripplannerai.repository.payment.PaymentRepository;
import com.tripplannerai.repository.payment.TempPaymentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import net.minidev.json.parser.JSONParser;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;

import static com.tripplannerai.util.ConstClass.*;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final ImpotencyRepository impotencyRepository;
    private final MemberRepository memberRepository;
    private final TempPaymentRepository tempPaymentRepository;
    private String widgetSecretKey = "test_gsk_docs_OaPz8L5KdmQXkzRz3y47BMw6";
    private JSONParser parser = new JSONParser();

    public ConfirmResponse confirm(String impotencyKey, PaymentRequest paymentRequest, Long id){

        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new NotFoundMemberException("not found member!1"));
        Optional<Impotency> optionalImpotency = impotencyRepository.findByImpotencyKey(impotencyKey);
        if (optionalImpotency.isPresent()) {
            throw new AlreadyPaymentRequestException("already request payment!!");
        }
        Impotency impotency = Impotency.of(impotencyKey);
        impotencyRepository.save(impotency);

        RestTemplate restTemplate = new RestTemplate();
        String auth = widgetSecretKey + ":";
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes(StandardCharsets.UTF_8));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Basic " + encodedAuth);

        HttpEntity<PaymentRequest> requestEntity = new HttpEntity<>(paymentRequest, headers);

        String url = "https://api.tosspayments.com/v1/payments/confirm";
        ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            paymentRepository.save(PaymentFactory.from(paymentRequest, member));
            member.changePoint(paymentRequest.getAmount());
        }

        return ConfirmResponse.of(SUCCESS_CODE, SUCCESS_MESSAGE);
    }

    public SaveTempResponse saveTemp(TempPaymentRequest tempPaymentRequest, Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new NotFoundMemberException("not found member!"));
        TempPayment tempPayment = PaymentFactory.from(tempPaymentRequest, member);
        tempPaymentRepository.save(tempPayment);
        return SaveTempResponse.of(SUCCESS_CODE, SUCCESS_MESSAGE);
    }

    public CheckTempResponse checkTemp(TempPaymentRequest tempPaymentRequest, Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new NotFoundMemberException("not found member!"));
        String orderId = tempPaymentRequest.getOrderId();
        Integer amount = tempPaymentRequest.getAmount();
        tempPaymentRepository.findByOrderIdAndAmountAndMember(orderId, amount, member)
                .orElseThrow(() -> new NotFoundTempPaymentException("not found tempPayment!!"));
        return CheckTempResponse.of(SUCCESS_CODE, SUCCESS_MESSAGE);
    }
}