package com.mar;

import com.google.common.collect.ImmutableList;
import com.google.common.io.Files;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.javacv.*;
import org.bytedeco.opencv.opencv_core.IplImage;
import org.bytedeco.opencv.opencv_core.Mat;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

import static com.mar.utils.Utils.getFileFromResources;
import static com.mar.webcam.TestWebCam.getCamPhoto;
import static com.mar.webcam.TestWebCam.getCamVideo;
import static org.bytedeco.ffmpeg.global.avcodec.*;
import static org.bytedeco.ffmpeg.global.avutil.AV_PIX_FMT_GRAY8;
import static org.bytedeco.ffmpeg.global.avutil.AV_PIX_FMT_YUV420P;
import static org.bytedeco.opencv.global.opencv_imgcodecs.*;

@Slf4j
public class Application {

    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
        log.debug("Hello");

        // Read/Write image from file
        File photoClair = getFileFromResources("art/base_clair.jpg");
        Mat art = imread(photoClair.getAbsolutePath(), IMREAD_GRAYSCALE); // color to gray
        imwrite("./src/main/resources/art/new_gray.jpg", art);

        log.debug("End");
    }


}
