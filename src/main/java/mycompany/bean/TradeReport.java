package mycompany;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.*;

import static mycompany.Constants.*;

/**
 * Created by Naresh Kumar Rachepalli on 06-05-2017.
 */

public class TradeReport {

    public List<TradeDO> getTradeList(String fileName) throws Exception {
        File file = new File(fileName);
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(file);
        document.getDocumentElement().normalize();
        NodeList nodeList = document.getElementsByTagName(ROOT_NODE);
        List<TradeDO> tradeDOList = new ArrayList<>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element)node;
                TradeDO tradeDO = new TradeDO(element.getElementsByTagName(ENTITY_NAME).item(INDEX).getTextContent(),
                        element.getElementsByTagName(TRADE_TYPE).item(INDEX).getTextContent(),
                        Double.parseDouble(element.getElementsByTagName(AGREED_FX).item(INDEX).getTextContent()),
                        element.getElementsByTagName(CURRENCY_TYPE).item(INDEX).getTextContent(),
                        ReportUtility.convertDate(element.getElementsByTagName(INSTRUCTION_DATE).item(INDEX).getTextContent()),
                        ReportUtility.convertDate(element.getElementsByTagName(SETTLEMENT_DATE).item(INDEX).getTextContent()),
                        Integer.parseInt(element.getElementsByTagName(UNITS).item(INDEX).getTextContent()),
                        Double.parseDouble(element.getElementsByTagName(PRICE_PER_UNIT).item(INDEX).getTextContent()),
                        DEFAULT);
                String validSettlementDate = ReportUtility.getValidSettlementDate(tradeDO.settlementDate, tradeDO.currencyType);
                tradeDO.settlementDate = validSettlementDate;
                tradeDOList.add(tradeDO);
            }
        }
        return tradeDOList;
    }



    public void tradeReport(List<TradeDO> tradeDOList) {
        System.out.println(HEADINGS);
        for ( TradeDO tradeDO : tradeDOList) {
            tradeDO.totalAmount =  tradeDO.agreedFx * tradeDO.pricePerUnit * tradeDO.units;
            System.out.println(PIPE + tradeDO.entityName
                    + PIPE + tradeDO.tradeType
                    + PIPE + tradeDO.agreedFx
                    + PIPE + tradeDO.currencyType
                    + PIPE + tradeDO.instructionDate
                    + PIPE + tradeDO.settlementDate
                    + PIPE + tradeDO.units
                    + PIPE + tradeDO.pricePerUnit
                    + PIPE + tradeDO.totalAmount
                    + PIPE);
        }
    }
}
