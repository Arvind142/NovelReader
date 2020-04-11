package NovelExtractor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
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
	boolean firstpage=true;

	/**
	 * this url will get chapter url for readlightnovel site
	 * 
	 * @param driver
	 * @return
	 * @throws Exception
	 */
	public List<String> chapterUrls(WebDriver driver, String siteurl,String doc) throws Exception {
		List<String> chapterUrls = new ArrayList<String>();
		String url = "";
		File f = new File("Docx/"+doc+".txt");
		if (!f.exists()) {
			FileWriter write = new FileWriter(f);
			driver.get(siteurl);
			Thread.sleep(10 * 1000);
			List<WebElement> naviagtor = new ArrayList<WebElement>();
			List<WebElement> chapterList = new ArrayList<WebElement>();
			naviagtor = driver.findElement(By.xpath("//*[@id=\"collapse-1\"]/div/div")).findElements(By.tagName("div"));
			for (Integer i = 0; i < naviagtor.size(); i++) {
				WebElement e = naviagtor.get(i);
				chapterList = e.findElement(By.tagName("UL")).findElements(By.tagName("li"));
				for (WebElement cpt : chapterList) {
					url = cpt.findElement(By.tagName("A")).getAttribute("href");
					if(url.matches("(https://www.readlightnovel.org/).*(/chapter-).+")) {
						chapterUrls.add(url);
						write.write(url + "\n");
						System.out.println(url);
					}
				}
			}
			write.close();

		} else {
			BufferedReader reader = new BufferedReader(new FileReader(f));
			while ((url = reader.readLine()) != null) {
				chapterUrls.add(url);
				System.out.println(url);
			}
			reader.close();
		}
		return chapterUrls;
	}

	/**
	 * will be used to write word document
	 * 
	 * @param driver
	 * @param site
	 * @return
	 */
	public boolean writeWord(WebDriver driver, String site) {
		try {
			out = new FileOutputStream(wordFile);
			List<WebElement> paragraphs = null;
			if (site.contains("readlightnovel")) {
				paragraphs = driver.findElement(By.xpath("//div[@class=\"chapter-content3\"]/div[@class=\"desc\"]"))
						.findElements(By.tagName("P"));
			} else {
				paragraphs = driver.findElement(By.className("chapter-content")).findElements(By.tagName("P"));
			}
			for (Integer iterator = 0; iterator < paragraphs.size(); iterator++) {
				if(paragraphs.get(iterator).getText().trim().equals("")) {
					continue;
				}
				XWPFParagraph paragraph = document.createParagraph();
				XWPFRun run = paragraph.createRun();
				if (iterator == 0 || paragraphs.get(iterator).getText().matches("(C|c)(hapter ).+")) {
					if(firstpage==false) {
						run.addBreak(BreakType.PAGE);
					}
					firstpage=false;
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
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * create word doc @ runtime
	 * 
	 * @param name
	 * @return
	 */
	public boolean createDocument(String name) {
		try {
			wordFile = new File("Docx/" + name + ".docx");
			document = new XWPFDocument();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * closing word document
	 * 
	 * @return
	 */
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
