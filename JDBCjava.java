import java.sql.*;
import java.util.Scanner;
import java.util.Random;
/**
 *
 * @author Mimi Opkins with some tweaking from Dave Brown
 */
public class JDBCSampleSource {
    //  Database credentials
    static String USER;
    static String PASS;
    static String DBNAME;
    static String userInput;
    //This is the specification for the printout that I'm doing:
    //each % denotes the start of a new field.
    //The - denotes left justification.
    //The number indicates how wide to make the field.
    //The "s" denotes that it's a string.  All of our output in this test are
    //strings, but that won't always be the case.
    static final String displayFormatTrunc = "%15s\n";
    static final String displayFormat="%-20s%20s%15s%15s\n";
    static final String displayFormatPublisher = "%-25s%45s%35s%35s\n";
    static final String displayFormatBooks = "%-20s%40s%19s%19s%15s\n";
    static final String displayFormatBookJoinColumn="%-40s%20s%16s%20s%40s%30s%20s%20s%20s%20s%10s\n";
    static final String displayFormatBookJoin="%-40s%10s%20s%25s%50s%19s%20s%23s%20s%15s%15s\n";
    static final String divider = "------------------------------------------------------------------"
            + "-----------------------------------------------------------------------------------------"
            + "-------------------------------------------------------------------------------------------------------\n";
// JDBC driver name and database URL
    static final String JDBC_DRIVER = "org.apache.derby.jdbc.ClientDriver";
    static String DB_URL = "jdbc:derby://localhost:1527/";
//            + "testdb;user=";
/**
 * Takes the input string and outputs "N/A" if the string is empty or null.
 * @param input The string to be mapped.
 * @return  Either the input string or "N/A" as appropriate.
 */
    public static String dispNull (String input) {
        //because of short circuiting, if it's null, it never checks the length.
        if (input == null || input.length() == 0)
            return "N/A";
        else
            return input;
    }
    
 
    /*public static boolean Groupchecker(ResultSet rs, String userInput){
        
          try {    
            while(rs.next()){
                if(rs.getString("groupname") == userInput)
                return true;
            }
            } catch(SQLException e) { e.printStackTrace();}   
            
            return false;
            
                  //conn.commit();
            
    }*/
    /* public static boolean Publisherchecker(ResultSet rs, String userInput){
        
          try {    
            while(rs.next()){
            if(rs.getString("publishername") == userInput)
                return true;
            }
            } catch(SQLException e) { e.printStackTrace();}   
            
            return false;
            
                  //conn.commit();
            
    }
      public static boolean Bookchecker(ResultSet rs, String userInput){
        
          try {    
            while(rs.next()){
            if(rs.getString("booktitle") == userInput)
                return true;
            }
            } catch(SQLException e) { e.printStackTrace();}   
            
            return false;
            
                  //conn.commit();
            
    }*/
    public static void main(String[] args) {
        //Prompt the user for the database name, and the credentials.
        //If your database has no credentials, you can update this code to
        //remove that from the connection string.
        Random rand = new Random();
        Scanner in = new Scanner(System.in);
        //System.out.print("Name of the database (not the user account): ");
        DBNAME = "JDBC";
        System.out.print("Database user name: ");
        USER = "ethan";
        System.out.print("Database password: ");
        PASS = "1234";
        //Constructing the database URL connection string
        DB_URL = DB_URL + DBNAME + ";user="+ USER + ";password=" + PASS;
        Connection conn = null; //initialize the connection
        Statement stmt = null;  //initialize the statement that we're using
        try {
            //STEP 2: Register JDBC driver
            Class.forName("org.apache.derby.jdbc.ClientDriver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL);

            //STEP 4: Execute a query
            
             System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT groupname FROM WRITINGGROUPS";
            ResultSet rs = stmt.executeQuery(sql);
            //Statement stup = null;

            //STEP 5: Extract data from result set
            
            //List all writing groups
            System.out.printf(displayFormatTrunc, "GROUP NAME");
            while (rs.next()) {
                //Retrieve by column name
                String groupName = rs.getString("groupname");
                

                //Display values
                System.out.printf(displayFormatTrunc,
                        dispNull(groupName));
            }
            
            
            //List all data for a group specified by the user;
            
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            
            System.out.print("Select a Group: " );
            userInput = in.nextLine();
            
            
            sql = "SELECT * FROM WritingGroups WHERE groupname ='" + userInput + "'";
            rs = stmt.executeQuery(sql);
                          
            System.out.printf(displayFormat, "Group Name ", "Head Writer", " YearFormed", " Subject");
            while (rs.next()) {
                //Retrieve by column name
                String group = rs.getString("groupname");
                
                String writer = rs.getString("headwriter");
                String year = rs.getString("yearformed");
                String subject = rs.getString("subject");

                //Display values
                System.out.printf(displayFormat,
                   dispNull(group), dispNull(writer), dispNull(year), dispNull(subject));
                    }    
              
                
                // If all 3 classes are wanted
                
               /*sql = "SELECT * FROM WritingGroups WHERE groupname ='" + userInput + "'" + "NATURAL JOIN Books NATURAL JOIN Publishers";
                rs = stmt.executeQuery(sql); 
                String groupName = rs.getString("groupname"); 
                String title = rs.getString("booktitle");
                String year = rs.getString("yearpublished");
                String pages = rs.getString("numberpages");
                String name2 = rs.getString("publishername");
                String address2 = rs.getString("publisheraddress");
                String phone2 = rs.getString("publisherphone");
                String email2 = rs.getString("publisheremail");
                
                String writer = rs.getString("headwriter");
                String yearformed = rs.getString("yearformed");
                String subject2 = rs.getString("subject");
                System.out.printf(displayFormatBookJoinColumn, "Book Title","Year Published","Number Pages","Publisher Name","Publisher Address",
                                   "Publisher Phone","Publisher Email","Group Name","Head Writer","Year Formed","Subject");
            
            System.out.printf(displayFormatBookJoin,
                   dispNull(groupName),dispNull(title), dispNull(year), dispNull(pages), dispNull(address2),dispNull(phone2),
                    dispNull(email2),dispNull(group2),dispNull(writer),dispNull(yearformed),dispNull(subject2));*/
            
            rs.close();
            stmt.close();
            
            // List all publishers
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            sql = "SELECT publishername FROM Publishers";
            rs = stmt.executeQuery(sql);
            
            System.out.printf(displayFormatTrunc, "PUBLISHER NAME");
            while(rs.next()){
                String publisherName = rs.getString("PUBLISHERNAME");
                
                System.out.printf(displayFormatTrunc, dispNull(publisherName));
            }
            
            System.out.print("\n");
            
            // List all data for a publisher specified by the user
            
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
         
            System.out.print("Select a Publisher: " );
            userInput = in.nextLine();
            sql = "SELECT * FROM Publishers WHERE publishername ='" + userInput + "'";
            rs = stmt.executeQuery(sql);
            
                System.out.printf(displayFormatPublisher, "Publisher Name", "Publisher Address","Publisher Phone","Publisher Email");
                while(rs.next()){
                String name = rs.getString("publishername");
                String address = rs.getString("publisheraddress");
                String phone = rs.getString("publisherphone");
                String email = rs.getString("publisheremail");
                
                System.out.printf(displayFormatPublisher,
                   dispNull(name), dispNull(address), dispNull(phone), dispNull(email));
                }
                // If all 3 classes are wanted
                
               /*sql = "SELECT * FROM Publishers WHERE publishername ='" + userInput + "'" + "NATURAL JOIN Books NATURAL JOIN Publishers";
                rs = stmt.executeQuery(sql); 
                String title = rs.getString("booktitle");
                String year = rs.getString("yearpublished");
                String pages = rs.getString("numberpages");
                String name2 = rs.getString("publishername");
                String address2 = rs.getString("publisheraddress");
                String phone2 = rs.getString("publisherphone");
                String email2 = rs.getString("publisheremail");
                String groupName2 = rs.getString("groupname");
                String writer = rs.getString("headwriter");
                String yearformed = rs.getString("yearformed");
                String subject2 = rs.getString("subject");
                System.out.printf(displayFormatBookJoinColumn, "Book Title","Year Published","Number Pages","Publisher Name","Publisher Address",
                                   "Publisher Phone","Publisher Email","Group Name","Head Writer","Year Formed","Subject");
                
                 System.out.printf(displayFormatBookJoin,
                   dispNull(groupName2),dispNull(title), dispNull(year), dispNull(pages), dispNull(address2),dispNull(phone2),
                    dispNull(email2),dispNull(group2),dispNull(writer),dispNull(yearformed),dispNull(subject2));*/
                
             
            
            rs.close();
            stmt.close();
            
            // List all books
            System.out.println("Creating statement...");
            stmt = conn.createStatement();  
            sql = "select booktitle from books";
            rs = stmt.executeQuery(sql);
            
            System.out.printf(displayFormatTrunc, "BOOK TITLE");
            while(rs.next()){
                String bookTitle = rs.getString("BOOKTITLE");
                
                System.out.printf(displayFormatTrunc, dispNull(bookTitle));
            }
            
            System.out.print("\n");
            
            // List all the data for a book specified by the user (includes associated publisher and writing group)
            
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            System.out.print("Select a Book: ");
            userInput = in.nextLine();
            sql = "SELECT * FROM Books NATURAL JOIN Publishers NATURAL JOIN WritingGroups WHERE booktitle = '" + userInput + "'";
            rs = stmt.executeQuery(sql);
                
                System.out.printf(displayFormatBookJoinColumn, "Book Title","Year Published","Number Pages","Publisher Name","Publisher Address",
                                   "Publisher Phone","Publisher Email","Group Name","Head Writer","Year Formed","Subject");
                System.out.printf(divider);
                while(rs.next()){
                String title = rs.getString("booktitle");
                String year = rs.getString("yearpublished");
                String pages = rs.getString("numberpages");
                String name2 = rs.getString("publishername");
                String address2 = rs.getString("publisheraddress");
                String phone2 = rs.getString("publisherphone");
                String email2 = rs.getString("publisheremail");
                String group2 = rs.getString("groupname");
                String writer = rs.getString("headwriter");
                String yearformed = rs.getString("yearformed");
                String subject2 = rs.getString("subject");
                
                System.out.printf(displayFormatBookJoin,
                   dispNull(title), dispNull(year), dispNull(pages), dispNull(name2),dispNull(address2),dispNull(phone2),
                    dispNull(email2),dispNull(group2),dispNull(writer),dispNull(yearformed),dispNull(subject2));
            } 
             stmt.close();
             rs.close();
             
             //Insert a new book
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String BooksNew[] = {"Naruto","X-Men","DeadPool","AquaMan","RickAndMorty"};
            int choice = rand.nextInt(5);
             sql = "INSERT INTO Books VALUES('KillerFantasy','"+BooksNew[choice]+"', 'Mongul Media', 1995, 400)";
            stmt.executeUpdate(sql);
            
            System.out.print("\n");
            stmt.close();
            //Select all books
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            sql = "SELECT booktitle FROM Books";
            rs = stmt.executeQuery(sql);
            System.out.printf(displayFormatTrunc, "BOOK TITLE");
            while(rs.next()){
                String bookTitle = rs.getString("BOOKTITLE");
                
                System.out.printf(displayFormatTrunc, dispNull(bookTitle));
            }
            rs.close();
            stmt.close();
            
            //Insert a new publisher and update all book published by one publisher to be published by the new publisher
             int Entry = rand.nextInt(16);
             int newEntry = rand.nextInt(9);
             int choiceNew = rand.nextInt(7);
             //List of old Publishers
             String PubNameOld[] = {"Mongul Media","P.B.B","ReadtoRead","Written Inc.","Control Publishers","Pliny Inc.","Finished Inc.","Publishing Apt.",
                                        "Decisions LLC.","Simplicity","K.W.S","Catch Media","LeagueofPublishers","ScienceInfo","AllInfo","ThronePublishing"};
             // List of new Publishers
             String PubNameNew[] = {"NewWriting","Oldies","CriticalWriting","IAS Inc.","Employ","Enjoyed Inc.","ShortStory","Final Inc.","SmartWrite"};
            
             //List of Publishers with Books Published
             String BooksPublish[] = {"Mongul Media","K.W.S","Written Inc.","LeagueofPublishers","Catch Media","Finished Inc.", "Simplicity"};
             
            // Insert a new publisher and update all books published by one publisher to be published by the new publisher
             System.out.println("Creating statement...");
             stmt = conn.createStatement();
             sql = " INSERT INTO Publishers VALUES('"+ PubNameNew[newEntry] + "','19230 Nogo Blvd, Xyxxx, CA, 90023','232-123-2132','finish@xyxx.com')";
             stmt.executeUpdate(sql);
            
            sql = "SELECT * FROM Publishers";
            rs = stmt.executeQuery(sql);
                System.out.printf("Updated List of Publishers\n");
                System.out.printf(displayFormatPublisher, "Publisher Name", "Publisher Address","Publisher Phone","Publisher Email");
                while(rs.next()){
                String name = rs.getString("publishername");
                String address = rs.getString("publisheraddress");
                String phone = rs.getString("publisherphone");
                String email = rs.getString("publisheremail");
                
                System.out.printf(displayFormatPublisher,
                   dispNull(name), dispNull(address), dispNull(phone), dispNull(email));
                }
            /*try {
                statementNew = conn.prepareStatement(queryNew);
                statementNew.setString(1, PubNameOld[Entry]); // Sets string name to a new publishers name
                statementNew.executeUpdate();
                //conn.commit();
            } catch(SQLException e) { e.printStackTrace(); }*/
            
             stmt.close();
             System.out.println("Creating Statement...");
             System.out.println("\n");
             stmt = conn.createStatement();
             //PreparedStatement statementNew = null;
             //Update Books Relation with new publisher
             sql = "SELECT * FROM Books";
             rs = stmt.executeQuery(sql);
             System.out.printf("Old Books List\n");
             System.out.printf(displayFormatBooks, "Group Name", "Book Title", "Publisher Name", "Year Published", "Number Pages");
             while(rs.next()){
                 String group = rs.getString("groupname");
                 String call = rs.getString("booktitle");
                 String publish = rs.getString("publishername");
                 String yr = rs.getString("yearpublished");
                 String numpgs = rs.getString("numberpages");
                 
                 System.out.printf(displayFormatBooks,dispNull(group),dispNull(call),dispNull(publish),dispNull(yr),dispNull(numpgs));
                 
             }
             
             System.out.print("\n");
             // Update Books
             sql = "UPDATE Books " + "SET publishername = '"+PubNameNew[newEntry]+"'" + "WHERE publishername = '"+ BooksPublish[choiceNew]+"'"; 
             stmt.executeUpdate(sql);
             /* try{
                 statementNew = conn.prepareStatement(sql);
                 statementNew.setString(1,PubNameNew[newEntry]);
                 statementNew.setString(2, PubNameOld[Entry]);
                 statementNew.executeUpdate();
             
             }catch(SQLException e) { e.printStackTrace(); }*/
             
             //Select Book data for books that have a new publisher
             sql = "SELECT * FROM Books WHERE publishername = '"+PubNameNew[newEntry]+"'";
             rs = stmt.executeQuery(sql);
             System.out.printf("Books List who has a new Publisher");
             System.out.printf("\n");
             System.out.printf(displayFormatBooks, "Group Name", "Book Title", "Publisher Name", "Year Published", "Number Pages");
             while(rs.next()){
                 String group = rs.getString("groupname");
                 String call = rs.getString("booktitle");
                 String publish = rs.getString("publishername");
                 String yr = rs.getString("yearpublished");
                 String numpgs = rs.getString("numberpages");
                 
                 System.out.printf(displayFormatBooks,dispNull(group),dispNull(call),dispNull(publish),dispNull(yr),dispNull(numpgs));
                 
             }
             
             //Remove book
            System.out.println("What book would you like to delete?");
            String dBook = in.nextLine();
            
            String query = "DELETE FROM BOOKS WHERE BOOKTITLE = ?";
            PreparedStatement statement = null;
            try {
                statement = conn.prepareStatement(query);
                statement.setString(1, dBook);
                statement.executeUpdate();
            } catch(SQLException e) { e.printStackTrace(); }
        
            System.out.println(dBook + " deleted.\n");
            
            //STEP 6: Clean-up environment
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try
        System.out.println("Goodbye!");
    }//end main
}//end FirstExample}