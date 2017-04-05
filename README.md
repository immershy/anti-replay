# Spring Anti-Replay Facility

## 1. Anti-Replay algorithm

---
	MD5(RequestMap.sortedString()+ requestUri)
	private_key: r8rw4d1kjwqgqqto9dwsq3ew0ip2np1b
---

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