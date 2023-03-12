package org.json.junit;

import org.json.JSONObject;
import org.json.XML;
import org.junit.Test;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.Assert.*;

public class Milestone5Test {

    @Test
    public void testCallbackWorksOnSimpleXML() {
        StringBuilder sb = new StringBuilder();
        String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"+
                "<contact>\n"+
                "  <nick>Crista </nick>\n"+
                "  <name>Crista Lopes</name>\n" +
                "  <address>\n" +
                "    <street>Ave of Nowhere</street>\n" +
                "    <zipcode>92614</zipcode>\n" +
                "  </address>\n" +
                "</contact>";
        Future<JSONObject> future = XML.toJSONObject(new StringReader(xmlString), (JSONObject jo) -> {sb.append(jo.toString());}, (Exception e) -> e.printStackTrace());

        try {
            future.get();           // get() blocks until the future is complete. (needed so that the method doesn't finish before the future)
        } catch(Exception e) {
            e.printStackTrace();
        }

        assertEquals("{\"contact\":{\"nick\":\"Crista\",\"address\":{\"zipcode\":92614,\"street\":\"Ave of Nowhere\"},\"name\":\"Crista Lopes\"}}", sb.toString());
    }

    @Test
    public void testCodeProceedsAfterAsyncCall() {
        // Test that even though the empty JSONObject is added to the list in a line after the converted XML
        // it is executed before because of the asynchronous call


        ArrayList<JSONObject> executionOrder = new ArrayList<>();

        String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"+
                "<contact>\n"+
                "  <nick>Crista </nick>\n"+
                "  <name>Crista Lopes</name>\n" +
                "  <address>\n" +
                "    <street>Ave of Nowhere</street>\n" +
                "    <zipcode>92614</zipcode>\n" +
                "  </address>\n" +
                "</contact>";
        Future<JSONObject> future = XML.toJSONObject(new StringReader(xmlString), (JSONObject jo) -> {executionOrder.add(jo);}, (Exception e) -> e.printStackTrace());

        executionOrder.add(XML.toJSONObject(""));

        try {
            future.get();           // get() blocks until the future is complete. (needed so that the method doesn't finish before the future)
        } catch(Exception e) {
            e.printStackTrace();
        }

        assertEquals("{}", executionOrder.remove(0).toString());
        assertEquals("{\"contact\":{\"nick\":\"Crista\",\"address\":{\"zipcode\":92614,\"street\":\"Ave of Nowhere\"},\"name\":\"Crista Lopes\"}}", executionOrder.remove(0).toString());
    }

    @Test
    public void testPerformingStreamOnJSONObjectOnCallback() {
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
        int expected = 19107;
        AtomicReference<Integer> actualSum = new AtomicReference<>(new Integer(0));
        Future<JSONObject> future = XML.toJSONObject(new StringReader(data), (JSONObject jo) ->
        {actualSum.set(jo.toStream().filter(obj -> obj.has("id")).map(obj ->
                (Integer) obj.get("id")).mapToInt(Integer::intValue).sum());}, (Exception e) -> e.printStackTrace());

        try {
            future.get();           // get() blocks until the future is complete. (needed so that the method doesn't finish before the future)
        } catch(Exception e) {
            e.printStackTrace();
        }

        assertEquals(actualSum.get().intValue(), expected);
    }
    @Test
    public void testUserProvidedExceptionIsReturned() {
        String errorXML = "< <!. %^#&#*(^$%#$&%*%this cannot be converted to a JSONObject/>/>>/>/>>//>/>/";

        assertThrows(Exception.class, () -> {
            Future<JSONObject> future = XML.toJSONObject(new StringReader(errorXML), (JSONObject jo) -> {System.out.println(jo);}, (Exception e) -> e.printStackTrace());
            future.get();
        });
    }
}
