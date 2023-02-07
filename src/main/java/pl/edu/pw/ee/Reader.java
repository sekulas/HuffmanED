package pl.edu.pw.ee;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

public class Reader {

    private static final int UTF_8_SIZE = 256;

    public static String readTextFile(String pathToRootDir) throws IOException {
        String text = "";

        try ( BufferedReader reader = new BufferedReader(new FileReader(pathToRootDir))) {
            String line;
            while ((line = reader.readLine()) != null) {
                text += line + '\n';
            }
        } catch (IOException e) {
            e.getMessage();
            throw new IOException("Something is wrong with text file - cannot read.");
        }

        text = text.substring(0, text.length() - 1);
        return text;
    }

    public static int[] countSymbols(String text) {
        int[] symbolOccurancies = new int[UTF_8_SIZE];
        int symbol;

        for (int i = 0; i < text.length(); i++) {
            symbol = text.charAt(i);
            if (symbol >= UTF_8_SIZE) {
                throw new IllegalArgumentException("File to read from is encoded differently than UTF_8");
            }
            symbolOccurancies[symbol]++;
        }

        return symbolOccurancies;
    }

    public static byte[] readCompressedFile(String pathToRootDir) throws IOException {

        byte[] textInBytes = null;

        try {
            File file = new File(pathToRootDir);
            InputStream inputStream = new FileInputStream(file);

            long fileSizeInBytes = file.length();
            textInBytes = new byte[(int) fileSizeInBytes];

            inputStream.read(textInBytes);

            inputStream.close();
        } catch (IOException e) {
            e.getMessage();
            throw new IOException("Something is wrong with compressed file - cannot read.");
        }

        return textInBytes;
    }
}
