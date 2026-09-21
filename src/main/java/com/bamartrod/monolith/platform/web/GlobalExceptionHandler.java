package com.bamartrod.monolith.platform.web;

import com.bamartrod.monolith.platform.kernel.DomainError;
import com.bamartrod.monolith.platform.kernel.DomainException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.Instant;
/**
 * Global exception handler for GlobalExceptionHandler — RFC-7807 problem details.
 *
 * @author Brandon Martinez
 */

@RestControllerAdvice

public class GlobalExceptionHandler {
    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ErrorBody> handle(DomainException ex, HttpServletRequest req){
        return toError(ex.error(), req);
    }
    private ResponseEntity<ErrorBody> toError(DomainError err, HttpServletRequest req){
        Object trace=req.getAttribute(CorrelationFilter.ATTRIBUTE);
        String traceId=trace!=null?trace.toString():null;
        var body=new ErrorBody(err.code(),err.message(),err.formatted(),Instant.now().toString(),traceId);
        HttpStatus status=switch(err){ case DomainError.NotFound ignored->HttpStatus.NOT_FOUND; case DomainError.Validation ignored->HttpStatus.BAD_REQUEST; case DomainError.Conflict ignored->HttpStatus.CONFLICT; };
        return ResponseEntity.status(status).body(body);
    }
    public record ErrorBody(String code,String message,String description,String timestamp,String traceId){}
}
