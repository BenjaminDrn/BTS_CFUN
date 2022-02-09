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
    
    private static void insertEntree(String lastname, String firstname, String salle, String codebarres, String dateentree, int numentree) {
    	Connection con = SqlConnection.connect();
    	PreparedStatement ps = null;
    	try {
    		String sql = "INSERT INTO users(lastname, firstname, salle, codebarres, dateentree, numentree) VALUES(?,?,?,?,?,?)";
    		ps = con.prepareStatement(sql);
    		ps.setString(1, lastname);
    		ps.setString(2, firstname);
    		ps.setString(3, salle);
    		ps.setString(4, codebarres);
    		ps.setString(5, dateentree);
    		ps.setInt(6, numentree);
    		ps.execute();
    		System.out.println("Data has been inserted !");
    	} catch(SQLException e) {
    		System.out.println(e.toString());
    	}
    	
    }
    
    public static void setInsertEntree(String lastname, String firstname, String salle, String codebarres, String dateentree, int numentree) {
    	insertEntree(lastname, firstname, salle, codebarres, dateentree, numentree);
    }
    
    /*
     * 
     * 	Set cost and date exit into database
     *
     */
    
    private static void updateDataUser(String datetime, double cout, String codeBarres) {
    	Connection con = SqlConnection.connect();
    	PreparedStatement ps = null;
    	try {
    		String sql = "UPDATE users SET datesortie = ?, cost = ? WHERE codebarres =" + codeBarres;
    		ps = con.prepareStatement(sql);
    		ps.setString(1, datetime);
    		ps.setDouble(2, cout);
    		ps.execute();
    		System.out.println("Data has been inserted !");
    	} catch(SQLException e) {
    		System.out.println(e.toString());
    	}
    	
    }
    
    public static void setUpdateDataUser(String datetime, double cout, String codeBarres) {
    	updateDataUser(datetime, cout, codeBarres);
    }
    
    
    /*
     * 
     * 	Add new equipment into database
     *
     */
    
    private static void insertEquipement(String name, String salle ) {
    	Connection con = SqlConnection.connect();
    	PreparedStatement ps = null;
    	try {
    		String sql = "INSERT INTO equipement(name, salle) VALUES(?,?)";
    		ps = con.prepareStatement(sql);
    		ps.setString(1, name);
    		ps.setString(2, salle);
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
    
    /*
     * 
     * 
     * 
     */
    
    private static int sendDataSpecificRow(String codebarres) {
    	Connection con = SqlConnection.connect();
    	PreparedStatement ps = null;
    	ResultSet rs = null;
    	
    	try {
    		String sql = "SELECT * FROM users WHERE codebarres = ?";
    		ps = con.prepareStatement(sql);
    		ps.setString(1, codebarres);
    		rs = ps.executeQuery();
    		
    		return rs.getInt("numentree");
    		
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
		return 0;
		
    }
    
    public static int setSendDataSpecificRow(String codebarres) {
    	return sendDataSpecificRow(codebarres);
    }
       
    
}
