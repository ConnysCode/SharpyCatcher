package tools;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.security.MessageDigest;

public class HashStuff {

    public static String getMD5(String url) throws Exception {
        URL u = new URL(url);

        BufferedImage buffImg = ImageIO.read(u);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(buffImg, "png", outputStream);
        byte[] data = outputStream.toByteArray();

        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(data);
        byte[] hash = md.digest();
        return returnHex(hash).replaceFirst("null", "");
    }

    static String returnHex(byte[] inBytes) throws Exception {
        String hexString = null;
        for (int i=0; i < inBytes.length; i++) {
            hexString +=
                    Integer.toString( ( inBytes[i] & 0xff ) + 0x100, 16).substring( 1 );
        }
        return hexString;
    }

}
