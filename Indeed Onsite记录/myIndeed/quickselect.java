package myIndeed;

import java.lang.reflect.Array;
import java.util.Random;

public class quickselect {

	public static void swap(int[] arr, int i, int j){
		int temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}

	public static void shuffle(int[] array,int l, int r) {
		Random random = new Random();
		if (random == null) random = new Random();
		for (int i = r; i >= l; i--) {
			swap(array,i,random.nextInt(r - l) + l);
		}
	}

//	return an index i such that arr[l..i] <= the orginial element arr[r]
//	and arr[i + 1...r] > this pivot element
	public static int partition (int[] arr, int l,int r) {
		if (l == r) {
			return l + 1;
		}
		int i = l;
		shuffle(arr,l,r);
		int pivot = arr[r];
		for (int j = l; j <= r - 1; j++) {
			if (arr[j] <= pivot) {
				swap(arr,i,j);
				i++;
			}
		}

		swap(arr,i,r);

		return i + 1;
	}

	//return the k th smallest element
	public static int quickselect (int[] arr, int k) {
		return selectHelper(arr,0,arr.length - 1,k);

	}

	public static int selectHelper (int[] arr, int l, int r, int k) {
		System.out.println("l = " + l);
		System.out.println("r = " + r);
		System.out.println("k = " + k);
		if (l == r) {
			return arr[l];
		}
		int index = partition(arr,l,r);
		System.out.println("index = " + index);
		if (index == k) {
			return arr[index - 1];
		}
		else if (index < k) {
			return selectHelper(arr,index ,r,k);
		}
		else return selectHelper(arr,l,index - 2,k);
	}

	public static void main (String[] args) {
		int arr[] = {12, 3, 5, 4, 4, 19, 26};
		System.out.println(quickselect(arr,4));
	}
}
