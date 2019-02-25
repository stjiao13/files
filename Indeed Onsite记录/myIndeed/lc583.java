package myIndeed;

public class lc583 {

//	lcs problem

	public int minDistance(String word1, String word2) {
		int len1 = word1.length();
		int len2 = word2.length();

		int[][] dp = new int[len1 + 1][len2 + 1];
		for (int i = 0; i < word1.length(); i++) {
			for (int j = 0; j < word2.length(); j++) {
				if (word1.charAt(i) == word2.charAt(j)) {
					dp[i + 1][j + 1] = 1 + dp[i][j];
				}
				else {
					dp[i + 1][j + 1] = Math.max(dp[i][j + 1],dp[i + 1][j]);
				}
			}
		}

		int lcsLen = dp[len1][len2];

		return len1 - lcsLen + len2 - lcsLen;
	}
}
