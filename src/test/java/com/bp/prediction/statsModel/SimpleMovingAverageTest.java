package com.bp.prediction.statsModel;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class SimpleMovingAverageTest {

    private SimpleMovingAverage model;

    @Before
    public void setup(){
        model = new SimpleMovingAverage(new double[]{1, 2, 3, 4, 5}, 3);
    }

    @Test
    public void testGetNextPrediction() throws Exception {
        assertThat(model.getNextPrediction(), is(4.0));
    }

    @Test
    public void testUpdate() throws Exception {
        model.update(6.0, null);
        assertThat(model.getNextPrediction(), is(5.0));
    }
}