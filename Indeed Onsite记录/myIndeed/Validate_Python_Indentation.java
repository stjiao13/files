package myIndeed;
import java.util.*;

//就是Trie，代码很长，需要写TrieNode, Trie, 以及dfs。写完面试只剩下3min，没有follow up，随便聊了聊结束。
//https://www.1point3acres.com/bbs/forum.php?mod=viewthread&tid=450098&extra=&page=1


//python的indent规则总结：
// 1. control statement 的下一行indent一定比control 大
// 2. 非control statement必须在stack中找到同属于一个indent的group
// eg:
//if True:
//                if True:
//                                        print("123")
//            print(456)
// 第三行没有同属于一个indent的group

//注意规则
//如何定义base
//如何remove comment
//stack不是必要的

//几个edge case
//comment的情况：inline comment会影响判断是否是control statement
//最后一行是：结尾
//control statement 的：后面还有space
//更复杂的情况 ：普通statement中有 '1：30'这样的字符串
//额外要求：输出错误的行

// follow up : if statement
// 1) if a : - 0 'what if'  aif ifb
//   if aif > 1 : first 3chars is "if "

// 2) else if b : - 1
// 3) else : - 2

//2) and 3) must follow a valid if statement in the indent level

// if  a :
//	xxxxx
//  xxxxxx
// else

public class Validate_Python_Indentation {
    // indentation match
    // 1.First line no indent
    // 2.the line after control line is one level more indent than
    // the previous one
    int base = 2;
    class Line {
        int indent;
        boolean isControl;
        int controlStatus;

		public Line(int indent, boolean isControl) {
			this.indent = indent;
			this.isControl = isControl;
		}

		public Line(int indent, boolean isControl, int controlStatus) {
            this.indent = indent;
            this.isControl = isControl;
            this.controlStatus = controlStatus;
        }
    }

    public int validate(String[] lines){
        //就用stack来存之前的line就行
		// Stack is threadsafe - not needed and make it slower and kind of legacy
		// The fact that Stack extends Vector is really strange, in my view.
		// Early in Java, inheritance was overused IMO - Properties being another example.
		// Stack can access element by position -> not needed

        ArrayDeque<Line> stack = new ArrayDeque<>();
        base = 2;
        int lastLine = 0;

        for (int i = 0; i < lines.length; i++) {
            String cur = lines[i];
            int indent = getIndent(cur);
            System.out.println("indent = " + indent);
            System.out.println("lines[i] = " + lines[i]);
			int controlStatus = getControlStatus(cur);
			if (indent == -1) {
                return i;
            }
            boolean isControl = isControl(cur);
            // check valid

            if (!stack.isEmpty()) {
                if (stack.peek().isControl) {
                    if (indent <= stack.peek().indent ) {
                        System.out.println("indent = " + indent);
                        return i;
                    }
                    // else and else if not allowed
					if (controlStatus == 2 || controlStatus == 3) {
						System.out.println("indent = " + indent);
						return i;
					}
                }
                else {
//                    if (indent > stack.peek().indent) {
//                        return i;
//                    }

					// save space
					if (!stack.isEmpty() && stack.peek().indent == indent && !isControl) {
						continue;
					}
                    while (!stack.isEmpty() && stack.peek().indent > indent) {
                        stack.pop();
                    }
                    if (!stack.isEmpty() && stack.peek().indent != indent) {
                        return i;
                    }

                    // 2 and 3 could not follow 3 or -1
                    if (!stack.isEmpty() && stack.peek().controlStatus == 3) {
                    	if(controlStatus != -1 && controlStatus != 1) {
                    		return i;
						}
					}
					if (!stack.isEmpty() && stack.peek().controlStatus == -1) {
						if(controlStatus != -1 && controlStatus != 1) {
							return i;
						}
					}

                }
                stack.push(new Line(indent,isControl,controlStatus));
                lastLine = i;
            }
            else {
                if (indent != 0) {
                    return i;
                }
                stack.push(new Line(indent,isControl));
                lastLine = i;
            }
        }
        if (!stack.isEmpty() && stack.peek().isControl) {
            return lastLine;
        }
        return -1;
    }


    // int a = 3;# -> the code before '#'
	//	     # if empty line -> empty stirng
    public String removeComment(String line) {
    	StringBuilder sb = new StringBuilder();
    	int validChars = 0;
		for (int i = 0; i < line.length(); i++) {
			if (line.charAt(i) == '#') {
				break;
			}
			sb.append(line.charAt(i));
			if (line.charAt(i) != ' ') {
				validChars++;
			}
		}

		if (validChars == 0) {
			return "";
		}
		return sb.toString();
	}


    public int getIndent(String line){
        int spaces = 0;
        System.out.println("line.length = " + line.length());
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == ' ') {
                spaces++;
            }
            else {
                break;
            }
        }
        System.out.println("line = " + line);
        System.out.println("spaces = " + spaces);
        System.out.println("base = " + base);
        if (spaces == 0) {
            return 0;
        }
        if ( base == 0) {
            base = spaces;
        }
        if (spaces % base != 0) {
            return -1;
        }

        return spaces / base;
    }


    //    如果要求control statement下一行缩进一定加一，可以这么写，不需要用stack

    public int validate2(String[] lines) {
        int lastLine = 0;
        Line prev = null;

        for (int i = 0; i < lines.length; i++) {
            String cur = lines[i];
            int indent = getIndent(cur);
            System.out.println("indent = " + indent);
            if (indent == -1) {
                return i;
            }
            boolean isControl = isControl(cur);
            int controlStatus = getControlStatus(cur);

            if (prev == null) {
                if (indent != 0) {
                    return i;
                }
            }
            else {
                if (prev.isControl) {
                    if (indent != prev.indent + 1) {
                        return i;
                    }
                }
                else {
                    if (indent > prev.indent) {
                        return i;
                    }
                }
            }
            prev = new Line(indent,isControl);

        }
        if (prev.isControl) {
            return lines.length - 1;
        }

        return -1;
    }

    public boolean isControl(String line) {
        for (int i = line.length() - 1; i >= 0; i--) {
            if (line.charAt(i) == ' ') {
                continue;
            }
            else if (line.charAt(i) == ':') {
                return true;
            }
            else {
                return false;
            }
        }


        return false;
    }

//    -1 if not control
//	0 1 2 for if, else if, else
	public int getControlStatus(String line) {
    	boolean isControl = false;
		for (int i = line.length() - 1; i >= 0; i--) {
			if (line.charAt(i) == ' ') {
				continue;
			}
			if (line.charAt(i) == ':') {
				isControl = true;
				break;
			}
		}

		if (!isControl) {
			return -1;
		}

		for (int i = 0; i < line.length(); i++) {
			if (line.charAt(i) != ' ') {
				String first3 = line.substring(i,Math.min(i + 3,line.length()));
				if (first3.equals("if ")) {
					return 1;
				}
				String first8 = line.substring(i,Math.min(i + 8,line.length()));
				if (first8.equals("else if ")) {
					return 2;
				}
				String first5 = line.substring(i,Math.min(i +5,line.length()));
				if (first5.equals("else ") ) {
					return 3;
				}
			}
		}

		return 1;
	}


    public static void main(String[] args) {
        Validate_Python_Indentation test = new Validate_Python_Indentation();
        String[] lines = {
                "if def:",
                "  if a :",
                "    b2c",
				"    b2c",
				"    b2c",
				"    b2c",
				"    b2c",
				"    b2c",
				"    b2c",
				"    b2c",
				"    b2c",
				"    b2c",
				"  else if cc:",
                "    b5c"
//                "b6c"
        };
        System.out.println(test.validate(lines));
//		System.out.println("test = " + test.removeComment("    abc  #123"));
//		System.out.println("test = " + test.removeComment("    "));

		//先这样吧，应该行了。
    }
}

/*============= Following Code Credit to Zhu Siyao ===============*/
//public class valid_python_indention {
//    public static boolean valid_python_indentation(List<String> inputs){
//        Stack<Integer> stack = new Stack<>();
//        for(int i=0;i<inputs.size();i++){
//            String str =  inputs.get(i);
//            String abbr = getAbbr(str);
//            int level = str.length()-abbr.length();
//
//            if(i!=0 && inputs.get(i-1).charAt(inputs.get(i-1).length()-1)==':'){
//                if(level<=stack.peek()) return false;
//            }else{
//                while(!stack.isEmpty() && level<stack.peek()) stack.pop();
//                if(!stack.isEmpty() && level!=stack.peek()) return false;
//
//            }
//            stack.push(level);
//            System.out.println(level);
//        }
//
//        return true;
//
//    }
//
//    private static String getAbbr(String str) {
//        String result = str.trim();
//        return result;
//    }
//
//    public static void main(String[] args){
//        List<String> inputs = new ArrayList<>();
//        inputs.add("def");
//        inputs.add("abc:");
//        inputs.add("  bcc");
//        inputs.add("  abc:");
//        inputs.add("    def");
//        inputs.add("    def");
//        inputs.add("  bcc");
//
//        System.out.println(valid_python_indentation(inputs));
//    }
//}