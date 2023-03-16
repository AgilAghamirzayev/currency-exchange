package com.umbrella.currencyexchange.exception.handler;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatusCode;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ErrorResponse {
   private HttpStatusCode status;
   private String message;

   @JsonFormat(pattern = "dd-MM-yyyy hh:mm:ss")
   private LocalDateTime timestamp;

}