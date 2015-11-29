客户端

Memcached本身是使用C开发的，客户端可以是php、C#、或者java，这里只介绍基于java的客户端。
常用的java的memcache 客户端有很多，这里列出两中，其中第一种 Memcached-Java-Client（java_memcached-release_2.6.6.jar）最为常用。
1.java_memcached-release_2.5.3（常用，推荐）
	1)简介
		这是比较通用的Memcached客户端框架。具体原创不详。
	2)依赖的jar
		A.commons-pool-1.5.6.jar
		B.java_memcached-release_2.5.3.jar
		C.slf4j-api-1.6.1.jar
		D.slf4j-simple-1.6.1.jar

2.alisoft-xplatform-asf-cache-2.5.1
	1)简介
		这个东东是阿里软件的架构师岑文初进行封装的。里面的注释都是中文的，比较好。
	2)依赖的jar
		A.alisoft-xplatform-asf-cache-2.5.1.jar
		B.commons-logging-1.0.4.jar
		C.hessian-3.0.1.jar
		D.log4j-1.2.9.jar
		E.stax-api-1.0.1.jar
		F.wstx-asl-2.0.2.jar