package ru.omickron.model;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class Version {
    private static final String SUFFIXES =
            Arrays.stream( Suffix.values() ).map( Suffix :: getValue ).collect( Collectors.joining( "|" ) );
    private static final String REG = "(\\d+)\\.(\\d+)\\.(\\d+)(" + SUFFIXES + ")?";
    private final int major;
    private final int minor;
    private final int patch;
    @NonNull
    private final Suffix suffix;

    @NonNull
    public static Version parse( @NonNull String version ) {
        Matcher matcher = Pattern.compile( REG ).matcher( version.trim() );
        if (matcher.matches()) {
            int major = Integer.parseInt( matcher.group( 1 ) );
            int minor = Integer.parseInt( matcher.group( 2 ) );
            int patch = Integer.parseInt( matcher.group( 3 ) );
            Suffix suffix = Suffix.parse( matcher.group( 4 ) );
            return new Version( major, minor, patch, suffix );
        }
        throw new RuntimeException( String.format( "Wrong version format for '%s'", version ) );
    }

    @Override
    public String toString() {
        return major + "." + minor + "." + patch + suffix.getValue();
    }

    @NonNull
    public Version incMajor() {
        return new Version( major + 1, 0, 0, suffix );
    }

    @NonNull
    public Version incMinor() {
        return new Version( major, minor + 1, 0, suffix );
    }

    @NonNull
    public Version incPatch() {
        return new Version( major, minor, patch + 1, suffix );
    }

    @NonNull
    public Version release() {
        return new Version( major, minor, patch, Suffix.RELEASE );
    }

    @NonNull
    public Version snapshot() {
        return new Version( major, minor, patch, Suffix.SNAPSHOT );
    }

    @NonNull
    public Version rc1() {
        return new Version( major, minor, patch, Suffix.RC1 );
    }

    @NonNull
    public Version rc2() {
        return new Version( major, minor, patch, Suffix.RC2 );
    }
}
