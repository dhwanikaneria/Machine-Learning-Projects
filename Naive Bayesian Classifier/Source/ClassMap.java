package com.naive;

import java.util.HashMap;

public class ClassMap {
	String c;
	int length;
	int count;
	HashMap<String,Integer> hm= new HashMap<String,Integer>();
	public String getC() {
		return c;
	}
	public void setC(String c) {
		this.c = c;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public HashMap<String, Integer> getHm() {
		return hm;
	}
	public void setHm(HashMap<String, Integer> hm) {
		this.hm = hm;
	}
}
