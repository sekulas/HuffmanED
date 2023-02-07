package pl.edu.pw.ee;

import java.io.IOException;
import java.util.HashMap;

public class Huffman {

    private static final int BITS_IN_BYTE = 8;
    private static final int BITS_OFFSET = 3;
    private static final String TO_COMPRESS = "toCompress.txt";
    private static final String TREE = "tree.txt";
    private static final String COMPRESSED = "compressed.bin";
    private static final String DECOMPRESSED = "decompressed.txt";

    public int huffman(String pathToRootDir, boolean compress) {

        int amountOfSymbols;

        if (compress == true) {
            String text = "";

            try {
                text = Reader.readTextFile(pathToRootDir + TO_COMPRESS);
            } catch (IOException e) {
                e.getMessage();
            }

            int[] symbolOccurancies = Reader.countSymbols(text);

            HuffmanTree tree = new HuffmanTree(symbolOccurancies);

            String writtenTree = tree.writeTree();
            tree.fillMapOfCodes(writtenTree, compress);

            String compressedText = compress(tree.getMapOfCodes(), text);
            byte[] textInBytes = toBytes(compressedText);

            try {
                Writer.writeTextFile(pathToRootDir + TREE, writtenTree);
                Writer.writeByteFile(pathToRootDir + COMPRESSED, textInBytes);
            } catch (IOException e) {
                e.getMessage();
            }

            amountOfSymbols = textInBytes.length * BITS_IN_BYTE;

        } else {

            String writtenTree = "";
            byte[] textInBytes = null;

            try {
                textInBytes = Reader.readCompressedFile(pathToRootDir + COMPRESSED);
                writtenTree = Reader.readTextFile(pathToRootDir + TREE);
            } catch (IOException e) {
                e.getMessage();
            }

            HuffmanTree tree = new HuffmanTree();
            tree.fillMapOfCodes(writtenTree, compress);

            String decompressedText = decompress(tree.getMapOfCodes(), textInBytes);

            try {
                Writer.writeTextFile(pathToRootDir + DECOMPRESSED, decompressedText);
            } catch (IOException e) {
                e.getMessage();
            }

            amountOfSymbols = decompressedText.length();
        }

        return amountOfSymbols;

    }

    public static String compress(HashMap<String, String> mapOfCodes, String text) {
        String compressedText = "";
        int textLength = text.length();

        for (int i = 0; i < textLength; i++) {
            String symbol = text.substring(i, i + 1);
            compressedText += mapOfCodes.get(symbol);
        }

        int missingBytes = (BITS_IN_BYTE - ((compressedText.length() + BITS_OFFSET) % BITS_IN_BYTE)) % BITS_IN_BYTE;

        for (int i = 0; i < missingBytes; i++) {
            compressedText += "0";
        }

        String bytesOffset = Integer.toBinaryString(missingBytes);
        if (bytesOffset.length() == 1) {
            bytesOffset = "00" + bytesOffset;
        } else if (bytesOffset.length() == 2) {
            bytesOffset = "0" + bytesOffset;
        }

        return (bytesOffset + compressedText);
    }

    public static byte[] toBytes(String compressedText) {
        int textLengthInBytes = compressedText.length() / BITS_IN_BYTE;
        byte[] textInBytes = new byte[textLengthInBytes];
        String byteString;
        Integer byteInteger;

        for (int i = 0; i < textLengthInBytes; i++) {
            byteString = compressedText.substring(i * BITS_IN_BYTE, (i + 1) * BITS_IN_BYTE);
            byteInteger = Integer.valueOf(String.valueOf(byteString), 2);
            textInBytes[i] = byteInteger.byteValue();
        }

        return textInBytes;
    }

    public static String decompress(HashMap<String, String> mapOfSymbols, byte[] textInBytes) {
        String decompressed = "";
        int amountOfBytes = textInBytes.length;
        int byteInInt;

        for (int i = 0; i < amountOfBytes; i++) {
            byteInInt = textInBytes[i] & 0xFF;
            decompressed += String.format("%8s", Integer.toBinaryString(byteInInt)).replace(" ", "0");
        }

        int bitsWithoutInformation = Integer.parseInt(decompressed.substring(0, BITS_OFFSET), 2);
        decompressed = decode(mapOfSymbols, decompressed, bitsWithoutInformation);

        return decompressed;
    }

    public static String decode(HashMap<String, String> mapOfSymbols, String decompressed, int bitsWithoutInformation) {
        int textLength = decompressed.length() - bitsWithoutInformation;
        String buffor = "";
        String decoded = "";

        for (int i = BITS_OFFSET; i < textLength; i++) {
            buffor += decompressed.charAt(i);

            if (mapOfSymbols.containsKey(buffor)) {
                decoded += mapOfSymbols.get(buffor);
                buffor = "";
            }
        }

        return decoded;
    }

}
