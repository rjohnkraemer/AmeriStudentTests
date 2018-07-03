package ameriStudent.test;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.TimeoutException;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

public class BaseTest {

	WebDriver driver;
	String loginURL, dashboardURL, baseURL, logoutURL, familyURL,
			createFamilyURL;
	JavascriptExecutor jse;
	
	BaseTest()
	{
		baseURL= "http://ameristudent-staging2.herokuapp.com";
		loginURL = baseURL + "/staff_members/sign_in";
		dashboardURL = baseURL + "/staff/dashboard";
		logoutURL = baseURL + "/staff_members/sign_out";
		familyURL = baseURL + "/families";
		createFamilyURL = baseURL + "/families/new";
	}
	
	@BeforeSuite(alwaysRun = true)
		public void setupBeforeSuite() {
		driver = new FirefoxDriver();
		jse = (JavascriptExecutor)driver;
	}  

	@BeforeTest(alwaysRun = true)
	public void setupBeforeTest() {
		login();
	}
	
	@AfterTest(alwaysRun = true)
	public void setupAfterTest() {
		//new WebDriverWait(driver, 5);
		logoutQuick();
	}
	
	@AfterSuite(alwaysRun = true)
	public void setupAfterSuite() {
		try
		{
			new WebDriverWait(driver, 5);
			driver.quit();
		}
		catch (SessionNotCreatedException e)
		{
			System.out.println("driver.quit() - no windows to close");
		}
	}
	
	public void login()
	{
		driver.get(loginURL);
		findWithWait(By.name("staff_member[email]")).sendKeys("r.john.kraemer+robot@gmail.com");	//user
		driver.findElement(By.name("staff_member[password]")).sendKeys("password");					//pass
		driver.findElement(By.className("mdl-button")).click();										//submit
		
		try {
			findWithWait(By.className("desktop-navigation"));										//wait for page to load, aka nav is loaded
		}
		catch(TimeoutException e)
		{
			System.out.println("Desktop nav not found, trying mobile");
			findWithWait(By.className("mobile-drawer")); //throws timeout if not found, uncaught
		}
		
	}
	
	public void logoutQuick()
	{
		driver.get(logoutURL);
	}
	
	public void logout()
	{
		findWithWait(By.id("profile-menu")).click();					//profile menu
		findWithWait(By.linkText("LOGOUT")).click();					//logout link -> logoutURL
	}
	
	
	public void obscuredElementClick(WebElement element)
	{
		Point pt = element.getLocation();
		int NumX = pt.getX();
		int NumY = pt.getY();
		
		Actions act = new Actions(driver);   
		act.moveByOffset(NumX+1, NumY).click().build().perform();
	}
	
	public WebElement findParent(WebElement element)
	{
		return new WebDriverWait(driver,10).until(
			    ExpectedConditions.presenceOfNestedElementLocatedBy(
			            element, By.xpath(".."))
	    );
	}
	
	
	public WebElement findChild(WebElement parent, By locator)
	{
		return new WebDriverWait(driver,10).until(
			    ExpectedConditions.presenceOfNestedElementLocatedBy(
			            parent, locator)
	    );
	}
	
	public WebElement findByText(String text)
	{
		return findWithWait(By.xpath("//*[contains(text(), '" + text + "')]"));
	}
	
	public WebElement findByText(String text, String type)
	{
		return findWithWait(By.xpath("//" + type + "[contains(text(), '" + text + "')]"));
	}
	
	public WebElement findByText(String text, Boolean visible)
	{
		if(!visible)
			return findByText(text);
		return findWithWait(By.xpath("//*[contains(text(), '" + text + "') and not(contains(@style,'display:none'))]"));
	}
	
	/*
	 * find element within 10 seconds
	 */
	public WebElement findWithWait(By locator) throws TimeoutException
	{
		return findWithWait(locator, 10);
	}

	/*
	 * find element within [seconds] seconds,  timeout fail afterwards
	 */
	public WebElement findWithWait(By locator, int seconds) throws TimeoutException
	{
		WebElement returnedElement = (new WebDriverWait(driver, seconds))
				  .until(ExpectedConditions.presenceOfElementLocated(locator));
		return returnedElement;
	}
	
	/*
	 * find element with ID [ID] and type keys [input]
	 */
	public WebElement keysByID(String ID, String input)
	{
		WebElement tempWebEle = findWithWait(By.cssSelector("[id*="+ ID + "]"));
		tempWebEle.sendKeys(input);
		return tempWebEle;
	}
	
	/*
	 * Find element with ID [ID]
	 */
	public WebElement findByID(String ID)
	{
		return findWithWait(By.cssSelector("[id*="+ ID + "]"));
	}
	
	public void scrollDown()
	{
		jse.executeScript("window.scrollBy(0,250)", "");
	}
}
