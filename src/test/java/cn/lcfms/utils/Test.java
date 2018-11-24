package cn.lcfms.utils;

import java.io.File;


class Obj{
	public int mi=123;
	public String ms="abc";
}

class Bean{
	private int i;
	private String[] strs;
	private Obj obj;
	public void setI(int i) {
		this.i = i;
	}
	public void setStrs(String[] strs) {
		this.strs = strs;
	}
	public void setObj(Obj obj) {
		this.obj = obj;
	}
	public int getI() {
		return i;
	}
	public String[] getStrs() {
		return strs;
	}
	public Obj getObj() {
		return obj;
	}		
}

public class Test {	
	@org.junit.jupiter.api.Test
	public void t1() {
		File file=new File("D:\\1.txt");
		Vardump.print(file);
	}
}
