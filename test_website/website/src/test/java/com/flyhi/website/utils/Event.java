package com.flyhi.website.utils;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Navigation;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.Select;

import com.flyhi.website.driver.Driver;
import com.gargoylesoftware.htmlunit.javascript.host.Element;
import com.thoughtworks.selenium.webdriven.commands.KeyEvent;

public class Event {
	public static WebDriver driver;
	private static StringBuffer verificationErrors = new StringBuffer();
	public static final String PATH_SEPARATOR = File.separator;
	
	private enum browsertype
	{
		IE, FF, CHROME, HTMLUNIT, SAFARI;
	}
	
	public static void broswerConfig(String vBrowser) throws IOException 
	{
		String browser = vBrowser;
		browsertype browserType = browsertype.valueOf(browser);
		switch(browserType)
		{
			case IE:
				driver = new InternetExplorerDriver();
				break;
			
			case CHROME:
				
				String userDir = System.getProperty("user.dir");
				StringBuilder filename = new StringBuilder();
	        	filename.append(PATH_SEPARATOR);
	        	filename.append("BrowserDrivers");
	        	filename.append(PATH_SEPARATOR);
	        	if(userDir.contains("/"))
	        	{
	        		filename.append("chromedriver"); // Mac OS X or Linux 
	        	} else {
	        		filename.append("chromedriver.exe"); // Windows
	        	}
	        	System.out.println(userDir+filename.toString());
				System.setProperty("webdriver.chrome.driver",userDir+filename.toString());
				driver = new ChromeDriver();
				break;
				
			case HTMLUNIT:
				driver = new HtmlUnitDriver();
				break;
			
			case FF:
				driver = new FirefoxDriver();
				break;
			case SAFARI:
				driver = new SafariDriver();
				break;
		}	
		driver.manage().window().maximize();
		
	}

	public static void implicitWait() 
	{
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	/*
	 * Deleting all cookies 
	 */
	public static void clearCookies()
	{
		driver.manage().deleteAllCookies();
	}
	/*
	 * Entering into a website
	 */
	public static void enterSite() 
	{
		driver.navigate().to(Driver.vURL);
	}
	/*
	 * Press Enter
	 */
	public static void enter(String fXPath)
	{
		driver.findElement(By.xpath(Driver.element.getProperty(fXPath))).sendKeys(Keys.ENTER);
	}
	/*
	 * Click A Button
	 */
	public static void clickButton(String fXPath) 
	{
		driver.findElement(By.xpath(Driver.element.getProperty(fXPath))).click();
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		
	}
	/*
	 * Clicking on a Link
	 */
	public static void clickLink(String fLinkName) 
	{
		driver.findElement(By.xpath(Driver.element.getProperty(fLinkName))).click();
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
	}
	/*
	 * Input a text into a Text Field
	 */
	public static void editInput(String fXPath, String fText) 
	{
		driver.findElement(By.xpath(Driver.element.getProperty(fXPath))).clear();		
		driver.findElement(By.xpath(Driver.element.getProperty(fXPath))).sendKeys(fText);
	}
	/*
	 * Enter date
	 */
	public static void editDate(String fXPath, String fText)
	{
		driver.findElement(By.xpath(Driver.element.getProperty(fXPath))).clear();		
		driver.findElement(By.xpath(Driver.element.getProperty(fXPath))).sendKeys(fText);
	}
	/*
	 * Input in Auto Fill Field
	 */
	public static void editAutoFill(String fXPath, String fText, String fXPath1)
	{
		driver.findElement(By.xpath(Driver.element.getProperty(fXPath))).clear();		
		driver.findElement(By.xpath(Driver.element.getProperty(fXPath))).sendKeys(fText);
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		driver.findElement(By.xpath(Driver.element.getProperty(fXPath1))).click();
	}
	/*
	 * Select From Dropdown List
	 */
	public static void selectDropdown(String fXPath, String fItemName) 
	{
		Select select = new Select(driver.findElement(By.xpath(fXPath)));
		select.selectByVisibleText(fItemName);
	}
	/*
	 * Check element is selected by default
	 */
	public static void defaultSelected(String fXPath) 
	{
		driver.findElement(By.xpath(Driver.element.getProperty(fXPath))).getAttribute("class").contains("active");
	}
	/*
	 * Get Element Text
	 */
	public static String getTextData(String eID)
	{
		WebElement output = driver.findElement(By.xpath(eID));
		return output.getText();
	}
	
	/*
	 * Verify Page Title
	 */
	public static boolean verifyElement(String fXPath)
	{
		return driver.findElement(By.xpath(Driver.element.getProperty(fXPath))).isDisplayed();
	}
	/*
	 * Get URL
	 */
	public String getCurrentUrl() 
	{
		return driver.getCurrentUrl();
	}
	/*
	 * Get Title
	 */
	public static String getTitle() 
	{
		return driver.getTitle();
	}
	/* 
	 * Get List Length
	 */
	public static List<WebElement> getLength(String eID)
	{
		List<WebElement> output = driver.findElements(By.xpath(eID));
		return output;
	}
	/*
	 * Getting Element Height
	 */
	public static double getElementHeight(String target) 
	{
		return driver.findElement(By.xpath(target)).getSize().getHeight();
	}

	/*
	 * Getting Element Width
	 */
	public static double getElementWidth(String target) 
	{
		return driver.findElement(By.xpath(target)).getSize().getWidth();
	}
	/*
	 * Switch to active element
	 */
	public static WebElement switchTo() 
	{
		return driver.switchTo().activeElement();
	}
	/*
	 * Accepting / Dismiss alerts
	 */
	public static void alertEvent(String vIP1) 
	{
		Alert alert = driver.switchTo().alert();
		alert.getText();
		if(vIP1.equals(alert.getText()))
		{		
			// And acknowledge the alert (equivalent to clicking "OK")
			alert.accept();
		}
		else
		{
			//And acknowledge the alert (equivalent to clicking "Dismiss")
			alert.dismiss();			
		}
	}
	
	/*
	 * Navigate function
	 */
	public static Navigation navigate() 
	{
		return driver.navigate();
	}
	/*
	 * Browser Refresh
	 */
	public static void refresh() 
	{
		driver.navigate().refresh();
	}
	/*
	 * Browser Back
	 */
	public static void backward() 
	{
		driver.navigate().back();
	}
	/*
	 * Browser Next
	 */
	public static void forward() 
	{
		driver.navigate().forward();
	}
	/*
	 * Quit the driver
	 */
	public static void driverQuit()
	{
		driver.quit();
	}
	
	/* 
	 * Save screenshot 
	 */
	public static void saveScreenshot() 
	{
		WebElement partialImg = driver.findElement(By.xpath("/html/body"));
		try 
		{
			File pngFile = new File(Driver.resultPath + "/" + Driver.vTCName + "_" + Driver.vStep + "_" + Driver.vDesc + ".png");
			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			Point p = partialImg.getLocation();
	        int width = partialImg.getSize().getWidth();
	        int height = partialImg.getSize().getHeight();
	        Rectangle rect = new Rectangle(width, height);
	        BufferedImage img = null;
	        img = ImageIO.read(scrFile);
	        BufferedImage dest = img.getSubimage(p.getX(), p.getY(), rect.width, rect.height);
	        ImageIO.write(dest, "png", scrFile);
			FileUtils.copyFile(scrFile, pngFile);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	/* 
	 * Find by Regular Expression
	 */
	public static void regular_expression(String eID, String eText, String reqExpr)
	{
		Pattern r = Pattern.compile(reqExpr);
		Matcher m = r.matcher(eText);
		
		if (m.find( )) 
		{
			System.out.println("Found value: " + m.group(0) );
			System.out.println("Found value: " + m.group(1) );
			driver.findElement(By.xpath(eID)).sendKeys(m.group(1));
			System.out.println("Found value: " + m.group(2) );
		} 
		else 
		{
         System.out.println("NO MATCH");
      }
	}

	/*
	 * Browser Stop
	 */
	public static void browserStop() 
	{
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) 
		{
			fail(verificationErrorString);
		}
	}

	private static void fail(String verificationErrorString) 
	{
		// TODO Auto-generated method stub
	}

	/* 
	 * DateTimeStamp
	 */
	public static String dateTimeStamp() 
	{
		Date date = new Date();
		SimpleDateFormat sdf;
		sdf = new SimpleDateFormat("MMMddhhmmaaa");
		return (sdf.format(date));
	}

	/* 
	 * Highlight Element
	 */
	public static void highlightElement(String element) 
	{
		WebElement highLightData = driver.findElement(By.xpath(element));
	    for (int i = 0; i < 2; i++) 
	    {
	        JavascriptExecutor js = (JavascriptExecutor) driver;
	        js.executeScript("arguments[0].setAttribute('style', arguments[1]);",
	        		highLightData, "border: 4px solid green;");
	        try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        js.executeScript("arguments[0].setAttribute('style', arguments[1]);",
	        		highLightData, "");
	    }
	}
	
	/*
	 * Highlight Error Element
	 */
	public static void errorHighlightElement(String element) 
	{
		WebElement highLightData = driver.findElement(By.xpath(element));
		
	    for (int i = 0; i < 2; i++) 
	    {
	        JavascriptExecutor js = (JavascriptExecutor) driver;
	        js.executeScript("arguments[0].setAttribute('style', arguments[1]);",
	        		highLightData, "border: 4px solid red;");
	        
	        try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	       js.executeScript("arguments[0].setAttribute('style', arguments[1]);",highLightData, "");
	       saveScreenshot(); 
	       
	    }
	}
	
	/*
	 * Scroll 
	 */
	public static void scroll()
	{
		 JavascriptExecutor js = (JavascriptExecutor) driver;
		 js.executeScript("window.scrollTo(0,400)");
	}
	
	
	/*
	 * List Data
	 */
	public static void listData(String eID)
	{
        List<WebElement> allSuggestions = driver.findElements(By.xpath(eID));
        
        for (WebElement suggestion : allSuggestions) 
        {
            System.out.println(suggestion.getText());
        }

	}
	
	/*
	 * File Upload
	 */
	public static void fileUpload() 
	{
		 try 
		 {
			 Runtime.getRuntime().exec("-- UPLOAD THE PATH OF THE FILE TO UPLOAD--");
	     } 
		 catch (IOException e) 
		 {
	            e.printStackTrace();
	     }
	}
	
	/*
	 * Hover Over
	 */
	public static void hoover(String element)
	{
		WebElement myElement =driver.findElement(By.xpath(element));
		Actions builder = new Actions(driver); 
		Actions hoverOverRegistrar = builder.moveToElement(myElement);
		hoverOverRegistrar.perform();
	}
}
