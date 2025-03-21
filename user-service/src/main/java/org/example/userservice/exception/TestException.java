package org.example.userservice.exception;


import org.springframework.http.HttpStatus;

public class TestException extends RuntimeException {
    private final HttpStatus httpStatus; // Http 상태값
    private final String errorCode;  // 에러 코드
    private final String errorMessage;  // 에러 메시지

    public TestException(ApiExceptionCode errorCode) {
        this.httpStatus = errorCode.getHttpStatus();
        this.errorCode = errorCode.getCode();
        this.errorMessage = errorCode.getMessage();
    }
}
