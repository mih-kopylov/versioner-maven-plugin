package ru.omickron.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

import static java.util.Objects.nonNull;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class Version {
    private static final String REG = "(\\d+)\\.(\\d+)\\.(\\d+)(-SNAPSHOT)?";
    private final int major;
    private final int minor;
    private final int patch;
    private final boolean snapshot;

    @NonNull
    public static Version parse( @NonNull String version ) {
        Matcher matcher = Pattern.compile( REG ).matcher( version.trim() );
        if (matcher.matches()) {
            int major = Integer.parseInt( matcher.group( 1 ) );
            int minor = Integer.parseInt( matcher.group( 2 ) );
            int patch = Integer.parseInt( matcher.group( 3 ) );
            boolean snapshot = nonNull( matcher.group( 4 ) );
            return new Version( major, minor, patch, snapshot );
        }
        throw new RuntimeException( String.format( "Wrong version format for '%s'", version ) );
    }

    @Override
    public String toString() {
        String result = major + "." + minor + "." + patch;
        return snapshot ? result + "-SNAPSHOT" : result;
    }

    @NonNull
    public Version incMajor() {
        return new Version( major + 1, 0, 0, snapshot );
    }

    @NonNull
    public Version incMinor() {
        return new Version( major, minor + 1, 0, snapshot );
    }

    @NonNull
    public Version incPatch() {
        return new Version( major, minor, patch + 1, snapshot );
    }

    @NonNull
    public Version release() {
        return new Version( major, minor, patch, false );
    }

    @NonNull
    public Version snapshot() {
        return new Version( major, minor, patch, true );
    }
}
