package com.digitale.connex;


public class Agent
{
	public String name;
	public boolean type;
	public String race;



	public Agent(String name, boolean type, String race)
		{
			this.name = name;
			this.type = type;
			this.race= race;

		}

	@Override
	public String toString()
		{
			return this.name;
		}
}