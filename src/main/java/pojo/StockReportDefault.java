package pojo;

import org.apache.log4j.Logger;

import java.util.Scanner;

public class StockReportDefault {
    final static Logger logger = Logger.getLogger(StockReportDefault.class.getName());

    public static String symbolDefault = "";
    public static String nameDefault = "";
    public static String industryDefault = "";
    public static String equityDefault = "";
    public static Double freeFloatDefault = Double.MIN_VALUE;
    public static Double weightageDefault = Double.MIN_VALUE;
    public static Double betaDefault = Double.MIN_VALUE;
    public static Double r2Default = Double.MIN_VALUE;
    public static Double volatilityDefault = Double.MIN_VALUE;
    public static Double monthlyReturnDefault = Double.MIN_VALUE;
    public static Double avgImpactCostDefault = Double.MIN_VALUE;
    public static String returnDateDefault = "";


    public static void setDefaultDouble() {
        Scanner sc = new Scanner(System.in);
        logger.info("Enter a Default value for MonthlyValue column (NOTE it will be used for other WRONG values)");
        try {
            monthlyReturnDefault = Double.parseDouble(sc.nextLine());
        } catch (NumberFormatException e) {
            logger.error(e);
        }
    }
}
