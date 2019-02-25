package myIndeed;
import java.util.*;
//K Stream 那一轮？如果假设有M个Stream，每个Stream的平均长度是N的话，
// 那么如果用HashMap来做的话，时间复杂度的话是O(MN + MNlog(MN)) 遍历+排序，空间复杂度是O(MN),
// mnlogmn 是考虑到结果最多有mn个，因此排序的cost的是mnlogmn
// 如果用heap来做的话，时间复杂度是O(MNlog(M))，空间复杂度是 O(M), 请问楼主这么分析是不是就够了？
class Stream{
	Iterator<Integer> iterator;
	public Stream(List<Integer> list){
		this.iterator = list.iterator();
	}
	public boolean move(){
		return iterator.hasNext();
	}
	public int getValue(){
		return iterator.next();
	}
}
class Num{
	int val;
	Stream stream;
	public Num(Stream stream){
		this.stream = stream;
		this.val = stream.getValue();
	}
}
/*  这个是iterator的版本，我真无聊
    class Data{
        int val;
        Iterator ite;
        public Data(Iterator iterator){
            this.ite = iterator;
            this.val = ite.next();
        }
    }
*/

class Merge_K_Sorted_Streams {

	class Node {
		List<Stream> ls ;
		int val;

		public Node(int val) {
			this.val = val;
			this.ls = new ArrayList<>();
		}
	}

	public List<Integer> getNumberInAtLeastKStream(List<Stream> lists, int k){
		List<Integer> res = new ArrayList<>();
		if (lists == null || lists.size() == 0) return res;

		PriorityQueue<Num> minHeap = new PriorityQueue<>(new Comparator<Num>() {
			@Override
			public int compare(Num o1, Num o2) {
				return o1.val - o2.val;
			}
		});
		//先把所有的stream放进heap里面
		for (Stream s: lists) {
			if (s.move()){ //这里先判断一下要不就崩了
				minHeap.offer(new Num(s));
			}
		}

		while (!minHeap.isEmpty()){
			Num cur = minHeap.poll();
			int curValue = cur.val;
			int count = 1;
			while (cur.stream.move()){
				int nextVal = cur.stream.getValue();
				if (nextVal == curValue){
					continue;
				}
				else {
					cur.val = nextVal;
					minHeap.offer(cur);
					break;
				}
			}
			//更新其他stream的头部，就是把指针往后挪，相同的数字就计数了。
			while (!minHeap.isEmpty() && curValue == minHeap.peek().val){
				count++;
				Num num = minHeap.poll();
//                int numVal = num.val;

				while (num.stream.move()){
					int nextVal = num.stream.getValue();
					if (curValue == nextVal){
						continue;
					}
					else {
						num.val = nextVal;
						minHeap.offer(num);
						break;
					}
				}
			}

			if (count >= k){
				res.add(curValue);
			}
		}
		return res;
	}

	public List<Integer> getNumberInAtLeastKStream2(List<Stream> lists, int k){
		List<Integer> res = new ArrayList<>();
		if (lists == null || lists.size() == 0) return res;
		Map<Integer,Node> map = new HashMap<>();
		PriorityQueue<Node> minHeap = new PriorityQueue<>((o1,o2) -> (o1.val - o2.val));

		for (int i = 0; i < lists.size(); i++) {
			Stream cur = lists.get(i);
			if (cur.move()) {
				int val = cur.getValue();
				if (map.containsKey(val)) {
					Node node = map.get(val);
					node.ls.add(cur);
				}
				else {
					Node node = new Node(val);
					node.ls.add(cur);
					minHeap.add(node);
					map.put(val,node);
				}
			}
		}

		while (!minHeap.isEmpty()) {
			Node min = minHeap.poll();
//			map.remove(min.val);
			if (min.ls.size() >= k) {
				res.add(min.val);
			}
			for (int i = 0; i < min.ls.size(); i++) {
				Stream cur = min.ls.get(i);
				if (cur.move()) {
					int val = cur.getValue();
					if (map.containsKey(val)) {
						Node node = map.get(val);
						node.ls.add(cur);
					}
					else {
						Node node = new Node(val);
						node.ls.add(cur);
						minHeap.add(node);
						map.put(val,node);
					}
				}
			}
		}

		return res;


	}

	public static void main(String[] args) {
		Merge_K_Sorted_Streams test = new Merge_K_Sorted_Streams();
		Integer[] arr1 = {1,2,3,4,10};
		Integer[] arr2 = {2,5,6,10,14};
		Integer[] arr3 = {5,7,12,14};

		List<Integer> l1 = Arrays.asList(arr1);
		List<Integer> l2 = Arrays.asList(arr2);
		List<Integer> l3 = Arrays.asList(arr3);

		List<Stream> lists = new ArrayList<>();
		lists.add(new Stream(l1));
		lists.add(new Stream(l2));
		lists.add(new Stream(l3));

		List<Integer> res = test.getNumberInAtLeastKStream2(lists, 2);
		System.out.println(res);
	}
}