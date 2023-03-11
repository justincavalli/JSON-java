package org.json.junit;

import org.json.JSONObject;
import org.json.XML;
import org.junit.Test;

import java.io.StringReader;
import java.util.concurrent.Future;

public class Milestone5Test {

    @Test
    public void firstTest() {
        String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"+
                "<contact>\n"+
                "  <nick>Crista </nick>\n"+
                "  <name>Crista Lopes</name>\n" +
                "  <address>\n" +
                "    <street>Ave of Nowhere</street>\n" +
                "    <zipcode>92614</zipcode>\n" +
                "  </address>\n" +
                "</contact>";
        Future<JSONObject> future = XML.toJSONObject(new StringReader(xmlString), (JSONObject jo) -> {System.out.println(jo);}, (Exception e) -> e.printStackTrace());
        System.out.println("complete");

        try {
            future.get();           // get() blocks until the future is complete. (needed so that the method doesn't finish before the future)
        } catch(Exception e) {
            e.printStackTrace();
        }
        System.out.println("actually done");
    }

}
