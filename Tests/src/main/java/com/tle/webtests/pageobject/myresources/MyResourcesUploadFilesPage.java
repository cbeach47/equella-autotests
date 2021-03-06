package com.tle.webtests.pageobject.myresources;

import org.openqa.selenium.By;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.tle.webtests.pageobject.AbstractPage;

public class MyResourcesUploadFilesPage extends AbstractPage<MyResourcesUploadFilesPage>
{
	private static final String REPLACE_FILE_INPUT_ID = "myr_f";
	private static final String UPLOAD_FILE_INPUT_ID = "myr_s";
	private static final String DESCRIPTION_INPUT_ID = "myr_d";
	private static final String SEARCH_TAGS_INPUT_ID = "myr_t";

	@FindBy(id = REPLACE_FILE_INPUT_ID)
	private WebElement fileInput;
	@FindBy(id = UPLOAD_FILE_INPUT_ID)
	private WebElement saveButton;
	@FindBy(id = DESCRIPTION_INPUT_ID)
	private WebElement descriptionField;
	@FindBy(id = SEARCH_TAGS_INPUT_ID)
	private WebElement tagField;
	@FindBy(id = "myr_c")
	private WebElement cancelButton;
	private MyResourcesPage myResourcesPage;

	@FindBy(id = "myr_dndt")
	private WebElement dndTagsField;
	@FindBy(id = "")
	private WebElement dndArchiveOption;
	@FindBy(id = "filedndarea")
	private WebElement dndDropZone;

	public MyResourcesUploadFilesPage(MyResourcesPage myResourcesPage)
	{
		super(myResourcesPage.getContext());
		this.myResourcesPage = myResourcesPage;
	}

	@Override
	protected WebElement findLoadedElement()
	{
		return descriptionField;
	}

	public MyResourcesPage uploadFile(String path, String description, String tags)
	{
		fileInput.sendKeys(path);
		setDescription(description);
		setSearchTags(tags);
		saveButton.click();
		return new MyResourcesPage(context, "scrapbook").get();
	}

	public boolean hasArchiveOption(String option)
	{
		try
		{
			String xpath = "//ul[@class='dropdown-menu']//li//a[contains(text(), '" + option + "')]";
			driver.findElement(By.xpath(xpath));
		}
		catch( NotFoundException ex )
		{
			return false;
		}
		return true;
	}

	public boolean hasDndTagField()
	{
		try
		{
			String xpath = "//input[@id='myr_dndt']";
			driver.findElement(By.xpath(xpath));
		}
		catch( NotFoundException ex )
		{
			return false;
		}
		return true;
	}

	public boolean hasDndDropZone()
	{
		try
		{
			String xpath = "//div[@id='filedndarea']";
			driver.findElement(By.xpath(xpath));
		}
		catch( NotFoundException ex )
		{
			return false;
		}
		return true;
	}

	public void setSearchTags(String tags)
	{
		tagField.clear();
		tagField.sendKeys(tags);
	}

	public void setDescription(String description)
	{
		descriptionField.clear();
		descriptionField.sendKeys(description);
	}

	public void setDndTags(String tags)
	{
		dndTagsField.clear();
		dndTagsField.sendKeys(tags);
	}

	public MyResourcesPage cancel()
	{
		cancelButton.click();
		return myResourcesPage.get();
	}

	public MyResourcesPage save()
	{
		saveButton.click();
		return myResourcesPage.get();
	}

}
