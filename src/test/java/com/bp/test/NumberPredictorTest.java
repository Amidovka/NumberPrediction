package com.bp.test;

import com.bp.prediction.predictor.NumberPredictor;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by burdind on 1.11.2015.
 */
public class NumberPredictorTest {

    /**
     * Tests the number of combinations.
     * The number of all combinations without repetition
     * must be equal to (2^n - 1) where n - number of parameters
     */
    @Test
    public void testCreateCombinations() {
        int[] testParam = {1, 2, 3, 4, 5};
        NumberPredictor predictor = new NumberPredictor();
        predictor.createCombinations(testParam);
        List<int[]> combinations = predictor.getCombinations();
        assertEquals((Math.pow(2,testParam.length) - 1), combinations.size(), 0);
    }
}