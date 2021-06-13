// Generated by Selenium IDE
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Keys;
import java.util.*;
import java.net.MalformedURLException;
import java.net.URL;
public class Test1Test {
  private WebDriver driver;
  private Map<String, Object> vars;
  JavascriptExecutor js;
  @Before
  public void setUp() {
    driver = new ChromeDriver();
    js = (JavascriptExecutor) driver;
    vars = new HashMap<String, Object>();
  }
  @After
  public void tearDown() {
    driver.quit();
  }
  @Test
  public void test1() {
    driver.get("https://www.facebook.com/");
    driver.manage().window().setSize(new Dimension(1054, 808));
    driver.findElement(By.id("u_0_2_7a")).click();
    driver.findElement(By.id("u_2_b_YV")).sendKeys("Mery");
    driver.findElement(By.id("u_2_d_4S")).sendKeys("Azibekyan");
    driver.findElement(By.id("u_2_g_Dl")).sendKeys("mery@asd.ss");
    driver.findElement(By.id("u_2_j_3X")).click();
    driver.findElement(By.id("u_2_j_3X")).sendKeys("mery@asd,ass");
    driver.findElement(By.id("password_step_input")).click();
    driver.findElement(By.id("password_step_input")).sendKeys("12345");
    driver.findElement(By.id("month")).click();
    {
      WebElement dropdown = driver.findElement(By.id("month"));
      dropdown.findElement(By.xpath("//option[. = 'дек']")).click();
    }
    driver.findElement(By.id("month")).click();
    driver.findElement(By.id("u_2_2_2w")).click();
    driver.findElement(By.id("u_2_s_v8")).click();
    driver.findElement(By.id("u_2_j_3X")).click();
    driver.findElement(By.id("u_2_j_3X")).sendKeys("mery@asd.ss");
    driver.findElement(By.id("u_2_s_v8")).click();
    driver.close();
  }
}