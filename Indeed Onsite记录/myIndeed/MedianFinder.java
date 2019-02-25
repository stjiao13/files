package myIndeed;

import java.util.PriorityQueue;

class MedianFinder {

	PriorityQueue<Integer> maxheap = new PriorityQueue<>((o1,o2) -> o2 - o1);
	PriorityQueue<Integer> minheap = new PriorityQueue<>();

	/** initialize your data structure here. */
	public MedianFinder() {

	}

	public void addNum(int num) {
		if (maxheap.isEmpty() && minheap.isEmpty()) {
			maxheap.offer(num);
		}
		else if (maxheap.isEmpty()) {
			int min = minheap.poll();
			maxheap.offer(Math.min(num,min));
			minheap.offer(Math.max(num,min));
		}
		else if (minheap.isEmpty()) {
			int max = maxheap.poll();
			maxheap.offer(Math.min(num,max));
			minheap.offer(Math.max(num,max));
		}else {
			int leftBound = maxheap.peek();
			int rightBound = minheap.peek();

			if (num <= leftBound) {
				// should add to maxheap
				if (maxheap.size() > minheap.size()) {
					// keep balanced
					minheap.offer(leftBound);
					maxheap.poll();
					maxheap.offer(num);
				}
				else {
					maxheap.offer(num);
				}
			}
			else if (num < rightBound) {
				if (maxheap.size() > minheap.size()) {
					minheap.offer(num);
				}
				else {
					maxheap.offer(num);
				}
			}
			else {
				if (minheap.size() > maxheap.size()) {
					maxheap.offer(rightBound);
					minheap.poll();
					minheap.offer(num);
				}
				else {
					minheap.offer(num);
				}
			}

		}
	}

	public double findMedian() {
		if (maxheap.isEmpty()) {
			return minheap.peek();
		}
		else if (minheap.isEmpty()) {
			return maxheap.peek();
		}
		else {
			if (maxheap.size() + minheap.size() % 2 == 0) {
				return 0.5 * maxheap.peek() + 0.5 * minheap.peek();
			}
			else if (maxheap.size() > minheap.size()){
				return maxheap.peek();
			}
			else {
				return minheap.peek();
			}
		}
	}
}