### Vardump简介
Vardump是一个简单的用于打印数据结构的java库，多维数组，List类型，Set类型，Map类型，对象类型，类型之间互相套用也没问题，交流QQ群： 348455534

### 使用方法
直接调用Vardump.print(各种数据结构)，你会神奇的发现所有数据结构一目了然

### 案例展示
```java
package cn.lcfms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Test {
	public static void main(String[] args){
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
    	map.put(null, "1111"); 	
    	List<Object> list=new ArrayList<Object>();
    	list.add("abc");
    	list.add('a');
    	list.add(true);
    	list.add(map);
    	list.add(null);
    	Vardump.print(list);
	}
}

```
打印结果：
```php
ArrayList(
    [0]=>(string)"abc" (length=3)
    [1]=>(char)a
    [2]=>(boolean)true
    [3]=>
        HashMap(
            {(string)"aaa" (length=3)=>(int)1}
            {(float)5.5=>(char)5}
            {null=>(string)"1111" (length=4)}
            {(int)1=>(string)"12345" (length=5)}
            {(string)"ccc" (length=3)=>(boolean)true}
            {(string)"bbb" (length=3)=>(float)1.5}
            {(char)g=>
                    String[0](
                        String[0](
                            [0]=>(string)"aaa" (length=3)
                            [1]=>(string)"bbb" (length=3)
                            [2]=>(string)"aaa" (length=3)
                            [3]=>(string)"bbb" (length=3)
                        )
                        String[1](
                            [0]=>(string)"aaa" (length=3)
                            [1]=>(string)"bbb" (length=3)
                            [2]=>(string)"aaa" (length=3)
                            [3]=>(string)"bbb" (length=3)
                        )
                    )
            }
            {(boolean)true=>
                            Object[0](
                                [0]=>(int)1
                                [1]=>(char)2
                                [2]=>(string)"3" (length=1)
                                [3]=>(boolean)true
                                [4]=>null
                            )
            }
        )
    [4]=>null
)

```


