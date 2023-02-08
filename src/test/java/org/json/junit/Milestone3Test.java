package org.json.junit;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONPointer;
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
        JSONObject output = XML.toJSONObject(new StringReader(data), (String a) -> {
            return "swe262_" + a;
        });
        String expectedOutput = "{\"swe262_page\":{\"swe262_revision\":{\"swe262_text\":{\"bytes\":1048,\"id\":10241},\"swe262_contributor\":{\"swe262_username\":\"test username\",\"swe262_id\":361},\"swe262_parentid\":3053,\"swe262_format\":\"text/x-wiki\",\"swe262_sha1\":\"sly9u7f89vjcpr9g84u9gl92jdy66by\",\"swe262_minor\":\"\",\"swe262_model\":\"wikitext\",\"swe262_timestamp\":\"2008-07-04T18:42:36Z\",\"swe262_id\":3984},\"swe262_ns\":10,\"swe262_title\":\"Template:Delete\"}}";
        assertEquals(expectedOutput, output.toString());
    }
}