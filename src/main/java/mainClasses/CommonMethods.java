package mainClasses;


import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.xwpf.usermodel.BreakType;
import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CommonMethods {

	// initializing global Variable
	XWPFDocument document = null;
	File wordFile = null;
	FileOutputStream out = null;

	public List<String> chapterUrls(WebDriver driver) throws Exception {
		Thread.sleep(10 * 1000);
		List<WebElement> naviagtor = new ArrayList<WebElement>();
		List<WebElement> chapterList = new ArrayList<WebElement>();
		List<String> chapterUrls = new ArrayList<String>();
		naviagtor = driver.findElement(By.xpath("//*[@id=\"collapse-1\"]/div/div")).findElements(By.tagName("div"));
		for (Integer i = 0; i < naviagtor.size(); i++) {
			WebElement e = naviagtor.get(i);
			chapterList = e.findElement(By.tagName("UL")).findElements(By.tagName("li"));
			for (WebElement cpt : chapterList) {
				String url = cpt.findElement(By.tagName("A")).getAttribute("href");
				chapterUrls.add(url);
			}
		}
		return chapterUrls;
	}

	public boolean writeWord(WebDriver driver, String site) {
		try {
			out = new FileOutputStream(wordFile);
			if (site.equals("webnovelonline")) {
				List<WebElement> paragraphs = driver.findElement(By.className("chapter-content"))
						.findElements(By.tagName("P"));
				for (Integer iterator = 0; iterator < paragraphs.size(); iterator++) {
					XWPFParagraph paragraph = document.createParagraph();
					XWPFRun run = paragraph.createRun();
					if (iterator == 0) {
						run.setBold(true);
						run.setUnderline(UnderlinePatterns.SINGLE);
						run.setText(paragraphs.get(iterator).getText());
					} else {
						run.setBold(false);
						run.setText(paragraphs.get(iterator).getText());
					}
					if (iterator == (paragraphs.size() - 1)) {
						run.addBreak(BreakType.PAGE);
					}
				}
				document.write(out);
				out.close();
			} else {
				System.out.println("defined to defaultBlock");
			}

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean createDocument(String name) {
		try {
			wordFile = new File("target/Docx/"+name + ".docx");
			document = new XWPFDocument();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean closeDocument() {
		try {
			document.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * refrence Code, do not use this, can use for reference
	 * 
	 * @param driver
	 * @param url
	 * @param file
	 * @throws Exception
	 */
	public void ReferenceCode(WebDriver driver, String url, String file) throws Exception {
		XWPFDocument documentx = new XWPFDocument();
		// Write the Document in file system
		File f = new File("solo_Leveling.docx");
		for (Integer i = 1; i <= 3; i++) {
			driver.get(url + String.valueOf(i));
			FileOutputStream outx = new FileOutputStream(f);
			String chapterName = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div[2]/div[1]/div[4]/p[1]"))
					.getText();
			XWPFParagraph title = document.createParagraph();
			// adding title
			XWPFRun run = title.createRun();
			run.setBold(true);
			run.setUnderline(UnderlinePatterns.SINGLE);
			run.setText(chapterName + ": ");
			for (WebElement e : driver.findElement(By.className("chapter-content")).findElements(By.tagName("P"))) {
				// adding paragraphs
				if (!e.getText().equals(chapterName)) {
					XWPFParagraph paragraph = document.createParagraph();
//						paragraph.setAlignment(ParagraphAlignment.);
					run = paragraph.createRun();
					run.setBold(false);
					run.setText(e.getText());
				}
			}
			// ading page break
			run.addBreak(BreakType.PAGE);
			documentx.write(outx);
			outx.close();
		}
		// closing document
		documentx.close();
	}

}
