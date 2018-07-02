package ameriStudent.test;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.ElementClickInterceptedException;
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
			findWithWait(By.id("undefined-undefined-Searchbyfamilyname*")).sendKeys("test"); 	//family name input
			findWithWait(By.partialLinkText("Test Permanent")).click();			 				//click on returned option
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
			
			
		}
		
		@Test
		public void DeleteFamily()
		{
			;
		}
		
		
		
}
