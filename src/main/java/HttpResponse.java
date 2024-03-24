import static enums.ServerConstants.CR_LF;

import enums.HttpStatus;
import java.util.List;
import response.HeaderLine;
import response.ResponseBodyLine;
import response.StatusLine;

public class HttpResponse {
  private String httpVersion;
  private String contentType;
  private String contentLength;
  private StatusLine statusLine;
  private List<HeaderLine> headerLines;
  private ResponseBodyLine body;
  public HttpResponse(){}
  public StatusLine getStatusLine(String httpVersion, HttpStatus httpStatus ) {
    StatusLine.StatusLineBuilder statusLineBuilder = new StatusLine.StatusLineBuilder();
    return statusLineBuilder
        .httpVersion(httpVersion)
        .status(HttpStatus.valueOf(httpStatus.name())).build();
  }
  public <T> HeaderLine getHeaderLine(String headerName, T headerValue) {
    HeaderLine.HeaderLineBuilder headerLineBuilder = new HeaderLine.HeaderLineBuilder();
    return headerLineBuilder
        .headerName(headerName)
        .headerValue(headerValue).build();
  }

  public <T> ResponseBodyLine getResponseBodyLine(T body) {
    ResponseBodyLine.ResponseBodyLineBuilder responseBodyLineBuilder = new ResponseBodyLine.ResponseBodyLineBuilder();
    return responseBodyLineBuilder
        .body(body).build();
  }
  public String writeResponse() {
    StringBuilder response = new StringBuilder();
    response.append(statusLine.getResponseLine());
    for (HeaderLine headerLine : headerLines) {
      response.append(headerLine.getResponseLine());
    }
    response.append(CR_LF);
    response.append(body.getResponseLine());
    return response.toString();
  }

  public void setStatusLine(StatusLine statusLine) {
    this.statusLine = statusLine;
  }

  public void setHeaderLines(List<HeaderLine> headerLines) {
    this.headerLines = headerLines;
  }

  public void setBody(ResponseBodyLine body) {
    this.body = body;
  }
}
