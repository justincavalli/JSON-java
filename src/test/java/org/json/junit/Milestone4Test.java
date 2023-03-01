package org.json.junit;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.junit.Test;

import java.io.StringReader;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class Milestone4Test {

    @Test
    public void returnAllUniqueKeys() {
        String data = "<?xml version=\"1.0\"?>\n" +
                "<page>\n" +
                "   <title>Template:Delete</title>\n" +
                "   <id>4521</id>\n" +
                "   <ns>10</ns>\n" +
                "   <revision>\n" +
                "       <id>3984</id>\n" +
                "       <timestamp>2008-07-04T18:42:36Z</timestamp>\n" +
                "       <contributor>\n" +
                "           <username>test username</username>\n" +
                "           <id>361</id>\n" +
                "       </contributor>\n" +
                "       <minor/>\n" +
                "       <model>wikitext</model>\n" +
                "       <format>text/x-wiki</format>\n" +
                "       <text bytes=\"1048\" id=\"10241\" />\n" +
                "       <sha1>sly9u7f89vjcpr9g84u9gl92jdy66by</sha1>\n" +
                "   </revision>\n" +
                "</page>";
        JSONObject jsonObject = XML.toJSONObject(data);
        Set<String> actualKeys = new HashSet<String>();
        String expectedKeys = "[minor, ns, format, title, revision, sha1, contributor, bytes, model, page, id, text, username, timestamp]";
        jsonObject.toStream().forEach(json -> actualKeys.addAll(json.keySet()));
        assertEquals(expectedKeys, actualKeys.toString());
    }
    @Test
    public void sumAllIds() {
        String data = "<?xml version=\"1.0\"?>\n" +
                "<page>\n" +
                "   <title>Template:Delete</title>\n" +
                "   <id>4521</id>\n" +
                    "<ns>10</ns>\n" +
                    "<revision>\n" +
                        "<id>3984</id>\n" +
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
        JSONObject jsonObject = XML.toJSONObject(data);
        Integer actualSum = jsonObject.toStream().filter(obj -> obj.has("id")).map(obj -> (Integer)obj.get("id")).mapToInt(Integer::intValue).sum();
        int expected = 19107;
        assertEquals(actualSum.intValue(), expected);
    }

    @Test
    public void makeValueUpperCaseForKeyEqualsName() {
        String data = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"+
                "<contact>\n"+
                "  <nick>Crista </nick>\n"+
                "  <name>Crista Lopes</name>\n" +
                "  <address>\n" +
                "    <name>other name</name>\n" +
                "    <street>Ave of Nowhere</street>\n" +
                "    <zipcode>92614</zipcode>\n" +
                "  </address>\n" +
                "</contact>";
        JSONObject jsonObject = XML.toJSONObject(data);
        List<Object> modifiedList = jsonObject.toStream().filter(obj -> obj.has("name")).map(json -> json.put("name", json.get("name").toString().toUpperCase())).collect(Collectors.toList());
        String actual = modifiedList.toString();
        String expected = "[{\"name\":\"OTHER NAME\"}, {\"name\":\"CRISTA LOPES\"}]";
        assertEquals(expected, actual);
    }

    @Test
    public void extractValuesForKeyEqualsTitle() {
        String data =
                "<Books>\n" +
                    "<book>\n" +
                        "<title>AAA</title>\n" +
                        "<author>ASmith</author>\n" +
                    "</book>\n" +
                    "<book>\n" +
                        "<title>BBB</title>\n" +
                        "<author>BSmith</author>\n" +
                    "</book>\n" +
                    "<book>\n" +
                        "<title>CCC</title>\n" +
                        "<author>CSmith</author>\n" +
                    "</book>\n" +
                "</Books>";
        JSONObject jsonObject = XML.toJSONObject(data);
        List<Object> titles = jsonObject.toStream().filter(obj -> obj.has("title")).map(json -> json.get("title")).collect(Collectors.toList());
        List<Object> expected = new ArrayList<>();
        expected.add("AAA");
        expected.add("BBB");
        expected.add("CCC");
        assertEquals(expected, titles);
    }

    @Test
    public void extractOddZipValues() {
        String data = "\n" +
                "<?xml version=\"1.0\"?>\n" +
                "<customers>\n" +
                "   <customer id=\"55000\">\n" +
                "      <name>Charter Group</name>\n" +
                "      <address1>\n" +
                "         <street>100 Main</street>\n" +
                "         <city>Framingham</city>\n" +
                "         <state>MA</state>\n" +
                "         <zip>01701</zip>\n" +
                "      </address1>\n" +
                "      <address2>\n" +
                "         <street>720 Prospect</street>\n" +
                "         <city>Framingham</city>\n" +
                "         <state>MA</state>\n" +
                "         <zip>01703</zip>\n" +
                "      </address2>\n" +
                "      <address3>\n" +
                "         <street>120 Ridge</street>\n" +
                "         <state>MA</state>\n" +
                "         <zip>01760</zip>\n" +
                "      </address3>\n" +
                "   </customer>\n" +
                "</customers>";
        JSONObject jsonObject = XML.toJSONObject(data);
        List<Object> actual = jsonObject.toStream().filter(obj -> obj.has("zip")).map(json -> json.get("zip")).filter(zip -> Integer.parseInt((String)zip) % 2 != 0).collect(Collectors.toList());
        String expected = "[01703, 01701]";
        assertEquals(expected, actual.toString());
    }
}