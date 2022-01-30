package util;

import com.mysql.jdbc.PreparedStatement;
import common.DataBaseName;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class IndustryClassifier {
    final static Logger logger = Logger.getLogger(IndustryClassifier.class.getName());
    public static Set<String> industrySet = new HashSet<>();


    public static boolean industryId(String industry) {
        return industrySet.contains(industry);
    }

    public static boolean industryId(PreparedStatement preparedStatement, String industry) {
        try {
            ResultSet rs = preparedStatement.executeQuery("select Industry from " + DataBaseName.industrytype + " where Industry=\"" + industry + "\"");
            rs.next();
            return rs.getString(1) != null && !rs.getString(1).isEmpty();
        } catch (SQLException e) {
            logger.error(e);
        }
        return false;
    }

    public static void setUp(PreparedStatement prep) {
        try {
            ResultSet rs = prep.executeQuery("select Industry from " + DataBaseName.industrytype);
            while (rs.next()) {
                String industry = rs.getString("Industry");
                logger.info(industry + " Added");
                industrySet.add(industry);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            logger.error(e);
        }
    }

    public static int fetchUp(PreparedStatement prep, String industry) {
        try {
            ResultSet rs = prep.executeQuery("select IndustryId from " + DataBaseName.industrytype + " where Industry=\"" + industry + "\"");
            rs.next();
            int id = rs.getInt("IndustryId");
            logger.info(industry + " value in Database is: " + id);
            return id;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            logger.error(e);
        }
        return -1;
    }

    public static void addIndustry(String industry) {
        industrySet.add(industry);
    }

}
