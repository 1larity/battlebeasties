/*
 * Copyright 2012 Richard Beech (digitale001@gmail.com)
 * Avatardef Class, stores Avatar graphic information, mainly used in char creator
 */
package com.digitale.connex;



public class AssetDef
{
	
	private int uid;
	private String assetName;
	private String ext;
	private String type;
	//private blob data;

	
	
	public AssetDef(
			int uid,
			String assetName,
			String imagename,
			String ext,
			String type
			) {
		this.setUid(uid);
		this.setAssetName(assetName);
		this.setExt(ext);
		this.setType(type);
		
		
	
	}
	


	public AssetDef() {
		// TODO Auto-generated constructor stub
	}



	public int getUid() {
		return uid;
	}



	public void setUid(int uid) {
		this.uid = uid;
	}



	public String getAssetName() {
		return assetName;
	}



	public void setAssetName(String assetName) {
		this.assetName = assetName;
	}



	public String getExt() {
		return ext;
	}



	public void setExt(String ext) {
		this.ext = ext;
	}



	public String getType() {
		return type;
	}



	public void setType(String type) {
		this.type = type;
	}



}