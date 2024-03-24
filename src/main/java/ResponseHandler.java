import static enums.ServerConstants.CR_LF;
import static enums.ServerConstants.HTTP_VERSION_1_1;

import enums.ContentType;
import enums.HttpHeaders;
import enums.HttpStatus;
import java.io.IOException;
import java.net.Socket;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import response.ResponseHeaderLine;
import request.HttpRequest;
import response.HttpResponse;
import response.ResponseBodyLine;
import response.StatusLine;

public class ResponseHandler {

  public void handleOK(Socket clientSocket) throws IOException {
    var osStream = clientSocket.getOutputStream();
    HttpResponse response = new HttpResponse();
    StatusLine statusLine = response.getStatusLine(HTTP_VERSION_1_1, HttpStatus.OK);
    osStream.write(statusLine.getResponseLine().concat(CR_LF).getBytes(StandardCharsets.UTF_8));
    osStream.flush();
  }

  public void handle404(Socket clientSocket) throws IOException {
    var osStream = clientSocket.getOutputStream();
    HttpResponse response = new HttpResponse();
    StatusLine statusLine = response.getStatusLine(HTTP_VERSION_1_1, HttpStatus.NOT_FOUND);
    osStream.write(statusLine.getResponseLine().concat(CR_LF).getBytes(StandardCharsets.UTF_8));
    osStream.flush();
  }
  public static void handleEcho(Socket clientSocket, HttpRequest parsedRequest) throws IOException {
    var osStream = clientSocket.getOutputStream();
    String echoString = parsedRequest.getRequestLine().getRequestTarget().substring(6);
    HttpResponse response = buildStatusHeadersAndPayload(echoString);
    osStream.write(response.writeResponse().getBytes(StandardCharsets.UTF_8));
    osStream.flush();
  }

  public static void handleUserAgent(Socket clientSocket, HttpRequest parsedRequest) throws IOException {
    var osStream = clientSocket.getOutputStream();

    String userAgent = (String) parsedRequest.getHeaderLines().stream()
        .filter(responseHeaderLine1 -> responseHeaderLine1.getHeaderName().equals(HttpHeaders.USER_AGENT.getHeader()))
        .findFirst()
        .map(responseHeaderLine1 -> responseHeaderLine1.getHeaderValue())
        .orElse("Unknown");

    HttpResponse response = buildStatusHeadersAndPayload(userAgent);

    osStream.write(response.writeResponse().getBytes(StandardCharsets.UTF_8));
    osStream.flush();
  }

  private static HttpResponse buildStatusHeadersAndPayload(String echoString) {
    HttpResponse response = new HttpResponse();

    StatusLine statusLine = response.getStatusLine(HTTP_VERSION_1_1, HttpStatus.OK);

    List<ResponseHeaderLine> responseHeaderLines = new ArrayList<>();
    ResponseHeaderLine responseHeaderLine = response.getHeaderLine(HttpHeaders.CONTENT_TYPE.getHeader(), ContentType.PLAIN_TEXT.getType());
    responseHeaderLines.add(responseHeaderLine);

    responseHeaderLine = response.getHeaderLine(HttpHeaders.CONTENT_LENGTH.getHeader(), echoString.length());
    responseHeaderLines.add(responseHeaderLine);

    ResponseBodyLine responseBodyLine = response.getResponseBodyLine(echoString);

    response.setStatusLine(statusLine);
    response.setHeaderLines(responseHeaderLines);
    response.setBody(responseBodyLine);
    return response;
  }
}
