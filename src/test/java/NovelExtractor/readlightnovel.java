package NovelExtractor;

import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.DriverManagerType;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.testng.annotations.BeforeMethod;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import NovelExtractor.CommonMethods;

public class readlightnovel extends CommonMethods {
	WebDriver driver = null;
	String url = "https://www.readlightnovel.org/a-returners-magic-should-be-special";

	@Test
	public void f() throws Exception {
		String novelName=url.split("/")[url.split("/").length-1];
		createDocument(novelName);
		List<String> urls = chapterUrls(driver,url,novelName);
		for (String url : urls) {
//		for(int i=0;i<3;i++) {
//			driver.get(urls.get(i));
			driver.get(url);
			writeWord(driver, url);
		}
		closeDocument();
	}

	@BeforeMethod
	public void beforeMethod() {
		// System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		WebDriverManager.getInstance(DriverManagerType.FIREFOX).setup();
		driver = new FirefoxDriver();
		driver.get(url);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}

	@AfterMethod
	public void afterMethod() {
		if (driver != null)
			driver.close();
	}

}
