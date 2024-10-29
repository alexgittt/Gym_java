package stepdefs.hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Hooks {

  public static WebDriver driver;

  @Before
  public void setup() {
    if (driver == null) {
      driver = new ChromeDriver();
      driver.get("https://uk.gymshark.com");
      driver.manage().addCookie(new Cookie("gs-headless-locale-production", "en-GB"));
      driver.manage().addCookie(new Cookie("OptanonAlertBoxClosed", "2024-10-29"));
      driver.navigate().refresh();
    }
  }

  @After
  public void teardown() {
    if (driver != null) {
      driver.quit();
      driver = null;
    }
  }

  public static WebDriver getDriver() {
    return driver;
  }

}
