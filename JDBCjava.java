import java.sql.*;
import java.util.Scanner;

/**
 *
 * @author Mimi Opkins with some tweaking from Dave Brown
 */
public class JDBCSampleSource_odd {
    //  Database credentials
    static String USER;
    static String PASS;
    static String DBNAME;
    //This is the specification for the printout that I'm doing:
    //each % denotes the start of a new field.
    //The - denotes left justification.
    //The number indicates how wide to make the field.
    //The "s" denotes that it's a string.  All of our output in this test are
    //strings, but that won't always be the case.
    static final String displayFormat="%-5s\n";
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

    @SuppressWarnings("empty-statement")
    public static void main(String[] args) throws SQLException {
        //Prompt the user for the database name, and the credentials.
        //If your database has no credentials, you can update this code to
        //remove that from the connection string.
        Scanner in = new Scanner(System.in);
        System.out.print("Name of the database (not the user account): ");
        DBNAME = in.nextLine();
        System.out.print("Database user name: ");
<<<<<<< HEAD
        USER = in.nextLine();
        System.out.print("Database password: ");
        PASS = in.nextLine();
=======
        USER = "mimi";
        System.out.print("Database password: ");
        PASS = "opkins";
>>>>>>> 4f87816e825aa174d122d413ad4a73bf7eb5be15
        //Constructing the database URL connection string
        DB_URL = DB_URL + DBNAME + ";user="+ USER + ";password=" + PASS;
        Connection conn = DriverManager.getConnection(DB_URL); //initialize the connection
        Statement stmt = conn.createStatement();  //initialize the statement that we're using
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
            sql = "SELECT GROUPNAME FROM WRITINGGROUPS";
            ResultSet rs = stmt.executeQuery(sql);

            //STEP 5: Extract data from result set
            System.out.printf(displayFormat, "GROUP NAME");
            while (rs.next()) {
                //Retrieve by column name
                String groupName = rs.getString("GROUPNAME");
                

                //Display values
                System.out.printf(displayFormat,
                        dispNull(groupName));
            }
            System.out.print("\n");
            sql = "select publishername from publisher";
            rs = stmt.executeQuery(sql);
            
            System.out.printf(displayFormat, "PUBLISHER NAME");
            while(rs.next()){
                String publisherName = rs.getString("PUBLISHERNAME");
                
                System.out.printf(displayFormat, dispNull(publisherName));
            }
            
            System.out.print("\n");
            
            sql = "select booktitle from book";
            rs = stmt.executeQuery(sql);
            
            System.out.printf(displayFormat, "BOOK TITLE");
            while(rs.next()){
                String bookTitle = rs.getString("BOOKTITLE");
                
                System.out.printf(displayFormat, dispNull(bookTitle));
            }
            
            System.out.print("\n");
            
            //sql = "INSERT INTO books VALUES('KillerFantasy', 'Naruto', 'Mongul Media', 1995, 400)";
            //stmt.executeUpdate(sql);
            
            
            sql = "select booktitle from book";
            rs = stmt.executeQuery(sql);
            System.out.printf(displayFormat, "BOOK TITLE");
            while(rs.next()){
                String bookTitle = rs.getString("BOOKTITLE");
                
                System.out.printf(displayFormat, dispNull(bookTitle));
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
