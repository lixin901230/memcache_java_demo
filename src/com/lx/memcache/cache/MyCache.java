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
	
	/**
	 * 测试
	 * 	1）为配置magent集群memcached服务时，使用memcachedServers服务地址列表进行测试
	 * 	2）配置magent集群后，则java memcached client 直接连magent服务地址即可
	 * @param args
	 */
	public static void main(String[] args) {
		
		try {
			//memcached服务地址列表，未使用magent配置集群时，可直接使用memcached服务地址进行测试（使用同一ip不同端口模拟多台memcached服务）
			String [] memcachedServers ={"192.168.0.125:11211", "192.168.0.125:11212", "192.168.0.125:11213"};  
			
			//使用magent集群后magent的服务地址（使用同一ip不同端口模拟多台magent服务）
			String [] magentServers ={"192.168.0.125:12000", "192.168.0.125:13000"};
			
			/* 初始化SockIOPool，管理memcached的连接池。这个方法有一个重载方法getInstance( String poolName )，每个poolName只构造一个SockIOPool实例。缺省构造的poolName是default。
				注意：如果在客户端配置多个memcached服务，一定要显式声明poolName*/
			SockIOPool pool = SockIOPool.getInstance();
			
			//未集群测试，直接使用memcached服务地址列表连接
			//pool.setServers(memcachedServers);	//设置连接池可用的cache服务列表，server的构成形式是IP:PORT（如：192.168.0.125:11211）
			//magent集群测试，使用magent服务地址列表连接
			pool.setServers(magentServers);		//设置连接池可用的cache服务列表，server的构成形式是IP:PORT（如：192.168.0.125:11211）
			pool.setSocketTO(3000);				//设置socket的读取等待超时值
			pool.setNagle(false);				//设置是否使用Nagle算法，因为我们的通讯数据量通常都比较大（相对TCP控制数据）而且要求响应及时，因此该值需要设置为false（默认是true）
			pool.setSocketConnectTO(2000);		//设置socket的连接等待超时值
			
			pool.setWeights(new Integer[]{6});	//设置连接池可用cache服务器的权重，和server数组的位置一一对应
			pool.setInitConn(6);				//设置开始时每个cache服务器的可用连接数
			pool.setMinConn(6);  				//设置每个服务器最少可用连接数
			pool.setMaxConn(200);  				//设置每个服务器最大可用连接数
			pool.setMaxIdle(1000*30*30);		//设置可用连接池的最长等待时间
			pool.setMaintSleep(30);				//设置连接池维护线程的睡眠时间，设置为0，维护线程不启动，维护线程主要通过log输出socket的运行状况，监测连接数目及空闲等待时间等参数以控制连接创建和关闭			
			pool.setAliveCheck(true);			//设置连接心跳监测开关，默认状态是false。设为true则每次通信都要进行连接是否有效的监测，造成通信次数倍增，加大网络负载，因此该参数应该在对HA（高可用）要求比较高的场合设为TRUE。
			pool.setFailback(true);				//设置连接失败恢复开关，设置为TRUE，当宕机的服务器启动或中断的网络连接后，这个socket连接还可继续使用，否则将不再使用，默认状态是true，建议保持默认。
			pool.setFailover(true);				//失效转移（故障转移），设置容错开关，设置为TRUE，当当前socket不可用时，程序会自动查找可用连接并返回，否则返回NULL，默认状态是true，建议保持默认。
			/*设置hash算法：
			    alg=0 使用String.hashCode()获得hash code,该方法依赖JDK，可能和其他客户端不兼容，建议不使用；
			    alg=1 使用original 兼容hash算法，兼容其他客户端；
			    alg=2 使用CRC32兼容hash算法，兼容其他客户端，性能优于original算法；
			    alg=3 使用MD5 hash算法；
			采用前三种hash算法的时候，查找cache服务器使用余数方法。采用最后一种hash算法查找cache服务时使用consistent方法*/
			pool.setHashingAlg(SockIOPool.CONSISTENT_HASH);
			pool.initialize();					//设置完pool参数后最后调用该方法，启动pool  
			
			/* 
			 * 下面两行代码只在java_memcached-release_2.5.3.jar版本中支持
			client.setCompressEnable(true);  
			client.setCompressThreshold(1000*1024);*/  
			
			
			//client.set("test1","test1");		//将数据放入缓存  
			
			//client.set("test2","test2", new Date(10000));	//将数据放入缓存,并设置失效时间 
			
			//client.delete("test1");			//删除缓存数据
			
			/*String str =(String)client.get("test1");	//获取缓存数据
			System.out.println(str);*/
			
			//设置缓存
//			client.set("key1","test1");
//			client.set("key3","test3");
			
			String key1 =(String)client.get("key1");	//获取缓存数据
			System.out.println("key1："+key1);
			
			String key3 =(String)client.get("key3");
			System.out.println("key3："+key3);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
