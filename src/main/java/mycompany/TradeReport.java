package mycompany;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Admin on 04-05-2017.
 */
public class TradeReport {

    public List<TradeDO> getTradeList(String fileName) throws Exception {
        File file = new File(fileName);
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(file);
        document.getDocumentElement().normalize();
        NodeList nodeList = document.getElementsByTagName("entity");
        List<TradeDO> tradeDOList = new ArrayList<>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element)node;
                TradeDO tradeDO = new TradeDO(element.getElementsByTagName("entityname").item(0).getTextContent(),
                        element.getElementsByTagName("tradetype").item(0).getTextContent(),
                        Double.parseDouble(element.getElementsByTagName("agreedfx").item(0).getTextContent()),
                        element.getElementsByTagName("currencytype").item(0).getTextContent(),
                        convertDate(element.getElementsByTagName("instructiondate").item(0).getTextContent()),
                        convertDate(element.getElementsByTagName("settlementdate").item(0).getTextContent()),
                        Integer.parseInt(element.getElementsByTagName("units").item(0).getTextContent()),
                        Double.parseDouble(element.getElementsByTagName("priceperunit").item(0).getTextContent()),
                        0.0);
                String validSettlementDate = getValidSettlementDate(tradeDO.settlementDate, tradeDO.currencyType);
                tradeDO.settlementDate = validSettlementDate;
                tradeDOList.add(tradeDO);
            }
        }
        return tradeDOList;
    }

    private String convertDate(String date) throws ParseException {
        Date tempDate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(date);
        return new SimpleDateFormat("dd-MMM-yyyy").format(tempDate);
    }

    public String getValidSettlementDate(String settlementDate, String currencyType) throws ParseException {
        Date date = new SimpleDateFormat("dd-MMM-yyyy").parse(settlementDate);

        String day = new SimpleDateFormat("EEEE").format(date);
        String updatedSettlementDate = settlementDate;
        if (currencyType.equalsIgnoreCase("AED") || currencyType.equalsIgnoreCase("SAR")) {
            if (day.equalsIgnoreCase("Friday")) {
                updatedSettlementDate = addDays(date, 2);
            } else if (day.equalsIgnoreCase("Saturday")) {
                updatedSettlementDate = addDays(date, 1);
            }
        } else {
            if (day.equalsIgnoreCase("Saturday")) {
                updatedSettlementDate = addDays(date, 2);
            } else if (day.equalsIgnoreCase("Sunday")) {
                updatedSettlementDate = addDays(date, 1);
            }
        }
        return updatedSettlementDate;
    }

    private String addDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);
        return new SimpleDateFormat("dd-MMM-yyyy").format(cal.getTime());
    }

    public void tradeReport(List<TradeDO> tradeDOList) {
        System.out.println("|ENTITY|BUY/SELL|AGREED FX|CURRENCY|INSTRUCTION DATE|SETTLEMENT DATE|UNITS|PRICE PER UNIT|FINAL AMOUNT IN USD|");
        for ( TradeDO tradeDO : tradeDOList) {
            tradeDO.totalAmount =  tradeDO.agreedFx * tradeDO.pricePerUnit * tradeDO.units;
            System.out.println("| " + tradeDO.entityName + " | " + tradeDO.tradeType + " | " + tradeDO.agreedFx + " | " + tradeDO.currencyType + " | " + tradeDO.instructionDate + " | " + tradeDO.settlementDate + " | " + tradeDO.units + " | " + tradeDO.pricePerUnit + " | " + tradeDO.totalAmount + " | ");
        }
    }
}
