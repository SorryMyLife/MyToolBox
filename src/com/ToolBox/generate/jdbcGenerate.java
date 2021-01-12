package com.ToolBox.generate;
/**
* <p>创建时间：2020年12月18日 下午12:47:31
* <p>项目名称：ToolBox
* 
* <p>类说明：
*
* @version 1.0
* @since JDK 1.8
* 文件名称：jdbcGenerate.java
* */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class jdbcGenerate {
	private String databasehost, username, userpassword, databasename;

	public String getDatabasehost() {
		return databasehost;
	}

	public String getUsername() {
		return username;
	}

	public String getUserpassword() {
		return userpassword;
	}

	public String getDatabasename() {
		return databasename;
	}

	public Integer getDatabaseport() {
		return databaseport;
	}

	public void setDatabasehost(String databasehost) {
		this.databasehost = databasehost;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setUserpassword(String userpassword) {
		this.userpassword = userpassword;
	}

	public void setDatabasename(String databasename) {
		this.databasename = databasename;
	}

	public void setDatabaseport(Integer databaseport) {
		this.databaseport = databaseport;
	}

	public jdbcGenerate(String databasehost, String username, String userpassword, String databasename,
			Integer databaseport) {
		super();
		this.databasehost = databasehost;
		this.username = username;
		this.userpassword = userpassword;
		this.databasename = databasename;
		this.databaseport = databaseport;
	}

	public jdbcGenerate(String username, String userpassword, String databasename) {
		super();
		this.databasehost = "localhost";
		this.username = username;
		this.userpassword = userpassword;
		this.databasename = databasename;
		this.databaseport = 3306;
	}

	public jdbcGenerate() {

	}

	private Integer databaseport;

	private String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

	private Connection sqlcon;

	public void InitConnection() throws SQLException, ClassNotFoundException {
		Class.forName(JDBC_DRIVER);
		String DB_URL = "jdbc:mysql://" + getDatabasehost() + ":" + getDatabaseport() + "/" + getDatabasename()
				+ "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

		sqlcon = DriverManager.getConnection(DB_URL, getUsername(), getUserpassword());
	}

	public void closeConnection() throws SQLException {
		sqlcon.close();
	}

	public List<String> getTables() {
		try {
			List<String> tables = new ArrayList<String>();
			InitConnection();
			ResultSet tables2 = sqlcon.getMetaData().getTables(getDatabasename(), null, null, new String[] { "TABLE" });
			while (tables2.next()) {
				tables.add(tables2.getString(3));
			}
			tables2.close();
			closeConnection();
			return tables;
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public Map<String, List<jdbcFiledInfo>> getFileds() {
		HashMap<String, List<jdbcFiledInfo>> Fileds = new HashMap<String, List<jdbcFiledInfo>>();
		
		getTables().stream().forEach((tablename) -> {
			List<jdbcFiledInfo> filedsList = new ArrayList<>();
			String sql = "show full columns from " + tablename;
			int tmpnum = 1;
			try {
				InitConnection();
				
				ResultSetMetaData metaData = sqlcon.prepareStatement("select * from " + tablename).getMetaData();
				ResultSet executeQuery = sqlcon.prepareStatement(sql).executeQuery(sql);
				while (executeQuery.next()) {
					filedsList.add(new jdbcFiledInfo(executeQuery.getString(1),
							metaData.getColumnClassName(tmpnum), executeQuery.getString(9)));
					tmpnum++;
				}
				executeQuery.close();
				closeConnection();
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Fileds.put(tablename, filedsList);
		});
		return Fileds;
	}

}
