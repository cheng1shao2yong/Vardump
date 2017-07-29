package test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.lcfms.Vardump;

class Bean{
	private int i;
	private String[][] s;
	private List<?> list;
	
	Bean(int i,String[][] s,List<?> list){
		this.i=i;
		this.s=s;
		this.list=list;
	}
	public List<?> getList() {
		return list;
	}
	public void setList(List<?> list) {
		this.list = list;
	}
	public int getI() {
		return i;
	}
	public void setI(int i) {
		this.i = i;
	}
	public String[][] getS() {
		return s;
	}
	public void setS(String[][] s) {
		this.s = s;
	}
	
}
class Test{
	int i;
	char c;
	Test(int i,char c){
		this.i=i;
		this.c=c;
	}
	public int getI() {
		return i;
	}
	public void setI(int i) {
		this.i = i;
	}
	public char getC() {
		return c;
	}
	public void setC(char c) {
		this.c = c;
	}	
}
public class T{	
     public static void main(String[] args) throws Exception{ 
    	/* 
    	int[][][] iarr={
    			{{123,4,5,6,7,5},{123,4,5,6,7,5},{123,4,5,6,7,5}},
    			{{123,4,5,6,7,5},{123,4,5,6,7,5},{123,4,5,6,7,5}}
    			};
    	Vardump.print(iarr);		
    	*/
    	Object[] objects={1,'2',"3",true,null};
    	String[][] strings={{"aaa","bbb","aaa","bbb"},{"aaa","bbb","aaa","bbb"}};
    	char[] c={'1','3','4','6'};
    	//Vardump.print(c);
    	HashMap<Object, Object> map=new HashMap<Object, Object>();
    	map.put("aaa", 1);
    	map.put("bbb", 1.5f);
    	map.put("ccc", true);
    	map.put(5.5f, '5');
    	map.put(1, "12345");
    	map.put(true, objects);
    	map.put('g', strings);
    	map.put(null, "1111");
    	//Vardump.print(map);
    	
    	List<Object> list=new ArrayList<Object>();
    	list.add("abc");
    	list.add('a');
    	list.add(true);
    	list.add(map);
    	list.add(null);
    	//Vardump.print(list);
    	Bean bean=new Bean(1, strings,list);
    	//Vardump.print(bean);
    	
    	Test t1=new Test(123,'a');
    	Test t2=new Test(666,'b');
    	List<Object> list1=new ArrayList<Object>();
    	list1.add(123);
    	list1.add(t1);
    	list1.add(t2);
    	//Vardump.print(list1);
    	
    	HashMap<Object, Object> map1=new HashMap<Object, Object>();
    	map1.put(1,t1);
    	map1.put(2,t2);
    	Vardump.print(map1);
     }

}
