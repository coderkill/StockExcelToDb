package pojo;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.xssf.usermodel.XSSFRow;

import java.text.SimpleDateFormat;

public class StockReport {
    final static Logger logger = Logger.getLogger(StockReport.class.getName());
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String symbol;
    String name;
    String industry;
    String equity;
    Double freeFloat;
    Double weightage;
    Double beta;
    Double r2;
    Double volatility;
    Double monthlyReturn;
    Double avgImpactCost;
    String returnDate;

    public StockReport(XSSFRow row) {
        if (checkValidColumn(row, 0)) {
            symbol = row.getCell(0).getStringCellValue();
        }
        if (checkValidColumn(row, 1)) {
            name = row.getCell(1).getStringCellValue();
        } else {
            name = "";
        }
        if (checkValidColumn(row, 2)) {
            industry = industryFormatting(row.getCell(2).getStringCellValue());
        } else {
            industry = "";
        }
        if (checkValidColumn(row, 3)) {
            row.getCell(3).setCellType(CellType.STRING);
            equity = row.getCell(3).getStringCellValue();
        }

        if (checkValidColumn(row, 4)) {
            freeFloat = row.getCell(4).getNumericCellValue();
        }
        if (checkValidColumn(row, 5)) {
            weightage = row.getCell(5).getNumericCellValue();
        }
        if (checkValidColumn(row, 6)) {
            beta = row.getCell(6).getNumericCellValue();
        }
        if (checkValidColumn(row, 7)) {
            r2 = row.getCell(7).getNumericCellValue();
        }
        if (checkValidColumn(row, 8)) {
            volatility = row.getCell(8).getNumericCellValue();
        }
        if (checkValidColumn(row, 9)) {
            row.getCell(9).setCellType(CellType.STRING);
            String monthlyValue = row.getCell(9).getStringCellValue();
            if (monthlyValue.equals("-")) {
                logger.warn("WRONG value for monthlyReturn provide default Value");
                if (StockReportDefault.monthlyReturnDefault == Double.MIN_VALUE) {
                    StockReportDefault.setDefaultDouble();
                }
                monthlyReturn = StockReportDefault.monthlyReturnDefault;
            } else monthlyReturn = Double.parseDouble(monthlyValue);
        }
        if (checkValidColumn(row, 10)) {
            avgImpactCost = row.getCell(10).getNumericCellValue();
        }
        if (checkValidColumn(row, 11)) {
            returnDate = sdf.format(row.getCell(11).getDateCellValue());
        }
    }

    public static String industryFormatting(String industry) {
        industry = industry.replace("&", "AND");
        return industry.toUpperCase();
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getEquity() {
        return equity;
    }

    public void setEquity(String equity) {
        this.equity = equity;
    }

    public Double getFreeFloat() {
        return freeFloat;
    }

    public void setFreeFloat(Double freeFloat) {
        this.freeFloat = freeFloat;
    }

    public Double getWeightage() {
        return weightage;
    }

    public void setWeightage(Double weightage) {
        this.weightage = weightage;
    }

    public Double getBeta() {
        return beta;
    }

    public void setBeta(Double beta) {
        this.beta = beta;
    }

    public Double getR2() {
        return r2;
    }

    public void setR2(Double r2) {
        this.r2 = r2;
    }

    public Double getVolatility() {
        return volatility;
    }

    public void setVolatility(Double volatility) {
        this.volatility = volatility;
    }

    public Double getMonthlyReturn() {
        return monthlyReturn;
    }

    public void setMonthlyReturn(Double monthlyReturn) {
        this.monthlyReturn = monthlyReturn;
    }

    public Double getAvgImpactCost() {
        return avgImpactCost;
    }

    public void setAvgImpactCost(Double avgImpactCost) {
        this.avgImpactCost = avgImpactCost;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public boolean checkValidColumn(XSSFRow row, int column) {
        Cell c = row.getCell(column, MissingCellPolicy.RETURN_BLANK_AS_NULL);
        return c != null;
    }

    @Override
    public String toString() {
        return "StockReport [symbol=" + symbol + ", name=" + name + ", industry=" + industry + ", equity=" + equity + ", freeFloat=" + freeFloat + ", weightage=" + weightage + ", beta=" + beta + ", r2=" + r2 + ", volatility=" + volatility + ", monthlyReturn=" + monthlyReturn + ", avgImpactCost=" + avgImpactCost + ", returnDate=" + returnDate + "]";
    }


}
