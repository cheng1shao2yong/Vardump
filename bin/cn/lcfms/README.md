## Vardump简介
Vardump是一个简单可以用于打印各种java数据结构的工具类，多维数组，List类型，Set类型，Map类型，对象类型，类型之间互相套用也没问题
> Vardump是我在lcfms框架中封装的一个数据结构打印库，支持独立使用。
[点击这里跳转到lcfms](https://gitee.com/lcfms/lcfms)
## 交流群：348455534
## 使用环境
jdk1.8+
## 使用方法
直接调用Vardump.print(各种数据结构)，你会神奇的发现所有数据结构一目了然
## 使用示例
```
package cn.lcfms.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
		Object[] objects={1,'2',"3",true,null};
    	String[][] strings={{"aaa","bbb","aaa","bbb"},{"aaa","bbb","aaa","bbb"}};
    	HashMap<Object, Object> map=new HashMap<Object, Object>();
    	map.put("aaa", 1);
    	map.put("bbb", 1.5f);
    	map.put("ccc", true);
    	map.put(5.5f, '5');
    	map.put(1, "12345");
    	map.put(true, objects);
    	map.put('g', strings);
    	List<Object> list=new ArrayList<Object>();
    	list.add("abc");
    	list.add('a');
    	list.add(true);
    	list.add(map);
    	list.add(null);
    	Bean bean=new Bean();
    	bean.setI(12345);
    	bean.setObj(new Obj());
    	bean.setStrs(new String[] {"11111","fffffffff","fdddddddd"});
    	list.add(bean);
    	Vardump.print(list);
	}
}
```
执行打印的结果为：
```
ArrayList(
    [0]=>(string)"abc" (length=3)
    [1]=>(char)a
    [2]=>(boolean)true
    [3]=>
        HashMap(
            {(string)"aaa" (length=3)=>(int)1}
            {(float)5.5=>(char)5}
            {(int)1=>(string)"12345" (length=5)}
            {(string)"ccc" (length=3)=>(boolean)true}
            {(string)"bbb" (length=3)=>(float)1.5}
            {(char)g=>
                String[](
                    String[](
                        [0]=>(string)"aaa" (length=3)
                        [1]=>(string)"bbb" (length=3)
                        [2]=>(string)"aaa" (length=3)
                        [3]=>(string)"bbb" (length=3)
                    )
                    String[](
                        [0]=>(string)"aaa" (length=3)
                        [1]=>(string)"bbb" (length=3)
                        [2]=>(string)"aaa" (length=3)
                        [3]=>(string)"bbb" (length=3)
                    )
                )
            }
            {(boolean)true=>
                Object[](
                    [0]=>(int)1
                    [1]=>(char)2
                    [2]=>(string)"3" (length=1)
                    [3]=>(boolean)true
                    [4]=>null
                )
            }
        )
    [4]=>null
    [5]=>
        cn.lcfms.utils.Bean(
            i=>(int)12345
            strs=>
                String[](
                    [0]=>(string)"11111" (length=5)
                    [1]=>(string)"fffffffff" (length=9)
                    [2]=>(string)"fdddddddd" (length=9)
                )
            obj=>
                cn.lcfms.utils.Obj(
                    mi=>(int)123
                    ms=>(string)"abc" (length=3)
                )
        )
)
```
同时支持同时打印http请求的request的cookies，session，parameter
```
//请求的url为http://localhost/?a=1&b=2&b=123
@RequestMapping("/index")
public String index(HttpServletRequest request) {
	Vardump.print(request);		
	return "index/index";
}
```
打印的效果如下
```
request(
    ParameterMap(
        {(string)"a" (length=1)=>
                String[](
                    [0]=>(string)"1" (length=1)
                )
        }
        {(string)"b" (length=1)=>
                String[](
                    [0]=>(string)"2" (length=1)
                    [1]=>(string)"123" (length=3)
                )
        }
    )
    cookies(
        (string)"{(string)"__guid"(length=6)=>(string)111872281.412025206174933600.1541334635043.79(length=45)}" (length=94)
        (string)"{(string)"JSESSIONID"(length=10)=>(string)EBD3C517709D3A58439311546492EEC9(length=32)}" (length=86)
        (string)"{(string)"monitor_count"(length=13)=>(string)63(length=2)}" (length=58)
    )
    session(
        {(string)"realName"(length=8)=>(string)"系统管理员" (length=5)}
        {(string)"img"(length=3)=>(int)0}
        {(string)"aname"(length=5)=>(string)"admin" (length=5)}
        {(string)"mobile"(length=6)=>(string)"13818135252" (length=11)}
        {(string)"dname"(length=5)=>(string)"行政部" (length=3)}
        {(string)"rid"(length=3)=>
                HashSet(
                    [0]=>(int)1
                )
        }
        {(string)"aid"(length=3)=>(int)23}
        {(string)"did"(length=3)=>(int)3}
        {(string)"email"(length=5)=>(string)"admin@lcfms.cn" (length=14)}
        {(string)"status"(length=6)=>(int)1}
    )
)
```
支持打印各种继承了Map与Collection接口的数据类型，比如Json
```
JSONObject object=JSONObject.fromObject("{\"employees\":[{\"firstName\":\"Bill\",\"lastName\":\"Gates\"},{\"firstName\":\"George\",\"lastName\":\"Bush\"},{\"firstName\":\"Thomas\",\"lastName\":\"Carter\"}]}");
Vardump.print(object);
```
打印结果如下
```
JSONObject(
    {(string)"employees" (length=9)=>
                JSONArray(
                    [0]=>
                        JSONObject(
                            {(string)"firstName" (length=9)=>(string)"Bill" (length=4)}
                            {(string)"lastName" (length=8)=>(string)"Gates" (length=5)}
                        )
                    [1]=>
                        JSONObject(
                            {(string)"firstName" (length=9)=>(string)"George" (length=6)}
                            {(string)"lastName" (length=8)=>(string)"Bush" (length=4)}
                        )
                    [2]=>
                        JSONObject(
                            {(string)"firstName" (length=9)=>(string)"Thomas" (length=6)}
                            {(string)"lastName" (length=8)=>(string)"Carter" (length=6)}
                        )
                )
    }
)
