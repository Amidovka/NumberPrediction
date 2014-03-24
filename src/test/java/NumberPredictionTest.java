import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
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
        assertThat(NumberPrediction.predictNextDouble(new double[]{1, 2, 3, 1, 2, 3}), is(equalTo(1.0)));
    }

}
