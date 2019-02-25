package myIndeed;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class reverseHTML {

	public static String reverseHTML2(String res) {
		if (res == null || res.length() == 0) return res;

		List<String> tokens = new LinkedList<String>();

		int len = res.length();
		int pointer = 0;
		int leftBorder = 0;

		//1234&euro;
		while (pointer < len) {

			char curChar = res.charAt(pointer);

			if (curChar != '&') {
				pointer++;
			} else {
				//reverse part before html, and store in tokens
				if (pointer != 0) {
					String beforeToken = reverse(res, leftBorder, pointer - 1);
					tokens.add(beforeToken);
					//update left. check 1point3acres for more.
					leftBorder = pointer;
				}

				//get html part, put into token
				StringBuilder sb = new StringBuilder();
				while (pointer < len && res.charAt(pointer) != ';') {
					sb.append(res.charAt(pointer));
					pointer++;
				}

				if (pointer < len) {
					sb.append(';');
					tokens.add(sb.toString());
					leftBorder = ++pointer;
				}
			}

		}

		//reverse the last part, put into token : left pointer to the end
		if (leftBorder < pointer) {
			String afterToken = reverse(res, leftBorder, res.length() - 1);
			tokens.add(afterToken);
		}

		StringBuilder result = new StringBuilder();
		for (int i = tokens.size() - 1; i >= 0; i--) {
			result.append(tokens.get(i));
		}

		return result.toString();
	}

	private static String reverse(String res, int leftBorder, int pointer) {
		StringBuilder sb = new StringBuilder();
		while (leftBorder <= pointer) {
			sb.append(res.charAt(pointer));
			pointer--;
		}
		return sb.toString();
	}

	public static String reverseHTML3(String res) {
		if (res == null || res.length() == 0) return res;
		StringBuilder ans = new StringBuilder();
		int endIdx = -1;

		for (int i = res.length() - 1; i >= 0; i--) {
			if (res.charAt(i) == ';') {
				if (endIdx == -1) {
					endIdx = i;
				}
				else {
					ans.append(new StringBuilder(res.substring(i + 1,endIdx + 1))
							.reverse().toString());
					endIdx = i;
				}
			}
			else if (res.charAt(i) == '&') {
				if (endIdx != -1) {
					ans.append(new StringBuilder(res.substring(i, endIdx + 1)));
					endIdx = -1;
				}
				else {
					ans.append(res.charAt(i));
				}
			}
			else {
				if (endIdx == -1) {
					ans.append(res.charAt(i));
				}
			}
		}
		if (endIdx != -1) {
			ans.append(new StringBuilder(res.substring(0,endIdx + 1))
					.reverse().toString());
		}
		return ans.toString();
	}

	public static String reverseHTML(String res) {
		if (res == null || res.length() == 0) return res;
		char [] arr = res.toCharArray();
		reverse(arr,0,arr.length - 1);
		int startIdx = -1;
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] == ';') {
				startIdx = i;
			}
			else if (arr[i] == '&') {
				if (startIdx != -1) {
					reverse(arr,startIdx,i);
					startIdx = -1;
				}
			}
		}

		return String.valueOf(arr);
	}

	private static void reverse(char[] arr, int start, int end) {
		while (start < end) {
			char temp = arr[start];
			arr[start] = arr[end];
			arr[end] = temp;
			start++;
			end--;
		}
	}
//https://leetcode.com/problems/reverse-words-in-a-string/description/
//	Input: "the sky is blue",
//Output: "blue is sky the".
//	may have more than one spaces between two words

	public String reverseWords(String s) {
		if(s == null || s.length() == 0) {
			return "";
		}
		char[] arr = s.toCharArray();
		// start index in arr
		int index = 0;
		int begin = -1;
		int end = -1;
		rev2(arr,0,arr.length - 1);
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] != ' ') {
				if (begin == -1) {
					begin = i;
				}
				end = i;
			}
			else if (begin != -1){
				rev(arr,index,end);
				index +=(end - begin + 2);
				begin = -1;
				end = -1;
			}
		}

		if (begin != -1) {
			rev(arr,index,end);
			index +=(end - begin + 2);
		}
		if (index == 0) {
			return "";
		}
		char[] copy = Arrays.copyOfRange(arr,0,index - 1);
		return String.valueOf(copy);
	}


	public static String reverseWords2(String s) {
		if(s == null || s.length() == 0) {
			return "";
		}
		char[] arr = s.toCharArray();
		// start index in arr
		int index = 0;
		int begin = -1;
		int end = -1;
		rev2(arr,0,arr.length - 1);
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] != ' ') {
				if (begin == -1) {
					begin = i;
				}
				end = i;
			}
			else {
				if (begin != -1) {
					// first copy
					int start = index;
					for (int j = begin; j <= end; j++) {
						arr[index++] = arr[j];
					}
					// then reverse
					rev2(arr,start,index - 1);
					if (index < arr.length) {
						arr[index++] = ' ';
					}
					begin = -1;

				}
			}

		}
		if (begin != -1) {
			// first copy
			int start = index;
			for (int j = begin; j <= end; j++) {
				arr[index++] = arr[j];
			}
			// then reverse
			rev2(arr, start, index - 1);
		}

//		if (index > 0 && arr[index - 1] == ' ') {
//			index--;
//		}

		char[] copy = new char[index];
		for (int i = 0; i < copy.length; i++) {
			copy[i] = arr[i];
		}

		return String.valueOf(copy);


	}

	private void rev(char[] arr, int start, int end) {
		while (start < end) {
			if (arr[start] == arr[end] && arr[start] == ' ') {
				return;
			}
			char temp = arr[start];
			arr[start] = arr[end];
			arr[end] = temp;
			start++;
			end--;
		}

	}

	private static void rev2(char[] arr, int start, int end) {
		while (start < end) {
			char temp = arr[start];
			arr[start] = arr[end];
			arr[end] = temp;
			start++;
			end--;
		}

	}

	public static void main(String[] args) {
		System.out.println(reverseHTML("&euro"));
		System.out.println(reverseHTML("&euro;"));
		System.out.println(reverseHTML("1234&euro;"));
		System.out.println(reverseHTML("1234&euro"));
		System.out.println(reverseHTML("1234&euro;324&euro;&euro;222"));
		System.out.println(reverseHTML("aaa;aaa &amp; bbb;;;"));
		System.out.println("re = " + reverseWords2("the    sky    is    blue   "));
	}
}