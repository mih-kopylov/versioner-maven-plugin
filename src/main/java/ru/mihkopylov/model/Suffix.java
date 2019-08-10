package ru.mihkopylov.model;

import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@AllArgsConstructor
@Getter
public enum Suffix {
    RELEASE( "" ),
    SNAPSHOT( "-SNAPSHOT" ),
    RC1( "-RC1" ),
    RC2( "-RC2" );
    @NonNull
    private final String value;

    @NonNull
    public static Suffix parse( @NonNull String value ) {
        return Arrays.stream( Suffix.values() )
                .filter( o -> o.value.equalsIgnoreCase( value ) )
                .findAny()
                .orElseThrow( () -> new RuntimeException( String.format( "Unknown suffix: '%s'", value ) ) );
    }
}
