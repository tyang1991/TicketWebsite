package util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by pianobean on 4/13/15.
 */
public class TimeZoneConnection {
    private static final String mUrlBase = "http://api.geonames.org/timezone?";

    public static String getTimeZoneInfo(String condition) {
        URL url;
        HttpURLConnection connection;
        BufferedReader reader;
        String line;
        StringBuffer result = new StringBuffer();

        try {
            //open the HttpURLConnection by given url.
            url = new URL(mUrlBase + condition);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();

            //Read the xml if request succeeded.
            if ((responseCode >= 200) && (responseCode <= 299)) {
                InputStream inputStream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(inputStream));
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                reader.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }
}
