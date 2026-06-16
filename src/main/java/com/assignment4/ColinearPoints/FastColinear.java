package com.assignment4.ColinearPoints;

import com.assignment4.Sorting.MergeInsertionSort;

import java.util.*;

public class FastColinear {
    final static int Chunk = 3;
    final static int Min = 0;

    private final Point[] points;
    private List<LineSegment> calculatedMaximalColinearLineSegments;
    private final MergeInsertionSort<LineData> sorter;

    private enum Computed {
        YES, NO
    }

    private Computed computed;

    public FastColinear(Point[] points) {
        this.points = points;
        computed = Computed.NO;
        sorter = new MergeInsertionSort<>();
    }

    public int numberOfSegments() {
        return getMaximalColinearLineSegments().size();
    }

    // Efficient force algorithm to find all maximal colinear line segments
    public List<LineSegment> getMaximalColinearLineSegments() {
        calculatedMaximalColinearLineSegments = new ArrayList<>();

        for (int i = 0; i < points.length; i++) {
            Point origin = points[i];
            LineData[] lineData = new LineData[points.length - 1];

            int index = 0;
            for (int j = 0; j < points.length; j++) {
                if (j == i) continue; // dont use the same point

                double slope    = origin.slopeTo(points[j]);
                lineData[index] = new LineData(points[j], slope);
                index += 1;
            }

            sorter.insertMergeSort(lineData);

            int start = 0;

            ArrayList<LineData> data = new ArrayList<>();

            while (start <= lineData.length) {
                boolean atEnd     = (start == lineData.length);
                boolean sameSlope = !atEnd && (data.isEmpty() || lineData[start].getSlope() == data.getFirst().getSlope()); // so we check at the chunk length or the slopes are equivalent

                if (sameSlope) {
                    data.add(lineData[start]);   // add when the slope condition is met
                    start++;
                } else {
                    // group is finished (slope changed, or insufficient data, ie not 3 in a row)
                    if (data.size() >= Chunk) {
                        recordMinMaxPoints(origin, data);
                    }

                    if (atEnd) break;
                    data = new ArrayList<>();    // reset the array for next chunk
                }
            }
        }


        computed = Computed.YES;
        return calculatedMaximalColinearLineSegments;
    }

    public void recordMinMaxPoints(Point origin, ArrayList<LineData> data) {
        Point min = origin, max = origin;

        for (LineData d : data) {
            Point p = d.getPoint();
            if (p.compareTo(min) < Min) min = p;
            if (p.compareTo(max) > Min) max = p;
        }

        if (min == origin) {
            calculatedMaximalColinearLineSegments.add(new LineSegment(min, max));
        }
    }

    public void showSegments() {
        //Make sure segments have been computed before drawing
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


