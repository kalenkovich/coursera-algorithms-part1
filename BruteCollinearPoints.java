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
        double pq, pr, ps, qr, qs, rs;
        double nInf = Double.NEGATIVE_INFINITY;
        lineSegments = new LineSegment[points.length];

        for (int i = 0; i < points.length; i++) {
            p = pointsSorted[i];

            for (int j = i + 1; j < points.length; j++) {
                q = pointsSorted[j];
                pq = p.slopeTo(q);

                for (int k = j + 1; k < points.length; k++) {
                    r = pointsSorted[k];
                    pr = p.slopeTo(r);
                    qr = q.slopeTo(r);

                    for (int m = k + 1; m < points.length; m++) {
                        s = pointsSorted[m];
                        ps = p.slopeTo(s);
                        qs = q.slopeTo(s);
                        rs = r.slopeTo(s);

                        if (Double.compare(pq, nInf) == 0 ||
                                Double.compare(pr, nInf) == 0 ||
                                Double.compare(ps, nInf) == 0 ||
                                Double.compare(qr, nInf) == 0 ||
                                Double.compare(qs, nInf) == 0 ||
                                Double.compare(rs, nInf) == 0) {
                            throw new IllegalArgumentException("Duplicate points detected");
                        }

                        if (Double.compare(pq, pr) == 0 &&
                                Double.compare(pq, ps) == 0) {
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