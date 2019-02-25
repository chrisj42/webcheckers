package com.webcheckers.ui;

import java.util.ArrayList;
import java.util.Iterator;

public class BoardView implements Iterable<Row> {
	
	public static final int SIZE = 8;
	private final ArrayList<Row> list = new ArrayList<>(SIZE);
	
	BoardView() {
		for(int i = 0; i < SIZE; i++)
			list.add(new Row(i));
	}
	
	@Override
	public Iterator<Row> iterator() {
		return list.iterator();
	}
}
