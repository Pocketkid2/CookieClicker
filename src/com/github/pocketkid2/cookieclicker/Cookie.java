package com.github.pocketkid2.cookieclicker;

public class Cookie {

	// The current cookie count
	private long count;

	public Cookie() {
		count = 0L;
	}

	// Returns the current count
	public long getCount() {
		return count;
	}

	// Increments the count by 1
	public void increment() {
		count++;
	}

	// Increments the count by the number given
	public void add(long i) {
		count += i;
	}

	@Override
	public String toString() {
		return Long.toString(count);
	}
}
