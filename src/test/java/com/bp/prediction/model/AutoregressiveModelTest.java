package com.bp.prediction.model;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class AutoregressiveModelTest {

    private AutoregressiveModel arModel;
    private int p;
    private int length;

    @Before
    public void setUp() throws Exception {
        p = 2;
        arModel = new AutoregressiveModel(new double[]{1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0, 11.0, 12.0, 13.0}, p);
        length = arModel.getYData().length;
    }

    @Test
    public void testEstimateARParams() throws Exception {
        double[] ARParams = arModel.estimateARParams();
        assertThat(ARParams.length, is(p+1));
    }

    @Test
    public void testGetNextPrediction() throws Exception {
        System.out.println(arModel.getNextPrediction());
        assertThat(arModel.getNextPrediction(), is(14.0));
    }

    @Test
    public void testUpdate() throws Exception {
        arModel.update(14.0, null);
        assertThat(arModel.getYData().length, is(length + 1));
        assertThat(arModel.getNextPrediction(), is(14.999999999999998));
    }

    @Test
    public void testCalculatePredictions() throws Exception {
        int predictionStart = 10;
        arModel.calculatePredictions(predictionStart);
        for (int i = 0; i < arModel.getYData().length - predictionStart; i++) {
            assertThat(arModel.getPredictions()[i], is(arModel.getYData()[predictionStart + i]));
        }
    }
}