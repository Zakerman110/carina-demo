package com.qaprosoft.carina.core.demo.gui.gsmarena.pages;

import com.qaprosoft.carina.core.demo.gui.gsmarena.components.compare.CompareCondidatesBlock;
import com.qaprosoft.carina.core.demo.gui.gsmarena.components.compare.CondidateDescriptionBlock;
import com.qaprosoft.carina.core.gui.AbstractPage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class CompareModelsPage extends AbstractPage
{
	@FindBy(xpath = "//header[@class='compare-candidates']")
	private CompareCondidatesBlock compareCondidatesBlock;

	@FindBy(id = "specs-list")
	private CondidateDescriptionBlock condidateDescriptionBlock;

	public CompareModelsPage(WebDriver driver)
	{
		super(driver);
		setPageAbsoluteURL("http://www.gsmarena.com/compare.php3");
	}

	public CompareCondidatesBlock getCompareCondidatesBlock()
	{
		return compareCondidatesBlock;
	}

	public CondidateDescriptionBlock getCondidateDescriptionBlock()
	{
		return condidateDescriptionBlock;
	}
}