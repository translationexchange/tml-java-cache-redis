/*
 * Copyright (c) 2018 Translation Exchange, Inc. All rights reserved.
 *
 *  _______                  _       _   _             ______          _
 * |__   __|                | |     | | (_)           |  ____|        | |
 *    | |_ __ __ _ _ __  ___| | __ _| |_ _  ___  _ __ | |__  __  _____| |__   __ _ _ __   __ _  ___
 *    | | '__/ _` | '_ \/ __| |/ _` | __| |/ _ \| '_ \|  __| \ \/ / __| '_ \ / _` | '_ \ / _` |/ _ \
 *    | | | | (_| | | | \__ \ | (_| | |_| | (_) | | | | |____ >  < (__| | | | (_| | | | | (_| |  __/
 *    |_|_|  \__,_|_| |_|___/_|\__,_|\__|_|\___/|_| |_|______/_/\_\___|_| |_|\__,_|_| |_|\__, |\___|
 *                                                                                        __/ |
 *                                                                                       |___/
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * @author Michael Berkovich
 */

package com.translationexchange.cache;

import java.util.Map;

import redis.clients.jedis.Jedis;

import com.translationexchange.core.Tml;
import com.translationexchange.core.cache.CacheAdapter;

public class Redis extends CacheAdapter {
	private Jedis jedis;

	/**
	 * Redis constructor
	 *  
	 * @param config Redis configuration
	 */
	public Redis(Map<String, Object> config) {
		super(config);
	}

	/**
	 * Returns Jedis
	 * 
	 * @return Jedis instance
	 */
	private Jedis getJedis() {
		if (jedis == null) {
			jedis = new Jedis((String)getConfig().get("host"));
		}
		
		return jedis;
	}
		
	/**
	 * Fetches data from Redis
	 */
	public Object fetch(String key, Map<String, Object> options) {
		try {
			Object data = getJedis().get(getVersionedKey(key, options)); 
			debug("cache " + (data == null ? "miss" : "hit") + " " + key);
			return data;
		} catch (Exception ex) {
			Tml.getLogger().logException("Failed to get a value from Redis", ex);
			return null;
		}
	}

	/**
	 * Stores data in Redis
	 */
	public void store(String key, Object data, Map<String, Object> options) {
		try {
			getJedis().set(getVersionedKey(key, options), data.toString());
		} catch (Exception ex) {
			Tml.getLogger().logException("Failed to store a value in Redis", ex);
		}
	}

	/**
	 * Deletes data from Redis
	 */
	public void delete(String key, Map<String, Object> options) {
		try {
			getJedis().set(getVersionedKey(key, options), null);
		} catch (Exception ex) {
			Tml.getLogger().logException("Failed to delete a value from Redis", ex);
		}
	}
	
}
