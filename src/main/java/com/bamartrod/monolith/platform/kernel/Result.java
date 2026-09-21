package com.bamartrod.monolith.platform.kernel;

import java.util.Optional;
import java.util.function.Function;

/**
 * Functional Either — sealed Result con capacidades monádicas para encadenar sin switch manual.
 */
public sealed interface Result<T> permits Result.Success, Result.Failure {
/**
 * Shared kernel primitive for Result — value object / error handling.
 *
 * @author Brandon Martinez
 */
    record Success<T>(T value) implements Result<T> {}
    record Failure<T>(DomainError error) implements Result<T> {}

    static <T> Result<T> of(Optional<T> opt, String code, String msg){
        return opt.<Result<T>>map(Success::new).orElseGet(()->new Failure<>(new DomainError.NotFound(code,msg)));
    }
    static <T> Result<T> of(Optional<T> opt, DomainError err){
        return opt.<Result<T>>map(Success::new).orElseGet(()->new Failure<>(err));
    }
    static <T> Result<T> success(T v){return new Success<>(v);}
    static <T> Result<T> failure(String c,String m){return new Failure<>(new DomainError.NotFound(c,m));}
    static <T> Result<T> failure(DomainError e){return new Failure<>(e);}

    // Monadic ops — permiten encadenar sin branching en controllers
    default <U> Result<U> map(Function<T, U> mapper){
        return switch(this){
            case Success<T>(var v) -> new Success<>(mapper.apply(v));
            case Failure<T>(var e) -> new Failure<>(e);
        };
    }
    default <U> Result<U> flatMap(Function<T, Result<U>> mapper){
        return switch(this){
            case Success<T>(var v) -> mapper.apply(v);
            case Failure<T>(var e) -> new Failure<>(e);
        };
    }
    default Result<T> recover(Function<DomainError, T> fn){
        return switch(this){
            case Success<T> s -> s;
            case Failure<T>(var e) -> new Success<>(fn.apply(e));
        };
    }
    default T orElseThrow(){ return switch(this){ case Success<T>(var v) -> v; case Failure<T>(var e) -> throw new DomainException(e); }; }
    default boolean isSuccess(){ return this instanceof Success; }
    default boolean isFailure(){ return this instanceof Failure; }
}
