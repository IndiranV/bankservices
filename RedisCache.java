package org.in.com.redis;

import redis.clients.jedis.Jedis;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class RedisCache {
	public static void main(String[] args) {
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream("Cache.properties"));
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		String host = properties.getProperty("redis.host");
		int port = Integer.parseInt(properties.getProperty("redis.port"));
		String password = properties.getProperty("redis.password");

		Jedis jedis = new Jedis(host, port);
		jedis.auth(password);
		System.out.println("Connection successful!");

		jedis.close();
	}
}