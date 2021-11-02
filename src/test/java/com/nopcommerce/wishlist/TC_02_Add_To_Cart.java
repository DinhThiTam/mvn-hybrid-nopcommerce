package com.nopcommerce.wishlist;

import org.openqa.selenium.WebDriver;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.nopcommerce.common.Common_01_Login_User;

import commons.BaseTest;
import pageObject.user.nopCommerce.DesktopsPO;
import pageObject.user.nopCommerce.HomePO;
import pageObject.user.nopCommerce.LoginPO;
import pageObject.user.nopCommerce.MyAccountPO;
import pageObject.user.nopCommerce.NotebooksPO;
import pageObject.user.nopCommerce.PageGenerator;
import pageObject.user.nopCommerce.ProductDetailsPO;
import pageObject.user.nopCommerce.RegisterPO;
import pageObject.user.nopCommerce.WishlistPO;
import utilities.DataUtil;


public class TC_02_Add_To_Cart extends BaseTest {
	String projectLocation = System.getProperty("user.dir");
	String firstName, lastName, emailAddress,password, birthDay, birthMonth, birthYear, companyName;
	
	@Parameters({"browser","url"})
	@BeforeClass
	public void initBrowser(String browserName, String appURL) {
		log.info("Pre-Condition - Step 01: Open browser '"+ browserName + "' and navigate '" + appURL + "'");
		driver = getBrowserDriver(browserName, appURL);
		homePage = PageGenerator.getHomePage(driver);
		verifyTrue(homePage.isHomePageSliderDisplayed());
		
		log.info("Pre-Condition - Step 02: Open 'Login' page on header");
		homePage.openMenuHeaderPageByClass(driver, "ico-login");
		loginPage = PageGenerator.getLoginPage(driver);
		
		log.info("Pre-Condition - Step 03: Set login page cookie");
		loginPage.setAllCookies(driver, Common_01_Login_User.loginPageCookie);
		loginPage.sleepInsecond(5);
		loginPage.refreshPage(driver);
		
		log.info("Pre-Condition - Step 04: Open homepage");
		homePage =  loginPage.openHomePage();
		
		log.info("Pre-Condition - Step 05: Verify Home Page is displayed");
		verifyTrue(homePage.isHomePageSliderDisplayed());
	
	}
	@Test
	public void Add_To_Cart_01() {
		log.info("Add_To_Cart_01 - Step 01: Open sub menu 'Notebooks'");
		homePage.openSubMenuPage(driver, "top-menu notmobile", "Computers ", "Notebooks ");
		notebooksPage = PageGenerator.getNotebooksPage(driver);
		
		log.info("Add_To_Cart_01 - Step 02: Click to the product title link");
		notebooksPage.clickToProductLinkByText(driver,"Apple MacBook Pro 13-inch");
		productDetailsPage = PageGenerator.getProductDetailsPage(driver);
		
		
		log.info("Add_To_Cart_01 - Step 03: Click to 'Add your review' link");
		productDetailsPage.clickButtonByID("add-to-wishlist-button-4");
		
		log.info("Add_To_Cart_01 - Step 04: Verify message is displayed");
		verifyEquals(productDetailsPage.getMessageInProductDetailsDisplayedByText(driver), "The product has been added to your wishlist");
		
		log.info("Add_To_Cart_01 - Step 05: Click to Close icon");
		productDetailsPage.clickToCloseIconInMessage(driver);
		productDetailsPage.sleepInsecond(2);
		
		log.info("Add_To_Cart_01 - Step 06: Open 'Wishlist' page on header");
		productDetailsPage.openMenuHeaderPageByClass(driver, "ico-wishlist");
		wishlistPage = PageGenerator.getWishlistPage(driver);
		
		log.info("Add_To_Cart_01 - Step 07: Verify Product Information displayed at table");
		verifyEquals(wishlistPage.getValueInTableIDAtColumnHorizontalNameAndRowIndex(driver, "cart", "1", "Product(s)"), "Apple MacBook Pro 13-inch");
		
		log.info("Add_To_Cart_01 - Step 08: Click to Checkbox at line '1' ");
		wishlistPage.clickToPageActionByRowAndClass("1","add-to-cart");
		
		log.info("Add_To_Cart_01 - Step 09: Click to 'Add to cart' button");
		wishlistPage.clickToButtonByName(driver, "Add to cart");
		
		log.info("Add_To_Cart_01 - Step 10: Verify Product Information displayed at table");
		verifyEquals(wishlistPage.getValueInTableIDAtColumnHorizontalNameAndRowIndex(driver, "cart", "1", "Product(s)"), "Apple MacBook Pro 13-inch");
		
		log.info("Add_To_Cart_01 - Step 11: Verify shopping cart title is displayed");
		verifyTrue(wishlistPage.isPageTitleShoppingCartDisplayed());
		wishlistPage.backPage(driver);
		
		log.info("Add_To_Cart_01 - Step 07: Verify message no data in page");
		verifyTrue(wishlistPage.isPageMessageNoDataDisplayedByText(driver, "The wishlist is empty!"));
	}
	
	@Parameters({"browser"})
	@AfterClass(alwaysRun=true)
	public void cleanBrowser(String browserName) {
		log.info("Post-Condition - Close Browser - " + browserName + "");
		cleanBrowserAndDriver();
	}
	WebDriver driver;
	HomePO homePage;
	RegisterPO registerPage;
	LoginPO loginPage;
	DataUtil fakeData;
	MyAccountPO myAccountPage;
	NotebooksPO notebooksPage;
	DesktopsPO desktopsPage;
	ProductDetailsPO productDetailsPage;
	WishlistPO wishlistPage;
}
