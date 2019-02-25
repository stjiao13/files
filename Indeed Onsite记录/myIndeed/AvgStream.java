package myIndeed;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class AvgStream {

//	optimize : merge record in same second
//	use a separate thread for expire() to avoid 突刺

//	time complexity：
//	record & getAvg() : O(1) if we have n record and n get Avg() ,or their frequency is of the same scale
//	why : suppose we do m record and n getAvg

//	1) do expire only when getAvg
//	total cost for getAvg: we need to remove at most m elements, so the average cost is O(m/n)
//	total cost for record:O(1) per operation


//	2) do expire when getAvg and record
//	cost for getAvg and record: remove at most m elements, other operation is O(1) cost,
//	total operation is m + n + m(remove),so average it is O(1) for getAvg() and record()

//	why call clearMap both when record and getAvg
//	reduce the cost of getAvg() and reduce space used
//	if not, a getAvg() operation may be costly and greatly effect user experience

//	eg : record cost 10ms or 20ms make not much difference
//	getAvg() cost 1s or 2s matters

//	getMedium: quickselect O(n) time complexity,worst case O(n^2)
//	heap-baesed method:
//	getmedium:O(1)
//	add O(logn)
//	remove O(n)
//	To make remove O(logN)
//One way this can be made O(logN) in your own Priority Queue implementation
// is to maintain an auxiliary data structure like a HashMap
// that maintains the mappings from a value in the priority queue to its position in the queue.
// So, at any given time - you would know the index position of any value.


	double sum = 0;
	ArrayDeque<long[]> queue = new ArrayDeque<>();
	int expireTime=3000000;
	public AvgStream() {
	}

	public void record(int val) {
		// merge record at the same time
		if (queue.isEmpty() && queue.peekLast()[1] == System.currentTimeMillis()) {
			queue.peekLast()[0] += val;
		}
		else {
			queue.offer(new long[]{val, System.currentTimeMillis() + expireTime});
		}

		sum += val;
		clearMap();
	}

	public double getAvg() {
		clearMap();
		if (queue.isEmpty()) {
			return 0;
		}
		return sum / queue.size();
	}

	public void clearMap() {
		long curTime = System.currentTimeMillis();
		while (!queue.isEmpty() && queue.peek()[1] + expireTime <= curTime) {
			sum -= queue.peek()[0];
			queue.poll();
		}
	}
}
