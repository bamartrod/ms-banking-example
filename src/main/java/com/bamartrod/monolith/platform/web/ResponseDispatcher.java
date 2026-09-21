package com.bamartrod.monolith.platform.web;

import com.bamartrod.monolith.platform.kernel.CorrelationId;
import com.bamartrod.monolith.platform.kernel.Result;
import org.springframework.http.ResponseEntity;
import java.util.function.Function;


/**
 * Functional dispatcher for ResponseDispatcher — maps Result to HTTP response.
 *
 * @author Brandon Martinez
 */
public final class ResponseDispatcher {
    private ResponseDispatcher(){}
    public static <T,R> ResponseEntity<R> dispatch(Result<T> result, CorrelationId cid, Function<T,R> mapper){
        return switch(result){
            case Result.Success<T>(var value) -> ResponseEntity.ok().header("X-Correlation-Id",cid.value()).body(mapper.apply(value));
            case Result.Failure<T>(var error) -> ResponseEntity.status(error.httpStatus()).header("X-Correlation-Id",cid.value()).build();
        };
    }
}
