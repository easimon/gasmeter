package org.homenet.easimon.util;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class CollectionUtilsTest {

	private List<Integer> prepareList(int size) {
		final List<Integer> list = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			list.add(i);
		}
		return list;
	}

	@Test
	public void testSplitEven() {
		List<List<Integer>> splitList = CollectionUtils.split(prepareList(1000), 2);
		assertEquals(2, splitList.size());
		for (List<Integer> list : splitList) {
			assertEquals(500, list.size());
		}
		assertEquals(Integer.valueOf(0), splitList.get(0).get(0));
		assertEquals(Integer.valueOf(999), splitList.get(1).get(499));
	}

	@Test
	public void testSplitOdd() {
		List<List<Integer>> splitList = CollectionUtils.split(prepareList(5001), 5);
		assertEquals(5, splitList.size());
		assertEquals(1001, splitList.get(0).size());
		assertEquals(1001, splitList.get(1).size());
		assertEquals(1001, splitList.get(2).size());
		assertEquals(1001, splitList.get(3).size());
		assertEquals(997, splitList.get(4).size());

		assertEquals(Integer.valueOf(0), splitList.get(0).get(0));
		assertEquals(Integer.valueOf(1001), splitList.get(1).get(0));
		assertEquals(Integer.valueOf(5000), splitList.get(4).get(996));
	}

}
