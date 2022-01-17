package com.mar;

import com.mar.utils.OpenCVData;
import nu.pattern.OpenCV;
import org.apache.commons.io.FilenameUtils;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.objdetect.Objdetect;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Stream;

import static com.mar.utils.Utils.*;

public class Application {

    public static final String absPath = "./src/main/resources/";

    public static final String imgResourcesPath = "art/";

    public static final String matResourcesPath = "mat/";

    public static void main(String[] args) throws URISyntaxException, IOException {
        System.out.println("Hello");

        OpenCV.loadLocally();

        File[] files = getFileFromResources(imgResourcesPath).listFiles();
        long dateLong = new Date().getTime();

        for (int i = 0; i < files.length; i++) {
            String filePhoto = imgResourcesPath + files[i].getName();
            Mat matrixPhoto = getMatFromImg(filePhoto);

//            int minFaceSize = Math.round(matrixPhoto.rows() * 0.05f);
            MatOfRect facesDetected = new MatOfRect();

            Stream.of(
                    new OpenCVData("haar/haarcascade_frontalface_alt.xml", new Scalar(0, 0, 255), 1.1, 2), // red
                    new OpenCVData("haar/haarcascade_frontalface_alt2.xml", new Scalar(0, 255, 0), 1.1, 5), // green
                    new OpenCVData("haar/haarcascade_frontalface_alt_tree.xml", new Scalar(255, 0, 0), 1.01, 2), //
                    // blue
                    new OpenCVData("haar/haarcascade_frontalface_default.xml", new Scalar(255, 0, 255), 1.1, 5) // black
            ).forEach(cvData -> {
                try {
                    CascadeClassifier cascadeClassifier = new CascadeClassifier();
                    cascadeClassifier.load(getAbsPathFromResources(cvData.getHaar()));
                    cascadeClassifier.detectMultiScale(
                            matrixPhoto,
                            facesDetected,
                            cvData.getScaleFactor(),
                            cvData.getMinNeighbors(),
                            Objdetect.CASCADE_SCALE_IMAGE
//                            ,
//                            new Size(minFaceSize, minFaceSize),
//                            new Size()
                    );

                    Rect[] facesArray = facesDetected.toArray();
                    if (facesArray.length != 0) {
                        for (Rect face : facesArray) {
                            Imgproc.rectangle(matrixPhoto, face.tl(), face.br(), cvData.getColor(), 3);
                        }
                        System.out.println(
                                String.format("[+]\t%s has %d faces. Haar: %s", FilenameUtils.getName(filePhoto),
                                        facesArray.length, cvData.getHaar())
                        );
                    } else {
                        System.out.println("[-]\t" + FilenameUtils.getName(filePhoto) + " hasn't faces. Haar: " + cvData.getHaar());
                    }

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            });

            saveMatToImage(matrixPhoto,
                    matResourcesPath + FilenameUtils.getBaseName(filePhoto) +
                            "_" + dateLong + "." + FilenameUtils.getExtension(filePhoto)
            );

        }

        System.out.println("End");
    }

    public static Mat getMatFromImg(String resourcePath) throws URISyntaxException, FileNotFoundException {
        return Imgcodecs.imread(getAbsPathFromResources(resourcePath));
    }

    public static void saveMatToImage(Mat imageMatrix, String targetPath) throws URISyntaxException {
        Imgcodecs.imwrite(absPath + targetPath, imageMatrix);
    }
}
