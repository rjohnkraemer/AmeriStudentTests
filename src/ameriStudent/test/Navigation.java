package ameriStudent.test;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
//import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

public class Navigation extends BaseTest {

		Navigation()
		{
			super();
		}
	

		@Test
		public void searchMapByName()
		{
			driver.get(familyURL);
			keysByID("Searchbyfamilyname", "test");	//family name input
			findByText("Test Permanent", true).click();		 				//click on returned option
		}
		
		@Test
		public void createFamily()
		{
			driver.get(createFamilyURL);
			
				//name
			keysByID("FirstName", "RobotCreated");
			keysByID("LastName", "Robotson");
			
				//gender			
			findByID("Gender").click();
			findByText("Male").click();

			
				//address
			keysByID("Street", "214 W Grand Ave");
			keysByID("City", "Grover Beach");
			keysByID("PostalCode","93433");
			keysByID("State","CA");
			
				//bedrooms
			keysByID("Totalbedroom","1");
					//click to open menu, click to select first item (1)
			findByID("Bedroomsavailableforstudents").click();
			findWithWait(By.cssSelector("[role*=menuitem]")).click();
			
			
				//confirm background check
			WebElement element = findByText("background check");
					//scroll before clicking
			jse.executeScript("arguments[0].scrollIntoView(true);",element);
			obscuredElementClick(element);

			
			findByText("Submit").click();			
			
			new WebDriverWait(driver, 10).until(
			          driver -> ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete"));
			
			try{ Thread.sleep(2000); }
				catch (Exception e){e.printStackTrace();}
			
			File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			try{ FileUtils.copyFile(scrFile, new File("C:\\Users\\John\\eclipse-workspace\\FirstWebDriver\\screenshots\\familyCreate.png")); }
				catch (Exception e) { e.printStackTrace(); }
			
		}
		
		@Test
		public void DeleteFamily()
		{
			driver.get(dashboardURL);
			
			findByText("RobotCreated Robotson").click();
			
				//delete button
			WebElement element = findByText("Delete");		
					//scroll before clicking
			jse.executeScript("arguments[0].scrollIntoView(true);",element);
			obscuredElementClick(element);			
			
			new WebDriverWait(driver, 10).until(
			          driver -> ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete"));
			
			try{ Thread.sleep(2000); }
				catch (Exception e){e.printStackTrace();}
			
			obscuredElementClick(
					findByText("Delete", "button"));
			
			try{ Thread.sleep(2000); }
			catch (Exception e){e.printStackTrace();}
			
			File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			try{ FileUtils.copyFile(scrFile, new File("C:\\Users\\John\\eclipse-workspace\\FirstWebDriver\\screenshots\\familyDelete.png")); }
				catch (Exception e) { e.printStackTrace(); }
		}
		
		
		
}
