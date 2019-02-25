package myIndeed;

import java.util.Stack;

class MaxStack {

	Stack<Integer> maximumStack;
	int max;

	/**
	 * initialize your data structure here.
	 */
	public MaxStack() {
		maximumStack = new Stack<>();
		max = 0;
	}

	public void push(int x) {
		if (maximumStack.isEmpty())  {
			max = x;
			maximumStack.push(0);
		}
		else {
			int d = max - x;
			maximumStack.push(d);
			max = Math.max(max,x);
		}
	}

	public int pop() {
		int peek = maximumStack.pop();
		if (peek > 0) {
			return max - peek;
		}
		else {
			int maxBefore = max;
			max = maxBefore + peek;
			return maxBefore;
		}
	}

	public int top() {
		int peek = maximumStack.peek();
		if (peek > 0) {
			return max - peek;
		}
		else {
			return max;
		}

	}

	public int peekMax() {
		return max;
	}

	public int popMax() {
		Stack<Integer> aux = new Stack<>();
		while (top() != max) {
			aux.push(pop());
		}
		pop();
		while (!aux.isEmpty()) {
			push(aux.pop());
		}

		return max;
	}
}