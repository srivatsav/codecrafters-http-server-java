package request;

public record RequestBodyLine<T>(T body) {
  public T getBody() {
    return body;
  }
}
