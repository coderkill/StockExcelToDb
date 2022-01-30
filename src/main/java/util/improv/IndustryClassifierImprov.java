package util.improv;

import com.mysql.jdbc.PreparedStatement;
import common.DataBaseName;
import org.apache.log4j.Logger;
import util.IndustryClassifier;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class IndustryClassifierImprov {
    final static Logger logger = Logger.getLogger(IndustryClassifier.class.getName());
    private static int id = 1;
    private static final Map<String, Integer> industryMap = new HashMap<>();

    public static boolean industryId(String industry) {
        return industryMap.containsKey(industry);
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
            ResultSet rs = prep.executeQuery("select * from " + DataBaseName.industrytype);
            while (rs.next()) {
                String industry = rs.getString("Industry");
                Integer industryId = rs.getInt("IndustryId");
                logger.info(industry + " Added");
                industryMap.put(industry, industryId);
            }
            rs = prep.executeQuery("select MAX(IndustryId) as maximum from " + DataBaseName.industrytype);
            rs.next();
            id = rs.getInt("maximum");
            if (id == 0) {
                id = 1;
            }
            else {
                id++;
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            logger.error(e);
        }
    }

    public static int fetchUp(String industry) {
        if (industryMap.containsKey(industry)) {
            return industryMap.get(industry);
        }
        return id;
    }

    public static void addIndustry(String industry) {
        industryMap.put(industry, id);
        id++;
    }

}
