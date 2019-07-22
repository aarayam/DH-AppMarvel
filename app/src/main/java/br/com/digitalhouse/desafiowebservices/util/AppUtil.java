package br.com.digitalhouse.desafiowebservices.util;

import android.app.Activity;
import android.os.Build;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.transition.Explode;
import android.view.Window;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AppUtil {
    private static char[] HEXCHARS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    private static String hexEncode(byte[] bytes) {
        char[] result = new char[bytes.length * 2];
        int b;
        for (int i = 0, j = 0; i < bytes.length; i++) {
            b = bytes[i] & 0xff;
            result[j++] = HEXCHARS[b >> 4];
            result[j++] = HEXCHARS[b & 0xf];
        }
        return new String(result);
    }


    /**
     * Cria um hash MD5 par enviarmos a API da marvel
     * */
    public static String md5(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());

            return hexEncode(digest.digest());

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void setAnimation(Activity activity) {
        activity.getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        activity.getWindow().setEnterTransition(new Explode());
    }

    @SuppressWarnings("deprecation")
    public static Spanned fromHtml(String html){
        if(html == null){
            // return an empty spannable if the html is null
            return new SpannableString("");
        }else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // FROM_HTML_MODE_LEGACY is the behaviour that was used for versions below android N
            // we are using this flag to give a consistent behaviour
            return Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        } else {
            return Html.fromHtml(html);
        }
    }
}
