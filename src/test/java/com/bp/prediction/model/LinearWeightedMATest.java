package com.bp.prediction.model;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class LinearWeightedMATest {

    private LinearWeightedMA model;
    private int length;

    @Before
    public void setUp() throws Exception {
        model = new LinearWeightedMA(new double[]{1, 2, 3, 4, 5, 6}, 4);
        length = model.getYData().length;
    }

    @Test
    public void testGetNextPrediction() throws Exception {
        assertThat(model.getNextPrediction(), is(5.0));
    }

    @Test
    public void testUpdate() throws Exception {
        model.update(7, null);
        assertThat(model.getYData().length, is(length + 1));
        assertThat(model.getNextPrediction(), is(6.0));
    }
}