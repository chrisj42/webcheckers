package com.webcheckers.ui;

import java.util.ArrayList;
import java.util.Iterator;

public class Row implements Iterable<Space> {
	
	private int idx;
	private final ArrayList<Space> list = new ArrayList<>(BoardView.SIZE);
	
	Row(int idx) {
		this.idx = idx;
		
		for(int i = 0; i < BoardView.SIZE; i++)
			list.add(new Space(i));
	}
	
	public int getIndex() { return idx; }
	
	@Override
	public Iterator<Space> iterator() {
		return list.iterator();
	}
}
