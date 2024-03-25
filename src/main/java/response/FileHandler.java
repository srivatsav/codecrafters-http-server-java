package response;

import java.io.File;

public class FileHandler {
  public static boolean fileExists(String filePath) {
      File file = new File(filePath);
      return file.exists();
  }

  public static File getFile(String filePath) {
    return new File(filePath);
  }

  public static File createFile(String filePath) {
    if(fileExists(filePath)) {
      return getFile(filePath);
    } else {
      return createNewFile(filePath);
    }
  }

  private static File createNewFile(String filePath) {
    File file = new File(filePath);
    try {
      file.createNewFile();
    } catch (Exception e) {
      System.out.println("An error occurred while creating the file.");
      e.printStackTrace();
    }
    return file;
  }
}
