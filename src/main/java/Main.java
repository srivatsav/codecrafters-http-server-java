import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
  public static void main(String[] args) {
    // You can use print statements as follows for debugging, they'll be visible when running tests.
    System.out.println("Logs from your program will appear here!");

    // Uncomment this block to pass the first stage
    //
     ServerSocket serverSocket = null;
     Socket clientSocket = null;

     try {
       serverSocket = new ServerSocket(4221);
       serverSocket.setReuseAddress(true);
       clientSocket = serverSocket.accept(); // Wait for connection from client.

       HttpRequest parsedRequest = handleRequest(clientSocket);

       if(parsedRequest.getEndpoint().equals("/"))
         clientSocket.getOutputStream().write("HTTP/1.1 200 OK\r\n\r\n".getBytes());
       else if(parsedRequest.getEndpoint().startsWith("/echo")) {
         String endpoint = parsedRequest.getEndpoint();
         String echoString = endpoint.substring(6);
         String contentTypeString = "Content-Type: text/plain\r\n";
         String contentLengthString =
             "Content-Length: " + echoString.length() + "\r\n";
         String httpResponse = "HTTP/1.1 200 OK\r\n" + contentTypeString +
             contentLengthString + "\r\n" + echoString + "\r\n";
         clientSocket.getOutputStream().write(httpResponse.getBytes());
       }
       else
          clientSocket.getOutputStream().write("HTTP/1.1 404 Not Found\r\n\r\n".getBytes());

       clientSocket.getOutputStream().flush();
     } catch (IOException e) {
       System.out.println("IOException: " + e.getMessage());
     }
  }

  private static HttpRequest handleRequest(Socket clientSocket) throws IOException{
    HttpRequest parser = new HttpRequest(clientSocket.getInputStream());
    return parser.parse();
  }
}
