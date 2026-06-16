package com.assignment4.ColinearPoints;

import javax.sound.sampled.Line;
import java.util.*;

public class BruteForceColinear {
    private Point[] points;
    private List<LineSegment> calculatedMaximalColinearLineSegments;

    private enum Computed {
        YES, NO
    }

    private Computed computed;

    public BruteForceColinear(Point[] points) {
        this.points = points;
        computed = Computed.NO;
    }

    public int numberOfSegments() {
        return getMaximalColinearLineSegments().size();
    }

    // Brute force algorithm to find all maximal colinear line segments
    public List<LineSegment> getMaximalColinearLineSegments() {

        calculatedMaximalColinearLineSegments = new ArrayList<>();

        for (int i = 0; i < points.length; i++) {
            Point p = points[i];
            for (int j = i + 1; j < points.length; j++) {
                Point q = points[j];
                for (int k = j + 1; k < points.length; k++) {
                    Point r = points[k];
                    for (int m = k + 1; m < points.length; m++) {
                        Point s = points[m];

                        double pqSlope = p.slopeTo(q), qrSlope = q.slopeTo(r), rsSlope = r.slopeTo(s);

                        if (pqSlope == qrSlope && qrSlope == rsSlope) {
                            // Find the starting and end point of the line segment
                            Point minP = p, maxP = p;

                            for (Point pt : new Point[]{q, r, s}) {
                                if (pt.compareTo(minP) < 0) minP = pt;
                                if (pt.compareTo(maxP) > 0) maxP = pt;
                            }

                            LineSegment ps = new LineSegment(minP, maxP);
                            calculatedMaximalColinearLineSegments.add(ps);
                        }
                    }
                }
            }
        }


        computed = Computed.YES;
        return calculatedMaximalColinearLineSegments;
    }

    public void showSegments() {
        if (computed == Computed.NO) {
            getMaximalColinearLineSegments();
        }
        for (LineSegment segment : calculatedMaximalColinearLineSegments) {
            segment.draw();
        }
    }

    public Point maxPoint() {
        int maxX = points[0].getX();
        int maxY = points[0].getY();
        for (int i = 1; i < points.length; i++) {
            if (points[i].getX() > maxX) {
                maxX = points[i].getX();
            }
            if (points[i].getY() > maxY) {
                maxY = points[i].getY();
            }
        }
        return new Point(maxX, maxY);
    }
}
