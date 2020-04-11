package mainClasses;

import io.github.bonigarcia.wdm.DriverManagerType;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;


/**
 * class created just as an reference, original code in test package
 * @author arvin
 *
 */
public class Extractor extends CommonMethods {
	static WebDriver driver = null;
	static String url = "https://webnovelonline.com/chapter/rzi9736032421560195771/chapter-";

	public static void main(String[] args) {
		Extractor e=new Extractor();
	}

	public Extractor() {
		open();
		surf();
		close();
	}

	public void open() {
		WebDriverManager.getInstance(DriverManagerType.FIREFOX).setup();
		driver = new FirefoxDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.MINUTES);
	}

	public void surf() {

		createDocument("SoloLevelingX");
		for (Integer i = 1; i <= 269; i++) {
			driver.get(url + String.valueOf(i));
			writeWord(driver, "webnovelonline");
		}
		closeDocument();

	}

	public void close() {

		if (driver != null)
			driver.quit();
	}
}
