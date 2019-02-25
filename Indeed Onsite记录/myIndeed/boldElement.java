package myIndeed;

public class boldElement {

	public String bold (String sentence, String keyword) {
		StringBuilder ans = new StringBuilder();
		boolean isEnd = false;
		int start = 0;
		while (start < sentence.length()) {
			System.out.println("start = " + start);
			int index = sentence.indexOf(keyword,start);
			System.out.println("index = " + index);
			if (index == -1) {
				break;
			}
			// judge if valid 
			int lastIndex = index + keyword.length() - 1;

			if (index > 0 && Character.isLetter(sentence.charAt(index - 1))) {
				ans.append(sentence.substring(start,lastIndex + 1));
			}
			else if (lastIndex < sentence.length() - 1 && Character.isLetter(sentence.charAt(lastIndex + 1))) {
				ans.append(sentence.substring(start,lastIndex + 1));
			}
			else {
				ans.append(sentence.substring(start, index));
				ans.append("<b>");
				ans.append(keyword);
				ans.append("</b>");
				System.out.println("ans.toString() = " + ans.toString());

			}

			start = lastIndex + 1;
		}

		ans.append(sentence.substring(start));
		
		return ans.toString();
	}
	
	public static void main (String[] args) {
		System.out.println("new boldElement().bold(\"all and all, I have two alls \") = " +
				new boldElement().bold("all and all  I have two alls and I have a tall dog","I have"));
	}
}
