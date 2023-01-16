package main;

import models.Dot;
import models.Polygon;
import utils.IntegralSolver;

public class Main {

    public static void main(String[] args) {
        args = new String[]{"1000", "0", "8", "8", "0", "0", "-8", "-8", "0"};
        if (args.length < 7 || args.length % 2 == 0) {
            System.err.println("ERROR");
            System.err.println("FIRST NUMBER IS FINENESS");
            System.err.println("THE FOLLOWING NUMBERS IS COORDINATES: X1 Y1 X2 Y2...");
            System.err.println("AND THEY SHOULD BE IN CLOCKWISE FORMAT OR ANTI-CLOCKWISE FORMAT");
            throw new IllegalArgumentException("Input args are in wrong format!");
        }

        Long fineness = Long.parseLong(args[0]);
        Dot[] dots = new Dot[args.length/2];
        for (int i = 0, j = 1; i < args.length / 2 && j < args.length; i++, j+=2) {
            dots[i] = new Dot(Double.parseDouble(args[j]), Double.parseDouble(args[j + 1]));
    }
        IntegralSolver integralSolver = new IntegralSolver(fineness, new Polygon(dots));
        integralSolver.integrate();
    }
}
