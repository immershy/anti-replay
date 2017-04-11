# Spring Anti-Replay

## 1. 防重放

---

[1.1 生成待签名字符串](#1-1)

[1.2 MD5签名](#1-2)

[1.3 请求时签名](#1-3)

### 1.1 生成待签名字符串

---

   
- 假设当前请求的接口地址为： http://www.aa.com/path/to/resource
   
-   对于如下的参数数组：

```
	String[] parameters={
	    		"bparam=aaa",
		    	"cparam=bbb",
			    "aparam=ccc"
			};
```


-  对数组中的每一个key值按字母顺序排序，排序完之后，把所有数组拼接起来，在加上当前请求的URI(/path/to/resource部分)，作为待加密字符串，即：

```
	String paramToEncode = "aparamcccbparamaaacparambbb/path/to/resource";
```
	
- 这串字符串便是待签名字符串

- 参数数组中包含用于随机化的参数，一般为time时间戳和nonce的组合。


### 1.2 MD5签名

---
	
- 在MD5签名时，需要私钥参与签名。当前约定私钥：

```
	r8rw4d1kjwqgqqto9dwsq3ew0ip2np1b
```

### 1.3 请求时签名
	
	
- 当拿到请求时的待签名字符串后，需要把私钥直接拼接到待签名字符串后面，形成新的字符串;

- 利用MD5的签名函数对这个新的字符串进行签名运算，从而得到32位签名结果字符串，该字符串赋值于参数sign。









## 2. Enable in Spring 

```
	@EnableAntiReplay
```

## 3. RequestStore

```
  Default implementation: InMemoryRequestStore, RedisRequestStore
```

## 4. ErrorHandler

```
  Default implementation: ServletErrorHandler
```

## 5. Customize

```
	Override @Bean requestStore, errorHandler
```

## 6. Externalize config file

```
	Recognized property source: command line >> environment var >> application.property outside jar >> default loca app.propery
	
	Config variables:    arp.key.private   arp.key.inputcharset    arp.request.activetime 
```