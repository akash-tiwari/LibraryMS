package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DB {
    //jdbc connector class

    public static ResultSet list(String query){
        final String connection =  "jdbc:mysql://localhost/library";
        String dbUsername = "root";
        String dbPassword = "akash123";

        String sql;

        Connection con = null;
        try {
            con = DriverManager.getConnection(connection, dbUsername, dbPassword);
            Statement st = con.createStatement();
            ResultSet m = st.executeQuery(query);
            return m;
        }catch (Exception e){
            System.err.println(e);
            return null;
        }
    };

    public static int dbQuery(String query){
        final String connection =  "jdbc:mysql://localhost/library";
        String dbUsername = "root";
        String dbPassword = "akash123";

        String sql;

        Connection con = null;
        try {
            con = DriverManager.getConnection(connection, dbUsername, dbPassword);

            Statement st = con.createStatement();
            int m = st.executeUpdate(query);
            if(m == 1) System.out.println("done successfully\n");
            else System.out.println("query failed\n");
            return m;

        }catch (Exception e){
            System.err.println(e);
            return 0;
        }
    };


}
