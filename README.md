<p align="center">
  <img src="https://raw.github.com/tr8n/tr8n/master/doc/screenshots/tr8nlogo.png">
</p>

Tr8n Redis Adapter
==================

This cache adapter allows you to cache Tr8n data in Redis server. 


Installation
==================

Add the following dependency to your pom.xml:

```xml
<dependency>
  <groupId>tr8n</groupId>
  <artifactId>com.tr8n.cache.redis</artifactId>
  <version>0.1.0</version>
</dependency>
```


Configuration
==================

To initialize and use this cache adapter use the following settings:

```java

Tr8n.getConfig().setCache(Utils.buildMap(
  "class",  "com.tr8n.cache.Redis",
  "host",   "localhost:6379",
  "version",  1,
  "timeout",  3600
));

```

