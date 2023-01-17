package utils;

import models.Dot;
import models.Polygon;

import java.util.List;

public class IntegralSolver {
    private Dot leftBottomDotBeam;
    private Dot rightUpperDotBeam;
    private Polygon polygon;
    private Long fineness;

    public IntegralSolver(Long fineness, Polygon polygon, Dot leftUpperDotBeam, Dot rightBottomDotBeam) {
        this.fineness = fineness;
        this.polygon = polygon;
        this.leftBottomDotBeam = leftUpperDotBeam;
        this.rightUpperDotBeam = rightBottomDotBeam;
    }

    private Double function(double x, double y) {
        return x * Math.sin(x*y);
    }
    private Double errorFunction(Dot center, long fineness) {
        Double x = center.getX();
        Double y = center.getY();
        Double a = polygon.getStartSmallDot(fineness, center).getX();
        Double b = polygon.getEndSmallDot(fineness, center).getX();
        Double c = polygon.getStartSmallDot(fineness, center).getY();
        Double d = polygon.getEndSmallDot(fineness, center).getY();
        if (a< leftBottomDotBeam.getX() || b> rightUpperDotBeam.getX() ||
                c< leftBottomDotBeam.getY() || d> rightUpperDotBeam.getY())
            return Math.abs(function(x,y)/2);
        return Math.abs((b-a)*(b-a)*y*(2*Math.cos(x*y)-x*y*Math.sin(x*y))-(d-c)*(d-c)/x/x/x*Math.sin(x*y));
    }
    public Double integrate() {
        Double error = 0D;
        Double answer = 0D;

        List<Dot> field = polygon.makeFieldInPolygon(fineness);

        Double dArea = polygon.getArea(fineness);

        for (Dot dot : field) {
            answer += function(dot.getX(), dot.getY()) * dArea;
            error += errorFunction(dot, fineness);
        }
        error = error/24*dArea;
        System.out.println("Answer: \n" + answer);
        System.out.println("Error: \n" + error);
        return answer;
    }


}
