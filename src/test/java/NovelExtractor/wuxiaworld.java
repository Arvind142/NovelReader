package NovelExtractor;

import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.github.bonigarcia.wdm.DriverManagerType;
import io.github.bonigarcia.wdm.WebDriverManager;

import org.testng.annotations.BeforeMethod;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;

public class wuxiaworld {
	WebDriver driver = null;
	
	String url = "https://www.wuxiaworld.com/novel/battle-through-the-heavens/btth-chapter-1";
	@Test
	public void f() {
		System.out.println("Title Of Page: " + driver.getTitle() + " url: " + driver.getCurrentUrl());
		System.out.println("-----------------------------------------------------------------------");
		System.out.println("Content of Chapter");
		System.out.println("-----------------------------------------------------------------------");
		System.out.println(driver.findElement(By.id("chapter-content")).getText());
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
			driver.quit();
	}

}
