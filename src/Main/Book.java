package Main;

import DB.DB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Book {
    String bookId;
    String title;
    String author;
    String publication;
    String department;
    String issuedBy;
    int availability;
    int status;

    public void Book(){

    }

    public Book(String bookId){
        this.bookId = bookId;
        String query = "SELECT * FROM book where bookId = "+bookId+ ";";
        ResultSet result = DB.list(query);
        try {
            result.next();
            this.title = result.getString("title");
            this.author = result.getString("author");
            this.publication = result.getString("publication");
            this.department = result.getString("department");
            this.availability = result.getInt("availability");
            this.issuedBy = result.getString("issuedBy");
        } catch (SQLException e){
            System.err.println(e);
        }
    }

    public void issueBook(String issuedBy, String bookId){
        //get book data;
        if(this.availability == 1){
            this.issuedBy = issuedBy;
            //set in db;
        }
    }

    public static int addNewBook(String title, String author, String publication, String department){
        String query = String.format("INSERT INTO book (title, author, publication, department, availability) VALUES(\"%s\", \"%s\", \"%s\", \"%s\", 1);", title, author, publication ,department);
        int m = DB.dbQuery(query);
        if(m != 1) return -1;
        query = String.format("SELECT bookId from book where title = \"%s\" and author = \"%s\" and publication = \"%s\" and department = \"%s\";",title,author,publication,department);
        try {
            ResultSet r = DB.list(query);
            r.next();
            return r.getInt("bookId");
        } catch (SQLException e){
            System.out.println(e);
            return -1;
        }
    }

    public static void removeBook(){
        Scanner s = new Scanner(System.in);
        String bookId = s.nextLine();
        String query = String.format("DELETE FROM book where bookId = \"%s\";", bookId);
        DB.dbQuery(query);
    }

    public String getIssuedBy() {
        return issuedBy;
    }

    public void setIssuedBy(String issuedBy) {
        this.issuedBy = issuedBy;
    }

    public int getAvailability() {
        return availability;
    }

    public void setAvailability(int availability) {
        this.availability = availability;
    }

    public int updateEntry(){
        String query = String.format("UPDATE book set availability = \"%s\", issuedBy = \"%s\" where bookId = \"%s\";", this.availability, this.issuedBy, this.bookId);
        int m = DB.dbQuery(query);
        if(m != 1) {
            System.out.println("Failed!");
        }
        return m;
    }

    public static void findBook(){
        System.out.println("Select the filter\ntype 1 for bookId\ntype 2 for title\ntype 3 for publication\ntype 4 for author\ntype 5 for department\n");
        Scanner s = new Scanner(System.in);
        int choice = s.nextInt();

        String query = "";

        switch (choice){
            case 1:
                System.out.println("type bookId");
                int id = s.nextInt();
                query = String.format("SELECT * from book where bookId = %d",id);
                break;
            case 2:
                System.out.println("type title");
                s.nextLine();
                String title = s.nextLine();
                query = String.format("SELECT * from book where title = \"%s\"",title);
                break;
            case 3:
                System.out.println("type publication");
                s.nextLine();
                String pub = s.nextLine();
                query = String.format("SELECT * from book where publication = \"%s\"", pub);
                break;
            case 4:
                System.out.println("type author");
                s.nextLine();
                String a = s.nextLine();
                query = String.format("SELECT * from book where author = \"%s\"",a);
                break;
            case 5:
                System.out.println("type department");
                s.nextLine();
                String d = s.nextLine();
                query = String.format("SELECT * from book where department = \"%s\"",d);
                break;
            default:
                System.out.println("No such choice available for now.");
        }

        ResultSet r = DB.list(query);


        try {
            int count = r.getMetaData().getColumnCount();
            while (!r.isLast()) {
                r.next();
                for(int i = 1; i <= count; i++ ){
                    if(r.getMetaData().getColumnName(i).equalsIgnoreCase("bookId") || r.getMetaData().getColumnName(i).equalsIgnoreCase("availability")) {
                        System.out.print(r.getInt(i) + " ");
                    }else {
                        System.out.print(r.getString(i) + " ");
                    }
                }
                System.out.println("\n");
            }
        } catch(SQLException e){
            System.out.println(e);
        }
    }
}
