package Code6;


import java.io.*;

public class Converter {
    public static byte[] convertToBytes(Object object) throws IOException {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(baos)) {
            objectOutputStream.writeObject(object);
            return baos.toByteArray();
        }
    }

    public static Object convertFromBytes(byte[] buffer) throws IOException, ClassNotFoundException {
        try (ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
             ObjectInputStream objectInputStream = new ObjectInputStream(bais)){
            Object object = objectInputStream.readObject();
            return object;
        }
    }
}
