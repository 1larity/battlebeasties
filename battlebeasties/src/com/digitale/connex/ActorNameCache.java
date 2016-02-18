package com.digitale.connex;

import com.badlogic.gdx.graphics.g2d.BitmapFontCache;

public class ActorNameCache  {
	  
	public  BitmapFontCache cache;
	   private String name;
	   
	
	   
	   public ActorNameCache(  ) {
	   }
	   
	   public ActorNameCache(BitmapFontCache cache, 
	              String  name)
	              {
	      setCache(cache);
	      setName(name);
	   }

	/**
	 * @return the cache
	 */
	public BitmapFontCache getCache() {
		return cache;
	}

	/**
	 * @param cache the cache to set
	 */
	public void setCache(BitmapFontCache cache) {
		this.cache = cache;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}


	}