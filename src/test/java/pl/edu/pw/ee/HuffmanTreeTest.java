package pl.edu.pw.ee;

import java.util.HashMap;
import java.util.PriorityQueue;
import org.junit.Test;
import static org.junit.Assert.*;

public class HuffmanTreeTest {

    @Test
    public void createTreeTest() {
        //given
        String text = "aab\naa";
        int[] symbolOccurancies = Reader.countSymbols(text);

        //when
        HuffmanTree tree = new HuffmanTree(symbolOccurancies);

        //then
        String writtenTree = tree.writeTree();
        String expectedTree = "\n:00;;b:01;;a:1;;";
        assertEquals(expectedTree, writtenTree);
    }

    @Test
    public void mapOfCodesTest() {
        //given
        String text = "aab\naa";
        int[] symbolOccurancies = Reader.countSymbols(text);

        HuffmanTree tree = new HuffmanTree(symbolOccurancies);
        String writtenTree = tree.writeTree();
        tree.fillMapOfCodes(writtenTree, true);

        //when
        HashMap<String, String> mapOfCodes = tree.getMapOfCodes();

        //then
        String aExpectedCode = "1";
        String bExpectedCode = "01";
        String nLineExpectedCode = "00";

        assertEquals(aExpectedCode, mapOfCodes.get("a"));
        assertEquals(bExpectedCode, mapOfCodes.get("b"));
        assertEquals(nLineExpectedCode, mapOfCodes.get("\n"));

    }

    @Test
    public void onlyOneNodeTree_Test() {
        //given
        String text = "a";
        int[] symbolOccurancies = Reader.countSymbols(text);
        HuffmanTree tree = new HuffmanTree(symbolOccurancies);

        //when
        String writtenTree = tree.writeTree();

        //then
        String expected = "a:1;;";
        assertEquals(expected, writtenTree);
    }

    @Test(expected = IllegalArgumentException.class)
    public void noRoot_noTreeTest() {
        //given
        String text = "";
        int[] symbolOccurancies = Reader.countSymbols(text);
        HuffmanTree tree = new HuffmanTree(symbolOccurancies);

        //when
        String writtenTree = tree.writeTree();

        //then
        String expected = "";
        assertEquals(expected, writtenTree);
    }

    @Test
    public void fillMapOfCodes_oneNodeTest() {
        //given
        HuffmanTree tree = new HuffmanTree();
        String writtenTree = "a:1;;";

        //when
        tree.fillMapOfCodes(writtenTree, false);

        //then
        HashMap<String, String> map = tree.getMapOfCodes();
        String current = map.get("1");
        String expected = "a";

        assertEquals(expected, current);
    }

    @Test(expected = IllegalArgumentException.class)
    public void noWayToFillTheMap_UsingEmptyString() {
        //given
        HuffmanTree tree = new HuffmanTree();
        String writtenTree = "";

        //when
        tree.fillMapOfCodes(writtenTree, true);

        //then
        HashMap<String, String> map = tree.getMapOfCodes();
        String current = map.get("1");
        String expected = "a";

        assertEquals(expected, current);
    }

}
