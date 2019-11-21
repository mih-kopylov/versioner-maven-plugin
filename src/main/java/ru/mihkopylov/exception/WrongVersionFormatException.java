package ru.mihkopylov.exception;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class WrongVersionFormatException extends RuntimeException {
    @NonNull
    private final String version;

    public WrongVersionFormatException( @NonNull String version ) {
        super( String.format( "Wrong version format: '%s'", version ) );
        this.version = version;
    }
}
