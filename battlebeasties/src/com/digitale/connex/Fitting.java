package com.digitale.connex;

import java.util.ArrayList;
import java.util.List;

public class Fitting  {
	
	public static List<Integer> defList = new ArrayList <Integer>();
	public static List<Integer> offList = new ArrayList <Integer>();
	public static List<Integer> augList = new ArrayList <Integer>();
	  
	public Fitting(){
		for (int i=0;i<5;i++){
			defList.add(0);
			offList.add(0);
			augList.add(0);
		}
	}

	}