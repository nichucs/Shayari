package cs.nizam.shayari.utils;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by nizamcs on 26/6/16.
 */
public class FileUtils {

    /**
     *
     * @param context
     * @param filename file with json content. Extension .json
     * @return
     */
    public static String loadJSONFromAsset(Context context, String filename) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
