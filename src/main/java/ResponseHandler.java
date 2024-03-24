import static enums.ServerConstants.CONTENT_TYPE;
import static enums.ServerConstants.HTTP_VERSION_1_1;

import enums.ContentType;
import enums.HttpHeaders;
import enums.HttpStatus;
import java.io.IOException;
import java.net.Socket;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import response.HeaderLine;
import response.ResponseBodyLine;
import response.StatusLine;

public class ResponseHandler {

  public void handleOK(Socket clientSocket) throws IOException {
    var osStream = clientSocket.getOutputStream();
    HttpResponse response = new HttpResponse();
    StatusLine statusLine = response.getStatusLine(HTTP_VERSION_1_1, HttpStatus.OK);
    osStream.write(statusLine.getResponseLine().getBytes(StandardCharsets.UTF_8));
    osStream.flush();
    osStream.close();
  }

  public void handle404(Socket clientSocket) throws IOException {
    var osStream = clientSocket.getOutputStream();
    HttpResponse response = new HttpResponse();
    StatusLine statusLine = response.getStatusLine(HTTP_VERSION_1_1, HttpStatus.NOT_FOUND);
    osStream.write(statusLine.getResponseLine().getBytes(StandardCharsets.UTF_8));
    osStream.flush();
    osStream.close();
  }
  public static void handleEcho(Socket clientSocket, HttpRequest parsedRequest) throws IOException {
    var osStream = clientSocket.getOutputStream();

    HttpResponse response = new HttpResponse();

    StatusLine statusLine = response.getStatusLine(HTTP_VERSION_1_1, HttpStatus.OK);

    List<HeaderLine> headerLines = new ArrayList<>();
    HeaderLine headerLine = response.getHeaderLine(HttpHeaders.CONTENT_TYPE.getHeader(), ContentType.PLAIN_TEXT.getType());
    headerLines.add(headerLine);
    String echoString = parsedRequest.getEndpoint().substring(6);
    headerLine = response.getHeaderLine(HttpHeaders.CONTENT_LENGTH.getHeader(), echoString.length());
    headerLines.add(headerLine);

    ResponseBodyLine responseBodyLine = response.getResponseBodyLine(echoString);

    response.setStatusLine(statusLine);
    response.setHeaderLines(headerLines);
    response.setBody(responseBodyLine);

    clientSocket.getOutputStream().write(response.writeResponse().getBytes(StandardCharsets.UTF_8));

    osStream.flush();
    osStream.close();
  }
}
