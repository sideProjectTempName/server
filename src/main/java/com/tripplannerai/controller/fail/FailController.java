package com.tripplannerai.controller.fail;

import com.tripplannerai.dto.response.fail.CloseResponse;
import com.tripplannerai.service.fail.FailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequiredArgsConstructor
public class FailController {

    private final FailService failService;

    @GetMapping(value = "/subscribe/{clientId}", produces = "text/event-stream")
    public SseEmitter subscribe(@PathVariable String clientId,
                                @RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId) {
        return failService.subscribe(clientId, lastEventId);
    }

    @DeleteMapping(value = "/close/{clientId}")
    public ResponseEntity<CloseResponse> close(@PathVariable String clientId){
        CloseResponse closeResponse = failService.close(clientId);
        return new ResponseEntity<>(closeResponse, HttpStatus.OK);
    }
}
