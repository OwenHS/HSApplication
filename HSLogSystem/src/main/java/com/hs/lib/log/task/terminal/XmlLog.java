package com.hs.lib.log.task.terminal;

import android.util.Log;

import com.hs.lib.log.HLog;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 * Created by huangshuo on 16/8/30.
 */
public class XmlLog {


    public void print(String tag, String xml, String headString) {

        if (xml != null) {
            xml = formatXML(xml);
            xml = headString + "\n" + xml;
        } else {
            xml = headString + HLog.NULL_TIPS;
        }

        printLine(tag, true);
        String[] lines = xml.split(HLog.LINE_SEPARATOR);
        for (String line : lines) {
            if (!isEmpty(line)) {
                Log.d(tag, "║ " + line);
            }
        }
        printLine(tag, false);
    }

    public boolean isEmpty(String line) {
        return isTextEmpty(line) || line.equals("\n") || line.equals("\t") || isTextEmpty(line.trim());
    }

    public boolean isTextEmpty(String text){
        if(text == null || text.length() < 1){
            return true;
        }
        return false;
    }

    public  String formatXML(String inputXML) {
        try {
            Source xmlInput = new StreamSource(new StringReader(inputXML));
            StreamResult xmlOutput = new StreamResult(new StringWriter());
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.transform(xmlInput, xmlOutput);
            return xmlOutput.getWriter().toString().replaceFirst(">", ">\n");
        } catch (Exception e) {
            e.printStackTrace();
            return inputXML;
        }
    }

    public void printLine(String tag, boolean isTop) {
        if (isTop) {
            Log.d(tag, "╔═══════════════════════════════════════════════════════════════════════════════════════");
        } else {
            Log.d(tag, "╚═══════════════════════════════════════════════════════════════════════════════════════");
        }
    }


}
