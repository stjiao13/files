package myIndeed;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class quintiles {

	public static int[] getQuintile (int[][] input, int k) {
		// 0 ... n - 1
		// 1 ... n
		//1-based: n/k, 2 * n / k, 3 * n / k,  (k - 1) * n / k
		//0-based: 1-based - 1

		// int[0] num, int[1] how many numbers is equal of lower than int[0];
		List<int[]> ls = new ArrayList<>();
		int sum = 0;

		Arrays.sort(input,(o1,o2) -> (o1[0] - o2[0]));

		// construct ls

		for (int i = 0; i < input.length; i++) {
			sum += input[i][1];
			ls.add(new int[]{input[i][0],sum});
		}

		int[] ans = new int[k - 1];
		int index= 0;
		// [5,2],[6,2],[7,2]
		// sum/k = 2

		for (int i = 0; i < ls.size(); i++) {
			int start;
			if (i == 0) {
				start = 1;
			}
			else {
				start = ls.get(i - 1)[1] + 1;
			}
			int end = ls.get(i)[1];
			// start 0 3
			// end 2 4
			int startIdx ;//0 //2
			int endIdx ;//1 // 2

			//  [0.5 1.4]
			// start - upper bound
			// end / lower bound
			// [0 3]

			if (start % (sum/k) == 0) {
				startIdx = start / (sum / k);
			}
			else {
				startIdx = start / (sum /k) + 1;
			}

			endIdx = end / (sum / k);

			for (int j = startIdx; j <= endIdx; j++) {
				if (j > 0 && j < k) {
					// 1 2 k -1
					ans[index++] = ls.get(i)[0];
				}
			}

		}

		for (int i = 0; i < ans.length; i++) {
			System.out.println("ans = " + ans[i]);
		}

		return ans;

	}

	public static void main(String[] args) {
//		List<int[]> ls = new ArrayList<>();
////		ls.add(new int[]{5,2});
////		ls.add(new int[]{6,2});
////		ls.add(new int[]{7,2});

		int[][] input = new int[][]{{5,2},{6,2},{7,2}};

		getQuintile(input,3);


	}
}
