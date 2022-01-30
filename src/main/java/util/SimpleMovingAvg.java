package util;

import common.DataBaseName;
import pojo.Sma;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import common.Connector;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SimpleMovingAvg {
    final static Logger logger = Logger.getLogger(SimpleMovingAvg.class.getName());
    static Map<String, Sma> smaMap = new HashMap<>();
    static Connection con;
    static String sqlQuery = "select Symbol,EquityCapital,Report from "+ DataBaseName.stockinfo;

    public static void SMA(int windowSize, ResultSet rs, LocalDate localDate) {
        try {
            LocalDate localDateMinus = localDate.minusMonths(windowSize);
            logger.info("Calculating between " + localDateMinus + " To " + localDate);
            while (rs.next()) {
                LocalDate date = LocalDate.parse(rs.getDate("Report").toString());
                String symbol = rs.getString("Symbol");
                long equity = Long.parseLong(rs.getString("EquityCapital"));
                if (date.isBefore(localDate) && date.isAfter(localDateMinus)) {
                    if (smaMap.containsKey(symbol)) {
                        smaMap.get(symbol).countUpdate();
                        smaMap.get(symbol).updateTotal(equity);
                    } else {
                        Sma sma = new Sma(1, equity);
                        smaMap.put(symbol, sma);
                    }
                }
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            logger.error(e);
        }
    }

    public static void fetchFromDb() {
        try {
            Scanner input = new Scanner(System.in);
            logger.info("Please enter the windowsize");
            int windowSize = Integer.parseInt(input.nextLine());
            logger.info("Please enter the start date, please enter format as yyyy-MM-dd");
            LocalDate date = LocalDate.parse(input.nextLine());
            con = Connector.getConnection();
            assert con != null;
            PreparedStatement sql_statement = (PreparedStatement) con.prepareStatement(sqlQuery);
            ResultSet rs = sql_statement.executeQuery();
            SMA(windowSize, rs, date);
            displayResults();
            smaMap.clear();
            con.close();
        } catch (SQLException | DateTimeParseException ex) {
            logger.error(ex);
        }
    }

    public static void displayResults() {
        smaMap.forEach((symbol, sma) -> {
            double movingAverage = sma.getTotal() / sma.getCount();
            logger.info("Symbol: " + symbol + "------ Moving average: " + movingAverage);
        });
    }
}
