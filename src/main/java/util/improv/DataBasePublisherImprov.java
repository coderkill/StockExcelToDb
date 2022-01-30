package util.improv;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import common.Connector;
import common.DataBaseName;
import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import pojo.StockReport;
import util.DataBasePublisher;
import util.StockClassifier;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class DataBasePublisherImprov {
    final static Logger logger = Logger.getLogger(DataBasePublisher.class.getName());
    static String jdbc_insert_sql_stock = "INSERT INTO " + DataBaseName.stocks + "(Symbol, StockName, IndustryId) VALUES" + "(?,?,?)";
    static String jdbc_insert_sql = "INSERT INTO " + DataBaseName.stockinfo + "(Symbol, EquityCapital, FreeFloatMarketCapitilisation, Weightage, Beta, R2, VolatilityPer, MonthlyReturn, AvgImpactCostPercent, Report) VALUES" + "(?,?,?,?,?,?,?,?,?,?)";
    static String jdbc_insert_sql_industry = "INSERT INTO " + DataBaseName.industrytype + "(IndustryId, Industry) VALUES" + "(?,?)";
    static Connection con;

    public void insert(String inputFile) {
        try {
            con = Connector.getConnection();
            assert con != null;
            PreparedStatement sql_statement = (PreparedStatement) con.prepareStatement(jdbc_insert_sql);
            PreparedStatement sql_statement_stock = (PreparedStatement) con.prepareStatement(jdbc_insert_sql_stock);
            PreparedStatement sql_statement_industry = (PreparedStatement) con.prepareStatement(jdbc_insert_sql_industry);

            FileInputStream input = new FileInputStream(inputFile);

            recovery(sql_statement);

            XSSFWorkbook wb = new XSSFWorkbook(input);
            XSSFSheet sheet = wb.getSheetAt(0);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                XSSFRow row = sheet.getRow(i);
                StockReport stockReport = new StockReport(row);
                addIndustryTypeTable(sql_statement_industry, stockReport);
                addStockTable(sql_statement_stock, stockReport);
                addStockInfoTable(sql_statement, stockReport);
                if (logger.isDebugEnabled()) {
                    logger.debug(stockReport);
                }
            }
            sql_statement_industry.executeBatch();
            sql_statement_stock.executeBatch();
            sql_statement.executeBatch();
            wb.close();
            input.close();
            logger.info("All records successfully imported");
        } catch (SQLException | IOException ex) {
            logger.error(ex);
        }
    }


    public void addStockTable(PreparedStatement sql_statement_stock, StockReport stockReport) {
        String name = stockReport.getName();
        String symbol = stockReport.getSymbol();
        if (!StockClassifier.stockPresent(symbol)) {
            try {
                if (name.isEmpty()) {
                    logger.info("Enter name of company for Symbol " + stockReport.getSymbol() + " as name is not present");
                    Scanner sc = new Scanner(System.in);
                    name = sc.nextLine();
                }
                sql_statement_stock.setString(1, symbol);
                sql_statement_stock.setString(2, name);
                sql_statement_stock.setInt(3, IndustryClassifierImprov.fetchUp(stockReport.getIndustry()));
                sql_statement_stock.addBatch();
                StockClassifier.addToStockSet(symbol);
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                logger.error(e);
            }
        }
    }

    public void addStockInfoTable(PreparedStatement sql_statement, StockReport stockReport) {
        try {
            sql_statement.setString(1, stockReport.getSymbol());
            sql_statement.setLong(2, Long.parseLong(stockReport.getEquity()));
            sql_statement.setDouble(3, stockReport.getFreeFloat());
            sql_statement.setDouble(4, stockReport.getWeightage());
            sql_statement.setDouble(5, stockReport.getBeta());
            sql_statement.setDouble(6, stockReport.getR2());
            sql_statement.setDouble(7, stockReport.getVolatility());
            sql_statement.setDouble(8, stockReport.getMonthlyReturn());
            sql_statement.setDouble(9, stockReport.getAvgImpactCost());
            sql_statement.setDate(10, java.sql.Date.valueOf(stockReport.getReturnDate()));
            sql_statement.addBatch();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            logger.error(e);
        }
    }

    public void addIndustryTypeTable(PreparedStatement sql_statement_industry, StockReport stockReport) {
        try {
            String industry = stockReport.getIndustry();
            if (!IndustryClassifierImprov.industryId(industry)) {
                if (industry.isEmpty()) {
                    if (!StockClassifier.stockPresent(stockReport.getSymbol())) {
                        logger.info("Enter Industry for Symbol " + stockReport.getSymbol() + " as industry is not present");
                        Scanner sc = new Scanner(System.in);
                        industry = sc.nextLine();
                    } else {
                        return;
                    }
                    stockReport.setIndustry(industry);
                    if (IndustryClassifierImprov.industryId(industry)) {
                        return;
                    }
                }
                int industryId = IndustryClassifierImprov.fetchUp(industry);
                sql_statement_industry.setInt(1, industryId);
                sql_statement_industry.setString(2, industry);
                sql_statement_industry.addBatch();
                IndustryClassifierImprov.addIndustry(industry);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            logger.error(e);
        }
    }

    public void recovery(PreparedStatement sql_statement) {
        IndustryClassifierImprov.setUp(sql_statement);
        StockClassifier.setUp(sql_statement);
    }

}
