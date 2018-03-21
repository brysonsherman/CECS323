package jdbc.project;

import java.sql.*;
import java.util.Scanner;

/**
 *
 * @author Robbie and Bryson
 */
public class JDBCProject
{
    
    // Database credentials
    static String DBNAME;
    static String USER;
    static String PASS;
    
    static final String displayFormatWritingGroups = "%-30s%-20s%-15s%-25s%-30s%-20s%-15s%-25s%-25s%-25s%-25s\n";
    static final String displayFormatPublishers = "%-25s%-25s%-25s%-25s%-30s%-20s%-15s%-30s%-20s%-15s%-25s\n";
    static final String displayFormatBooks = "%-30s%-20s%-15s%-25s%-25s%-25s%-30s%-30s%-20s%-15s%-25s\n";
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "org.apache.derby.jdbc.ClientDriver";  
    static String DB_URL = "jdbc:derby://localhost:1527/";
    
    /**
     * Takes the input string and outputs "N/A" if the string is empty or null.
     * @param input The string to be mapped.
     * @return Either the input string or "N/A" as appropriate.
     */
    public static String displayNull (String input)
    {
        //because of short circuiting, if it's null, it never checks the length.
        if (input == null || input.length() == 0)
            return "N/A";
        else
            return input;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        // Prompt the user for the database name.
        Scanner in = new Scanner(System.in);
        //System.out.print("Name of the database (not the user account): ");
        DBNAME = "JDBC";
        System.out.print("Database user name: ");
        USER = "mimi";
        System.out.print("Database password: ");
        PASS = "opkins";
        //Constructing the database URL connection string
        DB_URL = DB_URL + DBNAME + ";user="+ USER + ";password=" + PASS;
        Connection connection = null; //initialize the connection
        Statement statement = null;  //initialize the statement that we're using
        
        try
        {
            // STEP 2: Register JDBC driver
            Class.forName("org.apache.derby.jdbc.ClientDriver");

            // STEP 3: Open a connection
            System.out.println("Connecting to database...");
            int menuChoice = 0;
            do
            {   
                
                connection = DriverManager.getConnection(DB_URL);

                statement = connection.createStatement();

                // Random values for initialization error...gets changed later.
                String sql = "SELECT * FROM book";
                ResultSet rs = statement.executeQuery(sql);
                
                System.out.println();
                displayMenu();
                menuChoice = checkInt(1, 10);
            
                switch(menuChoice)
                {
                    case 1: // List all writing groups.
                        System.out.println("\nListing all writing groups...\n");
                        sql = "SELECT groupName FROM writingGroups";
                        rs = statement.executeQuery(sql);
                        while(rs.next())
                        {
                            // Retrieve by column name
                            String groupName = rs.getString("groupName");
                            // Display values
                            System.out.println(displayNull(groupName));    
                        }
                        break;
                    case 2: // List all the data for a writing group specified by the user.
                        System.out.println("\nWhich group would you like to know about?");
                        String writingStatement = "SELECT * FROM writingGroups LEFT OUTER JOIN books ON writingGroups.GROUPNAME = book.GROUPNAME"
                                + " LEFT OUTER JOIN publisher ON books.PUBLISHERNAME = publisher.PUBLISHERNAME WHERE writingGroups.GROUPNAME = ?";

                        PreparedStatement preparedWritingStatement = connection.prepareStatement(writingStatement);
                        String gName = displayNull(getStringInput(in));
                        preparedWritingStatement.setString(1, gName);
                        rs = preparedWritingStatement.executeQuery();
                        
                        System.out.println(rs.next());
                        
                        if(rs.next() == false)
                        {
                            if(gName.equals("N/A"))
                                System.err.println("-NO INPUT GIVEN-");
                            else
                                System.err.println("Group Name '" + gName + "' was not found in the database.");
                        } else
                        {
                            while(rs.next())
                            {
                                // Retrieve by column name
                                String groupName = rs.getString("groupName");
                                String headWriter = rs.getString("headWriter");
                                String yearFormed = rs.getString("yearFormed");
                                String subject = rs.getString("subject");
                                String bookTitle = rs.getString("bookTitle");
                                String yearPublished = rs.getString("yearPublished");
                                String numberPages = rs.getString("numberPages");
                                String publisherName = rs.getString("publisherName");
                                String publisherAddress = rs.getString("publisherAddress");
                                String publisherPhone = rs.getString("publisherPhone");
                                String publisherEmail = rs.getString("publisherEmail");
                                
                                // Display values
                                System.out.println();
                                System.out.printf(displayFormatWritingGroups, "Group Name", "Head Writer", "Year Formed", "Subject", 
                                    "Book Title", "Year Published", "# of Pages", 
                                    "Publisher Name", "Publisher Address", "Publisher Phone #", "Publisher Email");
                                System.out.printf(displayFormatWritingGroups, displayNull(groupName), displayNull(headWriter), displayNull(yearFormed), displayNull(subject),
                                    displayNull(bookTitle), displayNull(yearPublished), displayNull(numberPages), displayNull(publisherName), displayNull(publisherAddress),
                                    displayNull(publisherPhone), displayNull(publisherEmail));
                            }
                        }
                        
                        System.out.println("\n-----------------------------------------------------------------------------------------------------------------------"
                            + "------------------------------------------------------------------------------------------------------------------------------------");
                        break;
                    case 3: // List all publishers
                        System.out.println("\nListing all publishers...\n");
                        sql = "SELECT publisherName FROM publisher";
                        rs = statement.executeQuery(sql);
                        while (rs.next()) {
                            // Retrieve by column name
                            String publisherName = rs.getString("publisherName");
                            // Display values
                            System.out.println(displayNull(publisherName));
                        }
                        break;
                    case 4: // List all the data for a publisher specified by the user.
                        System.out.println("\nWhich publisher would you like to know about?");
                        String publisherStatement = "SELECT * FROM publisher LEFT OUTER JOIN book ON publisher.PUBLISHERNAME = book.PUBLISHERNAME "
                                + "LEFT OUTER JOIN writingGroups ON book.GROUPNAME = writingGroups.GROUPNAME WHERE publisher.PUBLISHERNAME = ?";
                        PreparedStatement preparedPublisherStatement = connection.prepareStatement(publisherStatement);
                        String pName = displayNull(getStringInput(in));
                        preparedPublisherStatement.setString(1, pName);
                        rs = preparedPublisherStatement.executeQuery();

                        if (rs.next() == false)
                        {
                            if (pName.equals("N/A"))
                            {
                                System.err.println("-NO INPUT GIVEN-");
                            } else
                            {
                                System.err.println("Publisher Name '" + pName + "' was not found in the database.");
                            }
                        } else
                        {
                            while (rs.next())
                            {
                                // Retrieve by column name
                                String publisherName = rs.getString("publisherName");
                                String publisherAddress = rs.getString("publisherAddress");
                                String publisherPhone = rs.getString("publisherPhone");
                                String publisherEmail = rs.getString("publisherEmail");
                                String bookTitle = rs.getString("bookTitle");
                                String yearPublished = rs.getString("yearPublished");
                                String numberPages = rs.getString("numberPages");
                                String groupName = rs.getString("groupName");
                                String headWriter = rs.getString("headWriter");
                                String yearFormed = rs.getString("yearFormed");
                                String subject = rs.getString("subject");

                                // Display values
                                System.out.println();
                                System.out.printf(displayFormatPublishers, "Publisher Name", "Publisher Address", "Publisher Phone #", "Publisher Email",
                                        "Book Title", "Year Published", "# of Pages",
                                        "Group Name", "Head Writer", "Year Formed", "Subject");
                                System.out.printf(displayFormatPublishers, displayNull(publisherName), displayNull(publisherAddress),
                                        displayNull(publisherPhone), displayNull(publisherEmail), displayNull(bookTitle),
                                        displayNull(yearPublished), displayNull(numberPages), displayNull(groupName),
                                        displayNull(headWriter), displayNull(yearFormed), displayNull(subject));
                            }
                        }
                        System.out.println("\n-----------------------------------------------------------------------------------------------------------------------"
                                + "------------------------------------------------------------------------------------------------------------------------------------");
                        break;
                    case 5: // List all book titles
                        System.out.println("\nListing all book titles...\n");
                        sql = "SELECT bookTitle FROM book";
                        rs = statement.executeQuery(sql);
                        while (rs.next()) {
                            // Retrieve by column name
                            String bookTitle = rs.getString("bookTitle");
                            // Display values
                            System.out.println(displayNull(bookTitle));
                        }
                        break;
                    case 6: // List all the data for a book specified by the user.
                        System.out.println("\nWhich book would you like to know about?");
                        String bookStatement = "SELECT * FROM book NATURAL JOIN publisher NATURAL JOIN writingGroups WHERE bookTitle = ?";
                        PreparedStatement preparedBookStatement = connection.prepareStatement(bookStatement);
                        String bName = displayNull(getStringInput(in));
                        preparedBookStatement.setString(1, bName);
                        rs = preparedBookStatement.executeQuery();

                        
                        //System.out.println(preparedBookStatement);
                        //System.out.println(rs);
                        
                        if (rs.next() == false) 
                        {
                            if (bName.equals("N/A")) 
                            {
                                System.err.println("-NO INPUT GIVEN-");
                            } else
                            {
                                System.err.println("Book Title '" + bName + "' was not found in the database.");
                            }
                        } else 
                        {
                            while (rs.next()) 
                            {
                                // Retrieve by column name
                                String bookTitle = rs.getString("bookTitle");
                                String yearPublished = rs.getString("yearPublished");
                                String numberPages = rs.getString("numberPages");
                                String publisherName = rs.getString("publisherName");
                                String publisherAddress = rs.getString("publisherAddress");
                                String publisherPhone = rs.getString("publisherPhone");
                                String publisherEmail = rs.getString("publisherEmail");
                                String groupName = rs.getString("groupName");
                                String headWriter = rs.getString("headWriter");
                                String yearFormed = rs.getString("yearFormed");
                                String subject = rs.getString("subject");

                                // Display values
                                System.out.println();
                                System.out.printf(displayFormatBooks, "Book Title", "Year Published", "# of Pages",
                                        "Publisher Name", "Publisher Address", "Publisher Phone #", "Publisher Email",
                                        "Group Name", "Head Writer", "Year Formed", "Subject");
                                System.out.printf(displayFormatBooks, displayNull(bookTitle), displayNull(yearPublished), displayNull(numberPages),
                                        displayNull(publisherName), displayNull(publisherAddress), displayNull(publisherPhone), displayNull(publisherEmail),
                                        displayNull(groupName), displayNull(headWriter), displayNull(yearFormed), displayNull(subject));
                            }
                        }
                        System.out.println("\n-----------------------------------------------------------------------------------------------------------------------"
                                + "------------------------------------------------------------------------------------------------------------------------------------");
                        break;
                    case 7: // Insert a new book.
                        String addBookStatement = "insert into books(groupName, bookTitle, publisherName, yearPublished, numberPages) values (?, ?, ?, ?, ?)";
                        PreparedStatement preparedAddStatement = connection.prepareStatement(addBookStatement);

                        System.out.print("To insert a new book into the database, we're going to need some information."
                                + "\nWhat is the name of the writing group for the book. ");
                        String addGroupName = displayNull(getStringInput(in));
                        System.out.print("\nNext, what is the title of the book. ");
                        String addBookTitle = displayNull(getStringInput(in));
                        System.out.print("\nWhat is the name of the publisher for the book. ");
                        String addPublisherName = displayNull(getStringInput(in));
                        System.out.print("\nWhat is the publication year for the book. ");
                        int addYearPublished = getIntInput(in);
                        System.out.print("\nHow many pages does the book have. ");
                        int addNumberPages = getIntInput(in);

                        preparedAddStatement.setString(1, addGroupName);
                        preparedAddStatement.setString(2, addBookTitle);
                        preparedAddStatement.setString(3, addPublisherName);
                        preparedAddStatement.setInt(4, addYearPublished);
                        preparedAddStatement.setInt(5, addNumberPages);

                        preparedAddStatement.executeUpdate();

                        System.out.println("\n-----------------------------------------------------------------------------------------------------------------------"
                                + "------------------------------------------------------------------------------------------------------------------------------------");
                        break;
                    case 8: // TODO: Insert a new publisher and update all books published by one publisher to be published by the new publisher.

                        System.out.println("\nWhich publisher would you like to add?");
                        String newPublisher = displayNull(getStringInput(in));
                        System.out.println("Enter new publisher's address.");
                        String newPubAddress = in.nextLine();
                        System.out.println("Enter new publisher's Phone.");
                        String newPubPhone = in.nextLine();
                        System.out.println("Enter new publisher's email.");
                        String newPubEmail = in.nextLine();

                        //Inserts new Publisher with all data
                        try {
                            connection = DriverManager.getConnection(DB_URL);

                            statement = connection.createStatement();
                            String updatePublisherStatement = "INSERT INTO publisher VALUES (?,?,?,?)";
                            preparedPublisherStatement = connection.prepareStatement(updatePublisherStatement);
                            preparedPublisherStatement.setString(1, newPublisher);
                            preparedPublisherStatement.setString(2, newPubAddress);
                            preparedPublisherStatement.setString(3, newPubPhone);
                            preparedPublisherStatement.setString(4, newPubEmail);
                            preparedPublisherStatement.executeUpdate();
                            System.out.println();
                            preparedPublisherStatement.close();
                        } catch (SQLException ex) {
                        } finally {
                            try {
                                statement.close();
                                connection.close();
                            } catch (SQLException ex) {
                                System.out.println("ERROR. Could not insert Publisher.");
                            }
                        }


                        System.out.println("\nWhich publisher would you like to remove?");
                        String oldPublisher = displayNull(getStringInput(in));

                        //updates the books table to new publisher
                        try {
                            connection = DriverManager.getConnection(DB_URL);

                            statement = connection.createStatement();
                            String updateBookStatement = "UPDATE Book SET PublisherName = ? WHERE PublisherName = ?";
                            PreparedStatement preparedUpdateStatement = connection.prepareStatement(updateBookStatement);
                            preparedUpdateStatement.setString(1, newPublisher);
                            preparedUpdateStatement.setString(2, oldPublisher);
                            preparedUpdateStatement.executeUpdate();

                            System.out.println();
                            preparedUpdateStatement.close();
                        } catch (SQLException ex) {
                            System.out.println("ERROR. Could not transfer books because of invalid existing publisher.");
                        } 
                        finally {
                            try {
                                statement.close();
                                connection.close();
                            } catch (SQLException ex) {
                            }
                        }
                        break;
                    case 9: // Remove a book specified by the user.
                        System.out.print("\nWhich book would you like to remove?");
                        String removeBookStatement = "DELETE FROM book WHERE bookTitle = ?";
                        PreparedStatement preparedRemoveStatement = connection.prepareStatement(removeBookStatement);
                        String removeBookName = displayNull(getStringInput(in));
                        preparedRemoveStatement.setString(1, removeBookName);

                        preparedRemoveStatement.executeUpdate();
                        break;
                    case 10:
                        System.out.println("-Exiting-");
                        break;
                    default:
                        System.err.println("-Invalid Option-");
                        break;
                }
                
                // Clean-up environment
                rs.close();
                statement.close();
                connection.close();
            }while(menuChoice != 10);
        } catch (SQLException se)
        {
            // Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e)
        {
            // Handle errors for Class.forName
            e.printStackTrace();
        } finally
        {
            // finally block used to close resources
            try
            {
                if(statement != null)
                    statement.close();
            } catch (SQLException se2)
            {
            } // nothing we can do
            try
            {
                if(connection != null)
                    connection.close();
            } catch (SQLException se)
            {
                se.printStackTrace();
            }// end finally try
        }// end try
        System.out.println("Goodbye!");
    }// end main
        
        public static String getStringInput(Scanner in)
        {
            String input = null;
            if(in.hasNextLine())
                input = in.nextLine();
            return input;
        }
        
        public static int getIntInput(Scanner in)
        {
            int input = 0;
            if(in.hasNextInt())
                input = in.nextInt();
            return input;
        }
        
        /**
         * Method returns a valid integer input given a range
         * @param low lower bound
         * @param high upper bound
         * @param in scanner
         * @return valid input
         */
        
        public static int checkInt(int low, int high)
        {
            Scanner in = new Scanner(System.in);
            int validNum = 0;
            boolean valid = false;
            while(!valid)
            {
                if(in.hasNextInt())
                {
                    validNum = in.nextInt();
                    if(validNum >= low && validNum <= high)
                        valid = true;
                    else
                        System.err.println("Invalid");
                } else
                {
                    in.next();
                    System.err.println("Invalid");
                }
            }
            return validNum;
        }
        
        public static void displayMenu()
        {
            System.out.println("1. List all writing groups.");
            System.out.println("2. List all data for a group of your choice.");
            System.out.println("3. List all publishers.");
            System.out.println("4. List all data for a publisher of your choice.");
            System.out.println("5. List all book titles.");
            System.out.println("6. List all data for a book of your choice.");
            System.out.println("7. Insert a new book.");
            System.out.println("8. Insert a new publisher and update all books published by one publisher to be published by the new publisher.");
            System.out.println("9. Remove a book of your choice.");
            System.out.println("10. Exit");
        }
}// end JDBCProject
