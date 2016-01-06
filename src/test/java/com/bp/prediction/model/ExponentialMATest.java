package com.bp.prediction.model;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ExponentialMATest {

    private ExponentialMA model;
    private int length;

    @Before
    public void setUp() throws Exception {
        model = new ExponentialMA(new double[]{1, 2, 3, 4, 5}, 3);
        length = model.getYData().length;
    }

    @Test
    public void testGetNextPrediction() throws Exception {
        assertThat(model.getNextPrediction(), is(4.25));
    }

    @Test
    public void testUpdate() throws Exception {
        model.update(6.0, null);
        assertThat(model.getYData().length, is(length + 1));
        assertThat(model.getNextPrediction(), is(5.25));
    }

    @Test
    public void testCalculatePredictions() {
        int predictionStart = 3;
        model.calculatePredictions(predictionStart);
        assertThat(model.getPredictions()[0], is(2.25));
        assertThat(model.getPredictions()[1], is(3.25));
    }
}