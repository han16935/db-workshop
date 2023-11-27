package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static DBConnection instance = null;
    private static Connection con = null;
    private DBConnection(){

    }

    public static DBConnection getDbConnection(){
        if(instance == null) instance = new DBConnection();
        return instance;
    }

    public Connection getConnection(){
        if(con == null){
            try{
              Class.forName("com.mysql.cj.jdbc.Driver");
              con = DriverManager.getConnection(
                      "jdbc:mysql://localhost/workshop",
                      "root", "a1b2c3d4!"
              );
                System.out.println("Connection success!");
            }

            catch (ClassNotFoundException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return con;
    }
}
