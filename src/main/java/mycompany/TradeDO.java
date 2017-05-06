package mycompany;

import java.time.LocalDate;

/**
 * Created by Admin on 04-05-2017.
 */
public class TradeDO {

    public String entityName;

    public String tradeType;

    public Double agreedFx;

    public String currencyType;

    public String instructionDate;

    public String settlementDate;

    public Integer units;

    public Double pricePerUnit;

    public Double totalAmount;

    public TradeDO(String entityName, String tradeType, Double agreedFx, String currencyType, String instructionDate, String settlementDate, Integer units, Double pricePerUnit, Double totalAmount) {
        this.entityName = entityName;
        this.tradeType = tradeType;
        this.agreedFx = agreedFx;
        this.currencyType = currencyType;
        this.instructionDate = instructionDate;
        this.settlementDate = settlementDate;
        this.units = units;
        this.pricePerUnit = pricePerUnit;
        this.totalAmount = totalAmount;
    }
}
