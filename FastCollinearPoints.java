import java.util.Arrays;

public class FastCollinearPoints {
    private int numberOfSegments = 0;
    private LineSegment[] lineSegments;

    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("points cannot be null");
        }

        Point[] aux = new Point[points.length];
        double[] slopes = new double[points.length];
        int n = points.length;
        int size = n * (n - 1) / 2 * 10; // let's hope this is enough
        // how I got this number: each segment is represented by 2 points, hence
        // n * (n - 1) / 2 possible segments. However, there will be duplicates
        // due to the slope not knowing direction. We'll assume that they end
        // up being not more than 10 times.
        lineSegments = new LineSegment[size];

        for (int i = 0; i < points.length; i++) {
            for (int j = 0; j < points.length; j++) {
                if (i == j) {
                    aux[i] = points[0];
                    aux[0] = points[i];
                }
                else {
                    aux[j] = points[j];
                }
            }

            // sort by values first, so that runs with the same slope are sorted (sort is stable for objects)
            Arrays.sort(aux, 1, aux.length);
            Arrays.sort(aux, aux[0].slopeOrder());
            for (int j = 1; j < points.length; j++) {
                slopes[j] = aux[0].slopeTo(aux[j]);
            }
            for (int j = 1; j < points.length - 2; ) {
                if (slopes[j] == slopes[j + 1]) {
                    int k;
                    for (k = j + 1; k < points.length - 1; k++) {
                        if (slopes[j] != slopes[k + 1]) {
                            break;
                        }
                    }
                    if (k > j + 1) {  // at least three identical slopes
                        Point min = min(aux[0], aux[j]);
                        Point max = max(aux[0], aux[k]);
                        numberOfSegments++;
                        lineSegments[numberOfSegments - 1] = new LineSegment(min, max);
                        j = k + 1;
                        continue;
                    }
                }
                j++;
            }
        }

        // remove duplicates
        Arrays.sort(lineSegments, (ls1, ls2) -> {
            if (ls1 == null && ls2 == null) {
                return 0;
            }
            if (ls1 == null) {
                return 1;
            }
            if (ls2 == null) {
                return -1;
            }
            return ls1.toString().compareTo(ls2.toString());
        });

        int i = 1;
        int originalNumberOfSegments = numberOfSegments;
        for (int j = 1; j < originalNumberOfSegments; j++) {
            if (lineSegments[j].toString().equals(
                    lineSegments[j - 1].toString())) {
                numberOfSegments--;
            }
            else {
                lineSegments[i] = lineSegments[j];
                i++;
            }
        }

        // drop nulls and duplicates
        lineSegments = Arrays.copyOf(lineSegments, numberOfSegments);


    }  // finds all line segments containing 4 or more points

    private static Point min(Point p1, Point p2) {
        Point min = p1;
        if (p2.compareTo(min) < 0) {
            min = p2;
        }
        return min;
    }

    private static Point max(Point p1, Point p2) {
        Point max = p1;
        if (p2.compareTo(max) > 0) {
            max = p2;
        }
        return max;
    }


    public int numberOfSegments() {
        return numberOfSegments;
    }        // the number of line segments


    public LineSegment[] segments() {
        return Arrays.copyOf(lineSegments, lineSegments.length);
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