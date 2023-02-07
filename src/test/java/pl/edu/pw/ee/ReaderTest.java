package pl.edu.pw.ee;

import java.io.IOException;
import org.junit.Test;
import static org.junit.Assert.*;

public class ReaderTest {

    @Test(expected = IOException.class)
    public void noFileBeforeCompressionTest() throws IOException {

        Reader.readTextFile("./src/thereIsNoWayThisFileExist.txt");

        assert true;
    }

    @Test(expected = IOException.class)
    public void noFileBeforeDecompression() throws IOException {

        byte[] bytes = Reader.readCompressedFile("./src/thereIsNoWayThisFileExist.bin");

        assert true;
    }

    @Test
    public void countOccurencies_withNewline() {
        //given
        String text = "aab\naa";
        int aFreqExpected = 4;
        int bFreqExpected = 1;
        int nFreqLineExpected = 1;

        //when
        int[] symbolOccurancies = Reader.countSymbols(text);

        //then
        assertEquals(aFreqExpected, symbolOccurancies['a']);
        assertEquals(bFreqExpected, symbolOccurancies['b']);
        assertEquals(nFreqLineExpected, symbolOccurancies['\n']);
    }

    @Test(expected = IllegalArgumentException.class)
    public void countOccurencies_polishSigns() {
        //given
        String text = "aśańń";
        int aFreqExpected = 4;
        int bFreqExpected = 1;
        int nFreqLineExpected = 1;

        //when
        int[] symbolOccurancies = Reader.countSymbols(text);

        //then
        assertEquals(aFreqExpected, symbolOccurancies['a']);
        assertEquals(bFreqExpected, symbolOccurancies['b']);
        assertEquals(nFreqLineExpected, symbolOccurancies['\n']);
    }

}
