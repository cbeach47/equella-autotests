package com.tle.webtests.pageobject.cal;

import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.tle.webtests.framework.PageContext;
import com.tle.webtests.pageobject.AbstractPage;
import com.tle.webtests.pageobject.generic.component.Calendar;
import com.tle.webtests.pageobject.generic.component.SelectCourseDialog;

public class EditActivationPage extends AbstractPage<EditActivationPage>
{
	@FindBy(id = "ea_sc")
	private WebElement selectCourseButton;
	@FindBy(id = "ea_saveButton")
	private WebElement saveButton;

	private Calendar fromDate;
	private Calendar untilDate;

	private final ActivationsSummaryPage returnTo;

	public EditActivationPage(PageContext context, ActivationsSummaryPage returnTo)
	{
		super(context, By.xpath("//h2[text()='Edit activation']"));
		this.returnTo = returnTo;
		fromDate = new Calendar(context, "ea_fd").get();
		untilDate = new Calendar(context, "ea_ud").get();
	}

	public boolean fromAndCourseSelectorDisabled()
	{
		return !selectCourseButton.isEnabled() && fromDate.isDisabled();
	}

	public ActivationsSummaryPage editActiveActivation(Date until)
	{
		setUntil(until);
		saveButton.click();
		return returnTo.get();
	}

	public ActivationsSummaryPage editPendingActivation(String course, Date[] range)
	{
		selectCourseButton.click();
		SelectCourseDialog scd = new SelectCourseDialog(context, "ea_selectCourseDialog").get();
		scd.searchSelectAndFinish(course, this);
		setFrom(range[1]); // [0] not a future date wtf
		setUntil(range[1]);
		saveButton.click();
		return returnTo.get();
	}

	public void setDateRange(Date[] range)
	{
		setFrom(range[0]);
		setUntil(range[1]);
	}

	public void setUntil(Date until)
	{
		untilDate.setDate(until, this);
	}

	public void setFrom(Date from)
	{
		fromDate.setDate(from, this);
	}

	public void saveWithError()
	{
		saveButton.click();
		this.get();
	}

	public boolean errorPresent()
	{
		return isPresent(By.className("date-error"));
	}
}
