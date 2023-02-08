package org.json.junit;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;

import static org.junit.Assert.*;

public class Milestone3Test {
    @Test
    public void shouldPrependSWE() {
        String data = "<?xml version=\"1.0\"?>\n" +
                "<page>\n" +
                "   <title>Template:Delete</title>\n" +
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
        JSONObject output = XML.toJSONObject(new StringReader(data), (String str) -> {
            return "swe262_" + str;
        });
        String expectedOutput = "{\"swe262_page\":{\"swe262_revision\":{\"swe262_text\":{\"bytes\":1048,\"id\":10241},\"swe262_contributor\":{\"swe262_username\":\"test username\",\"swe262_id\":361},\"swe262_parentid\":3053,\"swe262_format\":\"text/x-wiki\",\"swe262_sha1\":\"sly9u7f89vjcpr9g84u9gl92jdy66by\",\"swe262_minor\":\"\",\"swe262_model\":\"wikitext\",\"swe262_timestamp\":\"2008-07-04T18:42:36Z\",\"swe262_id\":3984},\"swe262_ns\":10,\"swe262_title\":\"Template:Delete\"}}";
        assertEquals(expectedOutput, output.toString());
    }

    @Test
    public void shouldReverseKeys() {
        String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"+
                "<contact>\n"+
                "  <nick>Crista </nick>\n"+
                "  <name>Crista Lopes</name>\n" +
                "  <address>\n" +
                "    <street>Ave of Nowhere</street>\n" +
                "    <zipcode>92614</zipcode>\n" +
                "  </address>\n" +
                "</contact>";
        JSONObject output = XML.toJSONObject(new StringReader(xmlString), (String str) -> {
            String newKey = "";
            for(int i = str.length()-1; i >= 0; i--)
                newKey += str.charAt(i);
            return newKey;
        });
        String expectedOutput = "{\"tcatnoc\":{\"eman\":\"Crista Lopes\",\"sserdda\":{\"edocpiz\":92614,\"teerts\":\"Ave of Nowhere\"},\"kcin\":\"Crista\"}}";
        assertEquals(expectedOutput, output.toString());
    }

    @Test
    public void testRepeatedKeysError() {
        String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"+
                "<contact>\n"+
                "  <nick>Crista </nick>\n"+
                "  <name>Crista Lopes</name>\n" +
                "  <address>\n" +
                "    <street>Ave of Nowhere</street>\n" +
                "    <zipcode>92614</zipcode>\n" +
                "  </address>\n" +
                "</contact>";

        assertThrows(JSONException.class, ()-> XML.toJSONObject(new StringReader(xmlString), (String str) -> {
            if(str.indexOf('n') > -1)
                return "testkey";   // nick and name will both become testkey, triggering JSONException
            else
                return str;
        }));
    }

    @Test
    public void testEmptyKeyError() {
        String xmlString = "<?xml version=\"1.0\"?>\n" +
                "<catalog>\n" +
                "   <book id=\"bk101\">\n" +
                "      <author>Gambardella, Matthew</author>\n" +
                "      <title>XML Developer's Guide</title>\n" +
                "      <genre>Computer</genre>\n" +
                "      <price>44.95</price>\n" +
                "      <publish_date>2000-10-01</publish_date>\n" +
                "      <description>An in-depth look at creating applications \n" +
                "      with XML.</description>\n" +
                "   </book>\n" +
                "   <book id=\"bk102\">\n" +
                "      <author>Ralls, Kim</author>\n" +
                "      <title>Midnight Rain</title>\n" +
                "      <genre>Fantasy</genre>\n" +
                "      <price>5.95</price>\n" +
                "      <publish_date>2000-12-16</publish_date>\n" +
                "      <description>A former architect battles corporate zombies, \n" +
                "      an evil sorceress, and her own childhood to become queen \n" +
                "      of the world.</description>\n" +
                "   </book>\n" +
                "   <book id=\"bk103\">\n" +
                "      <author>Corets, Eva</author>\n" +
                "      <title>Maeve Ascendant</title>\n" +
                "      <genre>Fantasy</genre>\n" +
                "      <price>5.95</price>\n" +
                "      <publish_date>2000-11-17</publish_date>\n" +
                "      <description>After the collapse of a nanotechnology \n" +
                "      society in England, the young survivors lay the \n" +
                "      foundation for a new society.</description>\n" +
                "   </book>\n" +
                "</catalog>";
        assertThrows(JSONException.class, ()-> XML.toJSONObject(new StringReader(xmlString), (String str) -> {
            if (str.equals("publish_date")) {
                return "";
            }
            else
                return str;
        }));
    }
}