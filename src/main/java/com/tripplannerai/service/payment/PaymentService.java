package com.tripplannerai.service.payment;

import com.tripplannerai.dto.request.payment.PaymentRequest;
import com.tripplannerai.dto.response.ConfirmResponse;
import com.tripplannerai.entity.impotency.Impotency;
import com.tripplannerai.entity.member.Member;
import com.tripplannerai.exception.member.NotFoundMemberException;
import com.tripplannerai.exception.payment.AlreadyPaymentRequestException;
import com.tripplannerai.mapper.payment.PaymentFactory;
import com.tripplannerai.repository.impotency.ImpotencyRepository;
import com.tripplannerai.repository.member.MemberRepository;
import com.tripplannerai.repository.payment.PaymentRepository;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;

import static com.tripplannerai.util.ConstClass.*;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final ImpotencyRepository impotencyRepository;
    private final MemberRepository memberRepository;
    private String widgetSecretKey = "test_gsk_docs_OaPz8L5KdmQXkzRz3y47BMw6";
    private JSONParser parser = new JSONParser();

    public ConfirmResponse confirm(String impotencyKey, PaymentRequest paymentRequest, String email) throws IOException, ParseException {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundMemberException("not found member!1"));
        Optional<Impotency> optionalImpotency = impotencyRepository.findByImpotencyKey(impotencyKey);
        if(optionalImpotency.isPresent()){
            throw new AlreadyPaymentRequestException("already request payment!!");
        }
        Impotency impotency = Impotency.of(impotencyKey);
        impotencyRepository.save(impotency);

        Base64.Encoder encoder = Base64.getEncoder();
        byte[] encodedBytes = encoder.encode((widgetSecretKey + ":").getBytes(StandardCharsets.UTF_8));
        String authorizations = "Basic " + new String(encodedBytes);

        URL url = new URL("https://api.tosspayments.com/v1/payments/confirm");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("Authorization", authorizations);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        OutputStream outputStream = connection.getOutputStream();
        outputStream.write(paymentRequest.toString().getBytes("UTF-8"));

        int code = connection.getResponseCode();
        boolean isSuccess = code == 200;

        InputStream responseStream = isSuccess ? connection.getInputStream() : connection.getErrorStream();
        if(isSuccess){
            paymentRepository.save(PaymentFactory.from(paymentRequest,member));
            member.changePoint(paymentRequest.getAmount());
        }

        Reader reader = new InputStreamReader(responseStream, StandardCharsets.UTF_8);
        JSONObject jsonObject = (JSONObject) parser.parse(reader);
        responseStream.close();

        return ConfirmResponse.of(SUCCESS_CODE,SUCCESS_MESSAGE);
    }
}
