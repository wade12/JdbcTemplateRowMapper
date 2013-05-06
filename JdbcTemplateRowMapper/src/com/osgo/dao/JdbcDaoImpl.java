package com.osgo.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.osgo.model.Circle;

// this class is responsible for talking to the database and getting the data
@Component
public class JdbcDaoImpl
{
	// @Autowired
	private DataSource dataSource;
	// private JdbcTemplate jdbcTemplate = new JdbcTemplate();
	private JdbcTemplate jdbcTemplate;
	
	
	public DataSource getDataSource()
	{
		return dataSource;
	} // end method getDataSource
	

	@Autowired
	public void setDataSource(DataSource dataSource)
	{
		// this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	} // end method setDataSource
	

	public JdbcTemplate getJdbcTemplate()
	{
		return jdbcTemplate;
	} // end method getJdbcTemplate


	public void setJdbcTemplate(JdbcTemplate jdbcTemplate)
	{
		this.jdbcTemplate = jdbcTemplate;
	} // end method setJdbcTemplate
	
	
	public Circle getCircle(int circleId)
	{
		// 1st thing needed is a connection object
		Connection conn = null;
		
		try
		{
		// create a connection
		conn = dataSource.getConnection();
			
		// create a prepared statement, then set id value
		// pass SQL query in PreparedStatement
		PreparedStatement ps = conn.prepareStatement("SELECT * FROM circle where id = ?");
		// set the id
		ps.setInt(1, circleId);
			
		// initialise a circle object to assign the result to the circle object
		Circle circle = null;
		ResultSet rs = ps.executeQuery();
			
		// if there is a record then assign that to a new circle
		if (rs.next())
		{
			circle = new Circle(circleId, rs.getString("name"));
		} // end if
			
		// close the RecordSet
		rs.close();
			
		// close the PreparedStatement
		ps.close();
			
		return circle;
		} // end try
		
		catch (Exception exception)
		{
			throw new RuntimeException(exception);
		} // end catch
		
		finally
		{
			try
			{
				// close the connection
				conn.close();
			}
			catch (SQLException exception)
			{
			} // end catch
		} //  end finally block
					
	} // end method getCircle
	
	
	// this method gets the total number of records in the circle table
	public int getCircleCount()
	{
		String sql = "SELECT COUNT(*) FROM circle";
		// jdbcTemplate.setDataSource(getDataSource());
		return jdbcTemplate.queryForInt(sql);
	} // end method getCircleCount
	
	
	public String getCircleName(int circleId)
	{
		String sql = "SELECT NAME FROM circle WHERE ID = ?";
		return jdbcTemplate.queryForObject(sql, new Object[] {circleId}, String.class);
		// Object[] is a 1-element array
		} // end method getCircleName
	
	
	// method returns the object itself directly
	public Circle getCircleForId(int circleId)
	{
		String sql = "SELECT * FROM circle WHERE ID = ?";
		return jdbcTemplate.queryForObject(sql, new Object[] {circleId}, new CircleMapper());
	} // end method getCircleForId
	
	
	// method returns a list of own model objects
	public List<Circle> getAllCircles()
	{
		String sql = "SELECT * FROM circle";
		return jdbcTemplate.query(sql, new CircleMapper());
	} // end method getAllCircles
	
	
	// inner class
	// create own implementation of the RowMapper
	private static final class CircleMapper implements RowMapper<Circle>
	{
		@Override
		// this method gets called for every record in the result set
		// that the jdbcTemplate gets when it executes the sql query that was passed.
		public Circle mapRow(ResultSet resultSet, int rowNum) throws SQLException
		{
			Circle circle = new Circle();
			circle.setId(resultSet.getInt("ID"));
			circle.setName(resultSet.getString("NAME"));
			return circle;
		} // end method mapRow
		
	} // end inner Class CircleMapper
	

} // end Class jdbcDaoImpl
