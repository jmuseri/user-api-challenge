package com.museri.challenge.exception;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
public class ExceptionResponse {

    private List<BugDetail> error = new ArrayList<>();

    public ExceptionResponse(String message, HttpStatus httpStatus) {
        this.error.add(BugDetail.builder()
                .withTimestamp(Instant.now())
                .withCode(httpStatus.value())
                .withDetail(message)
                .build()
        );
    }

    @Data
    @Builder (setterPrefix = "with")
    static class BugDetail {
        public Instant timestamp;
        public int code;
        public String detail;
    }

}
