package com.bamartrod.monolith.platform.kernel;

/**
 * Canonical sealed DomainError — unified for platform Result.Failure and legacy DomainException.
 */
public sealed interface DomainError permits DomainError.NotFound, DomainError.Validation, DomainError.Conflict {
    String code(); String message();
/**
 * Shared kernel primitive for DomainError — value object / error handling.
 *
 * @author Brandon Martinez
 */
    record NotFound(String code, String message) implements DomainError {}
    record Validation(String code, String message) implements DomainError {}
    record Conflict(String code, String message) implements DomainError {}
    default String formatted(){
        return switch(this){
            case NotFound nf -> "NOT_FOUND[%s]: %s".formatted(nf.code(), nf.message());
            case Validation v -> "VALIDATION[%s]: %s".formatted(v.code(), v.message());
            case Conflict c -> "CONFLICT[%s]: %s".formatted(c.code(), c.message());
        };
    }
    default int httpStatus(){
        return switch(this){
            case NotFound ignored -> 404;
            case Validation ignored -> 400;
            case Conflict ignored -> 409;
        };
    }
}
