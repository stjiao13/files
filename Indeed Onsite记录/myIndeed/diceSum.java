package myIndeed;

import java.lang.reflect.Array;
import java.util.Arrays;


// bottom up dp
// 三角形二维数组节约空间

// bottoup dfs + memo可能比dp快

public class diceSum {

	// f(2,11) = f(1,5)/6 + f(1,6)/6
	// f(times,target) = f(times - 1, target - 1) ... f(times - 1,target - 6)/6
	double[][] memo;
	// O(times * target)

	public double solve(int times, int target) {
		memo = new double[times + 1][];
//		for (int i = 0; i < memo.length; i++) {
//			Arrays.fill(memo[i],-1);
//		}
		return diceSum(times,target);
	}

	public double diceSum (int times,int target) {
		if (target <= 0 || target > 6 * times) {
			return 0;
		}

		if (times ==  1) {
			return 1.0/6.0;
		}
		if (memo[times] == null) {
			memo[times] = new double[6 * times + 1];
		}

		if (memo[times][target] != 0) {
			return memo[times][target];
		}

		double ans = 0;
		for (int i = 1; i <= 6;i++) {
			ans += diceSum(times - 1,target - i) / 6.0;
		}
		memo[times][target] = ans;
		return ans;
	}

	public static void main (String[] args) {
		diceSum test= new diceSum();
		System.out.println(test.solve(2,2));
	}
}
