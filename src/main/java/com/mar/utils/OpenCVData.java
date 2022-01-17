package com.mar.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.opencv.core.Scalar;

@Data
@AllArgsConstructor
public class OpenCVData {

    private String haar;
    private Scalar color;
    private double scaleFactor;
    private int minNeighbors;

}
