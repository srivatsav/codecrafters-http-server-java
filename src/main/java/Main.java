import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import request.HttpRequest;

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

       ExecutorService executorService = Executors.newFixedThreadPool(11);

       while(true) {
         clientSocket = serverSocket.accept(); // Wait for connection from client.

         Socket finalClientSocket = clientSocket;
         executorService.submit(() -> {
           try {
             HttpRequest parsedRequest = handleRequest(finalClientSocket);
             ResponseHandler responseHandler = new ResponseHandler();

             if(parsedRequest.getRequestLine().getRequestTarget().equals("/"))
               responseHandler.handleOK(finalClientSocket);
             else if(parsedRequest.getRequestLine().getRequestTarget().startsWith("/echo"))
               responseHandler.handleEcho(finalClientSocket, parsedRequest);
             else if (parsedRequest.getRequestLine().getRequestTarget().equals("/user-agent"))
               responseHandler.handleUserAgent(finalClientSocket, parsedRequest);
             else
               responseHandler.handle404(finalClientSocket);

             finalClientSocket.close();
           } catch (IOException e) {
             System.out.println("IOException: " + e.getMessage());
           }
         });
       }
     } catch (IOException e) {
       System.out.println("IOException: " + e.getMessage());
     }
  }

  private static HttpRequest handleRequest(Socket clientSocket) throws IOException{
    HttpRequest parser = new HttpRequest(clientSocket.getInputStream());
    return parser.parse();
  }
}
