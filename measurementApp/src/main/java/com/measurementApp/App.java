package com.measurementApp;
public class App {

    // UC1
    public static class Feet {

        private final double value;

        public Feet(double value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object obj) {

            if (this == obj)
                return true;

            if (obj == null)
                return false;

            if (getClass() != obj.getClass())
                return false;

            Feet other = (Feet) obj;

            return Double.compare(this.value, other.value) == 0;
        }
    }



    // UC2
    public static class Inches {

        private final double value;

        public Inches(double value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object obj) {

            if (this == obj)
                return true;

            if (obj == null)
                return false;

            if (getClass() != obj.getClass())
                return false;

            Inches other = (Inches) obj;

            return Double.compare(this.value, other.value) == 0;
        }
    }



    // UC3 + UC4 + UC5

    public enum LengthUnit {

        FEET(12.0),

        INCHES(1.0),

        YARDS(36.0),

        CENTIMETERS(0.393701);



        private final double conversionFactor;


        LengthUnit(double conversionFactor) {

            this.conversionFactor = conversionFactor;

        }



        public double getConversionFactor() {

            return conversionFactor;

        }

    }



    public static class Length {

        private final double value;

        private final LengthUnit unit;



        public Length(double value, LengthUnit unit) {

            if (!Double.isFinite(value))

                throw new IllegalArgumentException("Invalid value");

            if (unit == null)

                throw new IllegalArgumentException("Unit cannot be null");

            this.value = value;

            this.unit = unit;

        }



        private double toBaseUnit() {

            return this.value * this.unit.getConversionFactor();

        }



        public Length convertTo(LengthUnit targetUnit) {

            double result = convert(this.value, this.unit, targetUnit);

            return new Length(result, targetUnit);

        }



        @Override

        public boolean equals(Object obj) {

            if (this == obj)
                return true;

            if (obj == null)
                return false;

            if (getClass() != obj.getClass())
                return false;

            Length other = (Length) obj;

            return Double.compare(
                    this.toBaseUnit(),
                    other.toBaseUnit()
            ) == 0;

        }



        @Override
        public String toString() {

            return value + " " + unit;

        }
    }



    // UC5 Conversion API

    public static double convert( double value, LengthUnit source,LengthUnit target) {

        if (!Double.isFinite(value))

            throw new IllegalArgumentException("Invalid value");

        if (source == null || target == null)

            throw new IllegalArgumentException("Unit cannot be null");



        double baseValue = value * source.getConversionFactor();

        return baseValue / target.getConversionFactor();

    }



    // Demo methods


    public static void demonstrateLengthConversion( double value, LengthUnit from,LengthUnit to ) {

        double result = convert(value, from, to);

        System.out.println(value + " " + from +

                " = " + result + " " + to);

    }



    public static void main(String[] args) {


        demonstrateLengthConversion(  1.0, LengthUnit.FEET, LengthUnit.INCHES );


        demonstrateLengthConversion( 1.0, LengthUnit.YARDS, LengthUnit.FEET);


        demonstrateLengthConversion(  2.54,  LengthUnit.CENTIMETERS, LengthUnit.INCHES);

    }

}