package SearchEngine;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * A simulation of Google's search engine.
 *
 * @author       Calvin Luong Copyright (2019)
 * @version      1.0
 * @see also     WebCrawler, pageRank, heapSort, Website
 */

public class GoogleSearchEngine{

	/** crawler: Object that allows calls from WebCrawler class */
	/** crawler holds: url, keyword, urls, USER_AGENT, htmlDocument, 
	 *                 patternDomainName, matcher, DOMAIN_NAME_PATTERN */
	private static WebCrawler crawler;

	/** page: Object that allows calls from pageRank class */
	/** page holds: sum, frequencyOfWord, age, numOfLink, money */
	private static pageRank page;

	/** sorter: Object that allows calls from heapSort() */
	private static heapSort sorter = new heapSort();

	/** list holds: a list of PageRanks */
	private static ArrayList<pageRank> list;

	/** sortedURL holds: the sorted list of Websites */
	private static ArrayList<Website> sortedURL;

	/** websiteList holds: a list of Websites */
	private static ArrayList<Website> websiteList;

	/** URLHeap holds: a list of Websites that are in the heap */
	private static ArrayList<Website> URLHeap = new ArrayList<Website>();

	/** pageRankList holds: a list of PageRank scores */
	private static int[] pageRankList;

	/** heap holds: 30 PageRank scores from the heap */
	private static int[] heap = new int[30];

	public static void main(String args[]) 
	{ 	
		// Asks the user what key word they would like to search and initiates the search by using the WebCrawler.java
		Scanner userInput = new Scanner(System.in);
		System.out.println("----------------------------------");
		System.out.println("What would you like to search up?");
		System.out.println("----------------------------------");
		System.out.print("Input: ");
		String keyWord = userInput.next().toLowerCase();

		crawler = new WebCrawler(keyWord);
		crawler.search();
		createPageRanks();
		createWebsiteList();
		for (int i = 0; i < websiteList.size(); i++)
		{
			System.out.println((i + 1) + ". " + websiteList.get(i).getWebsite() + ", PageRank Score: " + websiteList.get(i).getScore());
		}

		Scanner user = new Scanner(System.in);
		System.out.println("-------------------------");
		System.out.println("Please enter 's' to sort.");
		System.out.println("-------------------------");
		System.out.print("Input: ");
		String test = user.next().toLowerCase();
		while (!test.equals("s"))
		{
			System.out.println("------------------");
			System.out.println("Please enter 's'.   ");
			System.out.println("------------------");
			System.out.print("Input: ");
			test = user.next().toLowerCase();
		}
		
		sort();
		getSortedWebsites();
		
		userInput = new Scanner(System.in);
		System.out.println("----------------------------------------------------------------------");
		System.out.println("To get info of a specific site, enter the number of the site .        ");
		System.out.println("Any number greater than 30 will be capped at 30.");
		System.out.println("Any number less than 1 will skip the step.");
		System.out.println("If you would like to skip this step and perform heap sort, enter '-1' ");
		System.out.println("----------------------------------------------------------------------");
		System.out.print("Input: ");
		int number = userInput.nextInt() - 1;
		while(number != -2)
		{
			if (number >= 0)
			{
				if (number > 29)
				{
					number = 29;
				}
				getInfo(number);
			}
			System.out.println("----------------------------------------------------");
			System.out.println("Next site or perform heap sort by entering in '-1'?");
			System.out.println("----------------------------------------------------");
			System.out.print("Input: ");
			number = userInput.nextInt() - 1;
		}

		priorityQueue();
		System.out.println("--------------------------------------------");
		System.out.println("Here is the first 20 links put into a heap: ");
		System.out.println("--------------------------------------------");
		getHeap();

		System.out.println("------------------------------------------------------");
		System.out.println("Enter 'a' to add a website.");
		System.out.println("Enter 'top' to see the number one site and remove it.");
		System.out.println("Enter 'p' to pay more money for a website.");
		System.out.println("Enter 'quit' to leave.");
		System.out.println("------------------------------------------------------");
		System.out.print("Input: ");
		String input2 = userInput.next().toLowerCase();

		while (!input2.equals("quit"))
		{	
			if (input2.equals("a"))
			{
				System.out.println("You will now be asked to input your website url.");

				System.out.print("Please enter the a website: ");
				String website = userInput.next().toLowerCase();

				System.out.println("You will now be asked to input four factors for you websites, the values range from 1 - 100 for each factor.");
				System.out.println("Inputs out of bounds will autimatically be capped at the lowest or highest value.");

				System.out.print("Please enter the number of times the main key word for your site will show up: ");
				int frequency = userInput.nextInt();
				System.out.print("Please enter how long your site has been active since creation: ");
				int age = userInput.nextInt();
				System.out.print("Please enter the number of links that currently refer to your site: ");
				int links = userInput.nextInt();
				System.out.print("Please enter the amount you would like to pay: ");
				int money = userInput.nextInt();

				insertInHeap(createWebsite(website, frequency, age, links, money));
				getHeap();
			}

			if (input2.equals("top"))
			{
				System.out.println("**REMOVING**");
				System.out.println("Page extracted is: " + URLHeap.get(0).getWebsite() + ". With a rank score of: " + URLHeap.get(0).getScore());
				System.out.println("Frequency of key word on the page: " + URLHeap.get(0).getFrequency());
				System.out.println("Age of the page: " + URLHeap.get(0).getAge());
				System.out.println("Number of link pointing to this page: " + URLHeap.get(0).getNumOfLinks());
				System.out.println("Money: " + URLHeap.get(0).getMoney());
				rankOneSite();
				getHeap();
			}

			if(input2.equals("p"))
			{
				System.out.println("Please enter the list number the website is at: ");
				int rank = userInput.nextInt() - 1;
				if (rank >= 0 && rank <= 29)
				{
					System.out.println("The amount remaining that you can add is: " + (100 - URLHeap.get(rank).getMoney()));
					System.out.println("The amount you want to add: ");
					int amount = userInput.nextInt();
					setRank(rank, amount);
					getHeap();
				}
			}

			System.out.println("------------------------------------------------------");
			System.out.println("Enter 'a' to add a website.");
			System.out.println("Enter 'top' to see the number one site and remove it.");
			System.out.println("Enter 'p' to pay more money for a website.");
			System.out.println("Enter 'quit' to leave.");
			System.out.println("------------------------------------------------------");
			System.out.print("Input: ");
			input2 = userInput.next().toLowerCase();
		}

		System.out.println("Thank you for using!");
	}

	/*
	 * Creates a list of 30 PageRank scores.
	 */
	public static void createPageRanks()
	{
		list = new ArrayList<pageRank>(30);
		for (int i = 0; i < 30; i++)
		{
			page = new pageRank();
			list.add(page);
		}
	}

	/*
	 * Searches using Google search engine to look for sites
	 * relating to the user's keyword. Creates a list of 30 urls 
	 * that each have a unique PageRank score. 
	 */
	public static void createWebsiteList()
	{
		websiteList = new ArrayList<Website>();
		int c = 0;
		for (String s : crawler.getUrls())
		{
			if (c == 30)
			{
				break;
			}
			else
			{
				websiteList.add(new Website(s, list.get(c)));
				c++;
			}
		}
	}

	/*
	 * Inverts the order of the Array.
	 * 
	 * @param A[] The target Array that is being inverted
	 */
	public static void invertArray(int A[])
	{
		for (int i = 0; i < A.length / 2; i++) {
			int temp = A[i];
			A[i] = A[A.length - 1 - i];
			A[A.length - 1 - i] = temp;
		}
	}

	/*
	 * Sorts the PageRank score of each website 
	 * by storing the PageRank scores into pageRankList and 
	 * then sorting it using heap sort. The end result will
	 * be a sorted Array that goes from biggest value to lowest
	 * value.
	 */
	public static void sort()
	{
		pageRankList = new int[30];
		for (int i = 0; i < 30; i++)
		{
			pageRankList[i] = websiteList.get(i).getScore();
		}
		sorter.heapSort(pageRankList);
		invertArray(pageRankList);
	}

	/*
	 * Links the PageRank score with the websites 
	 * by looking for the same PageRanks in pageRankList and
	 * websiteList and storing the website into sortedURL.
	 */
	public static void getSortedWebsites()
	{
		sortedURL = new ArrayList<Website>();
		for (int i = 0; i < 30; i++)
		{
			for (int j = 0; j < websiteList.size(); j++)
			{
				if (pageRankList[i] == websiteList.get(j).getScore())
				{
					sortedURL.add(new Website(websiteList.get(j).getWebsite(), websiteList.get(j).getPageRank()));
					websiteList.remove(j);
				}
			}
		}

		for (int i = 0; i < 30; i++)
		{
			System.out.println((i + 1) + ". " + sortedURL.get(i).getWebsite() + ", PageRank Score: " + sortedURL.get(i).getScore());
		}
	}

	/*
	 * Prints out info for a website.
	 * Info: Frequency of the key word on the page
	 * 		 How long the page has been active
	 * 		 Number of links that refer to the website
	 * 		 The amount of money the creator paid for the site
	 * 
	 * @param i The specific index of the website.
	 */
	public static void getInfo(int i)
	{
		System.out.println("Page is: " + sortedURL.get(i).getWebsite() + ". With a rank score of: " + sortedURL.get(i).getScore());
		System.out.println("Frequency of key word on the page: " + sortedURL.get(i).getFrequency());
		System.out.println("Age of the page: " + sortedURL.get(i).getAge());
		System.out.println("Number of link pointing to this page: " + sortedURL.get(i).getNumOfLinks());
		System.out.println("Money: " + sortedURL.get(i).getMoney());
	}

	/*
	 * Adds the first 20 sorted websites into a heap.
	 */
	public static void priorityQueue()
	{
		for(int i = 0; i < 20; i++)
		{
			sorter.maxHeapInsert(heap, sortedURL.get(i).getScore());
			URLHeap.add(sortedURL.get(i));
		}
	}

	/*
	 * Creates a Website object with custom factors.
	 * 
	 * @param URL The website's link.
	 * @param frequencyOfWord The amount of key words that will be on the page.
	 * @param age Duration of the page since creation.
	 * @param numOfLinks The amount of links pointing to this page.
	 * @param money The amount the creator paid to have this page show up in searches.
	 * 
	 * @return Creates a new Website with the user's input.
	 */
	public static Website createWebsite(String URL, int frequencyOfWord, int age, int numOfLinks, int money)
	{
		pageRank rank = new pageRank(frequencyOfWord, age, numOfLinks, money);
		return new Website(URL, rank);
	}

	/*
	 * Inserts a Website into the heap.
	 */
	public static void insertInHeap(Website w)
	{
		sorter.maxHeapInsert(heap, w.getScore());
		URLHeap.add(w);
	}

	/*
	 * Takes out the highest PageRank score website out of the heap and 
	 * shows the user.
	 */
	public static void rankOneSite()
	{
		sorter.heapExtractMaximum(heap);
	}

	/*
	 * Allows the user to pick a Website and increase the 
	 * PageRank score by adding money. 
	 * 
	 * @param i The index of the Website.
	 * @param money The amount the user is paying.
	 */
	public static void setRank(int i, int money)
	{
		/*
		 * Checks to make sure the total amount paid does not exceed
		 * 100 when the user is paying more money.
		 */
		int amountRemaining = 100 - URLHeap.get(i).getMoney();
		if (amountRemaining <= money)
		{
			money = amountRemaining;
		}

		/*
		 * Updates the money paid for the site
		 */
		URLHeap.get(i).addMoney(money);
		int newScore = heap[i] + money;
		sorter.heapIncreaseKey(heap, i, newScore);
		
	}

	/*
	 * Prints out the heap.
	 */
	public static void getHeap()
	{
		/*
		 * Counts all the elements in the heap Array
		 * that are not 0.
		 */
		int counter = 0;
		for (int i = 0; i < 30; i++)
		{
			if(heap[i] == 0)
			{
				break;
			}
			else
			{
				counter++;
			}
		}

		/*
		 * Creates an int Array copy of the heap Array.
		 */
		int[] temp = new int[counter];
		for (int i = 0; i < counter; i++)
		{
			temp[i] = heap[i];
		}

		/*
		 * Creates a Website ArrayList copy of the 
		 * URLHeap arraylist.
		 */
		ArrayList<Website> tempURLHeap = new ArrayList<Website>();
		for (Website w : URLHeap)
		{
			tempURLHeap.add(w);
		}

		/*
		 * Links the PageRank scores in heap with 
		 * the websites in URLHeap.
		 */
		URLHeap = new ArrayList<Website>();
		for (int i = 0; i < temp.length; i++)
		{
			for (int j = 0; j < tempURLHeap.size(); j++)
			{
				if (heap[i] == tempURLHeap.get(j).getScore())
				{
					URLHeap.add(new Website(tempURLHeap.get(j).getWebsite(), tempURLHeap.get(j).getPageRank()));
					tempURLHeap.remove(j);
				}
			}
		}

		/*
		 * Prints out the URL and its PageRank score.
		 */
		for (int i = 0; i < URLHeap.size(); i++)
		{
			System.out.println((i + 1) + ". " + URLHeap.get(i).getWebsite() + ", PageRank Score: " + URLHeap.get(i).getScore());
		}
	}
}
