import java.util.Arrays;

public class FastCollinearPoints {
    private int numberOfSegments = 0;
    private LineSegment[] lineSegments;

    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("points cannot be null");
        }

        Point[] aux = new Point[points.length];
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException("point cannot be null");
            }
            aux[i] = points[i];
        }

        double[] slopes = new double[points.length];
        lineSegments = new LineSegment[points.length];

        for (int i = 0; i < points.length; i++) {
            Arrays.sort(aux, aux[i].slopeOrder());
            for (int j = 1; j < points.length; j++) {
                slopes[j] = aux[0].slopeTo(aux[j]);
            }
            for (int j = 1; j < points.length - 2; j++) {
                if (slopes[j] == slopes[j + 1] && slopes[j] == slopes[j + 2]) {
                    numberOfSegments++;
                    lineSegments[numberOfSegments - 1] = new LineSegment(aux[0], aux[j + 2]);
                }
            }
        }

        lineSegments = Arrays.copyOf(lineSegments, numberOfSegments);
    }     // finds all line segments containing 4 or more points


    public int numberOfSegments() {
        return numberOfSegments;
    }        // the number of line segments


    public LineSegment[] segments() {
        return lineSegments;
    }           // the line segments

    public static void main(String[] args) {
        Point[] points = new Point[16];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                points[i * 4 + j] = new Point(i, j);
            }
        }

        FastCollinearPoints fcp = new FastCollinearPoints(points);
        System.out.println("Number of segments: " + fcp.numberOfSegments());
        for (LineSegment segment : fcp.segments()) {
            System.out.println(segment);
        }

    }
}