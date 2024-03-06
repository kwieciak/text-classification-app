package org.example.project1.util;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class SgmReader {
    public void readSgm() {
        try {
            File file = new File("data/reut2-000.sgm");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("REUTERS");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    System.out.println("Reuters ID : " + eElement.getAttribute("NEWID"));
                    System.out.println("Title : " + eElement.getElementsByTagName("TITLE").item(0).getTextContent());
                    System.out.println("Body : " + eElement.getElementsByTagName("BODY").item(0).getTextContent());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
