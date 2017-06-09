package com.tle.webtests.pageobject.wizard.controls.universal;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.tle.webtests.pageobject.ExpectWaiter;
import com.tle.webtests.pageobject.PageObject;
import com.tle.webtests.pageobject.WaitingPageObject;
import com.tle.webtests.pageobject.wizard.controls.UniversalControl;

public class KalturaUniversalControlType extends AbstractUniversalControlType<KalturaUniversalControlType>
{
	@FindBy(id = "{wizid}_dialog_kh_choice_0")
	private WebElement addExisting;
	@FindBy(id = "{wizid}_dialog_kh_choice_1")
	private WebElement addNew;
	@FindBy(id = "{wizid}_dialog_kh_query")
	private WebElement searchField;
	@FindBy(id = "{wizid}_dialog_kh_search")
	private WebElement searchButton;
	@FindBy(xpath = "id('{wizid}_dialog')//div[contains(@class,'kalturaHandler')]")
	private WebElement mainDiv;
	@FindBy(id = "{wizid}_dialog_kh_displayName")
	private WebElement nameField;
	@FindBy(id = "{wizid}_dialog_kh_divKcw")
	private WebElement kcwFlashWidget;

	public enum KalturaOption
	{
		EXISTING, NEW
	}

	public KalturaUniversalControlType(UniversalControl control)
	{
		super(control);
		this.waiter = new WebDriverWait(driver, 60, 50);
	}

	public KalturaUniversalControlType search(String query)
	{
		clickChoice("Add existing Kaltura media");
		waitForElement(searchField);
		searchField.clear();
		searchField.sendKeys(query);
		WaitingPageObject<KalturaUniversalControlType> submitWaiter = submitWaiter();
		searchButton.click();
		return submitWaiter.get();
	}

	public GenericAttachmentEditPage selectExistingVideo(int index)
	{
		return selectExistingVideos(editPage(), index);
	}

	public UniversalControl addExistingVideos(int... indexes)
	{
		return selectExistingVideos(control.attachmentCountExpectation(indexes.length), indexes);
	}

	private <T extends PageObject> T selectExistingVideos(WaitingPageObject<T> returnTo, int... index)
	{
		for( int i = 0; i < index.length; i++ )
		{
			WaitingPageObject<KalturaUniversalControlType> submitWaiter = submitWaiter();
			driver.findElement(By.id(page.subComponentId(ctrlnum, "dialog_kh_results_" + (index[i] - 1)))).click();
			submitWaiter.get();
		}

		WaitingPageObject<T> disappearWaiter = ExpectWaiter.waiter(removalCondition(addButton), returnTo);
		addButton.click();
		return disappearWaiter.get();
	}

	public void clickChoice(String choice)
	{
		driver.findElement(By.xpath("//h4/label[text()=" + quoteXPath(choice) + "]")).click();
		nextButton.click();
	}

	public String addNewVideo(String displayName, String... tags)
	{
		addNew.click();
		nextButton.click();
		waitForElement(kcwFlashWidget);

		kcwNext();

		return "";
	}

	@Override
	public String getType()
	{
		return "Kaltura";
	}

	@Override
	public WebElement getFindElement()
	{
		return mainDiv;
	}

	@Override
	public WebElement getNameField()
	{
		return nameField;
	}

	public boolean isDisabled()
	{
		return isPresent(By.xpath("//h3[text()='Kaltura media server is disabled']"));
	}

	public void kcwNext()
	{
		((JavascriptExecutor) driver).executeScript("nextStep = function(){document.getElementById("
			+ kcwFlashWidget.getAttribute("id") + ").goNextStep();};");
	}

	public void kcwPrev()
	{
		((JavascriptExecutor) driver).executeScript("prevStep = function(){document.getElementById("
			+ kcwFlashWidget.getAttribute("id") + ").goPrevStep();};");
	}
}