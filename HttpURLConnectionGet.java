import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Logger;

public class HttpURLConnectionGet extends Thread {
    private String address;
    private boolean stopConnect = false;
    private static final Logger logger = Logger.getLogger(HttpURLConnectionGet.class.getName());

    HttpURLConnectionGet(String address) {
        this.address = address;
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

                connection.setRequestMethod("GET");
                connection.setRequestProperty("User-Agent", "Yandex");
                int responseCode = connection.getResponseCode();

                logger.info("\nSending 'GET' request to URL : " + url);


                // Открываем файл для чтения
                String path = "C:\\Users\\alex_\\OneDrive\\Рабочий стол\\Servlets\\records.txt";
                File file = new File(path);
                BufferedReader reader = new BufferedReader(new FileReader(file));

                String line;
                StringBuilder content = new StringBuilder();

                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }

                reader.close();

                String fileData = content.toString();
                logger.info("Data from file: \n" + fileData);

                Thread.sleep(5000);
            }
        } catch (IOException | InterruptedException e) {
            logger.severe("Error: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}