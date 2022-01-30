package util;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import common.Connector;
import common.DataBaseName;
import org.apache.log4j.Logger;

public class CapitalCalculator {

	final static Logger logger = Logger.getLogger(CapitalCalculator.class.getName());
	static Connection con;
	static String sqlQuery = "select Symbol, MAX(EquityCapital) as Maximum ,MIN(EquityCapital) as Minimum, YEAR(Report) as year from " + DataBaseName.stockinfo +" group by Symbol,year";

	public static void calculatMaxMin() {
		try {
			con = Connector.getConnection();
			assert con != null;
			PreparedStatement sql_statement = (PreparedStatement) con.prepareStatement(sqlQuery);
			ResultSet rs = sql_statement.executeQuery();
			while(rs.next()) {
				String symbol = rs.getString("Symbol");
				long maxValue = rs.getLong("Maximum");
				long minValue = rs.getLong("Minimum");
				String year = rs.getString("year");
				logger.info("Symbol: " + symbol + ",   Max Value: " + maxValue + "  ,Min Value: " + minValue + " , YEAR: "+year);
			}
		} catch (SQLException ex) {
			logger.error(ex);
		}
	}
}
