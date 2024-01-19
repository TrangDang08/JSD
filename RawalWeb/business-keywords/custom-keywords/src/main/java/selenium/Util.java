package selenium;

import java.time.Duration;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
// import org.testng.Assert;
import org.testng.Assert;

import com.fpt.ivs.at.core.keywords.webkeyword.WaitForKeyword;
// import com.fpt.ivs.at.core.keywords.mobilekeyword.VerifyElementKeyword;
import com.fpt.ivs.at.core.object.UIObject;
// import com.fpt.ivs.at.core.utilities.Utilities;
import com.fpt.ivs.at.core.utilities.WebDriverUtilities;

public class Util {
    RemoteWebDriver driver = WebDriverUtilities.getDriver();
	WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(40));
	WaitForKeyword waitKeyword = new WaitForKeyword();

    public void printTitle(UIObject obj) {
		List<WebElement> title = obj.convertToWebElementList(driver);
		for (int i = 0; i < title.size(); i++) {
			System.out.println(title.get(i).getText());
		}
	}

	public void compareElementsText(UIObject obj, String str, String separator) {
		List<WebElement> elements = obj.convertToWebElementListWithTimeout(driver, 10);
		String[] text = str.split(separator);
		for (int i = 0; i < text.length; i++) {
			Assert.assertEquals(elements.get(i).getText(), text[i]);
		}
		
	}

	public void waitAndClick(UIObject obj) {
		// wait.until(ExpectedConditions.visibilityOf(obj.convertToWebElement(driver))).click();
		waitKeyword.waitForElementVisible(obj, 10);
		obj.convertToWebElement(driver).click();
	}

	public void changeSortDesc(UIObject obj) {
		WebElement column = obj.convertToWebElement(driver);
		String sortType = column.getAttribute("class");
		if (!sortType.contains("sorting_desc")) {
			column.click();
		}
	}

	public String generateSlug() {
		Random rand = new Random();
		return "TrangDT120" + rand.nextInt();
	}

	// VerifyElementKeyword wKeyword = new VerifyElementKeyword();
		// wKeyword.verifyElementAttributeValue(target, attributeName, expectedValue, timeout);
		// Utilities utilities = new Utilities();
		// utilities.waitForElementPresent(driver, target, timeout);
}