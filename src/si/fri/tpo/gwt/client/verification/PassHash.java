package si.fri.tpo.gwt.client.verification;

import com.google.gwt.user.client.Window;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by nanorax on 05/04/14.
 */
public class PassHash {

    // Online MD5 generator:
    // http://www.phpbbhacks.com/md5.php
    public static String getMD5Password(String password) {
        String hashword = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(password.getBytes());
            BigInteger hash = new BigInteger(1, md5.digest());
            hashword = hash.toString(16);
        } catch (NoSuchAlgorithmException nsae) {
            Window.alert(nsae.getMessage());
        }
        return hashword;
    }
}
