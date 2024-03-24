package enums;

public enum HttpHeaders {
  CONTENT_TYPE("Content-Type"),
  CONTENT_LENGTH("Content-Length"),
  CONNECTION("Connection"),
  DATE("Date"),
  SERVER("Server"),
  HOST("Host"),
  LOCATION("Location"),
  WWW_AUTHENTICATE("WWW-Authenticate"),
  AUTHORIZATION("Authorization"),
  SET_COOKIE("Set-Cookie"),
  COOKIE("Cookie"),
  UPGRADE("Upgrade"),
  SEC_WEBSOCKET_KEY("Sec-WebSocket-Key"),
  SEC_WEBSOCKET_VERSION("Sec-WebSocket-Version"),
  SEC_WEBSOCKET_ACCEPT("Sec-WebSocket-Accept"),
  SEC_WEBSOCKET_PROTOCOL("Sec-WebSocket-Protocol"),
  SEC_WEBSOCKET_EXTENSIONS("Sec-WebSocket-Extensions"),
  SEC_WEBSOCKET_PROTOCOL_CLIENT("Sec-WebSocket-Protocol-Client"),
  SEC_WEBSOCKET_PROTOCOL_SERVER("Sec-WebSocket-Protocol-Server"),
  SEC_WEBSOCKET_EXTENSIONS_CLIENT("Sec-WebSocket-Extensions-Client"),
  SEC_WEBSOCKET_EXTENSIONS_SERVER("Sec-WebSocket-Extensions-Server");

  private final String header;

  HttpHeaders(String header) {
    this.header = header;
  }

  public String getHeader() {
    return header;
  }
}
