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
	String url = "https://www.readlightnovel.org/battle-through-the-heavens/";

	@Test
	public void f() throws Exception 
	{
		driver.get(url);
		List<String> urls=chapterUrls(driver);
		for(String url:urls) {
			driver.get(url);
		}
	}

	@BeforeMethod
	public void beforeMethod() {
		//System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		WebDriverManager.getInstance(DriverManagerType.FIREFOX).setup();
		driver = new FirefoxDriver();
		driver.get(url);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.MINUTES);
	}

	@AfterMethod
	public void afterMethod() {
		if (driver != null)
			driver.close();
	}
	
	

}
