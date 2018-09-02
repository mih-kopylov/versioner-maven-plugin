package ru.omickron.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class VersionTest {
    @Test
    public void release() {
        Version version = Version.parse( "1.2.3" );
        assertEquals( new Version( 1, 2, 3, false ), version );
    }

    @Test
    public void releaseWithEndingSpace() {
        Version version = Version.parse( "1.2.3 " );
        assertEquals( new Version( 1, 2, 3, false ), version );
    }

    @Test
    public void snapshot() {
        Version version = Version.parse( "1.2.3-SNAPSHOT" );
        assertEquals( new Version( 1, 2, 3, true ), version );
    }
}