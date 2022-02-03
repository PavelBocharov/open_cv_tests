package com.mar.webcam;

import lombok.extern.slf4j.Slf4j;
import org.bytedeco.javacv.*;
import org.bytedeco.opencv.opencv_core.Mat;

import java.util.Calendar;

import static org.bytedeco.opencv.global.opencv_imgcodecs.imwrite;

@Slf4j
public class TestWebCam {

    public static void getCamVideo(String filePath, int deviceNumber, int second) {
        try (VideoInputFrameGrabber videoGrabber = VideoInputFrameGrabber.createDefault(deviceNumber)) {
            log.debug("getCamVideo [START] -> filePath: {}, deviceNumber: {}, second: {}", filePath, deviceNumber, second);
            videoGrabber.start();

            try (FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(
                    filePath,
                    videoGrabber.getImageWidth(),
                    videoGrabber.getImageHeight(),
                    videoGrabber.getAudioChannels()
            )) {

                recorder.start();

                int timer = second + 1;
                Calendar endTime = Calendar.getInstance();
                endTime.add(Calendar.SECOND, second);
                long end = endTime.getTime().getTime();
                while (Calendar.getInstance().getTime().getTime() <= end) {
                    int newSecond = (int) ((end - Calendar.getInstance().getTime().getTime()) / 1000);
                    if (timer > newSecond) {
                        timer = newSecond;
                    }
                    recorder.record(videoGrabber.grabFrame());
                }
            } catch (Exception e) {
                throw e;
            }
        } catch (Exception e) {
            log.debug("getCamVideo [FAIL] -> filePath: {}, deviceNumber: {}, second: {}", filePath, deviceNumber,
                    second);
            e.printStackTrace();
        }
        log.debug("getCamVideo [SUCCESS] -> filePath: {}, deviceNumber: {}, second: {}", filePath, deviceNumber,
                second);
    }

    public static void getCamPhoto(String fullPath, int camNumber) throws FrameGrabber.Exception {
        log.debug("getCamPhoto [START] -> cam number: {}, photoPath: {}.", camNumber, fullPath);
        try (OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(camNumber)) {
            grabber.start();
            Frame frame = grabber.grab();

            OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();
            Mat camImage = converter.convertToMat(frame);
            imwrite(fullPath, camImage);
        } catch (FrameGrabber.Exception e) {
            log.error("getCamPhoto [FAIL] -> cam number: {}. Error msg: {} ", camNumber, e.getLocalizedMessage());
            throw e;
        }
        log.debug("getCamPhoto [SUCCESS] -> cam number: {}.", camNumber);
    }

}
