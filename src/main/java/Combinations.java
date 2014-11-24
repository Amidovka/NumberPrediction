import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by amid on 20.11.14.
 */
public class Combinations {

    static int[] a;
    static int k, n;
    static ArrayList<int[]> A = new ArrayList<int[]>();

    /**
     *
     * @return list of all combinations of x params
     *          without repetition
     *
     * @throws IOException
     */
    public static ArrayList<int[]> combinations() throws IOException {

        int numOfCol = ComparePredictions.Data().get(0).length; // number of x parameters and y parameter together
        n = numOfCol - 1; // number of x parameters

        //cycle for all possible permutations
        for (k = 1; k <= n; k++) {
            a = new int[k];
            permutation(0, 0);
        }

        //creating combinations list
        ArrayList<int[]> listOfComb = new ArrayList<int[]>();
        for (int i = 0; i < A.size(); i++) {
            listOfComb.add(A.get(i));
        }

        return listOfComb;
    }

    /**
     *
     * @param pos
     * @param maxUsed
     * @return Permutation of x parameters without repetition.
     *         Number of permutations = (2^n)-1
     * @throws IOException
     */
    public static ArrayList<int[]> permutation(int pos, int maxUsed) throws IOException {

        if (pos == k) {
            int[] b = new int[a.length];
            for (int i = 0; i < a.length; i++) {
                b[i] = a[i];
            }
            A.add(b);
        } else {
            for (int i = maxUsed + 1; i <= n; i++) {
                a[pos] = i;
                permutation(pos + 1, i);
            }
        }
        return A;
    }
}
