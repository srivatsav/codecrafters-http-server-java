import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ObjectSerializer<T> {

  // Serialize object to bytes
  public static <T> byte[] serialize(T obj) throws IOException {
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
    objectOutputStream.writeObject(obj);
    objectOutputStream.close();
    return byteArrayOutputStream.toByteArray();
  }

  public static <T> char[] charArraySerialize(T obj) throws IOException {
    CharArrayWriter charArrayWriter = new CharArrayWriter();
    charArrayWriter.write((char[]) obj);
    return charArrayWriter.toCharArray();
  }


  // Deserialize bytes to object
  public T deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
    ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
    @SuppressWarnings("unchecked")
    T obj = (T) objectInputStream.readObject();
    objectInputStream.close();
    return obj;
  }

}
