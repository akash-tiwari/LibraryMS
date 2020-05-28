package Main;

import DB.DB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Student {
    private String rollNo;
    private String name;
    private String email;
    private String contactNo;
    private int status;

    @Deprecated
    public Student(){
        System.err.println("Please provide the student id");
    }

    public Student(String rollNo){
        this.rollNo = rollNo;

        String query = "SELECT * FROM student where rollNo = \""+rollNo+ "\";";
        ResultSet result = DB.list(query);
        try {
            result.next();
            this.name = result.getString("name");
            this.email = result.getString("email");
            this.contactNo = result.getString("contactNo");
            this.status = result.getInt("status");
        } catch (SQLException e){
            System.err.println(e);
        }
    }

    public static void createAccount(String rollNo, String name, String email, String contactNo){
        String query = String.format("INSERT INTO student (rollNo, name, email, contactNo, status) VALUES(\"%s\", \"%s\", \"%s\", \"%s\", 0);", rollNo, name, email, contactNo);
        DB.dbQuery(query);
    };

    public static void deleteAccount(){
        Scanner s = new Scanner(System.in);
        String rollNo = s.nextLine();
        String query = String.format("DELETE FROM student where rollNo = \"%s\";", rollNo);
        DB.dbQuery(query);
    }

    public int issueBook(){
        if(this.status >= 4){
            System.err.println("The student has issued "+ this.status + " books.");
            return 0;
        } else {
            this.status += 1;
            String query = String.format("UPDATE student SET status = \"%s\" where rollNo = \"%s\";",this.status, this.rollNo);
            int m = DB.dbQuery(query);
            if(m != 1){
                System.out.println("failed.");
            }
            return m;
        }
    }

    public void returnBook(){
        if(this.status <= 0){
            System.out.println("No books are issued.");
        } else {
            this.status -= 1;
            System.out.println("Entry is cleared.");
        }
    }

    public int getStatus(){
        return this.status;
    }

    public void updateEntry(){
        //update student in db;
    }

    public static void getAllIssuedBooks(){
        Scanner s = new Scanner(System.in);
        String id = s.nextLine();

        String query = String.format("SELECT * from book where issuedBy = \"%s\";",id);
        ResultSet r = DB.list(query);
        try {
            while (!r.isLast()) {
                r.next();
                System.out.println(r.getInt("bookId") + " " + r.getString("title") + " author: " + r.getString("author")+"\n");
            }
        } catch (SQLException e){
            System.out.println(e);
        }
    }
}
