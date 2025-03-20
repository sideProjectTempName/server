package com.tripplannerai.service.fail;

import com.tripplannerai.dto.request.EmailCertificationRequest;
import com.tripplannerai.dto.response.fail.CloseResponse;
import com.tripplannerai.dto.response.fail.FailResponse;
import com.tripplannerai.emiitter.EmitterRepository;
import com.tripplannerai.entity.fail.Fail;
import com.tripplannerai.repository.fail.FailRepository;
import com.tripplannerai.util.ConstClass;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

import static com.tripplannerai.util.ConstClass.*;

@Service
@RequiredArgsConstructor
@Transactional
public class FailService {

    private final FailRepository failRepository;
    private final EmitterRepository emitterRepository;
    private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60;

    public SseEmitter subscribe(String clientId, String lastEventId) {
        SseEmitter emitter = emitterRepository.save(clientId, new SseEmitter(DEFAULT_TIMEOUT));
        emitter.onCompletion(() -> emitterRepository.deleteById(clientId));
        emitter.onTimeout(() -> emitterRepository.deleteById(clientId));
        sendToClient(emitter, clientId, "EventStream Created. [clientId=" + clientId + "]");
        return emitter;
    }

    public void send(EmailCertificationRequest emailCertificationRequest){
        String clientId = emailCertificationRequest.getClientId();
        String email = emailCertificationRequest.getEmail();
        SseEmitter sseEmitter = emitterRepository.findById(clientId);
        Fail fail = Fail.of(clientId,"fail send Mail");
        FailResponse failResponse = FailResponse.of(NOT_VALID_EMAIL, NOT_VALID_EMAIL_MESSAGE, clientId, email);
        sendToClient(sseEmitter,clientId,failResponse);
        failRepository.save(fail);
    }

    private void sendToClient(SseEmitter emitter, String id, Object data) {
        try {
            emitter.send(SseEmitter.event()
                    .id(id)
                    .name("sse")
                    .data(data));
        } catch (IOException exception) {
            emitterRepository.deleteById(id);
            throw new RuntimeException("connect Fail!");
        }
    }

    public CloseResponse close(String clientId) {
        emitterRepository.deleteById(clientId);
        return CloseResponse.of(SUCCESS_CODE,SUCCESS_MESSAGE);
    }
}
