
public class MyHttpURLConnection {
    public static void main(String[] args) throws Exception {
        String address = "http://localhost:8080/servlets/records";

        HttpURLConnectionGet httpURLConnectionGet = new HttpURLConnectionGet(address);
        httpURLConnectionGet.start();
        HttpURLConnectionPost httpURLConnectionPost = new HttpURLConnectionPost(address);
        httpURLConnectionPost.start();
        Thread.sleep(60000000);
        httpURLConnectionGet.setStopConnect();
        httpURLConnectionPost.setStopConnect();
    }

}