package stepdefs;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import pageobjects.BagPage;
import pageobjects.ProductDisplayPage;
import stepdefs.hooks.Hooks;
import utils.StringUtils;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class BagStepDefs {

    private final WebDriver driver;
    private final BagPage bagPage;

    public BagStepDefs() {
        this.driver = Hooks.getDriver();
        this.bagPage = new BagPage(driver);
    }

    @Given("there are products with variantID {long}, {long} in the bag")
    public void thereAreProductsIanTheBage(Long variantIDOne, Long variantIDTwo) {
        String productUrlOne = StringUtils.getProductUrlFromVariantID(variantIDOne);
        driver.get(productUrlOne);
        long productIdOne = variantIDOne;
        ProductDisplayPage productDisplayPage = new ProductDisplayPage();
        productDisplayPage.addProductToBag();

        List<Long> variantIds = bagPage.getVariantIdsInBag();
        assertThat(variantIds).as("Expected product is in Bag").contains(productIdOne);

        String productUrlTwo = StringUtils.getProductUrlFromVariantID(variantIDTwo);
        driver.get(productUrlTwo);
        long productIdTwo = variantIDTwo;
        productDisplayPage.addProductToBag();

        variantIds = bagPage.getVariantIdsInBag();
        assertThat(variantIds).as("Expected product is in Bag").contains(productIdTwo);
    }

    @Given("there is a product with variantID {long} in the bag")
    public void thereIsAProductWithVariantIDInTheBag(Long variantID) {
        String productUrlOne = StringUtils.getProductUrlFromVariantID(variantID);
        driver.get(productUrlOne);
        long productIdOne = variantID;
        ProductDisplayPage productDisplayPage = new ProductDisplayPage();
        productDisplayPage.addProductToBag();

        List<Long> variantIds = bagPage.getVariantIdsInBag();
        assertThat(variantIds).as("Expected product is in Bag").contains(productIdOne);
    }

    @When("I remove a product with variantID {long}")
    public void iRemoveAProduct(Long variantID) {
        bagPage.removeProductByVariantId(variantID);
    }

    @Then("the product {long} is removed from the bag")
    public void theProductIsRemovedFromTheBag(Long variantID) {

        assertThat(bagPage.isProductNotVisibleInBag(variantID))
                .as("Product with variant ID " + variantID + " is visible in the bag and it shouldn't")
                .isTrue();
    }

    @When("I add quantity {int}")
    public void IAddQuantity(int quantity) {
        bagPage.selectQuantity(quantity);
    }

    @Then("product quantity is increased to {string}")
    public void ProductQuantityIsIncreasedTo(String quantity){
        bagPage.assertQuantity(quantity);
    }

    @Then("the bag is empty")
    public void TheBagIsEmpty(){
        bagPage.checkEmptyBag();
    }

    @Then("the product {long} is visible in the bag")
    public void theProductIsVisibleInTheBag(Long variantID) {
        List<Long> variantIds = bagPage.getVariantIdsInBag();
        assertThat(variantIds).as("Expected product is in Bag").contains(variantID);
    }
}
