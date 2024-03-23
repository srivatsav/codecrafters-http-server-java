import java.io.IOException;
import java.net.Socket;

import java.nio.charset.StandardCharsets;

public class ResponseHandler {

  public static void handle(Socket clientSocket, HttpResponse httpResponse) throws IOException {
    var osStream = clientSocket.getOutputStream();
    osStream.write(httpResponse.toString().getBytes(StandardCharsets.UTF_8));
    osStream.flush();
    osStream.close();
  }
  public static void handleEcho(Socket clientSocket, HttpResponse httpResponse) throws IOException {
    var osStream = clientSocket.getOutputStream();
    osStream.write(httpResponse.toString().getBytes(StandardCharsets.UTF_8));
    osStream.flush();
    osStream.close();
  }
}
