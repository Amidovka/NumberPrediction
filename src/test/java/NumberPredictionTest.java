import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

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
        assertThat(NumberPrediction.predictNextDouble(new double[]{1, 2, 3, 1, 2, 3}), is(equalTo(1.0)));
    }

    @Test
    public void testJumpsInverse(){
        assertThat(NumberPrediction.predictNextDouble(new double[]{3, 2, 1, 3, 2, 1, 3}), is(equalTo(2.0)));
    }

    @Test
    public void testJumpsRising(){
        assertThat(NumberPrediction.predictNextDouble(new double[]{1, 2, 3, 2, 3, 4}), is(equalTo(3.0)));
    }

    /**
     * Tests if the difference in actual and predicted value is greater than 2000.
     * Value index to test is 100.
     * "Window" matrix size is 25.
     * X parameters to analyse have indexes 0, 1, 5.
     *
     * @throws IOException
     */
    @Test
    public void testOptions() throws  IOException{
        assertEquals(2842.0, NumberPrediction.predictWithOptions(25, new int[]{0, 1, 5})[100], 2000.0);
    }
}