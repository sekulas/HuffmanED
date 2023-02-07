package pl.edu.pw.ee;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

public class Writer {

    public static void writeByteFile(String path, byte[] compressedText) throws IOException {

        try ( FileOutputStream fos = new FileOutputStream(path)) {
            fos.write(compressedText);
            fos.close();
        } catch (IOException e) {
            e.getMessage();
            throw new IOException("Cannot write ByteFile");
        }

    }

    public static void writeTextFile(String path, String text) throws IOException {

        try ( BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            writer.write(text);
            writer.close();
        } catch (IOException e) {
            e.getMessage();
            throw new IOException("Cannot write TextFile");
        }

    }

}
