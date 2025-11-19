  import java.io.*;
  import java.util.*;
  
  /**
   * LibraryManager class for managing books and members using collections and file handling.
   * Includes menu-driven interface.
   * Student: Yash Raj, Roll No: 2401010008, Program: B.Tech. CSE Core, Faculty: Lucky Verma
   */
  public class LibraryManager {
      private Map<Integer, Book> books;
      private Map<Integer, Member> members;
      private Set<String> categories;
      private Queue<String> waitingList;  // Optional: For popular books
      private int nextBookId = 1;
      private int nextMemberId = 1;
      private final String BOOKS_FILE = "books.txt";
      private final String MEMBERS_FILE = "members.txt";
      
      // Constructor
      public LibraryManager() {
          books = new HashMap<>();
          members = new HashMap<>();
          categories = new HashSet<>();
          waitingList = new LinkedList<>();
          loadFromFile();  // Load data on startup
      }
      
      // Add Book
      public void addBook() {
          Scanner scanner = new Scanner(System.in);
          System.out.print("Enter Book Title: ");
          String title = scanner.nextLine();
          System.out.print("Enter Author: ");
          String author = scanner.nextLine();
          System.out.print("Enter Category: ");
          String category = scanner.nextLine();
          Book book = new Book(nextBookId++, title, author, category);
          books.put(book.getBookId(), book);
          categories.add(category);
          saveToFile();
          System.out.println("Book added successfully with ID: " + book.getBookId());
      }
      
      // Add Member
      public void addMember() {
          Scanner scanner = new Scanner(System.in);
          System.out.print("Enter Member Name: ");
          String name = scanner.nextLine();
          System.out.print("Enter Email: ");
          String email = scanner.nextLine();
          if (!email.contains("@")) {
              System.out.println("Invalid email format.");
              return;
          }
          Member member = new Member(nextMemberId++, name, email);
          members.put(member.getMemberId(), member);
          saveToFile();
          System.out.println("Member added successfully with ID: " + member.getMemberId());
      }
      
      // Issue Book
      public void issueBook() {
          Scanner scanner = new Scanner(System.in);
          System.out.print("Enter Member ID: ");
          int memberId = scanner.nextInt();
          System.out.print("Enter Book ID: ");
          int bookId = scanner.nextInt();
          Member member = members.get(memberId);
          Book book = books.get(bookId);
          if (member == null || book == null) {
              System.out.println("Invalid member or book ID.");
              return;
          }
          if (book.isIssued()) {
              System.out.println("Book is already issued. Adding to waiting list.");
              waitingList.add("Member " + memberId + " waiting for Book " + bookId);
              return;
          }
          book.markAsIssued();
          member.addIssuedBook(bookId);
          saveToFile();
          System.out.println("Book issued successfully.");
      }
      
      // Return Book
      public void returnBook() {
          Scanner scanner = new Scanner(System.in);
          System.out.print("Enter Member ID: ");
          int memberId = scanner.nextInt();
          System.out.print("Enter Book ID: ");
          int bookId = scanner.nextInt();
          Member member = members.get(memberId);
          Book book = books.get(bookId);
          if (member == null || book == null || !book.isIssued() || !member.getIssuedBooks().contains(bookId)) {
              System.out.println("Invalid return request.");
              return;
          }
          book.markAsReturned();
          member.returnIssuedBook(bookId);
          saveToFile();
          System.out.println("Book returned successfully.");
      }
      
      // Search Books
      public void searchBooks() {
          Scanner scanner = new Scanner(System.in);
          System.out.print("Enter search term (title/author/category): ");
          String term = scanner.nextLine().toLowerCase();
          boolean found = false;
          for (Book book : books.values()) {
              if (book.getTitle().toLowerCase().contains(term) || book.getAuthor().toLowerCase().contains(term) || book.getCategory().toLowerCase().contains(term)) {
                  book.displayBookDetails();
                  found = true;
              }
          }
          if (!found) System.out.println("No books found.");
      }
      
      // Sort Books
      public void sortBooks() {
          List<Book> bookList = new ArrayList<>(books.values());
          Scanner scanner = new Scanner(System.in);
          System.out.println("Sort by: 1. Title 2. Author 3. Category");
          int choice = scanner.nextInt();
          switch (choice) {
              case 1:
                  Collections.sort(bookList);  // Comparable
                  break;
              case 2:
                  bookList.sort(Comparator.comparing(Book::getAuthor));  // Comparator
                  break;
              case 3:
                  bookList.sort(Comparator.comparing(Book::getCategory));  // Comparator
                  break;
              default:
                  System.out.println("Invalid choice.");
                  return;
          }
          for (Book book : bookList) {
              book.displayBookDetails();
          }
      }
      
      // Load from File
      private void loadFromFile() {
          try (BufferedReader br = new BufferedReader(new FileReader(BOOKS_FILE))) {
              String line;
              while ((line = br.readLine()) != null) {
                  String[] parts = line.split(",");
                  int id = Integer.parseInt(parts[0]);
                  Book book = new Book(id, parts[1], parts[2], parts[3]);
                  if (Boolean.parseBoolean(parts[4])) book.markAsIssued();
                  books.put(id, book);
                  categories.add(parts[3]);
                  if (id >= nextBookId) nextBookId = id + 1;
              }
          } catch (IOException e) {
              System.out.println("No existing books file found. Starting fresh.");
          }
          try (BufferedReader br = new BufferedReader(new FileReader(MEMBERS_FILE))) {
              String line;
              while ((line = br.readLine()) != null) {
                  String[] parts = line.split(",");
                  int id = Integer.parseInt(parts[0]);
                  Member member = new Member(id, parts[1], parts[2]);
                  if (parts.length > 3) {
                      for (String bid : parts[3].split(",")) {
                          if (!bid.isEmpty()) member.addIssuedBook(Integer.parseInt(bid));
                      }
                  }
                  members.put(id, member);
                  if (id >= nextMemberId) nextMemberId = id + 1;
              }
          } catch (IOException e) {
              System.out.println("No existing members file found. Starting fresh.");
          }
      }
      
      // Save to File
      private void saveToFile() {
          try (BufferedWriter bw = new BufferedWriter(new FileWriter(BOOKS_FILE))) {
              for (Book book : books.values()) {
                  bw.write(book.toString());
                  bw.newLine();
              }
          } catch (IOException e) {
              System.out.println("Error saving books.");
          }
          try (BufferedWriter bw = new BufferedWriter(new FileWriter(MEMBERS_FILE))) {
              for (Member member : members.values()) {
                  bw.write(member.toString());
                  bw.newLine();
              }
          } catch (IOException e) {
              System.out.println("Error saving members.");
          }
      }
      
      // Main Menu
      public void mainMenu() {
          Scanner scanner = new Scanner(System.in);
          int choice;
          do {
              System.out.println("\nWelcome to City Library Digital Management System");
              System.out.println("1. Add Book");
              System.out.println("2. Add Member");
              System.out.println("3. Issue Book");
              System.out.println("4. Return Book");
              System.out.println("5. Search Books");
              System.out.println("6. Sort Books");
              System.out.println("7. Exit");
              System.out.print("Enter your choice: ");
              choice = scanner.nextInt();
              switch (choice) {
                  case 1: addBook(); break;
                  case 2: addMember(); break;
                  case 3: issueBook(); break;
                  case 4: returnBook(); break;
                  case 5: searchBooks(); break;
                  case 6: sortBooks(); break;
                  case 7: saveToFile(); System.out.println("Exiting..."); break;
                  default: System.out.println("Invalid choice.");
              }
          } while (choice != 7);
          scanner.close();
      }
      
      // Main Method
      public static void main(String[] args) {
          LibraryManager manager = new LibraryManager();
          manager.mainMenu();
      }
  }
  
