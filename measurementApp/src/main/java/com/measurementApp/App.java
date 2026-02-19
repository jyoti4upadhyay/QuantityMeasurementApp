package com.measurementApp;

public class App {


// UC1: Feet Class
public static class Feet {

    private final double value;

    // Constructor
    public Feet(double value) {
        this.value = value;
    }

    // equals method override
    @Override
    public boolean equals(Object obj) {

        // same reference
        if (this == obj)
            return true;

        // null check
        if (obj == null)
            return false;

        // class check
        if (getClass() != obj.getClass())
            return false;

        // cast
        Feet other = (Feet) obj;

        // compare values
        return Double.compare(this.value, other.value) == 0;
    }
}


// UC2: Inches Class
public static class Inches {

    private final double value;

    // Constructor
    public Inches(double value) {
        this.value = value;
    }

    // equals method override
    @Override
    public boolean equals(Object obj) {

        // same reference
        if (this == obj)
            return true;

        // null check
        if (obj == null)
            return false;

        // class check
        if (getClass() != obj.getClass())
            return false;

        // cast
        Inches other = (Inches) obj;

        // compare values
        return Double.compare(this.value, other.value) == 0;
    }
}


// UC2 Method: Feet Equality Demo
public static void demonstrateFeetEquality() {

    Feet f1 = new Feet(1.0);
    Feet f2 = new Feet(1.0);

    System.out.println("Feet Equal: " + f1.equals(f2));
}


// UC2 Method: Inches Equality Demo
public static void demonstrateInchesEquality() {

    Inches i1 = new Inches(1.0);
    Inches i2 = new Inches(1.0);

    System.out.println("Inches Equal: " + i1.equals(i2));
}


// Main Method
public static void main(String[] args) {

    demonstrateFeetEquality();

    demonstrateInchesEquality();
}


}