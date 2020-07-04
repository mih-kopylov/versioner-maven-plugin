package ru.mihkopylov.exception;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class WrongSuffixFormatException extends RuntimeException {
    @NonNull
    private final String suffix;

    public WrongSuffixFormatException( @NonNull String suffix ) {
        super( String.format( "Wrong suffix format: '%s'", suffix ) );
        this.suffix = suffix;
    }
}
