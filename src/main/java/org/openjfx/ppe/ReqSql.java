package org.openjfx.ppe;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReqSql {

	/*
     * 
     * 	Add new enter into database
     *
     */
    
    private static void insertEntree(String lastname, String firstname, String salle, String codebarres, String date) {
    	Connection con = SqlConnection.connect();
    	PreparedStatement ps = null;
    	try {
    		String sql = "INSERT INTO enter(lastname, firstname, salle, codebarres, date) VALUES(?,?,?,?,?)";
    		ps = con.prepareStatement(sql);
    		ps.setString(1, lastname);
    		ps.setString(2, firstname);
    		ps.setString(3, salle);
    		ps.setString(4, codebarres);
    		ps.setString(5, date);
    		ps.execute();
    		System.out.println("Data has been inserted !");
    	} catch(SQLException e) {
    		System.out.println(e.toString());
    	}
    	
    }
    
    public static void setInsertEntree(String lastname, String firstname, String salle, String codebarres, String date) {
    	insertEntree(lastname, firstname, salle, codebarres, date);
    }
    
    /*
     * 
     * 	Add new equipment into database
     *
     */
    
    private static void insertEquipement(String name, String salle) {
    	Connection con = SqlConnection.connect();
    	PreparedStatement ps = null;
    	try {
    		String sql = "INSERT INTO equipement(name, salle) VALUES(?,?)";
    		ps = con.prepareStatement(sql);
    		ps.setString(1, name);
    		ps.setString(3, salle);
    		ps.execute();
    		System.out.println("Data has been inserted !");
    	} catch(SQLException e) {
    		System.out.println(e.toString());
    	}
    	
    }
    
    public static void setInsertEquipement(String name, String salle) {
    	insertEquipement(name, salle);
    }
    
    /*
     * 
     * 	Add cost in database
     *
     */
    
    private static void insertCost(String timeSpend, String cost, String codebarres) {
    	Connection con = SqlConnection.connect();
    	PreparedStatement ps = null;
    	try {
    		String sql = "INSERT INTO equipement(name, salle) VALUES(?,?)";
    		ps = con.prepareStatement(sql);
    		ps.setString(1, timeSpend);
    		ps.setString(3, cost);
    		ps.setString(3, codebarres);
    		ps.execute();
    		System.out.println("Data has been inserted !");
    	} catch(SQLException e) {
    		System.out.println(e.toString());
    	}
    	
    }
    
    public static void setInsertCost(String timeSpend, String cost, String codebarres) {
    	insertCost(timeSpend, cost, codebarres);
    }
    
    
    /*
     * 
     * 	change value in database
     *
     */
    
    private static void changeValue(String occupation, String nameEquipement) {
    	Connection con = SqlConnection.connect();
    	PreparedStatement ps = null;
    	try {
    		String sql = "UPDATE equipement SET occupation = ? WHERE name = ?";
    		ps = con.prepareStatement(sql);
    		ps.setString(1, occupation);
    		ps.setString(2, nameEquipement);
    		ps.execute();
    		System.out.println("Data has been inserted !");
    	} catch(SQLException e) {
    		System.out.println(e.toString());
    	}
    }
	
    /*
     * 
     * 	read specific row on the database 
     *
     */
    
    private static Boolean readSpecificRow(String codebarres) {
    	Connection con = SqlConnection.connect();
    	PreparedStatement ps = null;
    	ResultSet rs = null;
    	
    	try {
    		String sql = "SELECT codebarres FROM enter WHERE codebarres = ?";
    		ps = con.prepareStatement(sql);
    		ps.setString(1, codebarres);
    		rs = ps.executeQuery();
    		
    		if(rs.getString(1) != null) {    			
    			return true;
    		}
    		
    	} catch(SQLException e) {
    		System.out.println(e.toString());
    	} finally {
    		try {
    			rs.close();
    			ps.close();
    			con.close();
    		} catch(SQLException e) {
    			System.out.println(e.toString());
    		}
    	}
		return false;
		
    }
    
    public static Boolean SetReadSpecificRow(String codebarres) {
    	return readSpecificRow(codebarres);
    }
    
    /*
     * 
     * 	Row count
     *
     */
    
    private static int RowCount(String sqlreq){
    	
    	Connection con = SqlConnection.connect();
    	PreparedStatement ps = null;
    	ResultSet rs = null;
    	int count = 0;
    	try {
    		String sql = sqlreq;
    		ps = con.prepareStatement(sql);
    		rs = ps.executeQuery();
    		
    		count = rs.getInt("rowcount");
    		System.out.println("MyTable has " + count + " row(s).");
    		return count;
    		
    	} catch(SQLException e) {
    		System.out.println(e.toString());
    	} finally {
    		try {
    			rs.close();
    			ps.close();
    			con.close();
    		} catch(SQLException e) {
    			System.out.println(e.toString());
    		}
    	}
		return count;
    	
    }
    
    public static int setRowCount(String sqlreq) {
    	return RowCount(sqlreq);
    }
    
}
