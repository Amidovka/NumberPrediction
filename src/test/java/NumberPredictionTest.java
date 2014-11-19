import org.junit.Test;
import sun.misc.Compare;

import java.io.IOException;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created by amid on 23.03.14.
 */
public class NumberPredictionTest {

    @Test
    public void testAscend(){
        assertThat(NumberPrediction.predictNextDouble(new double[]{1, 2, 3, 4, 5}), is(equalTo(6.0)));
    }

    @Test
    public void testJumps(){
        assertThat(NumberPrediction.predictNextDouble(new double[]{1, 2, 3, 1, 2, 3, 1}), is(equalTo(2.0)));
    }

    @Test
    public void testJumpsInverse(){
        assertThat(NumberPrediction.predictNextDouble(new double[]{3, 2, 1, 3, 2, 1, 3}), is(equalTo(2.0)));
    }

    //time series has period of 3 numbers
    //every period each number is increased by 1
    @Test
    public void testJumpsIncrease(){
        assertThat(NumberPrediction.predictNextDouble(new double[]{1, 2, 3, 2, 3, 4}), is(equalTo(3.0)));
    }

    //time series has period of 3 numbers
    //every period each number is multiplied by 1.5
    @Test
    public void testJumpsIncrease1(){
        assertThat(NumberPrediction.predictNextDouble(new double[]{2, 3, 4, 3, 4.5, 6, 4.5}), is(equalTo(6.75)));
    }

    /**
     * Tests if the difference in actual and predicted value is greater than 2000.
     * Value index to test is 100.
     * "Window" matrix size is 25.
     * X parameters to analyse have indexes 1, 3, 5.
     *
     * @throws IOException
     */
    @Test
    public void testOptions() throws  IOException{
        assertEquals(3500.0, NumberPrediction.predictWithOptions(25, new int[]{1, 3, 5})[100], 2000.0);
    }

    @Test
    public void testRSquared() throws IOException {
        assertTrue(ComparePredictions.bestR2() <= 1 & ComparePredictions.bestR2() >= 0);
    }
}