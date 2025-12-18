/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {

        int i = 1;
        String winnner = "";
        while (!StdIn.isEmpty()) {
            String value = StdIn.readString();
            double p = (double) 1 / i;
            if (StdRandom.bernoulli(p)) {
                winnner = value;
            }
            i = i + 1;
        }

        System.out.println(winnner);

    }
}
