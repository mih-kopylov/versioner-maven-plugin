package ru.omickron.exceptions;

import lombok.NonNull;

public class UnknownEnumException extends RuntimeException {
    public UnknownEnumException( @NonNull Enum type ) {
        super( "Unknown enum value: " + type );
    }
}
