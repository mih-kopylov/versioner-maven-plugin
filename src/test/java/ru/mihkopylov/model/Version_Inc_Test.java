package ru.mihkopylov.model;

import org.junit.Test;
import ru.mihkopylov.exception.WrongSuffixFormatException;

import static org.junit.Assert.assertEquals;

public class Version_Inc_Test {
    @Test
    public void incMajor() {
        Version version = new Version( 1, 2, 3, "SUFFIX" ).incMajor();
        assertEquals( new Version( 2, 0, 0, "SUFFIX" ), version );
    }

    @Test
    public void incMinor() {
        Version version = new Version( 1, 2, 3, "SUFFIX" ).incMinor();
        assertEquals( new Version( 1, 3, 0, "SUFFIX" ), version );
    }

    @Test
    public void incPatch() {
        Version version = new Version( 1, 2, 3, "SUFFIX" ).incPatch();
        assertEquals( new Version( 1, 2, 4, "SUFFIX" ), version );
    }

    @Test
    public void release() {
        Version version = new Version( 1, 2, 3, "SUFFIX" ).release();
        assertEquals( new Version( 1, 2, 3, null ), version );
    }

    @Test
    public void withSuffix() {
        Version version = new Version( 1, 2, 3, "SUFFIX" ).withSuffix( "ANOTHER" );
        assertEquals( new Version( 1, 2, 3, "ANOTHER" ), version );
    }

    @Test
    public void withSuffixWithDots() {
        Version version = new Version( 1, 2, 3, "SUFFIX" ).withSuffix( "RC.1.12" );
        assertEquals( new Version( 1, 2, 3, "RC.1.12" ), version );
    }

    @Test
    public void combo() {
        Version version = new Version( 1, 2, 3, "SUFFIX" ).incMinor().release().incPatch().withSuffix( "SNAPSHOT" );
        assertEquals( new Version( 1, 3, 1, "SNAPSHOT" ), version );
    }

    @Test(expected = WrongSuffixFormatException.class)
    public void withEmptySuffix() {
        new Version( 1, 2, 3, "SUFFIX" ).withSuffix( "" );
    }

    @Test(expected = WrongSuffixFormatException.class)
    public void withIllegalCharacterInSuffix() {
        new Version( 1, 2, 3, "SUFFIX" ).withSuffix( "SUFFIX?" );
    }
}