package models;

import java.util.ArrayList;
import java.util.List;

public class Polygon {
    private final Dot[] listOfVertices;

    private Double top = Double.MIN_VALUE;
    private Double bottom = Double.MAX_VALUE;
    private Double left = Double.MAX_VALUE;
    private Double right = Double.MIN_VALUE;

    public Polygon(Dot[] listOfVertices) {
        this.listOfVertices = listOfVertices;
        fillEdges();
    }

    private void fillEdges() {
        for (Dot dot : listOfVertices) {
            top = Math.max(top, dot.getY());
            right = Math.max(right, dot.getX());
            bottom = Math.min(bottom, dot.getY());
            left = Math.min(left, dot.getX());
        }
    }

    private int direction(Dot a, Dot b, Dot c)
    {
        double val = (b.getY() - a.getY()) * (c.getX() - b.getX())
                - (b.getX() - a.getX()) * (c.getY() - b.getY());

        if (val == 0)

            // Collinear
            return 0;

        else if (val < 0)

            // Anti-clockwise direction
            return 2;

        // Clockwise direction
        return 1;
    }

   private  boolean isIntersect(Line l1, Line l2)
    {
        // Four direction for two lines and points of other line
        int dir1 = direction(l1.getDot1(), l1.getDot2(), l2.getDot1());
        int dir2 = direction(l1.getDot1(), l1.getDot2(), l2.getDot2());
        int dir3 = direction(l2.getDot1(), l2.getDot2(), l1.getDot1());
        int dir4 = direction(l2.getDot1(), l2.getDot2(), l1.getDot2());

        // When intersecting
        if (dir1 != dir2 && dir3 != dir4)
            return true;

        // When p2 of line2 are on the line1
        if (dir1 == 0 && l1.dotOnLine(l2.getDot1()))
            return true;

        // When p1 of line2 are on the line1
        if (dir2 == 0 && l1.dotOnLine(l2.getDot2()))
            return true;

        // When p2 of line1 are on the line2
        if (dir3 == 0 && l2.dotOnLine(l1.getDot1()))
            return true;

        // When p1 of line1 are on the line2
        if (dir4 == 0 && l2.dotOnLine(l1.getDot2()))
            return true;

        return false;
    }

    public boolean checkInside(Dot dot)
    {
        // Create a point at infinity, y is same as point p
        Line exline = new Line(dot, new Dot( Double.MAX_VALUE, dot.getY()));
        int count = 0;
        int i = 0;
        do {

            // Forming a line from two consecutive points of
            // poly
            Line side = new Line(listOfVertices[i], listOfVertices[(i + 1) % listOfVertices.length]);
            if (isIntersect(side, exline)) {

                // If side is intersects exline
                if (direction(side.getDot1(), dot, side.getDot2()) == 0)
                    return side.dotOnLine(dot);
                count++;
            }
            i = (i + 1) % listOfVertices.length;
        } while (i != 0);

        // When count is odd
        return count % 2 == 1;
    }

    public List<Dot> makeFieldInPolygon(long fineness) {
        List<Dot> field = new ArrayList<>();
        Double x = left;
        Double y;
        Double stepX = (right-left)/fineness;
        Double stepY = (top-bottom)/fineness;
        while (x < right) {
            y = bottom;
            while (y < top) {
                Dot dot = new Dot(x + stepX/2, y + stepY/2);
                if (checkInside(dot))
                    field.add(dot);
                y += stepY;
            }
            x+=stepX;
        }
        return field;
    }

    public Double getArea(long fineness) {
        return (right-left)/fineness*(top-bottom)/fineness;
    }

    public Dot getStartSmallDot(long fineness, Dot center) {
        return new Dot(center.getX()-(right-left)/fineness/2, center.getY()-(top-bottom)/fineness/2);
    }
    public Dot getEndSmallDot(long fineness, Dot center) {
        return new Dot(center.getX()+(right-left)/fineness/2, center.getY()+(top-bottom)/fineness/2);
    }
}
