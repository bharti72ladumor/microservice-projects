package com.bharti.common.paylod;

import com.bharti.common.utils.VariableUtils;import io.micrometer.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.SecureRandom;
import java.util.*;


public class MethodUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(VariableUtils.MethodUtils.class);

    public static <T> boolean isObjectisNullOrEmpty(T... t) {
        for (T ob : t) {
            if (ob == null || ob.toString().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public static <T> boolean isMapIsNullOrEmpty(Map<String, T> mapOfT) {
        return (mapOfT == null || mapOfT.isEmpty());
    }

    public static <T> boolean isSetIsNullOrEmpty(Set<T> setT) {
        return (setT == null || setT.isEmpty());
    }

    public static <T> boolean isListIsNullOrEmpty(List<T> listT) {
        return (listT == null || listT.isEmpty());
    }


    public static String generateRandomInteger(int length) {
        final String CHARACTERS = "0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            sb.append(randomChar);
        }
        return sb.toString();
    }

    public static String generateRandomStringOnlyAlphabet(int length) {
        final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            sb.append(randomChar);
        }
        return sb.toString();
    }


    public static String subNull(String str, String defaultStr) {
        return str != null && !StringUtils.isBlank(str) && !StringUtils.isEmpty(str)  ? str : defaultStr;
    }

    public static byte[] getImageFromStorage(String imageName, String imgDir) {
        Path imagePath = Path.of(imgDir, imageName);
        try {
            if (Files.exists(imagePath)) {
                return Files.readAllBytes(imagePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error("Error while getting image from storage : ", e);
        }
        return null;
    }
    public static <T> List<T> castList(Class<? extends T> clazz, Collection<?> rawCollection)
            throws ClassCastException {
        List<T> result = new ArrayList<>(rawCollection.size());
        for (Object o : rawCollection) {
            result.add(clazz.cast(o));
        }
        return result;
    }
    public static String removeSpaceAndQuotes(String val) {
        return val.trim().replace("'", "");
    }
}
