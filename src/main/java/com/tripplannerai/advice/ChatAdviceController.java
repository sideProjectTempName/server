package com.tripplannerai.advice.chat;

import com.tripplannerai.controller.chat.ChatController;
import com.tripplannerai.controller.payment.PaymentController;
import com.tripplannerai.dto.response.ErrorResponse;
import com.tripplannerai.exception.chat.NotFoundChatParticipantException;
import com.tripplannerai.exception.chat.NotFoundChatRoomException;
import com.tripplannerai.exception.member.NotFoundMemberException;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.tripplannerai.util.ConstClass.*;

@Order(1)
@RestControllerAdvice(basePackageClasses = ChatController.class)
public class ChatAdviceController {
    @ExceptionHandler(NotFoundMemberException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundMemberException() {
        return new ResponseEntity<>(ErrorResponse.of(NOT_FOUND_MEMBER_CODE,NOT_FOUND_MEMBER_MESSAGE), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(NotFoundChatParticipantException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundChatParticipantException() {
        return new ResponseEntity<>(ErrorResponse.of(NOT_FOUND_CHAT_PARTICIPANT_CODE,NOT_FOUND_CHAT_PARTICIPANT_MESSAGE), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(NotFoundChatRoomException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundChatRoomException() {
        return new ResponseEntity<>(ErrorResponse.of(NOT_FOUND_CHAT_ROOM_CODE,NOT_FOUND_CHAT_ROOM_MESSAGE), HttpStatus.NOT_FOUND);
    }
}
