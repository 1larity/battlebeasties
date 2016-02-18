package com.digitale.connex;

import java.sql.Timestamp;



public class Channeldef
{
	private int uid;
	private String channelname;
	private int channeltype;

	
	public Channeldef(
			int uid,
			String channelname, 
			int channeltype,
	Timestamp timestamp) {
		this.setUid(uid);
		this.setChannelname(channelname);
		this.setChanneltype(channeltype);
		
			}
	


	public Channeldef() {
		// TODO Auto-generated constructor stub
	}



	public int getUid() {
		return uid;
	}



	public void setUid(int uid) {
		this.uid = uid;
	}



	public String getChannelname() {
		return channelname;
	}



	public void setChannelname(String channelname) {
		this.channelname = channelname;
	}



	public int getChanneltype() {
		return channeltype;
	}



	public void setChanneltype(int channeltype) {
		this.channeltype = channeltype;
	}



	
}