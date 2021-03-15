package dev.matuszewski.decerto.source.exception;

public class RandomApiInvalidResponse extends RuntimeException {
    public RandomApiInvalidResponse() {
        super(ExceptionMessages.INVALID_API_RESPONSE);
    }
}
