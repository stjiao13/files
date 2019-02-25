package myIndeed;

/* =============================================================================
Question
=============================================================================*/
//Given a LinkedList, every node contains a array. Every element of the array is char
//		implement two functions
//		1. get(int index) find the char at the index
//		2. insert(char ch, int index) insert the char to the index
/* =============================================================================
code思路
=============================================================================*/

//two ways for appending node:
// eg: insert(4,ch) three times
// 5-1 : less cost for insert,more space, more cost for get
// eg: 5 -> 5-1 -> 5-1-1 -> 5-1-1-1
// 3-3 : more cost for insert because we have more operation, less space and less cost for get
// eg: 5 -> 3-3 -> 3-4 -> 3-5

//optimization ： splay tree

//Time Complexity:
// assume n is the number of chars and k is average number of chars per node
// get:O(n/k)
//insert: O(n/k)


//Advantages of Unrolled Linked List
//1. Due to its cache behavior, the unrolled linked list performs the sequential traversal very rapidly.
//2. It requires less storage space.
//3. It performs the operations more quickly than ordinary linked list.
//4. Indexing time O (N) is reduced to O (N/max), as we are able to process a whole node at a time instead of individual elements.
//
//Disadvantages of Unrolled Linked List
//The overhead per node for references and elements count is considerably high.
class Node{
	char[] chars = new char[5]; //定长5,反正总要有定长。
	int len; //表示数组里面实际有几个字母
	Node next;
	public Node(){}
}
class LinkedList{
	Node head;
	int totalLen; //这个totalLen代表所有char的个数
	public LinkedList(Node head, int total){
		this.head = head;
		this.totalLen = total;
	}

	public LinkedList() {

	}

	public char get(int index){
			Node iter = head;
			while (iter !=null && index >= iter.len) {
				index -= iter.len;
				iter = iter.next;
			}

			if (iter == null) {
				return ' ';
			}
			else {
				return iter.chars[index];
			}
	}
	//insert需要考虑1.普通插进去。2.插入的node满了，要在后面加个node。
	//3.插入的node是空的，那就要在尾巴上加个新node。
	//还需要考虑每个node的len，以及totalLen的长度变化。
	public void insert(char ch, int index){
		if (index < 0 || index > totalLen) {
			return;
		}
		totalLen++;

		if (head == null) {
			Node newNode = new Node();
			newNode.chars[0] = ch;
			newNode.len = 1;
			head = newNode;
			return;
		}

		Node prev = null;
		Node iter = head;

		while (iter !=null && index >= iter.len) {
			index -= iter.len;
			prev = iter;
			iter = iter.next;
		}

		if (iter == null) {
			Node newNode = new Node();
			newNode.chars[0] = ch;
			prev.next = newNode;
			newNode.len = 1;
		}
		else {
			if (iter.len < 5) {
				for (int i = iter.len - 1; i >= index; i--) {
					iter.chars[i + 1] = iter.chars[i];
				}
				iter.chars[index] = ch;
				iter.len++;
				return;
			}
			else {
				Node newNode = new Node();
				char[] temp = new char[6];
				for (int i = 0; i < index; i++) {
						temp[i] = iter.chars[i];
				}

				temp[index] = ch;
				for (int i = index; i < 5; i++) {
					temp[i + 1] = iter.chars[i];
				}
				iter.len = 3;
				for (int i = 3; i < 6; i++) {
					newNode.chars[i - 3] = temp[i];
				}
				newNode.len = 1;
				iter.next = newNode;
			}

		}
	}


	public void delete(int index){
		Node iter = head;
		Node prev = null;

		while (iter.next !=null && index >= iter.len) {
			index -= iter.len;
			prev = iter;
			iter = iter.next;
		}

		if (index == 5) {
			return;
		}
		else {
			if (index >= iter.len) {
				return;
			}
			else {
				totalLen--;
				if (iter.len == 0) {
					prev.next = iter.next;
					return;
				}
				int start = index;
				int end = iter.len;
				while (start < end - 1) {
					iter.chars[start] = iter.chars[start + 1];
				}
			}
		}
	}

	//链表题到时候画一个下面的小case，就能对准index了。
	public static void main(String[] args) {
		Node n1 = new Node(); //a b
		Node n2 = new Node(); //b
		Node n3 = new Node(); //a b c d e


		n1.chars[0] = 'a';
		n1.chars[1] = 'b';
		n2.chars[0] = 'b';
		n3.chars[0] = 'a';
		n3.chars[1] = 'b';
		n3.chars[2] = 'c';
		n3.chars[3] = 'd';
		n3.chars[4] = 'e';

		n1.next = n2;
		n2.next = n3;
		n1.len = 2;
		n2.len = 1;
		n3.len = 5;


		LinkedList ls = new LinkedList(null,0);
		ls.insert('a',0);
		System.out.println(ls.get(0));
		ls.insert('b',0);
		System.out.println("ls.get(0) = " + ls.get(0));
		System.out.println("ls.get(1) = " + ls.get(1));
		ls.insert('c',ls.totalLen);

		for (int i = 0; i < 128; i++) {
			ls.insert((char)i,ls.totalLen);
		}
		for (int i = 0; i < ls.totalLen; i++) {
			System.out.println("ls.get(i) = " + (int)ls.get(i));
		}

	}
}