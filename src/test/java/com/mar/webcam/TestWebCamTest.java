package com.mar.webcam;

import com.google.common.collect.ImmutableList;
import org.bytedeco.javacv.FrameGrabber;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static com.mar.webcam.TestWebCam.getCamPhoto;
import static com.mar.webcam.TestWebCam.getCamVideo;

class TestWebCamTest {

    @Test
    void getCamVideo_test() throws InterruptedException {
        // WebCam Video
        List<Runnable> webCamList = ImmutableList.of(
                () -> getCamVideo("./src/main/resources/video/cam_video_0.mp4", 0, 15),
                () -> getCamVideo("./src/main/resources/video/cam_video_1.mp4", 1, 15)
        );

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        webCamList.parallelStream().forEach(task -> executorService.execute(task));

        executorService.shutdown();
        executorService.awaitTermination(20, TimeUnit.SECONDS);
    }

    @Test
    void getCamPhoto_test() throws FrameGrabber.Exception {
        // WebCam Photo
        for (int i = 0; i < 2; i++) {
            getCamPhoto("./src/main/resources/art/cam_photo_" + i + "_" + new Date().getTime() + ".png", i);
        }
    }
}