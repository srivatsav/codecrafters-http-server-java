package enums;

public enum ContentType {
  PLAIN_TEXT("text/plain"),
  HTML("text/html"),
  JSON("application/json");

  private final String type;

  ContentType(String type) {
    this.type = type;
  }

  public String getType() {
    return type;
  }
}
