import java.util.Arrays;

public class BruteCollinearPoints {
    private int numberOfSegments = 0;
    private LineSegment[] lineSegments;

    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("points cannot be null");
        }

        Point[] pointsSorted = new Point[points.length];
        Point point;
        for (int i = 0; i < points.length; i++) {
            point = points[i];
            if (point == null) {
                throw new IllegalArgumentException("point cannot be null");
            }
            pointsSorted[i] = point;
        }
        Arrays.sort(pointsSorted);

        Point p, q, r, s;
        Double pq, pr, ps;
        lineSegments = new LineSegment[points.length];

        for (int i = 0; i < points.length; i++) {
            p = pointsSorted[i];

            for (int j = i + 1; j < points.length; j++) {
                q = pointsSorted[j];
                pq = p.slopeTo(q);

                for (int k = j + 1; k < points.length; k++) {
                    r = pointsSorted[k];
                    pr = p.slopeTo(r);

                    for (int l = k + 1; l < points.length; l++) {
                        s = pointsSorted[l];
                        ps = p.slopeTo(s);

                        if (p == q || p == r || p == s ||
                                q == r || q == s || r == s) {
                            throw new IllegalArgumentException("Duplicate points detected");
                        }

                        if (pq.equals(pr) && pq.equals(ps)) {
                            numberOfSegments++;
                            LineSegment lineSegment = new LineSegment(p, s);
                            lineSegments[numberOfSegments - 1] = lineSegment;

                        }
                    }
                }
            }
        }

        lineSegments = Arrays.copyOf(lineSegments, numberOfSegments);

    }

    public int numberOfSegments() {
        return numberOfSegments;
    }

    public LineSegment[] segments() {
        return Arrays.copyOf(lineSegments, lineSegments.length);
    }

    public static void main(String[] args) {
        Point[] points = new Point[16];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                points[i * 4 + j] = new Point(i, j);
            }
        }

        BruteCollinearPoints bcp = new BruteCollinearPoints(points);
        System.out.println("Number of segments: " + bcp.numberOfSegments());
        for (LineSegment segment : bcp.segments()) {
            System.out.println(segment);
        }

    }

}