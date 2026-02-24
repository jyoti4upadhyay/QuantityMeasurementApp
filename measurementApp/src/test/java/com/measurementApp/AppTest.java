package com.measurementApp;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import com.measurementApp.App.*;




public class AppTest {

    double epsilon = 0.000001;


    // UC1

    @Test
    public void testFeetEquality() {

        assertTrue(new Feet(1.0)

                .equals(new Feet(1.0)));

    }



    // UC2

    @Test
    public void testInchesEquality() {

        assertTrue(new Inches(1.0)

                .equals(new Inches(1.0)));

    }



    // UC3

    @Test
    public void testFeetToInchesEquality() {

        Length f = new Length(1.0, LengthUnit.FEET);

        Length i = new Length(12.0, LengthUnit.INCHES);

        assertTrue(f.equals(i));

    }



    // UC4

    @Test
    public void testYardToFeet() {

        Length yard = new Length(1.0, LengthUnit.YARDS);

        Length feet = new Length(3.0, LengthUnit.FEET);

        assertTrue(yard.equals(feet));

    }



    @Test
    public void testCmToInches() {

        Length cm = new Length(2.54, LengthUnit.CENTIMETERS);

        Length inch = new Length(1.0, LengthUnit.INCHES);

        assertTrue(cm.equals(inch));

    }



    // UC5 Conversion Tests


    @Test
    public void testConversion_FeetToInches() {

        assertEquals( 12.0,
                App.convert(      1.0,   LengthUnit.FEET, LengthUnit.INCHES ), epsilon );

    }



    @Test
    public void testConversion_InchesToFeet() {

        assertEquals(  2.0,

              App.convert( 24.0, LengthUnit.INCHES, LengthUnit.FEET ), epsilon  );

    }



    @Test
    public void testConversion_YardToFeet() {

        assertEquals( 3.0,

                App.convert( 1.0,LengthUnit.YARDS, LengthUnit.FEET ),epsilon );

    }



    @Test
    public void testConversion_CmToInch() {

        assertEquals(

                1.0,

                App.convert(

                        2.54,

                        LengthUnit.CENTIMETERS,

                        LengthUnit.INCHES

                ),

                epsilon

        );

    }



    @Test
    public void testConversion_Zero() {

        assertEquals(

                0.0,

                App.convert( 0.0, LengthUnit.FEET,  LengthUnit.INCHES ),epsilon);

    } 



    @Test
    public void testConversion_InvalidUnit() {

        assertThrows(

                IllegalArgumentException.class,

                () -> App.convert(  1.0, null,  LengthUnit.FEET )

        );

    }



}