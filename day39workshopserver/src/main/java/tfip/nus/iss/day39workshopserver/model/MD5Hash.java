package tfip.nus.iss.day39workshopserver.model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Hash {

    private String timeStamp;
    private String hash;

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public static MD5Hash getMD5Hash(String input) {
        String md5Hash = "";
        long unixTimeStamp = System.currentTimeMillis() / 1000L;
        String ts = String.valueOf(unixTimeStamp);
        MD5Hash md5 = new MD5Hash();
        md5.setTimeStamp(ts);
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest((ts + input).getBytes());

            // convert the byte array to a hexadecimal string
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }

            md5Hash = sb.toString(); // md5Hash = hashkey
            md5.setHash(md5Hash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return md5;
    }

}
