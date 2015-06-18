package praekelt.weblistingapp.Utils;

import android.content.Context;
import android.util.Log;

import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by altus on 2015/04/02.
 */
public class fileSystemUtils {

    public static void checkDirectory(String directory) {
        File dir = new File(directory);
        if(!dir.exists()) {
            dir.mkdir();
            Log.i("dirMade", dir.getAbsolutePath());
        }
    }

    public static void checkExternalDirectory(Context context, String subfolderName) {

        File dir = new File(context.getExternalFilesDir(null).toString() + "/" + subfolderName);
        if(!dir.exists()) {
            dir.mkdir();
            Log.i("dirMade", dir.getAbsolutePath());
        }
    }

    public static String fileMD5CheckSum(String filePath) throws IOException, NoSuchAlgorithmException {
        InputStream fis =  new FileInputStream(filePath);

        // TODO change to undefined length
        byte[] buffer = new byte[1024];
        MessageDigest complete = MessageDigest.getInstance("MD5");
        int numRead;

        do {
            numRead = fis.read(buffer);
            if (numRead > 0) {
                complete.update(buffer, 0, numRead);
            }
        } while (numRead != -1);

        fis.close();

        byte[] b = complete.digest();
        String result = "";

        for (int i=0; i < b.length; i++) {
            result += Integer.toString( ( b[i] & 0xff ) + 0x100, 16).substring( 1 );
        }
        Log.d("MD5Checksum", result);
        return result;
    }
}
