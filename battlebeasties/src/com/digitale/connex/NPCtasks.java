package com.digitale.connex;
import java.sql.*;
import java.util.Random;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
public class NPCtasks {
	
	 public static void totallyRandom(int limit){
	 System.out.println(
	 "start generate random land");
	 try {
		 Statement stmt;
		 ResultSet rs;

		 //Register the JDBC driver for MySQL.
		 Class.forName("com.mysql.jdbc.Driver");

		 //Define URL of database server 
		 String url = "jdbc:mysql://localhost:3306/test";

		 //Get a connection to the database 
		 Connection con = DriverManager.getConnection( url,"root", "universal40");

		 //Display URL and connection information
		 System.out.println("URL: " + url);
		 System.out.println("Connection: " + con);

		 //Get a Statement object
		 stmt = con.createStatement();
	
		 int x = 1;
		 int z = 1;
		 String groundtype="";
		 Random rng=new Random();
		 int RandomNumber = rng.nextInt(7);
		 
		 System.out.println("inserting groundtypes" +
		 		" with " + limit + "x" + limit + "dimensions");
		
				 //Insert sand into limit sized grid
				 
				 for(int xread=0;xread<limit-1;xread++){
					  for(int yread=0;yread<limit-1;yread++){
						  
					    
					    RandomNumber = rng.nextInt(7);
						 groundtype=Main.Land[RandomNumber].get_ground();
						 System.out.printf("x " + xread +" y "+ yread + groundtype);
					    stmt.executeUpdate("UPDATE landscape SET ground ='" + groundtype+ "' where x=" + (xread+1) + " and y=" + (yread+1));
					  }
					  System.out.println();
					}
	 con.close();
	 }catch( Exception e ) {
	 e.printStackTrace();
	 }//end catch
	 }//end method totallyRandom

	 
	 
	 
	 // generate heightmap using Diamond-square fractal algorithm
	public static void heightMap(final int DATA_SIZE){
		System.out.println(
		 "start generate Diamond-square land");
		//DATA_SIZE = size of grid to generate, note this must be a
		//value 2^n+1
		
		//an initial seed value for the corners of the data
		final double SEED = 1000.0;
		double[][] data = new double[DATA_SIZE][DATA_SIZE];
		//seed the data
		data[0][0] = data[0][DATA_SIZE-1] = data[DATA_SIZE-1][0] = 
		  data[DATA_SIZE-1][DATA_SIZE-1] = SEED;

		double h = 500.0;//the range (-h -> +h) for the average offset
		Random r = new Random();//for the new value in range of h
		//side length is distance of a single square side
		//or distance of diagonal in diamond
		for(int sideLength = DATA_SIZE-1;
		    //side length must be >= 2 so we always have
		    //a new value (if its 1 we overwrite existing values
		    //on the last iteration)
		    sideLength >= 2;
		    //each iteration we are looking at smaller squares
		    //diamonds, and we decrease the variation of the offset
		    sideLength /=2, h/= 2.0){
		  //half the length of the side of a square
		  //or distance from diamond center to one corner
		  //(just to make calcs below a little clearer)
		  int halfSide = sideLength/2;

		  //generate the new square values
		  for(int x=0;x<DATA_SIZE-1;x+=sideLength){
		    for(int y=0;y<DATA_SIZE-1;y+=sideLength){
		      //x, y is upper left corner of square
		      //calculate average of existing corners
		      double avg = data[x][y] + //top left
		      data[x+sideLength][y] +//top right
		      data[x][y+sideLength] + //lower left
		      data[x+sideLength][y+sideLength];//lower right
		      avg /= 4.0;

		      //center is average plus random offset
		      data[x+halfSide][y+halfSide] = 
		    //We calculate random value in range of 2h
		    //and then subtract h so the end value is
		    //in the range (-h, +h)
		    avg + (r.nextDouble()*2*h) - h;
		    }
		  }

		  //generate the diamond values
		  //since the diamonds are staggered we only move x
		  //by half side
		  //NOTE: if the data shouldn't wrap then x < DATA_SIZE
		  //to generate the far edge values
		  for(int x=0;x<DATA_SIZE-1;x+=halfSide){
		    //and y is x offset by half a side, but moved by
		    //the full side length
		    //NOTE: if the data shouldn't wrap then y < DATA_SIZE
		    //to generate the far edge values
		    for(int y=(x+halfSide)%sideLength;y<DATA_SIZE-1;y+=sideLength){
		      //x, y is center of diamond
		      //note we must use mod  and add DATA_SIZE for subtraction 
		      //so that we can wrap around the array to find the corners
		    	double avg = 
		            data[(x-halfSide+DATA_SIZE-1)%(DATA_SIZE-1)][y] + //left of center
		            data[(x+halfSide)%(DATA_SIZE-1)][y] + //right of center
		            data[x][(y+halfSide)%(DATA_SIZE-1)] + //below center
		            data[x][(y-halfSide+DATA_SIZE-1)%(DATA_SIZE-1)]; //above center
		      avg /= 4.0;

		      //new value = average plus random offset
		      //We calculate random value in range of 2h
		      //and then subtract h so the end value is
		      //in the range (-h, +h)
		      avg = avg + (r.nextDouble()*2*h) - h;
		      //update value for center of diamond
		      data[x][y] = (avg/5);

		      //wrap values on the edges, remove
		      //this and adjust loop condition above
		      //for non-wrapping values.
		      if(x == 0)  data[DATA_SIZE-1][y] = avg;
		      if(y == 0)  data[x][DATA_SIZE-1] = avg;
		    }
		  }
		}
		// write generated z to database
		try {
			 Statement stmt;
			 //Register the JDBC driver for MySQL.
			 Class.forName("com.mysql.jdbc.Driver");

			 //Define URL of database server 
			 String url = "jdbc:mysql://localhost:3306/test";

			 //Get a connection to the database 
			 Connection con = DriverManager.getConnection( url,"root", "universal40");

			 //Display URL and connection information
			 System.out.println("URL: " + url);
			 System.out.println("Connection: " + con);

			 //Get a Statement object
			 stmt = con.createStatement();
			 System.out.println("populating altitude");
		//print out the data
		for(int xread=0;xread<DATA_SIZE-1;xread++){
		  for(int yread=0;yread<DATA_SIZE-1;yread++){
			  
			  
		    System.out.printf("x " + xread +" y "+ yread + "%8.3f ", data[xread][yread]);
		    stmt.executeUpdate("UPDATE landscape SET z =" + data[xread][yread] + " where x=" + (xread+1) + " and y=" + (yread+1));
		  }
		  System.out.println();
		}
		con.close();
		 }catch( Exception e ) {
		 e.printStackTrace();
		 }//end catch
	
	
	
	System.out.println(
	 "generate Diamond-square land");
	}
	 
	
	
	
	//start method Fillblank
	//populates landscape table with empty ground tiles
	 
	public static void Fillblank(int limit) {
		System.out.println(
		 "start Fillblank");
		
		 try {
			 Statement stmt;
			 //Register the JDBC driver for MySQL.
			 Class.forName("com.mysql.jdbc.Driver");

			 //Define URL of database server 
			 String url = "jdbc:mysql://localhost:3306/test";

			 //Get a connection to the database 
			 Connection con = DriverManager.getConnection( url,"root", "universal40");

			 //Display URL and connection information
			 System.out.println("URL: " + url);
			 System.out.println("Connection: " + con);

			 //Get a Statement object
			 stmt = con.createStatement();
			 int x = 1;
			 int z = 1;
			 System.out.println("creating land with " + limit + "x" + limit + "dimensions");
			 do{
				 int y = 1;
				 do{
					 //Insert sand into limit sized grid
					 stmt.executeUpdate("INSERT INTO landscape(ground, x, y, z) VALUES('sand'," + x + "," + y + "," + z + ")");
					 y++;
				 }while (y<limit);
				 x++;
			 }while (x<limit);
						 
			 con.close();
			 }catch( Exception e ) {
			 e.printStackTrace();
			 }//end catch
		
		
		
		System.out.println(
		 "end Fillblank");
		
	}
	 public static void walknpcs (){
		 System.out.println(
		 "updating NPC locations");
		 try {
			 Statement stmt,updatestmt;


			 //Register the JDBC driver for MySQL.
			 Class.forName("com.mysql.jdbc.Driver");

			 //Define URL of database server 
			 String url = "jdbc:mysql://127.0.0.1:3306/test";

			 //Get a connection to the database 
			 Connection con = DriverManager.getConnection( url,"root", "universal40");

			 //Display URL and connection information
			 System.out.println("URL: " + url);
			 System.out.println("Connection: " + con);
			 String query;
			 //Get a Statement object
			 updatestmt = con.createStatement();
			 stmt = con.createStatement();
			 ResultSet rs = stmt.executeQuery("select UID, x,y  from npc");
		        while (rs.next()) {
		        	int mUID = Integer.valueOf(rs.getString("UID"));
		        	int mx = Integer.valueOf(rs.getString("x"));
		        	int my = Integer.valueOf(rs.getString("y"));
		        	  System.out.println("UID"+mUID+" x "+mx+ " y "+my);
		        	  double r = Math.random();
		              if      (r < 0.25 && mx>1)mx--;
		              else if (r < 0.50 && mx <63) mx++;
		              else if (r < 0.75 && my >1) my--;
		              else if (r < 1.00 &&my<63) my++;
		              query="UPDATE  npc set  x="+mx+", y="+my +", z = (SELECT z from landscape where x="+mx+" and y="+my+") WHERE UID="+mUID;
		              System.out.println(query);
		            //  update npc set npc.x= mx, npc.y= my,npc.z = (SELECT z from landscape where x=1 and y=2) where UID=176
		              int affected=updatestmt.executeUpdate(query );
		             
		        }
		        
			
		 con.close();
		 }catch(MySQLIntegrityConstraintViolationException e){
			 System.out.println("fail");
		 }catch( Exception e ) {
		 e.printStackTrace();
		 }//end catch
		 }//end method totallyRandom

		 
		 
	}//end class RandomLand