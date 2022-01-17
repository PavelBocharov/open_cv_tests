package com.mar.utils;

import com.mar.Application;
import lombok.experimental.UtilityClass;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.util.UUID;

@UtilityClass
public class Utils {

    public static boolean not(boolean flag) {
        return !flag;
    }

    public static boolean not(Boolean flag) {
        return !Boolean.TRUE.equals(flag);
    }

    public static String getShortUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static URI getURIFromResources(final String resourcePath) throws FileNotFoundException, URISyntaxException {
        URL fileUrl = Utils.class.getClassLoader().getResource(resourcePath);
        if (fileUrl == null) {
            throw new FileNotFoundException("File not found in resources: " + resourcePath);
        }
        return fileUrl.toURI();
    }

    public static String getAbsPathFromResources(final String resourcePath) throws FileNotFoundException, URISyntaxException {
        return getFileFromResources(resourcePath).getAbsolutePath();
    }

    public static File getFileFromResources(final String resourcePath) throws FileNotFoundException, URISyntaxException {
        File file = new File(getURIFromResources(resourcePath));
        if (not(file.exists())) {
            throw new FileNotFoundException("File not found in resources: " + resourcePath);
        }
        FilenameUtils.getExtension(file.getName());
        return file;
    }
}
