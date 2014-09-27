package cuenation.api.util;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ETag {

    public static String create(String data) throws Exception {
        String eTag = "";

        try {
            byte[] bytes = MessageDigest.getInstance("MD5").digest(data.getBytes("UTF-8"));
            BigInteger number = new BigInteger(1, bytes);
            eTag = number.toString(16);

            return eTag;
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            throw new Exception(e.getMessage(), e);
        }
    }

    public static class Exception extends java.lang.Exception {

        public Exception(String message, Throwable cause) {
            super(message, cause);
        }
    }

}
