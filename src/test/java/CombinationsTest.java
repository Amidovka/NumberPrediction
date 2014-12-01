import org.junit.Test;
import static java.lang.Math.pow;
import static org.junit.Assert.*;

import java.io.IOException;

/**
 * Created by amid on 20.11.14.
 */
public class CombinationsTest {

    /**
     * tests the number of combinations
     * by expression 2^n - 1
     *
     * @throws IOException
     */
    @Test
    public void testPermutation() throws IOException {
        int numOfCol = RSquaredStatistics.Data().get(0).length; // number of x parameters and y parameter together
        int n = numOfCol - 1; // number of x parameters

        assertTrue((pow(2, n) - 1) == Combinations.getCombinations().size());
    }
}
