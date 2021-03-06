package com.tle.webtests.pageobject;

import org.openqa.selenium.By;

import com.tle.webtests.framework.PageContext;

/**
 * @author Aaron
 *
 */
public class ErrorPage extends AbstractPage<ErrorPage>
{
	public ErrorPage(PageContext context)
	{
		super(context, By.xpath("//div[@class='area error']")); //  /h2[normalize-space(text())='An error occurred']
	}

	public String getMainErrorMessage()
	{
		return driver.findElement(By.xpath("//div[contains(@class, 'error')]/h2")).getText();
	}

	public String getSubErrorMessage()
	{
		return driver.findElement(By.xpath("//div[contains(@class, 'error')]/h3[1]")).getText();
	}

	public String getDetail()
	{
		return context.getDriver().findElement(By.xpath("//div[@class='area error']/p[@id='description']")).getText();
	}
}
