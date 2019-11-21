package ru.mihkopylov.model;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import ru.mihkopylov.exception.WrongSuffixFormatException;
import ru.mihkopylov.exception.WrongVersionFormatException;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
@EqualsAndHashCode
public class Version {
    private static final String SUFFIX_PATTERN = "[\\w-\\\\.]+";
    private static final String VERSION_PATTERN = "(\\d+)\\.(\\d+)\\.(\\d+)(-(" + SUFFIX_PATTERN + "))?";
    private static final String HYPHEN = "-";
    private final int major;
    private final int minor;
    private final int patch;
    @Nullable
    private final String suffix;

    @NonNull
    public static Version parse( @NonNull String version ) {
        Matcher matcher = Pattern.compile( VERSION_PATTERN ).matcher( version.trim() );
        if (matcher.matches()) {
            int major = Integer.parseInt( matcher.group( 1 ) );
            int minor = Integer.parseInt( matcher.group( 2 ) );
            int patch = Integer.parseInt( matcher.group( 3 ) );
            String suffix = matcher.group( 5 );
            return new Version( major, minor, patch, suffix );
        }
        throw new WrongVersionFormatException( version );
    }

    @Override
    public String toString() {
        return major + "." + minor + "." + patch + Optional.ofNullable( suffix ).map( o -> HYPHEN + o ).orElse( "" );
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
        return new Version( major, minor, patch, null );
    }

    @NonNull
    public Version withSuffix( @NonNull String suffix ) {
        if (!suffix.matches( SUFFIX_PATTERN )) {
            throw new WrongSuffixFormatException( suffix );
        }
        return new Version( major, minor, patch, suffix );
    }
}
