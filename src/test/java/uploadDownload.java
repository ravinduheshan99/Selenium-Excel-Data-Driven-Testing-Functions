import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class uploadDownload {
	

    // Function to update the Excel file at a specified row and column with the new value
    private static boolean updateCell(String fileName, int row, int col, String updatedValue) throws IOException {
        FileInputStream fis = new FileInputStream(fileName);
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFSheet sheet = workbook.getSheet("Sheet1");

        Row rowField = sheet.getRow(row - 1);
        Cell cellField = rowField.getCell(col - 1);
        cellField.setCellValue(updatedValue);  // Update the cell with the new value

        FileOutputStream fos = new FileOutputStream(fileName);
        workbook.write(fos);

        workbook.close();
        fis.close();
        fos.close();

        return true;
    }

    // Function to find the row number of the given fruit name in the Excel file
    private static int getRowNumber(String fileName, String text) throws IOException {
        FileInputStream fis = new FileInputStream(fileName);
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFSheet sheet = workbook.getSheet("Sheet1");

        Iterator<Row> rows = sheet.iterator();
        int k = 1;
        int rowIndex = -1;

        // Iterate through the rows and find the row with the given text (fruit name)
        while (rows.hasNext()) {
            Row row = rows.next();
            Iterator<Cell> cells = row.cellIterator();

            while (cells.hasNext()) {
                Cell cell = cells.next();
                if (cell.getCellType() == CellType.STRING && cell.getStringCellValue().equalsIgnoreCase(text)) {
                    rowIndex = k;  // Store the row index where the fruit name is found
                }
            }
            k++;
        }

        workbook.close();
        fis.close();
        return rowIndex;
    }

    // Function to find the column number of the given column name in the Excel file
    private static int getColumnNumber(String fileName, String colName) throws IOException {
        FileInputStream fis = new FileInputStream(fileName);
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFSheet sheet = workbook.getSheet("Sheet1");

        Iterator<Row> rows = sheet.iterator();
        Row firstRow = rows.next();  // Get the first row to identify columns
        Iterator<Cell> ce = firstRow.cellIterator();

        int k = 1;
        int column = 0;

        // Iterate through the first row to find the column number of the specified column name
        while (ce.hasNext()) {
            Cell value = ce.next();
            if (value.getStringCellValue().equalsIgnoreCase(colName)) {
                column = k;  // Store the column number where the column name matches
            }
            k++;
        }

        workbook.close();
        fis.close();

        System.out.println(column);
        return column;
    }

    public static void main(String[] args) throws IOException {

        String fruitName = "Apple";
        String updatedValue = "350";
        String fileName = "C:/Users/Ravindu Haputhanthri/Downloads/download.xlsx";
        
        // Initialize WebDriver for Chrome browser
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(7));
        driver.get("https://rahulshettyacademy.com/upload-download-test/index.html");

        // Click on the download button to download the Excel file
        driver.findElement(By.cssSelector("#downloadButton")).click();
        
        // Get the column number for 'price' and row number for 'Apple'
        int col = getColumnNumber(fileName, "price");
        int row = getRowNumber(fileName, "Apple");
        
        // Update the cell in the Excel sheet with the new price value
        Assert.assertTrue(updateCell(fileName, row, col, updatedValue));

        // Upload the modified Excel file
        WebElement upload = driver.findElement(By.cssSelector("input[type='file']"));
        upload.sendKeys(fileName);

        // Wait for the success toast message to appear after file upload
        //By popupLocator = By.cssSelector(".Toastify__toast-body div:nth-child(2");
        By popupLocator = By.xpath("//div[contains(@class,'Toastify__toast-body')]/div[text()='Updated Excel Data Successfully.']");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement popup = wait.until(ExpectedConditions.visibilityOfElementLocated(popupLocator));
        
        // Validate the success message in the toast popup
        String toastText = popup.getText();
        Assert.assertEquals("Updated Excel Data Successfully.", toastText);
        System.out.println("Popup message shown: " + toastText);

        // Wait for the popup to disappear after the success message is shown
        wait.until(ExpectedConditions.invisibilityOfElementLocated(popupLocator));
        System.out.println("Popup disappeared.");
        
        // Verify that the updated price value is displayed correctly in the web table
        String priceColumn = driver.findElement(By.xpath("//div[text()='Price']")).getAttribute("data-column-id");
        String actualPrice = driver.findElement(By.xpath("//div[text()='" + fruitName + "']/parent::div/parent::div/div[@id='cell-" + priceColumn + "-undefined']")).getText();
        Assert.assertEquals("350", actualPrice);
        
        System.out.println("Updated values validated successfully.");

        // Close the browser session after test completion
        driver.quit();
    }
}
