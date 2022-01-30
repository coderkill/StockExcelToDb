package util;

import com.mysql.jdbc.PreparedStatement;
import common.DataBaseName;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.Set;

public class StockClassifier {
    final static Logger logger = Logger.getLogger(StockClassifier.class.getName());
    private static final Set<String> stockSet = new LinkedHashSet<>();

    public static boolean stockPresent(String symbol) {
		return stockSet.contains(symbol);
    }

    //Multiple DB hits
    public static boolean stockPresent(PreparedStatement preparedStatement, String symbol) {
        try {
            ResultSet rs = preparedStatement.executeQuery("select Symbol from "+ DataBaseName.stocks + " where Symbol=\"" + symbol + "\"");
            rs.next();
			return rs.getString(1) != null && !rs.getString(1).isEmpty();
        } catch (SQLException e) {
            logger.error(e);
        }
        return false;
    }

    public static void setUp(PreparedStatement prep) {
        try {
            ResultSet rs = prep.executeQuery("select Symbol from "+ DataBaseName.stocks);
            while (rs.next()) {
                String symbol = rs.getString("Symbol");
                logger.info(symbol + " Added");
                stockSet.add(symbol);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            logger.error(e);
        }
    }

    public static void addToStockSet(String symbol) {
        stockSet.add(symbol);
    }
}
