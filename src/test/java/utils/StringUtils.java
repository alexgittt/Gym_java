package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class StringUtils {

  private static final Map<Long, String> productUrlMap = new HashMap<>();

  static {
    productUrlMap.put(39654522814667L, "https://uk.gymshark.com/products/gymshark-speed-t-shirt-black-aw23");
    productUrlMap.put(39654813106379L, "https://uk.gymshark.com/products/gymshark-speed-5-shorts-artificial-teal-ss24");
  }

  public static long extractVariantIDFromString(String string) {
    Matcher m = Pattern.compile("\\d{14}").matcher(string);
    if (m.find()) {
      return Long.parseLong(m.group(0));
    } else {
      throw new IllegalArgumentException("14 digit Variant ID not found within string [" + string + "]");
    }
  }

  public static String getProductUrlFromVariantID(Long variantID) {
    String productUrl = productUrlMap.get(variantID);
    if (productUrl == null) {
      throw new IllegalArgumentException("No URL found for variant ID: " + variantID);
    }
    return productUrl;
  }
}
