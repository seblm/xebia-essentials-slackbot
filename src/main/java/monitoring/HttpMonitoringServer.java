package monitoring;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import static java.lang.Integer.parseInt;

public class HttpMonitoringServer {

    private HttpServer server;

    public void start() {
        try {
            server = HttpServer.create(new InetSocketAddress(parseInt(System.getenv("PORT"))), 0);
            server.createContext("/", httpExchange -> {
                String response = "everything is fine";
                httpExchange.sendResponseHeaders(200, response.length());
                OutputStream os = httpExchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            });
            server.setExecutor(null);
            server.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void stop() {
        server.stop(0);
    }

}
