import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

/**
 * Created by merlin on 17/2/1.
 */
public class BruteCollinearPoints {
    private Point[] points;
    private  BruteLineSegments lineSegments;
    private LineSegment[] segments;
    private int numOfLine;

    private class BruteLineSegments {
        LineSegment[] segments;
        int num;
        public BruteLineSegments() {
            segments = new LineSegment[1];
            num = 0;
        }

        public void add(LineSegment line) {
            if (segments.length > num)
                segments[num++] = line;
            else
            {
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

    private void getLines(){
        int size = points.length;
        for (int i = 0; i < size; i++) {
            double[] slopes = new double[size];
            for (int j = 0; j < size; j++) {
                slopes[j] = points[i].slopeTo(points[j]);
            }
            for (int j=i+1; j < size; j++) {
                double temp = slopes[j];
                int count = 0;
                int end = j;
                for (int k=j+1; k < size; k++) {
                    if (slopes[k] == temp) {
                        count++;
                        end = k;
                    }
                }
                if (count >= 2)
                    lineSegments.add(new LineSegment(points[i],points[end]));
            }
        }
        this.numOfLine = lineSegments.num;
        this.segments = new LineSegment[numOfLine];
        for (int i = 0; i < numOfLine; i++) {
            segments[i] = lineSegments.segments[i];
        }
        this.lineSegments = null;
    }

    public BruteCollinearPoints(Point[] points) {
        lineSegments = new BruteLineSegments();
        this.points = points;
        this.sort(points);
        getLines();
    }

    private void merge(Point[] a,Point[] aux,int lo,int mid,int hi) {
        for (int i = 0; i <= hi; i++) {
            aux[i] = a[i];
        }
        int i = lo,j = mid+1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid) a[k] = aux[j++];
            else if (j > hi) a[k] = aux[i++];
            else if (aux[i].compareTo(aux[j]) < 0) a[k] = aux[i++];
            else if (aux[i].compareTo(aux[j]) > 0) a[k] = aux[j++];
            else if (aux[i].compareTo(aux[j]) == 0) throw new IllegalArgumentException();
        }
    }

    private void sort(Point[] a,Point[] aux,int lo,int hi) {
        if (hi <= lo) return;
        int mid = lo + (hi - lo)/2;
        sort(a,aux,lo,mid);
        sort(a,aux,mid+1,hi);
        merge(a,aux,lo,mid,hi);
    }

    private void sort(Point[] a) {
        Point[] aux = new Point[a.length];
        sort(a,aux,0,a.length - 1);
    }

    public int numberOfSegments() {
        return numOfLine;
    }

    public LineSegment[] segments() {
        return this.segments;
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
