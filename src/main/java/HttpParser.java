import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class HttpParser {
    private static BufferedReader bufferedReader;
    private String httpMethod;
    private String endpoint;
    private String host;
    private Map  headers;

    public HttpParser(InputStream socketInputStream) {
        this.bufferedReader = new BufferedReader(new InputStreamReader(socketInputStream));
    }
    public void parseHttpRequest() throws IOException {

      // read line by line from the bufferedReader
      String line = bufferedReader.readLine();
      String [] lineSplit = line.split("\\s+");
      this.httpMethod = lineSplit[0];
      this.endpoint = lineSplit[1];
    }

    public String getHttpMethod() {
        return this.httpMethod;
    }

  public String getEndpoint() {
    return endpoint;
  }
}
