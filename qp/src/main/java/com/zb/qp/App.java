package com.zb.qp;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

 

/**
 * quickstart项目
 * 
 */
public class App {
	public static void main(String[] args) {
		
		App app = new App();
		try {
			app.service();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	
	
	public void service() throws ClassNotFoundException, IOException, SQLException{

		 List<User> list = this.dao();
		 
		 ExportExcel<User> export = new ExportExcel<User>();
		 String[] headers = {"姓名","年龄","性别"};
		 export.exportExcel(headers, list, "user");
	  
		 
	}
	
	
     public List dao() throws IOException, ClassNotFoundException, SQLException {
    	 InputStream in = new BufferedInputStream(new FileInputStream("F:\\zb\\qp\\target\\classes\\com\\zb\\qp\\db.properties"));
		 Properties p = new Properties();
		 p.load(in);
		 String url = p.getProperty("url");
		 String userName = p.getProperty("userName");
		 String password = p.getProperty("password");
		 
		 Class.forName("com.mysql.jdbc.Driver");
		 Connection con = DriverManager.getConnection(url,userName,password); 
		 Statement st = con.createStatement(); 
		 ResultSet set = st.executeQuery("select * from user");
		 List<User> list = new ArrayList<User>();
		 
		 
		 while(set.next()){ 
             User user = new User();
             user.setName(set.getString(1));
             user.setSex(set.getString(2));
             user.setAge(set.getString(3));
             list.add(user);
         }
		 
		 con.close();
	     st.close();   
	     return list;
     }
      
     
}
