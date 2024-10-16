import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Logger;

public class HttpURLConnectionPost extends Thread {
    private final String address;
    private UserData userData;
    private boolean stopConnect = false;
    private static Logger logger = Logger.getLogger(HttpURLConnectionPost.class.getName());

    HttpURLConnectionPost(String address) {
        this.address = address;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }
    void setStopConnect() {
        this.stopConnect = true;
    }

    @Override
    public void run() {
        try {
            while (!stopConnect) {
                URL url = new URL(address);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("User-Agent", "Mozilla/5.0");
                connection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

                String name = userData.getName();
                String phone = userData.getPhone();

                String urlParameters = "name=" + name + "&phone=" + phone;

                connection.setDoOutput(true);
                DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
                wr.writeBytes(urlParameters);
                wr.flush();
                wr.close();

                int responseCode = connection.getResponseCode();
                logger.info("\nSending 'POST' request to URL : " + url);
                logger.info("Post parameters : " + urlParameters);


                Thread.sleep(15000);
            }

        } catch (IOException | InterruptedException e) {
            logger.info(e.toString());
            Thread.currentThread().interrupt();
        }

    }
}