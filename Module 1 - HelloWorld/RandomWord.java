import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {
        String champion = "";
        for (int i = 1; !StdIn.isEmpty(); i++) {
            double pr = 1.0 / i;
            String word = StdIn.readString();
            if (StdRandom.bernoulli(pr)) {
                champion = word;
            }
        }
        System.out.println(champion);
    }
}
