package com.lx.memcache.cache;

import java.util.Date;

import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;

/**
 * 	基于java_memcached-release_2.5.3.jar的memcached客户端
 * @author 	lixin
 * @date	2015-2-4 上午10:55:32
 */
public class MyCache {
	
	//建立MemcachedClient实例
	public static MemCachedClient client = null;
	
	static {
		if(client == null){
			client = new MemCachedClient();
		}
	}
	
	public static void main(String[] args) {
		
		//初始化SockIOPool，管理memcached的连接池
        String [] addr ={"127.0.0.1:11211"};  
        Integer [] weights = {3};
        SockIOPool pool = SockIOPool.getInstance();  
        pool.setServers(addr);  
        pool.setWeights(weights);  
        pool.setInitConn(5);  
        pool.setMinConn(5);  
        pool.setMaxConn(200);  
        pool.setMaxIdle(1000*30*30);  
        pool.setMaintSleep(30);
        pool.setNagle(false);  
        pool.setSocketTO(30);  
        pool.setSocketConnectTO(0);  
        pool.initialize();  
          
        //String[] s = pool.getServers();
        
        /* 
         * 下面两行代码只在java_memcached-release_2.5.3.jar版本中支持
        client.setCompressEnable(true);  
        client.setCompressThreshold(1000*1024);*/  
          
        //将数据放入缓存  
        client.set("test2","test2");  
          
        //将数据放入缓存,并设置失效时间 
        Date date=new Date(10000);
        client.set("test1","test1", date);  
          
        //删除缓存数据  
//      client.delete("test1");  
          
        //获取缓存数据  
        String str =(String)client.get("test1");  
        System.out.println(str);  
    }
}
