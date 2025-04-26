import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class dataDriven {

	public static void main(String[] args) throws IOException {
		
		
		//Once column is identified then scan entire testcase column to identify purchase testcase row
		//After you grab purchase testcase row = pull all the data of that row and feed into test
		
		FileInputStream fis = new FileInputStream("D:\\Documents\\Career\\My Projects\\Selenium Projects\\06-Selenium-Excel-Data-Driven-Testing-Functions\\ExcelDriven\\Test Data\\Test Data.xlxs");
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		
		int sheetCount = workbook.getNumberOfSheets();
		
		for(int i=0; i<sheetCount; i++) {
			if(workbook.getSheetName(i).equalsIgnoreCase("Sheet1")) {
				XSSFSheet mySheet =  workbook.getSheetAt(i);
				//Identify testcase column by scanning the entire 1st row
				Iterator<Row> rows = mySheet.iterator();
				Row firstrow = rows.next(); //Comes to the header row
				Iterator<Cell> cell= firstrow.cellIterator();
				
				while(cell.hasNext()) {
					Cell cellValue = cell.next();
					if(cellValue.getStringCellValue().equalsIgnoreCase("Test Cases")) {
						//Desired column
					}
				}
			}
		}

	}

}
