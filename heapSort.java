package SearchEngine;

/**
 * Simulates the heap sort algorithm.
 *
 * @author       Calvin Luong Copyright (2019)
 * @version      1.0
 */

public class heapSort {

	/** heapSize holds: size of the heap */
	private int heapSize;
	
	/*
	 * Returns the node's parent.
	 * 
	 * @param i the target node.
	 * 
	 * @return node's parent.
	 */
	public int getParent(int i)
	{
		return i / 2;
	}

	/*
	 * Returns the node's left child.
	 * 
	 * @param i the target node.
	 * 
	 * @return node's left child.
	 */
	public int getLeftChild(int i)
	{
		return 2 * i;
	}

	/*
	 * Returns the node's right child.
	 * 
	 * @param i the target node.
	 * 
	 * @return node's right child.
	 */
	public int getRightChild(int i)
	{
		return 2 * i + 1;
	}

	/*
	 * Maintains the max heap property for the specific node.
	 * 
	 * @param A[] the Array that the target node is in.
	 * @param i the target node.
	 */
	public void maxHeapify(int A[], int i)
	{
		int l = getLeftChild(i);
		int r = getRightChild(i);
		int largest;

		if (l <= heapSize && A[l] > A[i])
		{
			largest = l;
		}
		else
		{
			largest = i;
		}

		if (r <= heapSize && A[r] > A[largest])
		{
			largest = r;
		}

		if (largest != i)
		{
			int temp = A[i];
			A[i] = A[largest];
			A[largest] = temp;

			maxHeapify(A, largest);
		}
	}

	/*
	 * Converts an Array A into a max heap property.
	 * 
	 * @param A[] the Array being converted into max heap property.
	 */
	public void buildMaxHeap(int A[])
	{
		this.heapSize = A.length - 1;
		for (int i = A.length / 2 - 1; i >= 0; i--)
		{
			maxHeapify(A, i);
		}
	}

	/*
	 * Sorts the heap to maintain max heap property.
	 * 
	 * @param A[] the Array that is being sorted using the
	 * heap sort algorithm.
	 */
	public void heapSort(int A[])
	{
		buildMaxHeap(A);
		for (int i = heapSize; i >= 0; i--)
		{
			int temp = A[i];
			A[i] = A[0];
			A[0] = temp;
			this.heapSize--;
			maxHeapify(A, 0);
		}
	}

	/*
	 * Inserts element key into the Array.
	 * 
	 * @param A[] the Array that the key is being inserted into.
	 * @param key the element being added into the Array.
	 */
	public void maxHeapInsert(int A[], int key)
	{
		this.heapSize = heapSize + 1;
		A[heapSize] = Integer.MIN_VALUE;
		heapIncreaseKey(A, heapSize, key);	
	}

	/*
	 * Removes the largest element in the Array.
	 * 
	 * @param A[] the target Array.
	 * 
	 * @return the largest element in the Array.
	 */
	public int heapExtractMaximum(int A[])
	{
		if (heapSize < 0)
		{
			throw new IllegalArgumentException("heap underflow");
		}

		int max = A[0];
		A[0] = A[heapSize];
		heapSize--;
		maxHeapify(A, 0);
		return max;
	}

	/*
	 * Picks a specific element and increases its value.
	 * 
	 * @param A[] target Array.
	 * @param i the index of the target node.
	 * @param key the new value of the current key.
	 */
	public void heapIncreaseKey(int A[], int i, int key)
	{
		if (key < A[i])
		{
			throw new IllegalArgumentException("new key is smaller than current key");
		}
		A[i] = key;
		while (i > 0 && A[getParent(i)] < A[i])
		{
			int temp = A[getParent(i)];
			A[getParent(i)] = A[i];
			A[i] = temp;
			i = getParent(i);
		}
	}

	/*
	 * Returns the element at the zero index in the Array.
	 * 
	 * @param A[] the target Array.
	 * 
	 * @return element at index of zero.
	 */
	public int heapMaximum(int A[])
	{
		return A[0];
	}
	
}
