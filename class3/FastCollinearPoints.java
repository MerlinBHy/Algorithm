import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

/**
 * Created by merlin on 17/2/5.
 */
public class FastCollinearPoints {
    private LineSegment[] segments;
    private int numOfLine;
    private Point[] points;
    private class FastLineSegments {
        LineSegment[] segments;
        int num;
        
        public FastLineSegments() {
            segments = new LineSegment[1];
            num = 0;
        }
        
        public void add(LineSegment line) {
            if (segments.length > num)
                segments[num++] = line;
            else {
                segments = resize();
                segments[num++] = line;
            }
        }
        
        private LineSegment[] resize() {
            LineSegment[] temp = new LineSegment[segments.length * 2];
            for (int i = 0; i < segments.length; i++) {
                temp[i] = segments[i];
            }
            return temp;
        }
    }
    
    public FastCollinearPoints(Point[] fastPoints) {
        this.points = new Point[fastPoints.length];
        for (int i = 0; i < fastPoints.length; i++) {
            this.points[i] = fastPoints[i];
        }
        FastLineSegments lineSegments = new FastLineSegments();
        int size = points.length;
        Point[] temp = new Point[size];
        this.sort(points,null);
        for (int i = 0;i < size; i++) {
            temp[i] = points[i];
        }
        for (int i = 0; i < size; i++) {
            this.sort(points,temp[i].slopeOrder());
            for (int m = 0; m + 2 < size;) {
                int n = m + 2;
                if (temp[i].slopeTo(points[m]) == temp[i].slopeTo(points[n])) {
                    while (temp[i].slopeTo(points[m]) == temp[i].slopeTo(points[n])) {
                        if (n + 1 < size)
                            n++;
                        else
                            break;
                    }
                    double slope = temp[i].slopeTo(points[m]);
                    int start = 0, end = 0;
                    for (int k = 0; k < size; k++) {
                        if (temp[i].slopeTo(temp[k]) == slope) {
                            if (k > i)
                                start = i;
                            else
                                start = -1;
                            break;
                        }
                    }
                    for (int k = size - 1; k > 0; k--) {
                        if (temp[i].slopeTo(temp[k]) == slope) {
                            if (k <= i)
                                end = -1;
                            else
                                end = k;
                            break;
                        }
                    }
                    if (start  >= 0 && end >= 0) {
                        LineSegment lineSegment = new LineSegment(temp[start], temp[end]);
                        lineSegments.add(lineSegment);
                    }
                    m = n;
                }else
                    m++;
            }
        }
        this.numOfLine = lineSegments.num;
        this.segments = new LineSegment[numOfLine];
        for (int i = 0; i < numOfLine; i++) {
            segments[i] = lineSegments.segments[i];
        }
    }
    
    private void merge(Point[] a, Point[] aux, int lo, int mid, int hi, Comparator<Point> comparator) {
        for (int i = 0; i <= hi; i++) {
            aux[i] = a[i];
        }
        int i = lo,j = mid+1;
        if (comparator == null) {
            for (int k = lo; k <= hi; k++) {
                if (i > mid) a[k] = aux[j++];
                else if (j > hi) a[k] = aux[i++];
                else if (aux[i].compareTo(aux[j]) < 0) a[k] = aux[i++];
                else if (aux[i].compareTo(aux[j]) > 0) a[k] = aux[j++];
                else if (aux[i].compareTo(aux[j]) == 0) throw new IllegalArgumentException();
            }
        }else {
            for (int k = lo; k <= hi; k++) {
                if (i > mid) a[k] = aux[j++];
                else if (j > hi) a[k] = aux[i++];
                else if (comparator.compare(aux[i],aux[j]) < 0) a[k] = aux[i++];
                else if (comparator.compare(aux[i],aux[j]) > 0) a[k] = aux[j++];
                else if (comparator.compare(aux[i],aux[j]) == 0) {
                    if (aux[i].compareTo(aux[j]) < 0)
                        a[k] = aux[i++];
                    else if (aux[i].compareTo((aux[j])) > 0)
                        a[k] = aux[j++];
                    else
                        throw new IllegalArgumentException();
                }
            }
        }
    }
    
    private void sort(Point[] a,Point[] aux,int lo,int hi,Comparator<Point> comparator) {
        if (hi <= lo) return;
        int mid = lo + (hi - lo)/2;
        sort(a,aux,lo,mid,comparator);
        sort(a,aux,mid+1,hi,comparator);
        merge(a,aux,lo,mid,hi,comparator);
    }
    
    private void sort(Point[] a,Comparator<Point> comparator) {
        Point[] aux = new Point[a.length];
        sort(a,aux,0,a.length - 1,comparator);
    }
    
    public int numberOfSegments() {
        return numOfLine;
    }
    
    public LineSegment[] segments() {
        return segments;
    }
    
}
