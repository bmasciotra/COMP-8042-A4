package com.assignment4.ColinearPoints;

public class LineData implements Comparable<LineData> {

    private final Point p;
    private final double slope;

    public LineData(Point p, double slope) {
        this.p = p;
        this.slope = slope;
    }

    @Override
    public int compareTo(LineData o) {
        return Double.compare(this.slope, o.slope);
    }

    public Point getPoint() {
        return p;
    }

    public double getSlope() {
        return slope;
    }
}
