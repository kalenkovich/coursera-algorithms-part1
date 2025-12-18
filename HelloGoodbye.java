/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

public class HelloGoodbye {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Usage: java HelloGoodbye <num1> <num2>");
            return;
        }

        String arg1 = args[0];
        String arg2 = args[1];

        System.out.println("Hello " + arg1 + " and " + arg2 + ".");
        System.out.println("Goodbye " + arg2 + " and " + arg1 + ".");

    }
}
