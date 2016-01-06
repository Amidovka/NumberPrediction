package com.bp.prediction.model;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class SimpleMovingAverageTest {

    private SimpleMovingAverage model;
    private int length;

    @Before
    public void setup(){
        model = new SimpleMovingAverage(new double[]{1, 2, 3, 4, 5, 6, 7, 8, 9}, 3);
        length = model.getYData().length;
    }

    @Test
    public void testGetNextPrediction() throws Exception {
        assertThat(model.getNextPrediction(), is(8.0));
    }

    @Test
    public void testUpdate() throws Exception {
        model.update(10.0, null);
        assertThat(model.getYData().length, is(length + 1));
        assertThat(model.getNextPrediction(), is(9.0));
    }

    @Test
    public void testCalculatePredictions() {
        int predictionStart = 7;
        model.calculatePredictions(predictionStart);
        assertThat(model.getPredictions()[0], is(6.0));
        assertThat(model.getPredictions()[1], is(7.0));
    }
}