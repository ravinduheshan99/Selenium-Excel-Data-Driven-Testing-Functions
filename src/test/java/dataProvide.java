import java.io.FileInputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class dataProvide {
	
	DataFormatter formatter = new DataFormatter(); // Handles conversion of all cell types to String
	
	@Test(dataProvider="driveTest1")
	public void testCaseData1(String param1, String param2, int param3) {
		System.out.println(param1 + " " + param2 + " " + param3);
	}
	
	@DataProvider(name="driveTest1") 
	public Object[][] getData1() {
		// Hardcoded data provider
		Object[][] data = {{"Hello1", "Text1", 1}, {"Hello2", "Text2", 2}, {"Hello3", "Text3", 3}};
		return data;
	}
	
	@Test(dataProvider="driveTest2")
	public void testCaseData2(String param1, String param2, String param3) {
		System.out.println(param1 + " " + param2 + " " + param3);
	}
	
	@DataProvider(name="driveTest2") 
	public Object[][] getData2() throws IOException {
		// Read data from Excel
		FileInputStream fis = new FileInputStream("D:\\Documents\\Career\\My Projects\\Selenium Projects\\06-Selenium-Excel-Data-Driven-Testing-Functions\\ExcelDriven\\Test Data\\Test Data.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		int sheetCount = workbook.getNumberOfSheets();
		
		Object data[][] = null;
		
		for (int i = 0; i < sheetCount; i++) {
			// Load "Sheet4"
			if (workbook.getSheetName(i).equalsIgnoreCase("Sheet4")) {
				XSSFSheet mySheet = workbook.getSheetAt(i);
				
				int rowCount = mySheet.getPhysicalNumberOfRows(); // Total rows
				XSSFRow row = mySheet.getRow(0);
				int colCount = row.getLastCellNum(); // Total columns
				
				data = new Object[rowCount - 1][colCount]; // Skip header row
				
				for (int j = 0; j < rowCount - 1; j++) {
					row = mySheet.getRow(j + 1); // Skip header row
					
					for (int k = 0; k < colCount; k++) {
						XSSFCell cell = row.getCell(k);
						data[j][k] = formatter.formatCellValue(cell); // Convert any cell to string
					}
				}
			}
		}
		workbook.close();
		return data;
	}
}
