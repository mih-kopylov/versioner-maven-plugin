package ru.mihkopylov.model;

import org.junit.Test;
import ru.mihkopylov.exception.WrongVersionFormatException;

import static org.junit.Assert.assertEquals;

public class Version_Parse_Test {
    @Test
    public void release() {
        Version version = Version.parse( "1.2.3" );
        assertEquals( new Version( 1, 2, 3, null ), version );
    }

    @Test
    public void releaseWithEndingSpace() {
        Version version = Version.parse( "1.2.3 " );
        assertEquals( new Version( 1, 2, 3, null ), version );
    }

    @Test
    public void snapshot() {
        Version version = Version.parse( "1.2.3-SNAPSHOT" );
        assertEquals( new Version( 1, 2, 3, Suffix.SNAPSHOT ), version );
    }

    @Test
    public void snapshotWithTrailingSpace() {
        Version version = Version.parse( "1.2.3-SNAPSHOT " );
        assertEquals( new Version( 1, 2, 3, Suffix.SNAPSHOT ), version );
    }

    @Test
    public void snapshotWithLeadingSpace() {
        Version version = Version.parse( " 1.2.3-SNAPSHOT" );
        assertEquals( new Version( 1, 2, 3, Suffix.SNAPSHOT ), version );
    }

    @Test
    public void rc1() {
        Version version = Version.parse( "1.2.3-RC1" );
        assertEquals( new Version( 1, 2, 3, Suffix.RC1 ), version );
    }

    @Test
    public void rc2() {
        Version version = Version.parse( "1.2.3-RC2" );
        assertEquals( new Version( 1, 2, 3, Suffix.RC2 ), version );
    }

    @Test
    public void preSuffixWithHyphen() {
        Version version = Version.parse( "1.2.3-PART1-RC1" );
        assertEquals( new Version( 1, 2, 3, "PART1-RC1" ), version );
    }

    @Test
    public void preSuffixWithDot() {
        Version version = Version.parse( "1.2.3-PART1.RC1" );
        assertEquals( new Version( 1, 2, 3, "PART1.RC1" ), version );
    }

    @Test(expected = WrongVersionFormatException.class)
    public void noSuffix() {
        Version.parse( "1.2.3-" );
    }

    @Test(expected = WrongVersionFormatException.class)
    public void illegalSuffix() {
        Version.parse( "1.2.3-S?UFFIX" );
    }
}