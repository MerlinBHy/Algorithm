import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * Created by merlin on 17/1/22.
 */
public class Permutation {

    public static void main(String[] args)
    {
        int K = Integer.parseInt(args[0]);
        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<String>();
        while (!StdIn.isEmpty())
        {
            String item = StdIn.readString();
            randomizedQueue.enqueue(item);
        }
        for (int i = 0; i < K; i++)
        {
            StdOut.println(randomizedQueue.dequeue());
        }
    }
}
