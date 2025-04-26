import java.io.FileInputStream;
import java.io.IOException;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class dataDriven {

	public static void main(String[] args) throws IOException {
		
		FileInputStream fis = new FileInputStream("D:\\Documents\\Career\\My Projects\\Selenium Projects\\06-Selenium-Excel-Data-Driven-Testing-Functions\\ExcelDriven\\Test Data\\Test Data.xlxs");
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		
		int sheetCount = workbook.getNumberOfSheets();
		
		for(int i=0; i<sheetCount; i++) {
			if(workbook.getSheetName(i).equalsIgnoreCase("Sheet1")) {
				XSSFSheet mySheet =  workbook.getSheetAt(i);
			}
		}

	}

}
