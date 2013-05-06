package com.osgo.model;

public class Circle
{
	private int id;
	private String name;
	

	public int getId()
	{
		return id;
	} // end method getId


	public void setId(int id)
	{
		this.id = id;
	} // end method setId


	public String getName()
	{
		return name;
	} // end method getName
	

	public void setName(String name)
	{
		this.name = name;
	} // end method setName
	
	public Circle(int circleId, String name)
	{
		setId(circleId);
		setName(name);
	} // end 2-argument constructor


	public Circle()
	{
	} // end constructor
	
} // end Class Circle
