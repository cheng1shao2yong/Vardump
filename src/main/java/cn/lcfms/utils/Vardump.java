package cn.lcfms.utils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


/**
 * 打印基本数据类型与普通可以直接显示的类型
 * @author 老成
 */
class Basic{
	protected static Stack<Integer> stack=new Stack<>();
	protected static StringBuffer content=new StringBuffer();
	private static HashSet<Object> set=new HashSet<>();
	final protected static Class<?>[] C={
			Byte.class,
			Short.class,
			Integer.class,
			Long.class,
			Float.class,
			Double.class,
			Boolean.class,
			Character.class,
			String.class,
			StringBuilder.class,
			StringBuffer.class,
			BigDecimal.class,
			BigInteger.class
	};
	
	final protected static String[] N={
			"(byte)",
			"(short)",
			"(int)",
			"(long)",
			"(float)",
			"(double)",
			"(boolean)",
			"(char)",
			"(string)",
			"(stringbuilder)",
			"(stringbuffer)",
			"(bigdecimal)",
			"(biginteger)"
	};
		
	public static<T> void print(T t){
		if(t==null){
			append("null");
		}else{
			boolean b=true;
			for(int i=0;i<Basic.C.length;i++){
				Class<?> c = Basic.C[i];
				if(t.getClass().equals(c)){
					if(c.getName().equals("java.lang.String")){					
						int length=((String)t).length();
						String r=Basic.N[i]+"\""+t+"\""+" (length="+length+")";
						append(r);
					}else if(c.getName().equals("java.lang.Character")){					
						String r=Basic.N[i]+"'"+t+"'";
						append(r);
					}else{
						String r=Basic.N[i]+t;
						append(r);
					}	
					b=false;
				}		
			}	
			if(b){
				printObj(t);
			}
		}		
		show();
	}
	protected static<T> void print(String before,T t,String after){
		if(t==null){
			append(before+"null"+after);
		}else{
			boolean b=true;
			for(int i=0;i<Basic.C.length;i++){
				Class<?> c = Basic.C[i];
				if(t.getClass().equals(c)){
					if(c.getName().equals("java.lang.String") ){					
						int length=((String)t).length();
						String r=before+Basic.N[i]+"\""+t+"\" (length="+length+")"+after;
						append(r);
					}else if(c.getName().equals("java.lang.Character")){					
						String r=before+Basic.N[i]+"'"+t+"'"+after;
						append(r);
					}else{
						String r=before+Basic.N[i]+t+after;
						append(r);
					}
					b=false;
				}
			}
			if(b){
				append(before);
				Basic.stack.push(1);
				printObj(t);
				Basic.stack.pop();
				append(after);
			}
		}
	}
		
	protected static void append(String str){
		if(!str.equals("")){
			content.append(stackToString()+str+"\n");	
		}			
	}
		
	protected static void show(){
		System.out.print(content);
		content=new StringBuffer();
	}
	
	private static String stackToString(){
		StringBuffer result=new StringBuffer();
		for(int i=0;i<Basic.stack.size();i++){
			result.append("    ");
		}
		return result.toString();
	}
	
	private static void printObj(Object obj){	
		set.add(obj);
		Class<?> beanClass=obj.getClass();
		Field[] fields=beanClass.getDeclaredFields();
		printObjectBefore(obj.getClass().getName());	
		for (Field field : fields) {
			field.setAccessible(true);
			try {
				Object o=field.get(obj);				
				if(set.contains(o)) {
					append(field.getName()+"=>("+o.getClass().getName()+")this");
					continue;
				}
				Coll.print(field.getName()+"=>",o,"");	
			} catch (Exception e) {
				continue;
			}		
		}	
		printObjectAfter();
	}
	
	private static void printObjectBefore(String type){
		append(type+"(");
		Basic.stack.push(1);
	}
	private static void printObjectAfter(){
		Basic.stack.pop();
		append(")");
	}
}
/**
 * 打印数组类型
 * @author 老成
 */
class Array extends Basic{	

	public static<T> void print(T t){
		if(null==t) {
			Basic.print(t);	
			return;
		}
		if(t.getClass().isArray()){
			Array.print(t,0);
			show();
			return;
		}
		Basic.print(t);	
	}

	protected static<T> void print(T t,int id){
		if(t==null){
			Basic.print("["+id+"]=>",null,"");
			return;
		}
		Class<?> c=t.getClass();
		if(c.isArray()){
			String typeName=c.getTypeName();	
			try{
				Object[] arr = (Object[]) t;
				String type=arr.getClass().getTypeName();
				type=type.substring(type.lastIndexOf(".")+1,type.indexOf("[]"));				
				printArrayBefore(id,type);	
				for(int i=0;i<arr.length;i++){							 
					Array.print(arr[i],i);				
				}	
				printArrayAfter();
			}catch(Exception e){
				switch(typeName){
					case "byte[]":	
						byte[] m0=(byte[])t;
						Array.print(m0,id);
						break;
					case "short[]":
						short[] m1=(short[])t;
						Array.print(m1,id);
						break;
					case "int[]":
						int[] m2=(int[])t;
						Array.print(m2,id);
						break;
					case "long[]":
						long[] m3=(long[])t;
						Array.print(m3,id);
						break;
					case "double[]":
						double[] m4=(double[])t;
						Array.print(m4,id);
						break;
					case "float[]":	
						float[] m=(float[])t;
						Array.print(m,id);
						break;
					case "boolean[]":
						boolean[] m5=(boolean[])t;
						Array.print(m5,id);
						break;
					case "char[]":
						char[] m6=(char[])t;
						Array.print(m6,id);
						break;					
				}
			}
		}else{
			Basic.print("["+id+"]=>",t,"");
		}
	}	
	
	protected static void print(byte[] data,int id){
		String type=data.getClass().getTypeName();
		type=type.substring(type.lastIndexOf(".")+1,type.indexOf("[]"));	
		printArrayBefore(id,type);
		for(int i=0;i<data.length;i++){
			Basic.print("["+i+"]=>",data[i],"");
		}
		printArrayAfter();
	}	
	protected static void print(short[] data,int id){
		String type=data.getClass().getTypeName();
		type=type.substring(type.lastIndexOf(".")+1,type.indexOf("[]"));	
		printArrayBefore(id,type);
		for(int i=0;i<data.length;i++){
			Basic.print("["+i+"]=>",data[i],"");
		}
		printArrayAfter();
	}	
	protected static void print(int[] data,int id){
		String type=data.getClass().getTypeName();
		type=type.substring(type.lastIndexOf(".")+1,type.indexOf("[]"));	
		printArrayBefore(id,type);
		for(int i=0;i<data.length;i++){
			Basic.print("["+i+"]=>",data[i],"");
		}
		printArrayAfter();
	}	
	protected static void print(long[] data,int id){
		String type=data.getClass().getTypeName();
		type=type.substring(type.lastIndexOf(".")+1,type.indexOf("[]"));	
		printArrayBefore(id,type);
		for(int i=0;i<data.length;i++){
			Basic.print("["+i+"]=>",data[i],"");
		}
		printArrayAfter();
	}	
	protected static void print(float[] data,int id){
		String type=data.getClass().getTypeName();
		type=type.substring(type.lastIndexOf(".")+1,type.indexOf("[]"));	
		printArrayBefore(id,type);
		for(int i=0;i<data.length;i++){
			Basic.print("["+i+"]=>",data[i],"");
		}
		printArrayAfter();
	}	
	protected static void print(double[] data,int id){
		String type=data.getClass().getTypeName();
		type=type.substring(type.lastIndexOf(".")+1,type.indexOf("[]"));	
		printArrayBefore(id,type);
		for(int i=0;i<data.length;i++){
			Basic.print("["+i+"]=>",data[i],"");
		}
		printArrayAfter();
	}	
	protected static void print(boolean[] data,int id){
		String type=data.getClass().getTypeName();
		type=type.substring(type.lastIndexOf(".")+1,type.indexOf("[]"));	
		printArrayBefore(id,type);
		for(int i=0;i<data.length;i++){
			Basic.print("["+i+"]=>",data[i],"");
		}
		printArrayAfter();
	}
	protected static void print(char[] data,int id){
		String type=data.getClass().getTypeName();
		type=type.substring(type.lastIndexOf(".")+1,type.indexOf("[]"));	
		printArrayBefore(id,type);
		for(int i=0;i<data.length;i++){
			Basic.print("["+i+"]=>",data[i],"");
		}
		printArrayAfter();
	}
	private static void printArrayBefore(int id,String type){
		append(type+"[](");
		Basic.stack.push(1);
	}
	private static void printArrayAfter(){
		Basic.stack.pop();
		append(")");
	}
}
/**
 * 打印集合类型
 * @author 老成
 */
class Coll extends Array{

	public static <T> void print(Collection<T> coll){
		String type=coll.getClass().getTypeName();
		type=type.substring(type.lastIndexOf(".")+1);
		printCollBefore(type);
		Iterator<T> iter=coll.iterator();
		int i=0;
		while(iter.hasNext()){
			T t=iter.next();
			if(t==null){
				Basic.print("["+i+"]=>",null,"");
				i++;
			}else{
				if(t.getClass().isArray()){
					Basic.append("["+i+"]=>");
					Basic.stack.push(1);
					Array.print(t);
					Basic.stack.pop();
					i++;
					continue;
				}			
				if(t instanceof Collection){
					Basic.append("["+i+"]=>");
					Basic.stack.push(1);
					Coll.print((Collection<?>)t);
					Basic.stack.pop();
					i++;
					continue;
				}
				if(t instanceof Map){
					Basic.append("["+i+"]=>");
					Basic.stack.push(1);
					Coll.print((Map<?,?>)t);
					Basic.stack.pop();
					i++;
					continue;
				}
				if(t instanceof Enumeration){
					Basic.append("["+i+"]=>");
					Basic.stack.push(1);
					Coll.print((Enumeration<?>)t);
					Basic.stack.pop();	
					i++;
					continue;
				}
				Basic.print("["+i+"]=>",t,"");
				i++;				
			}			
		}
		printCollAfter();
		show();
	}
	
	public static <K, V> void print(Map<K,V> map){
		if(null==map){
			Basic.print(null);
			return;
		}
		String type=map.getClass().getTypeName();
		type=type.substring(type.lastIndexOf(".")+1);
		printCollBefore(type);
		Set<?> set=map.keySet();
		Iterator<?> iter=set.iterator();
		while(iter.hasNext()){
			Object key=iter.next();	
			Object value=map.get(key);	
			String k_b="";
			if(key==null){
				k_b="null";
			}else{
				for(int i1=0;i1<Basic.C.length;i1++){
					Class<?> c = Basic.C[i1];
					if(key.getClass().equals(c)){
						if(c.getName().equals("java.lang.String")){					
							int length=((String)key).length();
							k_b=Basic.N[i1]+"\""+key+"\" (length="+length+")";
						}else if(c.getName().equals("java.lang.Character")){					
							k_b=Basic.N[i1]+"'"+key+"'";
						}else{
							k_b=Basic.N[i1]+key;
						}
					}
				}
			}
			if(!k_b.equals("")){              
				Coll.print("{"+k_b+"=>",value,"}");				
			}else{
				Coll.print("{",key,"");
				append("=>");			
				Coll.print("",value,"}");
			}		
		}
		printCollAfter();
		show();
	}

	public static <T> void print(Enumeration<T> en){
		String type=en.getClass().getTypeName();
		type=type.substring(type.lastIndexOf(".")+1);
		printCollBefore(type);
		int i=0;
		while(en.hasMoreElements()){
			T t=en.nextElement();
			if(t==null){
				Basic.print("["+i+"]=>",null,"");
			}else{
				for(int i1=0;i1<Basic.C.length;i1++){
					Class<?> c = Basic.C[i1];
					if(t.getClass().equals(c)){
						Basic.print("["+i+"]=>",t,"");	
					}
				}
				if(t.getClass().isArray()){
					Basic.append("["+i+"]=>");
					Basic.stack.push(1);
					Array.print(t);
					Basic.stack.pop();
				}			
				if(t instanceof Collection){
					Basic.append("["+i+"]=>");
					Basic.stack.push(1);
					Coll.print((Collection<?>)t);
					Basic.stack.pop();
				}
				if(t instanceof Map){
					Basic.append("["+i+"]=>");
					Basic.stack.push(1);
					Coll.print((Map<?,?>)t);
					Basic.stack.pop();
				}if(t instanceof Enumeration){
					Basic.append("["+i+"]=>");
					Basic.stack.push(1);
					Coll.print((Enumeration<?>)t);
					Basic.stack.pop();				
				}else{
					String cl=t.getClass().getTypeName();
					if(cl.indexOf("java.")!=0){
						Basic.print("["+i+"]=>",t,"");
					}
				}
				
			}			
			i++;
		}
		printCollAfter();
		show();
	}

	protected static<T> void print(String before,T t,String after){
		if(t==null){
			append(before+"null"+after);
			return;
		}
		boolean b=true;		
		for(int i=0;i<Basic.C.length;i++){
			Class<?> c = Basic.C[i];
			if(t.getClass().equals(c)){
				if(c.getName().equals("java.lang.String")){					
					int length=((String)t).length();
					String r=before+Basic.N[i]+"\""+t+"\" (length="+length+")"+after;
					append(r);
				}else if(c.getName().equals("java.lang.Character")){					
					String r=before+Basic.N[i]+"'"+t+"'"+after;
					append(r);
				}else{
					String r=before+Basic.N[i]+t+after;
					append(r);
				}	
				b=false;
			}
		}
		if(b){
			append(before);
			int l=before.length()/9;
			if(l==0)l=1;
			for(int i=0;i<l;i++){
				Basic.stack.push(1);
			}	
			if(t.getClass().isArray()){				
				Array.print(t);				
			}else if(t instanceof Collection){
				Coll.print((Collection<?>)t);
			}else if(t instanceof Map){
				Coll.print((Map<?,?>)t);
			}else if(t instanceof Enumeration){
				Coll.print((Enumeration<?>)t);
			}else{
				Basic.print(t);
			}
			for(int i=0;i<l;i++){
				Basic.stack.pop();
			}
			append(after);		
		}	
	}
	
	private static void printCollBefore(String type){
		append(type+"(");
		Basic.stack.push(1);
	}
	
	private static void printCollAfter(){
		Basic.stack.pop();
		append(")");
	}
	
}

/**
 * servlet扩展类,将注释部分打开可用
 * @author 老成
 */
public class Vardump extends Coll{
	
	public static void print(HttpServletRequest request){
		printBefore("request");
		print(request.getParameterMap());
		print(request.getCookies());
		print(request.getSession());
		printAfter();
		show();
	}	
	
	public static void print(Cookie[] cookies){
		printBefore("cookies");
		for(Cookie cookie:cookies) {
			print("{(string)\""+cookie.getName()+"\"(length="+cookie.getName().length()+")=>(string)"+cookie.getValue()+"(length="+cookie.getValue().length()+")}");
		}
		printAfter();
		show();
	}	
	
	public static void print(HttpSession session){
		printBefore("session");
		Enumeration<String> names = session.getAttributeNames();
		while(names.hasMoreElements()) {
			String next = names.nextElement();
			print("{(string)\""+next+"\"(length="+next.length()+")=>",session.getAttribute(next),"}");	
		}
		printAfter();
		show();
	}	
	
	private static void printBefore(String type){
		append(type+"(");
		Basic.stack.push(1);
	}
	
	private static void printAfter(){
		Basic.stack.pop();
		append(")");
	}

}