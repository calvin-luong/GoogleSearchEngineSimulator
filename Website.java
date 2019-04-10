package SearchEngine;

/**
 * Represents a website with a pageRank.
 *
 * @author       Calvin Luong Copyright (2019)
 * @version      1.0
 * @see also     pageRank
 */

public class Website {
	
	/** pageRank: Object that allows calls from pageRank class */
	/** pageRank holds:  frequencyOfWord, age, numOfLinks, money, sum */
	public pageRank pageRank;
	
	/** website holds: the String url of a website */
	public String website;
	
	/*	
	 * Creates a Website constructor for Website class that
	 * has the website's url and its PageRank score.
	 * 
	 * @param website  The website's url.
	 * @param pageRank The PageRank score of the Website.
	 */
	public Website(String website, pageRank pageRank)
	{ 
		int count = 0;
		for (int i = 0; i < website.length(); i++)
		{
			if(website.substring(i, i + 1).equals("/"))
			{
				break;
			}
			count++;
		}
		this.website = website.substring(0, count);
		this.pageRank = pageRank;
	}

	/*
	 * Returns the pageRank.
	 * 
	 * @return pageRank
	 */
	public pageRank getPageRank()
	{
		return pageRank;
	}

	/*
	 * Returns the score of the PageRank.
	 * 
	 * @return sum of all four factors.
	 */
	public int getScore()
	{
		return pageRank.sum;
	}
	
	/*
	 * Returns the number of times the key word 
	 * appears on the website.
	 * 
	 * @return the number of times the key word on the 
	 * website.
	 */
	public int getFrequency()
	{
		return pageRank.frequencyOfWord;
	}
	
	/*
	 * Returns the age of website.
	 * 
	 * @return the age of the website.
	 */
	public int getAge()
	{
		return pageRank.age;
	}
	
	/*
	 * Returns the number of links pointing to the website.
	 * 
	 * @return the number of links pointing to the website.
	 */
	public int getNumOfLinks()
	{
		return pageRank.numOfLinks;
	}
	
	/*
	 * Returns the amount paid by the user for the website to
	 * be shown on the search engine.
	 * 
	 * @return the amount paid for the website.
	 */
	public int getMoney()
	{
		return pageRank.money;
	}
	
	/*
	 * Returns the website's url.
	 * 
	 * @return the website's url.
	 */
	public String getWebsite()
	{
		return website;
	}
	
	/*
	 * Adds to the amount paid for the website. The total amount
	 * of money paid will cap out at 100. The website's PageRank
	 * score will be recalculated.
	 * 
	 * @param money money being added into the total amount paid.
	 */
	public void addMoney(int money)
	{
		pageRank.money = pageRank.money + money;
		if (pageRank.money > 100)
		{
			pageRank.money = 100;
		}
		pageRank.sum = pageRank.frequencyOfWord + pageRank.age + pageRank.numOfLinks + pageRank.money;
	}
}
