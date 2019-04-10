package SearchEngine;

/**
 * Represents a page rank score.
 *
 * @author       Calvin Luong Copyright (2019)
 * @version      1.0
 */

public class pageRank {

	/** frequencyOfWord holds: the amount of key words on the page */
	public int frequencyOfWord;
	
	/** age holds: the duration of the page */
	public int age;
	
	/** numOfLinks holds: the number of links that refer to this page */
	public int numOfLinks;
	
	/** money holds: the amount of money paid for the page */
	public int money;
	
	/** sum holds: the sum of four factors */
	public int sum;

	/*
	 * Creates a pageRank constructor for pageRank class that
	 * has random numbers (1 - 100) for each factor.
	 */
	public pageRank()
	{
		this.frequencyOfWord = (int) (Math.random() * 100 + 1);
		this.age = (int) (Math.random() * 100 + 1);
		this.numOfLinks = (int) (Math.random() * 100 + 1);
		this.money = (int) (Math.random() * 100 + 1);
		this.sum = frequencyOfWord + age + numOfLinks + money;
	}
	
	/*
	 * Creates a pageRank constructor for pageRank class that 
	 * allows custom inputs for each factor.
	 * 
	 * @param frequencyOfWord The amount of key words on the page.
	 * @param age The duration of the page.
	 * @param numOfLinks The number of links that refer to this page. 
	 * @param money The amount of money paid for the page.
	 */
	public pageRank(int frequencyOfWord, int age, int numOfLinks, int money)
	{
		this.frequencyOfWord = frequencyOfWord;
		this.age = age;
		this.numOfLinks = numOfLinks;
		this.money = money;
		getSum();
	}

	/*
	 * Recalculates the sum for a pageRank object.
	 * 
	 * @return sum of all four factors.
	 */
	public int getSum()
	{
		return this.sum = frequencyOfWord + age + numOfLinks + money;
	}
}
