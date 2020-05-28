package Main;

import java.util.Scanner;

public class Library {
    public static void main(String[] args){
        Scanner s = new Scanner(System.in);

        System.out.println("what you wanna do?\ntype 1 for creating new account\ntype 2 to delete existing account"
        + "\ntype 3 to add new book\ntype 4 to permanently delete book from library\ntype 5 to issue a book\n"+
                "type 6 to return a book\ntype 7 to find all books issued by a student\ntype 8 to find a book\n");
        Integer option = s.nextInt();

        switch(option){
            case 1:
                System.out.println("You chose 1: create new account.");
                createAccount();
                break;
            case 2:
                System.out.println("You chose 2:  delete existing account.\n type your rollNo.");
                Student.deleteAccount();
                break;
            case 3:
                System.out.println("You chose 3: add new book.");
                addBook();
                break;
            case 4:
                System.out.println("You chose 4:remove book\ntype bookId.");
                Book.removeBook();
                break;
            case 5:
                System.out.println("You chose 5: issue a book.\n type your rollno");
                issueBook();
                break;
            case 6:
                System.out.println("You chose 6: return a book.\ntype book id.");
                returnBook();
                break;
            case 7:
                System.out.println("You chose 7:find books issued by student.\nType student rollNo");
                Student.getAllIssuedBooks();
                break;
            case 8:
                System.out.println("You chose 8: find a book.");
                Book.findBook();
                break;
            default:
                System.out.println("No such option available.");
        }
    }

    private static void createAccount(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Type your rollNo");
        String rollNo = sc.nextLine();
        System.out.println("Type your name");
        String name = sc.nextLine();
        System.out.println("Type your email");
        String email = sc.nextLine();
        System.out.println("Type your phone number");
        String phone = sc.nextLine();

        Student.createAccount(rollNo, name, email, phone);
    }

    private static void addBook(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Type title");
        String title = sc.nextLine();
        System.out.println("Type author");
        String author = sc.nextLine();
        System.out.println("Type publication");
        String pub = sc.nextLine();
        System.out.println("Type department");
        String dept = sc.nextLine();

        int resp = Book.addNewBook(title, author, pub, dept);
        System.out.format("Book Id is %d",resp);
    }

    private static void issueBook(){
        Scanner sc = new Scanner(System.in);
        String rollNo = sc.nextLine();

        Student std = new Student(rollNo);
        int status = std.getStatus();
        if(status >= 4){
            System.err.println("student's can't issue more than 4 books.");
        } else {
            System.out.println("type the bookId\n");
            String bookId = sc.nextLine();
            Book book = new Book(bookId);
            if(book.getAvailability() == 0){
                System.err.println("\nBook not available.");
                return;
            }
            book.setAvailability(0);
            book.setIssuedBy(rollNo);
            int m = std.issueBook();
            if(m!= 1) return;
            m = book.updateEntry();
            if(m == 1) System.out.println("Done!");
        }
    }

    private static void returnBook(){
        Scanner s = new Scanner(System.in);
        String id = s.nextLine();
        Book book = new Book(id);

        if( book.getAvailability() == 1){
            System.out.println("Already in returned state.");
            return;
        }

        String rollNo = book.getIssuedBy();
        Student student = new Student(rollNo);
        student.returnBook();
        student.updateEntry();

        book.setIssuedBy(null);
        book.setAvailability(1);
        int m = book.updateEntry();
        if(m == 1) System.out.println("Done!");
    }
}
