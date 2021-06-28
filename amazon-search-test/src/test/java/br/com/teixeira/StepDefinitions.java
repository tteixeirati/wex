package br.com.teixeira;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import utils.CurrencyConversion;

public class StepDefinitions {

	private WebDriver driver;
	private WebDriverWait wait;
	private String numberResultsNoFilter;
	private String numberResultsFiltered;
	private String greaterPrice;		
	private String greaterPriceUsd;
	private Double greaterPriceDouble;

	@Given("an Amazon Brasil Google search")
	public void an_Amazon_Brasil_Google_search() {

		System.setProperty("webdriver.chrome.driver","src/test/resources/chromedriver.exe");
		try {
			driver = new ChromeDriver();
			wait = new WebDriverWait(driver, 30);

		} catch(SessionNotCreatedException e) {
			System.out.println("Couldn't start ChromeDriver. \nPlease verify ChromeBrowser Version and ChromeDriver version in the path src/test/resources/");
			e.printStackTrace();

		}
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.format("Cukes: %n\n");
		try {
			driver.get("https://www.google.com/");

			wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.name("q")));
			driver.findElement(By.name("q")).clear();
			driver.findElement(By.name("q")).sendKeys("Amazon Brasil");

			wait.until(ExpectedConditions.elementToBeClickable(By.className("gNO89b")));
			driver.findElement(By.className("gNO89b")).click();

			wait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText("www.amazon.com.br")));
			driver.findElement(By.partialLinkText("www.amazon.com.br")).click();

		}catch(Exception e) {
			e.printStackTrace();	

		}
	}

	@When("Search For Iphone Using The Search Bar")
	public void search_For_Using_The_Search_Bar() {

		wait.until(ExpectedConditions.elementToBeClickable(By.id("twotabsearchtextbox")));
		driver.findElement(By.id("twotabsearchtextbox")).clear();
		driver.findElement(By.id("twotabsearchtextbox")).sendKeys("Iphone");

		wait.until(ExpectedConditions.elementToBeClickable(By.id("nav-search-submit-text")));
		driver.findElement(By.id("nav-search-submit-text")).click();
	}

	@When("Count The Total List Of Found Products")
	public void count_The_Total_List_Of_Found_Products() {

		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
				"//div[@class=\"a-section a-spacing-small a-spacing-top-small\"]/span")));
		String resultsTextNoFilter = driver.findElement(By.xpath(
				"//div[@class=\"a-section a-spacing-small a-spacing-top-small\"]/span")).getText();
		numberResultsNoFilter = resultsTextNoFilter.substring(16, 22);

	}

	@When("Count The Total Of Iphone Items Found")
	public void count_The_Total_Of_Items_Found() {
		
		wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Celulares e Smartphones")));
		driver.findElement(By.linkText("Celulares e Smartphones")).click();
		String resultsTextFiltered = driver.findElement(By.xpath(
				"//div[@class=\"a-section a-spacing-small a-spacing-top-small\"]/span")).getText();
		numberResultsFiltered = resultsTextFiltered.substring(8, 11);

	}

	@Then("Make Sure At Least 80% Of Items Found are Iphone")
	public void make_Sure_At_Least_Of_Items_Found_are() {
		
		int numberResultsFilteredInt = Integer.parseInt(numberResultsFiltered.replace(".", ""));
		int numberResultsNoFilterInt = Integer.parseInt(numberResultsNoFilter.replace(".", ""));
		Assert.assertTrue((numberResultsFilteredInt/numberResultsNoFilterInt) > 0.8);
		
	}
	
	@When("Find The The More Expensive Iphone In Page")
	public void find_The_The_More_Expensive_In_Page() {
		
		wait.until(ExpectedConditions.elementToBeClickable(By.id("a-autoid-0-announce")));
		driver.findElement(By.id("a-autoid-0-announce")).click();

		wait.until(ExpectedConditions.elementToBeClickable(By.id("s-result-sort-select_2")));
		driver.findElement(By.id("s-result-sort-select_2")).click();

		wait.until(ExpectedConditions.presenceOfElementLocated(By.className("a-price-whole")));
		greaterPrice = driver.findElement(By.id("a-price-whole")).getText().replace(".", "");
							
	}
	
	@When("Convert Its Value To USD Using https:\\/\\/exchangeratesapi.io\\/ API")
	public void convert_Its_Value_To_USD_Using_https_exchangeratesapi_io_API() {
		
		greaterPriceUsd = CurrencyConversion.convertBrlUsd(greaterPrice);		
		greaterPriceDouble =  Double.parseDouble(greaterPriceUsd);
		
	}

	@Then("Make Sure The Converted Value Is Not Greater Than US2000")
	public void make_Sure_The_Converted_Value_Is_Not_Greater_Than_US() {
		
		Assert.assertTrue(greaterPriceDouble < 2000);

	}
	
	@Then("I quit the WebDriver")
	public void i_quit_the_WebDriver() {

		driver.quit();
		
	}
	
}