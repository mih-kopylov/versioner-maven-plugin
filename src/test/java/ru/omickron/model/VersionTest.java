package ru.omickron.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class VersionTest {
    @Test
    public void release() {
        Version version = Version.parse( "1.2.3" );
        assertEquals( new Version( 1, 2, 3, Suffix.RELEASE ), version );
    }

    @Test
    public void releaseWithEndingSpace() {
        Version version = Version.parse( "1.2.3 " );
        assertEquals( new Version( 1, 2, 3, Suffix.RELEASE ), version );
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

    @Test(expected = RuntimeException.class)
    public void unknownSuffix() {
        Version.parse( "1.2.3-SUFFIX" );
    }

    @Test(expected = RuntimeException.class)
    public void twoSuffixes() {
        Version.parse( "1.2.3-PART1-RC1" );
    }
}