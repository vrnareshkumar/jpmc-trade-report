package mycompany;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Admin on 05-05-2017.
 */
public class TradeReportTest {

    List<TradeDO> tradeDOList;
    TradeReport tradeReport;

    @Before
    public void setUp() {
        tradeReport = new TradeReport();
        tradeDOList = new ArrayList<>();
    }

    @Test
    public void testTradeListSize() throws Exception{
        assertTrue(2 == tradeReport.getTradeList("test.xml").size());
    }

    @Test
    public void testTradeReport() throws Exception{
        tradeDOList = tradeReport.getTradeList("test.xml");
        tradeReport.tradeReport(tradeDOList);
    }

    @Test
    public void testTradeReportWithInvalidSettlementDates() throws Exception{
        tradeDOList = tradeReport.getTradeList("test1.xml");
        tradeReport.tradeReport(tradeDOList);
        assertEquals(3 , tradeDOList.size());
    }

    @Test
    public void testGetValidSettlementDate() throws Exception{
        assertEquals("08-May-2017", tradeReport.getValidSettlementDate("07-May-2017", "GBP"));
    }

    @Test
    public void testGetValidSettlementDateForSpecialCurrency() throws Exception {
        assertEquals("07-May-2017", tradeReport.getValidSettlementDate("05-May-2017", "SAR"));
    }
}
