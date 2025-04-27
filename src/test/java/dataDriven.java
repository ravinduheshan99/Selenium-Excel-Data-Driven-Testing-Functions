import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class dataDriven {

	public static void main(String[] args) throws IOException {
		
		// Load Excel file
		FileInputStream fis = new FileInputStream("D:\\Documents\\Career\\My Projects\\Selenium Projects\\06-Selenium-Excel-Data-Driven-Testing-Functions\\ExcelDriven\\Test Data\\Test Data.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook(fis); // Create workbook instance
		int sheetCount = workbook.getNumberOfSheets(); // Get number of sheets
		
		for(int i=0; i<sheetCount; i++) {
			if(workbook.getSheetName(i).equalsIgnoreCase("Sheet1")) {
				
				XSSFSheet mySheet =  workbook.getSheetAt(i);
				
				// Identify "Test Cases" column
				Iterator<Row> rows = mySheet.iterator();
				Row firstrow = rows.next();
				Iterator<Cell> cell= firstrow.cellIterator();
				
				int k=0;
				int columnIndex=0;
				
				while(cell.hasNext()) {
					Cell cellValue = cell.next();
					if(cellValue.getStringCellValue().equalsIgnoreCase("Test Cases")) {
						columnIndex=k; // Found "Test Cases" column
					}
					k++;
				}
				System.out.println("Column Index : "+columnIndex);
				
				
				// Ready to scan rows for specific test cases
				while(rows.hasNext()) {
					Row r = rows.next();
					if(r.getCell(columnIndex).getStringCellValue().equalsIgnoreCase("Purchase")) {
						Iterator<Cell> cv = r.cellIterator();
						
						while(cv.hasNext()) {
							System.out.println(cv.next().getStringCellValue());
						}
					}
				}
			}
		}
	}
}
