import common.Connector;
import org.apache.log4j.Logger;
import util.CapitalCalculator;
import util.SimpleMovingAvg;
import util.improv.DataBasePublisherImprov;

import java.util.Scanner;

public class MainMethod {

    final static Logger logger = Logger.getLogger(MainMethod.class.getName());

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int flag = 0;
        do {
            logger.info("MENU");
            logger.info("1.Insert Excel sheet into DB");
            logger.info("2.SMA");
            logger.info("3.High and low for Equity Capital");
            logger.info("Enter anything else to EXIT");
            String option = sc.nextLine();
            switch (option) {
                case "1":
                    logger.info("Enter input file");
                    String inputFile = sc.nextLine();
//                    new DataBasePublisherImprov().insert("/nifty_data.xlsx");
                    new DataBasePublisherImprov().insert(inputFile);
                    break;
                case "2":
                    SimpleMovingAvg.fetchFromDb();
                    break;
                case "3":
                    CapitalCalculator.calculatMaxMin();
                    break;
                default:
                    logger.info("Quitting");
                    flag = 1;
                    break;
            }
        } while (flag != 1);
        sc.close();
        Connector.closeConnection();

    }
}
