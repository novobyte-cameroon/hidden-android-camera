package org.novobyte.blog.hiddencamera;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class PostRequestManager {

    public static final String ERROR_TIMEOUT = "2eczcbnewiaTIMEksndadOUT";
    public static final String ERROR_MALFORMED_URL = "13hbdfhasdMALFORMEDkfdlfURL";

/**
 * the complete thumbnail contains the parameters and the values
* */

    public static String getResponse(String stringUrl,String params){
        Log.i("pimp","requested url: "+stringUrl);
        try {
            URL url = new URL(stringUrl);
            HttpURLConnection urlConnection  = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setConnectTimeout(5000);
            urlConnection.connect();
            OutputStream os =  urlConnection.getOutputStream();
            os.write(params.getBytes());
            InputStream is = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String data = "";
            while (br.ready()||data==""){
                data+=br.readLine();
            }
            os.close();
            is.close();
            return data;
        } catch (MalformedURLException e) {
            // wrong thumbnail structure
            e.printStackTrace();
            return  ERROR_MALFORMED_URL;
        } catch (IOException e) {
            // cant connect to the internet
            e.printStackTrace();
            return  ERROR_TIMEOUT;
        }

    }


    public static  boolean checkIfErrorFromMessage(String msg){

        if(msg.equals(ERROR_MALFORMED_URL)||msg.equals(ERROR_TIMEOUT)){
            return true;
        }

        return  false;
    }

    public static String decodeErrorMessage(String encodedMessage){
        switch (encodedMessage){
            case ERROR_TIMEOUT: return  "Connection Timeout";
            case ERROR_MALFORMED_URL: return  "Malformed URL";
            default: return "null";
        }
    }
}
