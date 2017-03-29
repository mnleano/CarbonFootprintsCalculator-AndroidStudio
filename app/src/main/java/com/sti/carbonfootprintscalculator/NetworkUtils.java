package com.sti.carbonfootprintscalculator;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by mykelneds on 16/01/2017.
 */

public class NetworkUtils {

    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    public static boolean isInternetAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork == null) return false;

        switch (activeNetwork.getType()) {
            case ConnectivityManager.TYPE_WIFI:
                if ((activeNetwork.getState() == NetworkInfo.State.CONNECTED ||
                        activeNetwork.getState() == NetworkInfo.State.CONNECTING))
//                      &&  isInternet())
                    return true;
                break;
            case ConnectivityManager.TYPE_MOBILE:
                if ((activeNetwork.getState() == NetworkInfo.State.CONNECTED ||
                        activeNetwork.getState() == NetworkInfo.State.CONNECTING))
//                      &&  isInternet())
                    return true;
                break;
            default:
                return false;
        }
        return false;
    }
}
