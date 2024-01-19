package function;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.fpt.ivs.at.core.utilities.WebDriverUtilities;

public class SubFunction {
    RemoteWebDriver driver = WebDriverUtilities.getDriver();
    WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(30));

    public void login (String email, String password) {
        WebElement emailInput = webDriverWait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("email"))));
        WebElement passInput = driver.findElement(By.id("password"));
        WebElement loginBtn = driver.findElement(By.xpath("//button[@type='submit']"));
        emailInput.sendKeys(email);
        passInput.sendKeys(password);
        loginBtn.click();
    }

    public void addProductCateLg(String name, String description) {
		List<WebElement> language = webDriverWait.until(ExpectedConditions
                .visibilityOfAllElementsLocatedBy(By.xpath("//div[contains(@class, 'tablang')]")) );
		for (int i = 0; i < language.size(); i++) {
            WebElement btnLanguage = language.get(i);
            btnLanguage.click();

            WebElement cateName = webDriverWait.until(ExpectedConditions
                    .visibilityOfElementLocated(By.name("name" + i)));	
            cateName.sendKeys(name);

            WebElement catedescription = driver.findElement(By.xpath("//p/ancestor::div[@contenteditable='true']"));
            catedescription.sendKeys(description);
        }
	}
}