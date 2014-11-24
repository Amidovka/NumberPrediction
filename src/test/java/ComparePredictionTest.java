import org.junit.Test;
import java.io.IOException;
import static org.junit.Assert.*;

/**
 * Created by amid on 23.11.14.
 */
public class ComparePredictionTest {

    /**
     * tests the value
     * of the best R-squared statistic
     *
     * @throws IOException
     */
    @Test
    public void testRSquared() throws IOException {
        assertTrue(ComparePredictions.bestR2() <= 1 & ComparePredictions.bestR2() >= 0);
    }
}
