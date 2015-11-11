package com.bp.test;

import com.bp.prediction.predictor.NumberPredictor;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Created by burdind on 1.11.2015.
 */
public class NumberPredictorTest {

    NumberPredictor predictor = new NumberPredictor();

    /**
     * Tests the number of combinations.
     * The number of all combinations without repetition
     * must be equal to (2^n - 1) where n - number of parameters
     */
    @Test
     public void testCreateCombinations() {
        int[] testParam = {1, 2, 3, 4, 5};
        predictor.createCombinations(testParam);
        List<int[]> combinations = predictor.getCombinations();
        assertEquals((Math.pow(2,testParam.length) - 1), combinations.size(), 0);
    }

    @Test
    public void testAscend(){
        assertThat(predictor.predictNextDouble(new double[]{1, 2, 3, 4, 5}), is(equalTo(6.0)));
    }

    @Test
    public void testJumps(){
        assertThat(predictor.predictNextDouble(new double[]{1, 2, 3, 1, 2, 3}), is(equalTo(1.0)));
    }

    @Test
    public void testJumpsInverse(){
        assertThat(predictor.predictNextDouble(new double[]{3, 2, 1, 3, 2, 1, 3}), is(equalTo(2.0)));
    }

    @Test
    public void testJumpsRising(){
        assertThat(predictor.predictNextDouble(new double[]{1, 2, 3, 2, 3, 4}), is(equalTo(3.0)));
    }
}