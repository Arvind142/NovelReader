package NovelExtractor;

import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.DriverManagerType;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.testng.annotations.BeforeMethod;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;

public class webnovelonline extends CommonMethods {
	WebDriver driver = null;
	String url = "https://webnovelonline.com/chapter/rzi9736032421560195771/chapter-";

	@Test
	public void f() throws Exception {
		createDocument("SoloLevelingX");
		for (Integer i = 1; i <= 3; i++) {
			driver.get(url + String.valueOf(i));
			writeWord(driver, "webnovelonline");
		}
		closeDocument();
	}

	@BeforeMethod
	public void beforeMethod() {
		// System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		WebDriverManager.getInstance(DriverManagerType.FIREFOX).setup();
		driver = new FirefoxDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.MINUTES);
	}

	@AfterMethod
	public void afterMethod() {
		if (driver != null)
			driver.quit();
	}

}
