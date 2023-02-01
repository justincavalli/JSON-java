package org.json.junit;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONPointer;
import org.json.XML;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;

import static org.junit.Assert.*;

public class Milestone2Test {

    @Test
    public void shouldExtractContributor() {
        String data = "<?xml version=\"1.0\"?>\n" +
                "<page>\n" +
                "<title>Template:Delete</title>\n" +
                "<ns>10</ns>\n" +
                "<revision>\n" +
                "<id>3984</id>\n" +
                "<parentid>3053</parentid>\n" +
                "<timestamp>2008-07-04T18:42:36Z</timestamp>\n" +
                "<contributor>\n" +
                "<username>test username</username>\n" +
                "<id>361</id>\n" +
                "</contributor>\n" +
                "<minor/>\n" +
                "<model>wikitext</model>\n" +
                "<format>text/x-wiki</format>\n" +
                "<text bytes=\"1048\" id=\"10241\" />\n" +
                "<sha1>sly9u7f89vjcpr9g84u9gl92jdy66by</sha1>\n" +
                "</revision>\n" +
                "</page>";
        JSONObject output = XML.toJSONObject(new StringReader(data), new JSONPointer("/page/revision/contributor"));
        String expectedOutput = "{\"contributor\":{\"id\":361,\"username\":\"test username\"}}";
        assertEquals(expectedOutput, output.toString());
    }

    @Test
    public void shouldReplaceContributor() {
        JSONObject replacement = XML.toJSONObject("<street>Ave of the Arts</street>\n");
        String data = "<?xml version=\"1.0\"?>\n" +
                "<page>\n" +
                "<title>Template:Delete</title>\n" +
                "<ns>10</ns>\n" +
                "<revision>\n" +
                "<id>3984</id>\n" +
                "<parentid>3053</parentid>\n" +
                "<timestamp>2008-07-04T18:42:36Z</timestamp>\n" +
                "<contributor>\n" +
                "<username>test username</username>\n" +
                "<id>361</id>\n" +
                "</contributor>\n" +
                "<minor/>\n" +
                "<model>wikitext</model>\n" +
                "<format>text/x-wiki</format>\n" +
                "<text bytes=\"1048\" id=\"10241\" />\n" +
                "<sha1>sly9u7f89vjcpr9g84u9gl92jdy66by</sha1>\n" +
                "</revision>\n" +
                "</page>";
        JSONObject output = XML.toJSONObject(new StringReader(data), new JSONPointer("/page/revision/contributor"), replacement);
        String expectedOutput = "{\"page\":{\"ns\":10,\"title\":\"Template:Delete\",\"revision\":{\"sha1\":\"sly9u7f89vjcpr9g84u9gl92jdy66by\",\"contributor\":{\"street\":\"Ave of the Arts\"},\"minor\":\"\",\"format\":\"text/x-wiki\",\"model\":\"wikitext\",\"id\":3984,\"text\":{\"bytes\":1048,\"id\":10241},\"parentid\":3053,\"timestamp\":\"2008-07-04T18:42:36Z\"}}}";
        assertEquals(expectedOutput, output.toString());
    }

    @Test
    public void shouldExtractStreet() {
        String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"+
                "<contact>\n"+
                "  <nick>Crista </nick>\n"+
                "  <name>Crista Lopes</name>\n" +
                "  <address>\n" +
                "    <street>Ave of Nowhere</street>\n" +
                "    <zipcode>92614</zipcode>\n" +
                "  </address>\n" +
                "</contact>";
        JSONObject output = XML.toJSONObject(new StringReader(xmlString), new JSONPointer("/contact/address/street"));
        assertEquals("{\"street\":\"Ave of Nowhere\"}", output.toString());
    }

    @Test
    public void shouldReplaceStreet() {
        JSONObject replacement = XML.toJSONObject("<street>Ave of the Arts</street>\n");
        String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"+
                "<contact>\n"+
                "  <nick>Crista </nick>\n"+
                "  <name>Crista Lopes</name>\n" +
                "  <address>\n" +
                "    <street>Ave of Nowhere</street>\n" +
                "    <zipcode>92614</zipcode>\n" +
                "  </address>\n" +
                "</contact>";
        JSONObject output = XML.toJSONObject(new StringReader(xmlString), new JSONPointer("/contact/address/street"), replacement);
        String expectedOutput = "{\"contact\":{\"nick\":\"Crista\",\"address\":{\"zipcode\":92614,\"street\":{\"street\":\"Ave of the Arts\"}},\"name\":\"Crista Lopes\"}}";
        assertEquals(expectedOutput, output.toString());
    }

    @Test
    public void extractShouldThrowException() {
        String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"+
                "<contact>\n"+
                "  <nick>Crista </nick>\n"+
                "  <name>Crista Lopes</name>\n" +
                "  <address>\n" +
                "    <street>Ave of Nowhere</street>\n" +
                "    <zipcode>92614</zipcode>\n" +
                "  </address>\n" +
                "</contact>";
        assertThrows(JSONException.class, () -> {XML.toJSONObject(new StringReader(xmlString), new JSONPointer("/contact/address/streets"));});     // invalid key path being tested for
    }

    @Test
    public void replaceShouldThrowException() {
        JSONObject replacement = XML.toJSONObject("<street>Ave of the Arts</street>\n");
        String xmlString = "<?xml version=\"1.0\"?>\n" +
            "<Tests xmlns=\"http://www.adatum.com\">\n" +
                "<Test TestId=\"0001\" TestType=\"CMD\">\n" +
                    "<Name>Convert number to string</Name>\n" +
                    "<CommandLine>Examp1.EXE</CommandLine>\n" +
                    "<Input>1</Input>\n" +
                    "<Output>One</Output>\n" +
                "<Test TestId=\"0002\" TestType=\"CMD\">\n" +
                    "<Name>Find succeeding characters</Name>\n" +
                    "<CommandLine>Examp2.EXE</CommandLine>\n" +
                    "<Input>abc</Input>\n" +
                    "<Output>def</Output>\n" +
                "</Test>\n" +
            "</Tests>";
        assertThrows(JSONException.class, () -> {XML.toJSONObject(new StringReader(xmlString), new JSONPointer("/contact/address/street"), replacement);});     // invalid XML String being tested for
    }

    @Test
    public void testExtractOptimization() throws IOException {
        String data = "<?xml version=\"1.0\"?>\n" +
                "<page>\n" +
                "<title>Template:Delete</title>\n" +
                "<ns>10</ns>\n" +
                "<revision>\n" +
                "<id>3984</id>\n" +
                "<parentid>3053</parentid>\n" +
                "<timestamp>2008-07-04T18:42:36Z</timestamp>\n" +
                "<contributor>\n" +
                "<username>test username</username>\n" +
                "<id>361</id>\n" +
                "</contributor>\n" +
                "<minor/>\n" +
                "<model>wikitext</model>\n" +
                "<format>text/x-wiki</format>\n" +
                "<text bytes=\"1048\" id=\"10241\" />\n" +
                "<sha1>sly9u7f89vjcpr9g84u9gl92jdy66by</sha1>\n" +
                "</revision>\n" +
                "</page>";
        StringReader reader = new StringReader(data);
        JSONObject output = XML.toJSONObject(reader, new JSONPointer("/page/revision/contributor"));
        assertNotEquals(-1, reader.read());     // Shows there is still more of the String to read, meaning we did not parse the entire XML
    }

}