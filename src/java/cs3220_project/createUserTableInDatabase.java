package cs3220_project;

import globals.globalVariables;
import globals.methods;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class createUserTableInDatabase extends HttpServlet {

    public void init() {

        String HOST = globalVariables.getHOST();
        String NAME_OF_DATABASE = globalVariables.getNAME_OF_DATABASE();
        String USERNAME = globalVariables.getNAME_OF_DATABASE();
        String PASSWORD = globalVariables.getPASSWORD();
        String TABLE_NAME = globalVariables.getTABLE_NAME();

        //This file simply checks if a simpsons_characters table exists in the database, and if not creates and fills it with simpson characters
        Connection c = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            c = DriverManager.getConnection("jdbc:mysql://" + HOST + "/" + NAME_OF_DATABASE, USERNAME, PASSWORD);
            c.setAutoCommit(true);
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(" SELECT * FROM information_schema.tables WHERE table_schema = '" + NAME_OF_DATABASE + "' AND table_name = '" + TABLE_NAME + "' LIMIT 1;");
            if (!rs.next()) {
                //If the table doesn't exist
                //Create the table
                String sql = "CREATE TABLE " + TABLE_NAME
                        + "(EMAIL             VARCHAR(100)  NOT NULL PRIMARY KEY,"
                        + " USERNAME           VARCHAR(50) NOT NULL UNIQUE,"
                        + " PASSWORD           VARBINARY(500) NOT NULL,"
                        + " IS_ADMIN           BOOLEAN NOT NULL DEFAULT FALSE,"
                        + " LOGGED_IN_COOKIE   VARCHAR(200) NOT NULL)";
                stmt.executeUpdate(sql);

                sql = "CREATE TABLE POI"
                        + "(ID                  INT(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,"
                        + " CREATOR             VARCHAR(100)  NOT NULL,"
                        + " DATE_OF_CREATION           TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,"
                        + " TITLE           VARCHAR(100) NOT NULL,"
                        + " TYPE           VARCHAR(100) NOT NULL,"
                        + " LONGITUDE           DECIMAL(9,5) NOT NULL,"
                        + " LATITUDE           DECIMAL(7,5) NOT NULL,"
                        + " DESCRIPTION   VARCHAR(500) NOT NULL)";
                stmt.executeUpdate(sql);

                String password = methods.hash_SHA_256(globalVariables.getSALT() + "a");
                sql = "INSERT INTO " + TABLE_NAME + " (EMAIL, USERNAME, PASSWORD, IS_ADMIN, LOGGED_IN_COOKIE) "
                        + "VALUES (?, ?, ?, ?, ?);";
                PreparedStatement pstmt = c.prepareStatement(sql);
                pstmt.setString(1, "a@a.com");
                pstmt.setString(2, "Victor");
                pstmt.setString(3, password);
                pstmt.setBoolean(4, true);
                pstmt.setString(5, "Works");

                pstmt.executeUpdate();
                methods.insertIntoPOITable(c, new String[]{"a@a.com", "FCU ATM", "ATM", "-118.168871", "34.063440", "Cal State FCU ATM near South Bus Stop. It's free to be used most of the time"});
                methods.insertIntoPOITable(c, new String[]{"a@a.com", "Wells Fargo ATM", "ATM", "-118.167124", "34.067337", "Wells Fargo ATM near library entrance. It's mostly in use"});
                methods.insertIntoPOITable(c, new String[]{"a@a.com", "Food Court", "Food_Drink", "-118.168771", "34.067617", "This is the school's food court. You can get everything here. From healthy to eat today, die tomorrow unhealthy"});
                methods.insertIntoPOITable(c, new String[]{"a@a.com", "Juice It Up", "Food_Drink", "-118.168294", "34.067657", "You can get Juices, and the like here. No food though"});
                methods.insertIntoPOITable(c, new String[]{"a@a.com", "Computer Science Restroom", "Restroom", "-118.166368", "34.066525", "Ths restroom is on the ground floor of the building. It's at the corner connecting the computer science building to the engineering building. It's also wheelchair accessible"});
                methods.insertIntoPOITable(c, new String[]{"a@a.com", "Computer Science Restroom", "Restroom", "-118.166593", "34.066407", "Ths restroom is on the first floor of the building."});
                methods.insertIntoPOITable(c, new String[]{"a@a.com", "MLK Restroom", "Restroom", "-118.165867", "34.067609", "This restroom is on the 3rd floor (4000 level) of the Martin Luther King building. It's wheelchair accessible, spacious and usually clean."});
                methods.insertIntoPOITable(c, new String[]{"a@a.com", "Library Restroom", "Restroom", "-118.167336", "34.067559", "This restroom is on the first floor of the library. It's wheelchair accessible and clean but very small."});
                methods.insertIntoPOITable(c, new String[]{"a@a.com", "Near Food Court Restroom", "Restroom", "-118.168735", "34.067261", "This restroom is on the ground floor of the food court building. Just a little to the left. It's very close to the elevators."});
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (c != null) {
                    c.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
