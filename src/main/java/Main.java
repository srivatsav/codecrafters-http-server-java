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
       ResponseHandler responseHandler = new ResponseHandler();

       if(parsedRequest.getEndpoint().equals("/"))
         responseHandler.handleOK(clientSocket);
       else if(parsedRequest.getEndpoint().startsWith("/echo"))
         responseHandler.handleEcho(clientSocket, parsedRequest);
       else
         responseHandler.handle404(clientSocket);

     } catch (IOException e) {
       System.out.println("IOException: " + e.getMessage());
     }
  }

  private static HttpRequest handleRequest(Socket clientSocket) throws IOException{
    HttpRequest parser = new HttpRequest(clientSocket.getInputStream());
    return parser.parse();
  }
}
