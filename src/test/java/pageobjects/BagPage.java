package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.Select;
import utils.SeleniumCommands;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static utils.SeleniumCommands.getCommands;
import static utils.StringUtils.extractVariantIDFromString;

public class BagPage{

  private WebDriver driver;

  public BagPage(WebDriver driver) {
    this.driver = driver;
  }

  private static final By BAG_PAGE = By.cssSelector("[data-locator-id='miniBag-component']");
  private static final By BAG_ITEMS = By.cssSelector("[data-locator-id^='miniBag-miniBagItem']");
  public static final String GS_LOCATOR_ATTRIBUTE = "data-locator-id";
  private static final By DELETE_BAG_ITEM_BUTTON = By.cssSelector("[data-locator-id^='miniBag-remove']");
  private static final By BAG_EMPTY_PAGE = By.cssSelector("[data-locator-id^='miniBag-miniBagEmpty']");
  private static final By QUANTITY = By.cssSelector("span.qty-selector_text__4uAGo");

  public BagPage() {
    getCommands().waitForAndGetVisibleElementLocated(BAG_PAGE);
  }

  public List<Long> getVariantIdsInBag() {
    return getBagItems().stream()
      .map(this::getVariantId)
      .collect(Collectors.toList());
  }

  private List<WebElement> getBagItems() {
    return getCommands().waitForAndGetAllVisibleElementsLocated(BAG_ITEMS);
  }

  private long getVariantId(WebElement bagItem) {
    return extractVariantIDFromString(getCommands().getAttributeFromElement(bagItem, GS_LOCATOR_ATTRIBUTE));
  }

  public void removeProductByVariantId(long variantId) {

    try {
      WebElement removeButton = driver.findElement(By.cssSelector("button[data-locator-id=\"miniBag-remove-" + variantId + "-remove\"]"));
      removeButton.click();
    } catch (NoSuchElementException e) {
      System.out.println("Product with variant ID " + variantId + " not found in the bag.");
    }
  }

  public boolean isProductNotVisibleInBag(long variantId) {
    By selector = By.cssSelector("button[data-locator-id=\"miniBag-remove-" + variantId + "-remove\"]");
    boolean isNotVisible = getCommands().waitForAndGetNonVisibleElementLocated(selector);

    if (!isNotVisible) {
      System.out.println("Product with variant ID " + variantId + " has not been removed from the bag");
    }
    return isNotVisible;
  }

  public void selectQuantity(int quantity) {
    if (quantity < 1 || quantity > 10) {
      throw new IllegalArgumentException("Quantity must be between 1 and 10.");
    }
    WebElement quantityDropdown = driver.findElement(By.id("A5A9E-BB2J-S-qty-dropdown"));
    Select select = new Select(quantityDropdown);
    select.selectByValue(String.valueOf(quantity));
  }

  public void assertQuantity(String expectedQuantity) {
    WebElement quantityElement = driver.findElement(QUANTITY);
    String quantityText = quantityElement.getText();
    assertThat(quantityText)
            .as("Expected text to contain quantity " + expectedQuantity + " but was " + quantityText)
            .contains(expectedQuantity);
  }

  public void checkEmptyBag() {
    getCommands().waitForAndGetAllVisibleElementsLocated(BAG_EMPTY_PAGE);
  }
}

