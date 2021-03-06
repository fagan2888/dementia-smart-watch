package tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class updatePatientTests {

	private static WebDriver driver;
	
	public static void login() throws InterruptedException {
		WebElement form = null;
		WebElement username = null;
		WebElement password = null;
		
		WebDriverWait pageLoaded = new WebDriverWait(driver, 30);
		try {
			form = pageLoaded.until(ExpectedConditions.presenceOfElementLocated(By.className("pure-form")));
		} catch (Exception e) {
			System.out.println("Unable to locate element, Page not loaded");
		}
		
		try {
			username = driver.findElement(By.name("username"));
		} catch (Exception e) {
			System.out.println("Fail to locate username element");
		}
		
		try {
			password = driver.findElement(By.name("password"));
		} catch (Exception e) {
			System.out.println("Fail to locate password element");
		}

		username.sendKeys(new String[] {"j"});
		password.sendKeys(new String[] {"qwerty"});
		form.submit();
	}
	
	@Before
	public void setUp() throws InterruptedException {
		driver = new ChromeDriver();
		System.setProperty("webdriver.chrome.driver", "C:/Users/Dawn/Dropbox/INB372/Selenium/chromedriver_win32/chromedriver.exe");
		driver.get("http://localhost:8080/assignment/index.jsp");
		driver.manage().window().maximize();
		login();
		
		Thread.sleep(2000);
		
		WebDriverWait pageLoad = new WebDriverWait(driver, 30);
		
		try {
			pageLoad.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//*[contains(text(), 'My Patients')]")));
		} catch (Exception e) {
			fail("cannot locate the link, page didn't load");
		}
		driver.findElement(By.linkText("My Patients")).click();

		try {
			pageLoad.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//*[contains(text(), 'Change Details')]")));
		} catch (Exception e) {
			fail("Cannot locate the link, page not loaded");
		}
		driver.findElement(By.xpath("//a[contains(@href,'PatientDetails.jsp?patientid=6')]")).click();
		Thread.sleep(3000);
	}
	
	@After
	public void logOut() {
		if (driver != null) {
			WebDriverWait pageLoad = new WebDriverWait(driver, 30);
			try {
				pageLoad.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//*[contains(text(), 'Logout')]")));
			} catch (Exception e) {
				fail("Cannot locate the link, page not loaded");
			}
			driver.findElement(By.linkText("Logout")).click();
			driver.close();
		}
	}
	
	@Test
	public void checkClientValidation() throws InterruptedException {
		WebElement fName = null;
		WebElement lName = null;
		
		WebDriverWait pageLoad = new WebDriverWait(driver, 30);
		try {
			pageLoad.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//*[contains(text(), 'Update Patient Details')]")));
		} catch (Exception e) {
			fail("Cannot locate the link, page not loaded");
		}
		
		WebDriverWait addPatientPageLoad = new WebDriverWait(driver, 30);	
		try {
			fName = addPatientPageLoad.until(ExpectedConditions.presenceOfElementLocated(By.id("firstName")));
		} catch (Exception e) {
			fail("Cannot locate element, page failed to load");
		}
		
		try {
			lName = driver.findElement(By.id("lastName"));
		} catch (Exception e) {
			fail("Cannot locate element, page failed to load");
		}
		
		fName.sendKeys(new String[] {"%$#"});
		lName.click();
		Thread.sleep(3000);
		
		try {
			driver.findElements(By.xpath("//*[contains(text(), 'First name can only')]"));
		} catch (Exception e) {
			fail("Cannot locate element, page failed to load");
		}
	}
	
	@Test
	public void checkServerValidation() throws InterruptedException {
		WebElement fName = null;
		WebElement patientForm = null;
		
		WebDriverWait pageLoad = new WebDriverWait(driver, 30);
		try {
			pageLoad.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//*[contains(text(), 'Update Patient Details')]")));
		} catch (Exception e) {
			fail("Cannot locate the link, page not loaded");
		}
		
		WebDriverWait addPatientPageLoad = new WebDriverWait(driver, 30);	
		try {
			fName = addPatientPageLoad.until(ExpectedConditions.presenceOfElementLocated(By.id("firstName")));
		} catch (Exception e) {
			fail("Cannot locate element, page failed to load");
		}
		
		try {
			patientForm = driver.findElement(By.className("pure-button"));
		} catch (Exception e) {
			fail("cannot locate element, something has gone wrong");
		}
		
		fName.sendKeys(new String[] {"%$#"});
		Thread.sleep(3000);
		patientForm.click();
		patientForm.click();
		Thread.sleep(3000);
		
		try {
			driver.findElements(By.xpath("//*[contains(text(), 'Some information you entered')]"));
		} catch (Exception e) {
			fail("Cannot locate element, page failed to load");
		}
		Thread.sleep(3000);
		driver.get("http://localhost:8080/assignment/Home.jsp");
	}
	
	@Test
	public void checkUpdateWorks() throws InterruptedException {
		WebElement fName = null;
		WebElement patientForm = null;
		
		WebDriverWait pageLoad = new WebDriverWait(driver, 30);
		try {
			pageLoad.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//*[contains(text(), 'Update Patient Details')]")));
		} catch (Exception e) {
			fail("Cannot locate the link, page not loaded");
		}
		
		WebDriverWait updatePatientLoad = new WebDriverWait(driver, 30);	
		try {
			fName = updatePatientLoad.until(ExpectedConditions.presenceOfElementLocated(By.id("firstName")));
		} catch (Exception e) {
			fail("Cannot locate element, page failed to load");
		}
		
		try {
			patientForm = driver.findElement(By.className("pure-form"));
		} catch (Exception e) {
			fail("cannot locate element, something has gone wrong");
		}
		
		fName.sendKeys(Keys.chord(Keys.CONTROL, "a"), "Joshua");
		Thread.sleep(3000);
		patientForm.submit();
		Thread.sleep(3000);
		
		try {
			fName = updatePatientLoad.until(ExpectedConditions.presenceOfElementLocated(By.id("firstName")));
		} catch (Exception e) {
			fail("Cannot locate element, page failed to load");
		}
		
		driver.findElement(By.xpath("//a[contains(@href, '#')]")).click();
		Thread.sleep(2000);
		
		if (fName.getAttribute("value").equals("Joshua")) {
			return;
		} else {
			fail("Data was not edited on database after submitting");
		}
	}
}
