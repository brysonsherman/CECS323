package jdbc.project;

import java.sql.*;
import java.util.Scanner;

/**
 *
 * @author Robbie
 */
public class JDBCProject
{
    
    // Database credentials
    static String DBNAME;
    
    static final String displayFormatWritingGroups = "%-30s%-20s%-15s%-25s%-30s%-20s%-15s%-25s%-25s%-25s%-25s\n";
    static final String displayFormatPublishers = "%-25s%-25s%-25s%-25s%-30s%-20s%-15s%-30s%-20s%-15s%-25s\n";
    static final String displayFormatBooks = "%-30s%-20s%-15s%-25s%-25s%-25s%-30s%-30s%-20s%-15s%-25s\n";
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "org.apache.derby.jdbc.ClientDriver";  
    static String DB_URL = "jdbc:derby://localhost:1527/JDBC Project";
    
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
        System.out.print("Name of the database (not the user account): ");
        DBNAME = in.nextLine();
        //Constructing the database URL connection string
        DB_URL = DB_URL + DBNAME;
        // Initialize the connection
        Connection connection = null;
        // Initialize the statement that we're using
        Statement statement = null;
        
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
                String sql = "SELECT * FROM books";
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
                        
                        //SELECT * FROM writingGroups NATURAL JOIN books NATURAL JOIN publishers WHERE groupName = ?;
                        String writingStatement = "SELECT * FROM writingGroups LEFT OUTER JOIN books ON writingGroups.GROUPNAME = books.GROUPNAME"
                                + " LEFT OUTER JOIN publishers ON books.PUBLISHERNAME = publishers.PUBLISHERNAME WHERE writingGroups.GROUPNAME = ?";

                        PreparedStatement preparedWritingStatement = connection.prepareStatement(writingStatement);
                        String gName = displayNull(getStringInput(in));
                        preparedWritingStatement.setString(1, gName);
                        rs = preparedWritingStatement.executeQuery();

                        boolean groupFound = false;

                        while (rs.next()) {
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
                            groupFound = true;
                            System.out.printf(displayFormatWritingGroups, "Group Name", "Head Writer", "Year Formed", "Subject",
                                    "Book Title", "Year Published", "# of Pages",
                                    "Publisher Name", "Publisher Address", "Publisher Phone #", "Publisher Email");
                            System.out.printf(displayFormatWritingGroups, displayNull(groupName), displayNull(headWriter), displayNull(yearFormed), displayNull(subject),
                                    displayNull(bookTitle), displayNull(yearPublished), displayNull(numberPages), displayNull(publisherName), displayNull(publisherAddress),
                                    displayNull(publisherPhone), displayNull(publisherEmail));
                        }
                        if (!groupFound) {
                            if (gName.equals("N/A")) 
                                System.err.println("-NO INPUT GIVEN-");
                            else 
                                System.err.println("Group Name '" + gName + "' was not found in the database.");
                        }
                        
                        System.out.println("\n-----------------------------------------------------------------------------------------------------------------------"
                            + "------------------------------------------------------------------------------------------------------------------------------------");
                        break;
                    case 3: // List all publishers
                        System.out.println("\nListing all publishers...\n");
                        sql = "SELECT publisherName FROM publishers";
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
                        
                        //SELECT * FROM publishers NATURAL JOIN books NATURAL JOIN writingGroups WHERE publisherName = ?;
                        String publisherStatement = "SELECT * FROM publishers LEFT OUTER JOIN books ON publishers.PUBLISHERNAME = books.PUBLISHERNAME "
                                + "LEFT OUTER JOIN writingGroups ON books.GROUPNAME = writingGroups.GROUPNAME WHERE publishers.PUBLISHERNAME = ?";
                        PreparedStatement preparedPublisherStatement = connection.prepareStatement(publisherStatement);
                        String pName = displayNull(getStringInput(in));
                        preparedPublisherStatement.setString(1, pName);
                        rs = preparedPublisherStatement.executeQuery();

                        boolean publisherFound = false;
                        
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
                            publisherFound = true;
                            System.out.printf(displayFormatPublishers, "Publisher Name", "Publisher Address", "Publisher Phone #", "Publisher Email",
                                    "Book Title", "Year Published", "# of Pages",
                                    "Group Name", "Head Writer", "Year Formed", "Subject");
                            System.out.printf(displayFormatPublishers, displayNull(publisherName), displayNull(publisherAddress),
                                    displayNull(publisherPhone), displayNull(publisherEmail), displayNull(bookTitle),
                                    displayNull(yearPublished), displayNull(numberPages), displayNull(groupName),
                                    displayNull(headWriter), displayNull(yearFormed), displayNull(subject));
                        }
                        if (!publisherFound)
                        {
                            if (pName.equals("N/A"))
                            {
                                System.err.println("-NO INPUT GIVEN-");
                            } else
                            {
                                System.err.println("Publisher Name '" + pName + "' was not found in the database.");
                            }
                        }
                        System.out.println("\n-----------------------------------------------------------------------------------------------------------------------"
                                + "------------------------------------------------------------------------------------------------------------------------------------");
                        break;
                    case 5: // List all book titles
                        System.out.println("\nListing all book titles...\n");
                        sql = "SELECT bookTitle FROM books";
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
                        String bookStatement = "SELECT * FROM books NATURAL JOIN publishers NATURAL JOIN writingGroups WHERE bookTitle = ?";
                        PreparedStatement preparedBookStatement = connection.prepareStatement(bookStatement);
                        String bName = displayNull(getStringInput(in));
                        preparedBookStatement.setString(1, bName);
                        rs = preparedBookStatement.executeQuery();
                        
                        boolean bookFound = false;
                        
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
                            bookFound = true;
                            System.out.printf(displayFormatBooks, "Book Title", "Year Published", "# of Pages",
                                    "Publisher Name", "Publisher Address", "Publisher Phone #", "Publisher Email",
                                    "Group Name", "Head Writer", "Year Formed", "Subject");
                            System.out.printf(displayFormatBooks, displayNull(bookTitle), displayNull(yearPublished), displayNull(numberPages),
                                    displayNull(publisherName), displayNull(publisherAddress), displayNull(publisherPhone), displayNull(publisherEmail),
                                    displayNull(groupName), displayNull(headWriter), displayNull(yearFormed), displayNull(subject));
                        }
                        if (!bookFound)
                        {
                            if (bName.equals("N/A"))
                                System.err.println("-NO INPUT GIVEN-");
                            else
                                System.err.println("Book Title '" + bName + "' was not found in the database.");
                        }
                        System.out.println("\n-----------------------------------------------------------------------------------------------------------------------"
                                + "------------------------------------------------------------------------------------------------------------------------------------");
                        break;
                    case 7: // Insert a new book.
                        String addBookStatement = "insert into books(groupName, bookTitle, publisherName, yearPublished, numberPages) values (?, ?, ?, ?, ?)";
                        PreparedStatement preparedAddBookStatement = connection.prepareStatement(addBookStatement);

                        System.out.print("To insert a new book into the database, we're going to need some information."
                                + "\nWhat is the name of the writing group for the book? ");
                        String addGroupName = displayNull(getStringInput(in));
                        System.out.print("\nNext, what is the title of the book? ");
                        String addBookTitle = displayNull(getStringInput(in));
                        System.out.print("\nWhat is the name of the publisher for the book? ");
                        String addPublisherName = displayNull(getStringInput(in));
                        System.out.print("\nWhat is the publication year for the book? ");
                        int addYearPublished = getIntInput(in);
                        System.out.print("\nHow many pages does the book have. ");
                        int addNumberPages = getIntInput(in);

                        preparedAddBookStatement.setString(1, addGroupName);
                        preparedAddBookStatement.setString(2, addBookTitle);
                        preparedAddBookStatement.setString(3, addPublisherName);
                        preparedAddBookStatement.setInt(4, addYearPublished);
                        preparedAddBookStatement.setInt(5, addNumberPages);

                        preparedAddBookStatement.executeUpdate();

                        System.out.println("\n-----------------------------------------------------------------------------------------------------------------------"
                                + "------------------------------------------------------------------------------------------------------------------------------------");
                        break;
                    case 8: // TODO: Insert a new publisher and update all books published by one publisher to be published by the new publisher.
                        
                        // Insert New Publisher
                        String addPublisherStatement = "insert into publishers(publisherName, publisherAddress, publisherPhone, publisherEmail) "
                                + "values (?, ?, ?, ?)";
                        PreparedStatement preparedAddPublisherStatement = connection.prepareStatement(addPublisherStatement);
                        
                        System.out.print("To insert a new publisher into the database, we're going to need some information."
                                + "\nWhat is the name of the publisher? ");
                        String addPublisherNameCase8 = displayNull(getStringInput(in));
                        System.out.print("\nNext, what is the address of the publisher? ");
                        String addPublisherAddress = displayNull(getStringInput(in));
                        System.out.print("\nWhat is the phone number of the publisher? ");
                        String addPublisherPhone = displayNull(getStringInput(in));
                        System.out.print("\nFinally, what is the publisher's email? ");
                        String addPublisherEmail = displayNull(getStringInput(in));
                        
                        preparedAddPublisherStatement.setString(1, addPublisherNameCase8);
                        preparedAddPublisherStatement.setString(2, addPublisherAddress);
                        preparedAddPublisherStatement.setString(3, addPublisherPhone);
                        preparedAddPublisherStatement.setString(4, addPublisherEmail);
                        
                        preparedAddPublisherStatement.executeUpdate();
                        // Finish Insert New Publisher
                        
                        // Update all books published by one publisher to be published by the new publisher.
                        String updateBooksPublisher = "UPDATE books SET publisherName = addPublisherNameCase8 WHERE publisherName = ?";
                        PreparedStatement preparedUpdateBooksPublisherStatement = connection.prepareStatement(updateBooksPublisher);
                        
                        System.out.print("Books published by which publisher are to be updated? ");
                        String updateBooksWithThisPublisher = displayNull(getStringInput(in));
                        
                        preparedUpdateBooksPublisherStatement.setString(1,updateBooksWithThisPublisher);
                        
                        preparedUpdateBooksPublisherStatement.executeUpdate();
                        // Finish updating
                        
                        break;
                    case 9: // Remove a book specified by the user.
                        System.out.print("\nWhich book would you like to remove?");
                        String removeBookStatement = "DELETE FROM books WHERE bookTitle = ?";
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
