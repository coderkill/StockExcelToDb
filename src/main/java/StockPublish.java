import java.io.FileInputStream;
import java.text.SimpleDateFormat;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import pojo.StockReport;

public class StockPublish {
	public static void main(String[] args) throws Exception {
//		try {
//			Class.forName("com.mysql.jdbc.Driver");
//			Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/sakila?useSSL=false", "root",
//					"root");
//			PreparedStatement sql_statement = null;
//			String jdbc_insert_sql = "INSERT INTO stockinfo"
//					+ "(Symbol, EquityCapital, FreeFloatMarketCapitilisation, Weightage, Beta, R2, VolatilityPer, MonthlyReturn, AvgImpactCostPercent, Report) VALUES"
//					+ "(?,?,?,?,?,?,?,?,?,?)";
//			sql_statement = (PreparedStatement) con.prepareStatement(jdbc_insert_sql);
//			ResultSet rs = sql_statement.executeQuery("select * from actor");
////			System.out.println(rs);
//			while(rs.next()) {
//				String industry = rs.getString("first_name");
////				Date date = rs.getDate("last_update");
//				String date = rs.getString("last_update");
//				int x= rs.getInt("actor_id");
//				System.out.println(industry);
//				System.out.println(date);
//				System.out.println(x);
//
//			}
//			String jdbc_insert_sql_stock = "INSERT INTO stocks" + "(Symbol, StockName, IndustryId) VALUES" + "(?,?,?)";
//			PreparedStatement sql_statement_stock = (PreparedStatement) con.prepareStatement(jdbc_insert_sql_stock);
//			String jdbc_insert_sql_industry = "INSERT INTO industrytype" + "(IndustryId, Industry) VALUES" + "(?,?)";
//			PreparedStatement sql_statement_industry = (PreparedStatement) con
//					.prepareStatement(jdbc_insert_sql_industry);
			FileInputStream input = new FileInputStream("D:\\nifty_data.xlsx");
//
			XSSFWorkbook wb = new XSSFWorkbook(input);
			XSSFSheet sheet = wb.getSheetAt(0);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//            Iterator rows = sheet.rowIterator(); 
			int k=0;
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				k++;
				XSSFRow row = sheet.getRow(i);
				StockReport stockReport = new StockReport(row);
				System.out.println(stockReport);
//				System.out.println(row.toString());
//				String symbol = row.getCell(0).getStringCellValue();
//				String name = row.getCell(1).getStringCellValue();
//				String industry = row.getCell(2).getStringCellValue();
//				String equity = row.getCell(3).getStringCellValue();
//				Double freeFloat = row.getCell(4).getNumericCellValue();
//				Double weightage = row.getCell(5).getNumericCellValue();
//				Double beta = row.getCell(6).getNumericCellValue();
//				Double r2 = row.getCell(7).getNumericCellValue();
//				Double volatility = row.getCell(8).getNumericCellValue();
//				Double monthlyReturn = row.getCell(9).getNumericCellValue();
//				Double avgImpactCost = row.getCell(10).getNumericCellValue();
//				String returnDate = sdf.format(row.getCell(11).getDateCellValue());
			}
			System.out.println(k);
//
//				sql_statement.setString(1, symbol);
//				sql_statement.setString(2, equity);
//				sql_statement.setDouble(3, freeFloat);
//				sql_statement.setDouble(4, weightage);
//				sql_statement.setDouble(5, beta);
//				sql_statement.setDouble(6, r2);
//				sql_statement.setDouble(7, volatility);
//				sql_statement.setDouble(8, monthlyReturn);
//				sql_statement.setDouble(9, avgImpactCost);
//				sql_statement.setDate(10, java.sql.Date.valueOf(returnDate));
//				sql_statement.addBatch();
//
//				sql_statement_stock.setString(1, symbol);
//				sql_statement_stock.setString(2, name);
////				sql_statement_stock.setInt(3, IndustryClassifier.industryId(industry));
////				sql_statement_stock.addBatch();
////
////				sql_statement_industry.setInt(1, IndustryClassifier.industryId(industry));
////				sql_statement_industry.setString(2, industry);
////				sql_statement_industry.addBatch();
//
//				System.out.println("Import rows " + i);
//			}
////            while(rows.hasNext()) {        
////                HSSFRow row = (HSSFRow) rows.next(); 
////                Iterator cells = row.cellIterator();
////                    while(cells.hasNext()) {
////                        HSSFCell cell =  (HSSFCell) cells.next();
////                        switch(cell.getCellType()) { 
////                        case STRING: //handle string columns
////                                sql_statement.setString(1,  
////                                           cell.getStringCellValue());                                                                                     
////                                break;
////                        case NUMERIC: //handle double data
////                                    sql_statement.setDouble(2,cell.getNumericCellValue() );
////                                break;
////                        }
////                    }
////            }
//
//			sql_statement.executeBatch(); // we can execute the statement before
//			sql_statement_stock.executeBatch();
//			sql_statement_industry.executeBatch();

//			con.commit();
//			sql_statement.close();
//			con.close();
//			wb.close();
//			input.close();
			System.out.println("Success import excel to mysql table");

//		} catch (ClassNotFoundException e) {
//			System.out.println(e);
//		} catch (SQLException ex) {
//			System.out.println(ex);
//		} 
//		catch (IOException ioe) {
//			System.out.println(ioe);
//		}

	}
}
