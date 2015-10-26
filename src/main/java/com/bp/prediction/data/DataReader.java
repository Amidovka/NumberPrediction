package com.bp.prediction.data;

import java.util.List;

/**
 * Created by burdind on 26.10.2015.
 */
public interface DataReader {

    List<String[]> parseData();
    void createMatrix();
}
