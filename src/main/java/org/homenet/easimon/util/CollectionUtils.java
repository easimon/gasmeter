package org.homenet.easimon.util;

import java.util.ArrayList;
import java.util.List;

public final class CollectionUtils {

	private CollectionUtils() {
		// NOOP
	}

	public static final <T> List<List<T>> split(final List<T> list, final int amountOfSublists) {
		final int rest = list.size() % amountOfSublists;
		final int sizeOfSublists = (list.size() / amountOfSublists) + (rest > 0 ? 1 : 0);
		final List<List<T>> result = new ArrayList<>(amountOfSublists);
		for (int i = 0; i < amountOfSublists; i++) {
			List<T> sublist = list.subList(sizeOfSublists * i, Math.min(sizeOfSublists * (i + 1), list.size()));
			result.add(sublist);
		}
		return result;
	}

}
