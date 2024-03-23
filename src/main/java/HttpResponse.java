import enums.HttpStatus;

public class HttpResponse {
  private final HttpStatus httpStatus;

  public HttpResponse(HttpStatus status) {
    this.httpStatus = status;
  }
  public static final HttpResponse OK = new HttpResponse(HttpStatus.OK);
  public static final HttpResponse NOT_FOUND = new HttpResponse(HttpStatus.NOT_FOUND);


}
