import static enums.ServerConstants.CR_LF;
import static enums.ServerConstants.HTTP_VERSION_1_1;

import enums.ContentType;
import enums.HttpHeaders;
import enums.HttpStatus;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import request.HttpRequest;
import request.RequestBodyLine;
import response.FileHandler;
import response.HttpResponse;
import response.ResponseHeaderLine;

public class FileIO {

  public static void serveFile(HttpResponse response, String filePath, OutputStream osStream) throws IOException {
    List<ResponseHeaderLine> responseHeaderLines = new ArrayList<>();
    ResponseHeaderLine responseHeaderLine = response.getHeaderLine(HttpHeaders.CONTENT_TYPE.getHeader(), ContentType.OCTET_STREAM.getType());
    responseHeaderLines.add(responseHeaderLine);

    File file = FileHandler.getFile(filePath);
    responseHeaderLine = response.getHeaderLine(HttpHeaders.CONTENT_LENGTH.getHeader(), file.length());
    responseHeaderLines.add(responseHeaderLine);

    response.setStatusLine(response.getStatusLine(HTTP_VERSION_1_1, HttpStatus.OK));
    response.setHeaderLines(responseHeaderLines);
    osStream.write(response.writeResponse().getBytes(StandardCharsets.UTF_8));
    osStream.flush();

    try(FileInputStream fis = new FileInputStream(file)){
      byte[] dataBuffer = new byte[2048];
      int bytesRead;
      while((bytesRead = fis.read(dataBuffer)) != -1) {
        osStream.write(dataBuffer, 0, bytesRead);
      }
    }
    osStream.flush();
  }

  public static void createFile(HttpResponse response, String filePath, HttpRequest parsedRequest,
      OutputStream osStream) throws IOException {
    response.setStatusLine(response.getStatusLine(HTTP_VERSION_1_1, HttpStatus.CREATED));

    File file = FileHandler.createFile(filePath);
    RequestBodyLine requestBodyLine = parsedRequest.getRequestBodyLine();
    char[]arr = (char[]) requestBodyLine.getBody();
    FileOutputStream fos = new FileOutputStream(file);
    OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
    try {
      osw.write(arr);
      System.out.println("File created successfully!");
    } catch (IOException e) {
      System.out.println("An error occurred while creating the file.");
      e.printStackTrace();
    } finally {
      osw.close();
      fos.close();
    }
    osStream.write(response.writeResponse().concat(CR_LF).getBytes(StandardCharsets.UTF_8));
    osStream.flush();
  }
}
