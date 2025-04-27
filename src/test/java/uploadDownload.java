import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class uploadDownload {

    public static void main(String[] args) {

        // Define the fruit name to validate price against
        String fruitName = "Apple";

        // Initialize WebDriver (Chrome)
        WebDriver driver = new ChromeDriver();

        // Set implicit wait for finding elements
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(7));

        // Navigate to the upload/download test page
        driver.get("https://rahulshettyacademy.com/upload-download-test/index.html");

        // Trigger file download (optional here, as file is already uploaded in next step)
        driver.findElement(By.id("downloadButton")).click();

        // Locate the file input element and upload the specified Excel file
        WebElement upload = driver.findElement(By.cssSelector("input[type='file']"));
        upload.sendKeys("C:/Users/Ravindu Haputhanthri/Downloads/updated_data.xlsx");

        // Define the locator for the success toast/popup message
        By popupLocator = By.xpath("//div[contains(@class,'Toastify__toast-body')]/div[text()='Updated Excel Data Successfully.']");

        // Use explicit wait to wait until the toast appears (Selenium 4 with Duration)
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement popup = wait.until(ExpectedConditions.visibilityOfElementLocated(popupLocator));

        // Get the popup text and validate it using TestNG assertion
        String toastText = popup.getText();
        Assert.assertEquals(toastText, "Updated Excel Data Successfully.");
        System.out.println("Popup message shown: " + toastText);

        // Additional console log to confirm validation result
        if (popup.getText().equals("Updated Excel Data Successfully.")) {
            System.out.println("Success message validated.");
        } else {
            System.out.println("Unexpected message: " + popup.getText());
        }

        // Wait until the popup disappears from the screen
        wait.until(ExpectedConditions.invisibilityOfElementLocated(popupLocator));
        System.out.println("Popup disappeared.");

        // Get the column ID of the 'Price' column using its label's data attribute
        String priceColumn = driver.findElement(By.xpath("//div[text()='Price']")).getAttribute("data-column-id");

        // Use the fruit name to locate its price cell in the table dynamically
        String actualPrice = driver.findElement(By.xpath("//div[text()='" + fruitName + "']/parent::div/parent::div/div[@id='cell-" + priceColumn + "-undefined']")).getText();

        // Assert that the updated price matches the expected value
        Assert.assertEquals(actualPrice, "350");

        // Log the successful validation of updated value
        System.out.println("Updated values validated successfully.");

        // Clean up and close the browser session
        driver.quit();
    }
}
