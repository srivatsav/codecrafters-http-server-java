package request;

import enums.HttpHeaders;
import response.ResponseHeaderLine;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class HttpRequest {
    private BufferedReader bufferedReader;
    private RequestLine requestLine;
    private List<RequestHeaderLine> requestHeaderLines =  new ArrayList<>();

    private RequestBodyLine requestBodyLine;

  public HttpRequest(InputStream socketInputStream) {
        this.bufferedReader = new BufferedReader(new InputStreamReader(socketInputStream));
    }
    public HttpRequest parse() throws IOException {
      // read line by line from the bufferedReader
      try {
        //first line is requestLine
        String requestLine = bufferedReader.readLine();
        String [] requestLineSplit = requestLine.split("\\s+");
        this.requestLine = new RequestLine.RequestLineBuilder()
            .httpMethod(requestLineSplit[0])
            .requestTarget(requestLineSplit[1])
            .httpVersion(requestLineSplit[2])
            .build();

        // read header lines
        String line;
        while ((line = bufferedReader.readLine()) != null) {
          if (!line.isEmpty()) {
            String [] lineSplit = line.split("\\s+");
            RequestHeaderLine responseHeaderLine = new RequestHeaderLine.HeaderLineBuilder()
                .headerName(lineSplit[0])
                .headerValue(lineSplit[1])
                .build();
            this.requestHeaderLines.add(responseHeaderLine);
          } else {
            break;
          }
        }
        // read the body based on the content length header
        // if content length is not present, then read till the end of the stream
        String contentLength = (String) this.requestHeaderLines.stream()
            .filter(responseHeaderLine1 -> responseHeaderLine1.getHeaderName().equals(HttpHeaders.CONTENT_LENGTH.getHeader()))
            .findFirst()
            .map(responseHeaderLine1 -> responseHeaderLine1.getHeaderValue())
            .orElse("Unknown");

          if(contentLength.equals("Unknown")){
            StringBuilder body = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
              body.append(line);
            }
            this.requestBodyLine = new RequestBodyLine(body);
          } else {
            int length = Integer.parseInt(contentLength);
            char[] body = new char[length];
            bufferedReader.read(body, 0, length);
            this.requestBodyLine = new RequestBodyLine(body);
          }

        } catch (Exception e){
          System.out.println("Error parsing request: " + e.getMessage());
        }
        return this;
    }

  public RequestLine getRequestLine() {
    return requestLine;
  }

  public List<RequestHeaderLine> getHeaderLines() {
    return requestHeaderLines;
  }

  public RequestBodyLine getRequestBodyLine() {
    return requestBodyLine;
  }
}
