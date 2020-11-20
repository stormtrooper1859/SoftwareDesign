package main.apiIntegration;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.akirakozov.sd.refactoring.Main;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;

import static org.junit.Assert.*;

public class APIIntegrationTest {
    private static final String HOST_URL = "http://localhost:8081";
    private Thread serverThread;
    private String testingWorkingDirectory;

    @Before
    public void testBefore() throws InterruptedException {
        changeWorkingDirectoryToTesting();
        startUpServer();
    }

    @After
    public void testAfter() throws IOException {
        serverThread.interrupt();
        removeTestingWorkingDirectory();
    }

    @Test
    public void apiCorrectnessTest() {
        String response1 = makeRequest("add-product", "name=apple&price=300");
        assertEquals("OK", response1);

        String response2 = makeRequest("add-product", "name=pear&price=400");
        assertEquals("OK", response2);

        String response3 = makeRequest("get-products", null);
        assertEquals("<html><body>apple\t300</br>pear\t400</br></body></html>", response3);

        String response4 = makeRequest("add-product", "name=banana&price=200");
        assertEquals("OK", response4);

        String response5 = makeRequest("get-products", null);
        assertEquals("<html><body>apple\t300</br>pear\t400</br>banana\t200</br></body></html>", response5);

        String response6 = makeRequest("query", "command=count");
        assertEquals("<html><body>Number of products: 3</body></html>", response6);

        String response7 = makeRequest("query", "command=max");
        assertEquals("<html><body><h1>Product with max price: </h1>pear\t400</br></body></html>", response7);

        String response8 = makeRequest("query", "command=min");
        assertEquals("<html><body><h1>Product with min price: </h1>banana\t200</br></body></html>", response8);

        String response9 = makeRequest("query", "command=sum");
        assertEquals("<html><body>Summary price: 900</body></html>", response9);

        String response10 = makeRequest("query", "command=average");
        assertEquals("Unknown command: average", response10);
    }

    private String makeRequest(String method, String args) {
        try {
            String urlLink = HOST_URL + "/" + method + (args != null ? "?" + args : "");
            URL url = new URL(urlLink);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return response.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void changeWorkingDirectoryToTesting() {
        String currentWorkingDirectory = System.getProperty("user.dir");
        testingWorkingDirectory = currentWorkingDirectory + "\\testingDirectory";
        new File(testingWorkingDirectory).mkdirs();
        System.setProperty("user.dir", testingWorkingDirectory);
    }

    private void removeTestingWorkingDirectory() throws IOException {
        Files.walk(Paths.get(testingWorkingDirectory))
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .forEach(File::delete);
    }

    private void startUpServer() throws InterruptedException {
        serverThread = new Thread(() -> {
            try {
                Main.main(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        serverThread.start();
        Thread.sleep(10000);
    }
}
