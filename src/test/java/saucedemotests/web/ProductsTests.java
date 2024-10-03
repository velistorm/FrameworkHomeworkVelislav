package saucedemotests.web;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import saucedemotests.core.SauceDemoBaseWebTest;
import saucedemotests.enums.TestData;

public class ProductsTests extends SauceDemoBaseWebTest {
    public final String BACKPACK_TITLE = "Sauce Labs Backpack";
    public final String SHIRT_TITLE = "Sauce Labs Bolt T-Shirt";

    @BeforeEach
    public void beforeTest(){
        loginPage.navigate();

        // Submit login form
        loginPage.submitLoginForm(TestData.STANDARD_USER_USERNAME.getValue(), TestData.STANDARD_USER_PASSWORD.getValue());
        inventoryPage.waitForPageTitle();

        // Assert expected page navigated
        inventoryPage.assertNavigated();
    }

    @Test
    public void productAddedToShoppingCart_when_addToCart(){

        inventoryPage.addProductsByTitle(BACKPACK_TITLE);
        inventoryPage.addProductsByTitle(SHIRT_TITLE);

        inventoryPage.clickShoppingCartLink();

        Assertions.assertEquals(inventoryPage.getShoppingCartItemsNumber(), 2);

    }

    @Test
    public void userDetailsAdded_when_checkoutWithValidInformation(){
        inventoryPage.addProductsByTitle(BACKPACK_TITLE);
        inventoryPage.addProductsByTitle(SHIRT_TITLE);

        inventoryPage.clickShoppingCartLink();
        shoppingCartPage.clickCheckout();

        checkoutYourInformationPage.fillShippingDetails("Velislav", "Petev", "1000");
        checkoutYourInformationPage.clickContinue();

        Assertions.assertEquals(shoppingCartPage.getShoppingCartItems().size(), 2);
        Assertions.assertEquals(checkoutOverviewPage.getTotalLabelText(), "Total: $49.66");
    }

    @Test
    public void orderCompleted_when_addProduct_and_checkout_withConfirm(){
        inventoryPage.addProductsByTitle(BACKPACK_TITLE);
        inventoryPage.addProductsByTitle(SHIRT_TITLE);

        inventoryPage.clickShoppingCartLink();
        shoppingCartPage.clickCheckout();

        checkoutYourInformationPage.fillShippingDetails("Velislav", "Petev", "1000");
        checkoutYourInformationPage.clickContinue();

        checkoutOverviewPage.clickFinish();
        Assertions.assertEquals(shoppingCartPage.getShoppingCartItems().size(), 0);
    }
}