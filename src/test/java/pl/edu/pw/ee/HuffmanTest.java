package pl.edu.pw.ee;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import static org.junit.Assert.assertEquals;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HuffmanTest {

    @Test
    public void compression() throws IOException {
        //given
        String filePath = "./data/";
        Huffman huffman = new Huffman();

        //when
        int returnedValue = huffman.huffman(filePath, true);

        //then
        int expectedFileSize = (int) Files.size(Path.of(filePath + "compressed.bin")) * 8;
        assertEquals(expectedFileSize, returnedValue);
    }

    @Test
    public void decompression() throws IOException {
        //given
        String filePath = "./data/";
        Huffman huffman = new Huffman();

        //when
        int returnedValue = huffman.huffman(filePath, false);

        //then
        long expectedFileSize = (int) Files.size(Path.of(filePath + "decompressed.txt"));
        assertEquals(expectedFileSize, returnedValue);
    }

    @Test
    public void compressTest() {
        //given
        String text = "aab\naa";
        int[] symbolOccurancies = Reader.countSymbols(text);

        HuffmanTree tree = new HuffmanTree(symbolOccurancies);
        String writtenTree = tree.writeTree();
        tree.fillMapOfCodes(writtenTree, true);
        HashMap<String, String> mapOfCodes = tree.getMapOfCodes();

        //when
        String compressedText = Huffman.compress(mapOfCodes, text);

        //then
        String expected = "1011101001100000";
        int stringSizeMultipleOfEight = expected.length() % 8;
        assertEquals(expected, compressedText);
        assertEquals(0, stringSizeMultipleOfEight);
    }

    @Test
    public void compressTest_compressedTextIsMultipleOfEight_prefix000_andOneLetterText() {
        //given
        String text = "aaaaa";
        int[] symbolOccurancies = Reader.countSymbols(text);

        HuffmanTree tree = new HuffmanTree(symbolOccurancies);
        String writtenTree = tree.writeTree();
        tree.fillMapOfCodes(writtenTree, true);
        HashMap<String, String> mapOfCodes = tree.getMapOfCodes();

        //when
        String compressedText = Huffman.compress(mapOfCodes, text);

        //then
        String expected = "00011111";
        int stringSizeMultipleOfEight = expected.length() % 8;
        assertEquals(expected, compressedText);
        assertEquals(0, stringSizeMultipleOfEight);
    }

    @Test
    public void compressTest_compressedTextWithTwoLetters() {
        //given
        String text = "aaabb";
        int[] symbolOccurancies = Reader.countSymbols(text);

        HuffmanTree tree = new HuffmanTree(symbolOccurancies);
        String writtenTree = tree.writeTree();
        tree.fillMapOfCodes(writtenTree, true);
        HashMap<String, String> mapOfCodes = tree.getMapOfCodes();

        //when
        String compressedText = Huffman.compress(mapOfCodes, text);

        //then
        String expected = "00011100";
        int stringSizeMultipleOfEight = expected.length() % 8;
        assertEquals(expected, compressedText);
        assertEquals(0, stringSizeMultipleOfEight);
    }

    @Test
    public void toBytesTest() {
        //given
        String text = "aab\naa";
        int[] symbolOccurancies = Reader.countSymbols(text);

        HuffmanTree tree = new HuffmanTree(symbolOccurancies);
        String writtenTree = tree.writeTree();
        tree.fillMapOfCodes(writtenTree, true);
        HashMap<String, String> mapOfCodes = tree.getMapOfCodes();
        String compressedText = Huffman.compress(mapOfCodes, text);

        //when
        byte[] textInBytes = Huffman.toBytes(compressedText);

        //then
        Byte firstByte = textInBytes[0];
        Byte secondByte = textInBytes[1];
        Byte expectedFirstByte = -70;
        Byte expectedSecondByte = 96;
        int expectedTextInBytesLength = textInBytes.length;

        assertEquals(expectedFirstByte, firstByte);
        assertEquals(expectedSecondByte, secondByte);
        assertEquals(2, expectedTextInBytesLength);
    }

    @Test
    public void decompress_test() {
        //given
        HuffmanTree tree = new HuffmanTree();
        String writtenTree = "\n:00;;b:01;;a:1;;";
        tree.fillMapOfCodes(writtenTree, false);
        HashMap<String, String> mapOfCodes = tree.getMapOfCodes();
        byte[] textInBytes = {-70, 96};

        //when
        String compressedText = Huffman.decompress(mapOfCodes, textInBytes);

        //then
        String expectedTextAfterDecompression = "aab\naa";

        assertEquals(expectedTextAfterDecompression, compressedText);
    }

}
