package globals;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.servlet.http.Cookie;
import models.POI;
import models.User;

public class methods {

    /**
     * This method checks if a user is logged into the application.
     *
     * @param cookies All the cookies received in the current request
     * @return String - The email address of the logged in user or an empty
     * string if no one is logged in or the string 'exception' if an exception
     * occurs
     */
    public static String isLoggedIn(Cookie[] cookies) {
        //Check if there is a loggedIn cookie
        boolean loggedInCookieFound = false;
        String cookieValue = "";
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("loggedIn")) {
                    cookieValue = cookie.getValue();
                    loggedInCookieFound = true;
                }
            }
        }
        //If there isn't a loggedIn cookie
        if (!loggedInCookieFound) {
            //return an empty string
            return "";
        } else {  //If there is a loggedIn cookie
            //check the database for the row that has that loggedin value

            String HOST = globalVariables.getHOST();
            String NAME_OF_DATABASE = globalVariables.getNAME_OF_DATABASE();
            String USERNAME = globalVariables.getNAME_OF_DATABASE();
            String PASSWORD = globalVariables.getPASSWORD();
            String TABLE_NAME = globalVariables.getTABLE_NAME();

            Connection c = null;
            try {
                Class.forName("com.mysql.jdbc.Driver");
                c = DriverManager.getConnection("jdbc:mysql://" + HOST + "/" + NAME_OF_DATABASE, USERNAME, PASSWORD);
                c.setAutoCommit(true);

                String sql = " SELECT EMAIL FROM " + TABLE_NAME + " WHERE LOGGED_IN_COOKIE=? LIMIT 1";
                PreparedStatement stmt = c.prepareStatement(sql);

                //Bind values into the parameters.
                stmt.setString(1, cookieValue);
                ResultSet rs = stmt.executeQuery();
                if (!rs.next()) {
                    //If there is no such row, return an empty string
                    return "";
                } else {
                    //If there is such a row, return the email of that row
                    return rs.getString("EMAIL");
                }
            } catch (Exception e) {
                return "exception";
            } finally {
                try {
                    if (c != null) {
                        c.close();
                    }
                } catch (Exception e) {
                    return "exception";
                }
            }
        }
    }

    /**
     * This ensures that the input of the user is valid
     *
     * @param referer A string containing the referring page
     * @param expectedRefererName A string containing the expected referring
     * page
     * @param parametersToBeValidated An array of Strings containing the values
     * of the parameters we are expecting to validate
     * @return True if everything checks out. False if there is an error
     */
    public static boolean paramValidator(String referer, String expectedRefererName, String[] parametersToBeValidated) {
        //Check if the user is coming from the registration page
        //If not, return false
        /*if (referer == null) {
            return false;
        }*/
        if (!referer.contains(expectedRefererName)) {
            return false;
        } //If so, check if all the parameters sent are valid
        else {
            //Iterate through the arrayList, and make sure that no parameter is null or empty            
            for (String parameter : parametersToBeValidated) {
                //If a parameter is bad, return false
                if (parameter == null || parameter.trim().length() == 0) {
                    return false;
                }
            }
            //If all parameters are good, return true
            return true;
        }
    }

    /**
     * This method gets the SHA-256 hash of a string
     *
     * @param toBeHashed The string you want to hash
     * @return String - Returns the hashed form of the input string or the
     * string 'exception' if there is an exception
     */
    public static String hash_SHA_256(String toBeHashed) {
        try {
            MessageDigest digest;
            digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digest.digest(toBeHashed.getBytes(StandardCharsets.UTF_8));
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < encodedhash.length; i++) {
                String hex = Integer.toHexString(0xff & encodedhash[i]);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException ex) {
            return "exception";
        }
    }

    /**
     * This method checks if the logged in user is an admin
     *
     * @param email The email address of the user who we wish to check for admin
     * rights
     * @return boolean - True if the user is an admin, false, if not
     */
    public static boolean is_user_an_admin(String email) {
        String HOST = globalVariables.getHOST();
        String NAME_OF_DATABASE = globalVariables.getNAME_OF_DATABASE();
        String USERNAME = globalVariables.getNAME_OF_DATABASE();
        String PASSWORD = globalVariables.getPASSWORD();
        String TABLE_NAME = globalVariables.getTABLE_NAME();

        Connection c = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            c = DriverManager.getConnection("jdbc:mysql://" + HOST + "/" + NAME_OF_DATABASE, USERNAME, PASSWORD);
            c.setAutoCommit(true);

            String sql = " SELECT EMAIL FROM " + TABLE_NAME + " WHERE EMAIL=? AND IS_ADMIN=? LIMIT 1";
            PreparedStatement stmt = c.prepareStatement(sql);

            //Bind values into the parameters.
            stmt.setString(1, email);
            stmt.setBoolean(2, true);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (Exception e) {
            return false;
        } finally {
            try {
                if (c != null) {
                    c.close();
                }
            } catch (Exception e) {
                return false;
            }
        }
    }

    /**
     * This method inserts a POI into the POI table
     *
     * @param c An already established connection to the database
     * @param values A string array of 6 values to be placed in the table in the
     * order: creator, title, type, longitude, latitude, description
     */
    public static void insertIntoPOITable(Connection c, String[] values) {
        try {
            String sql = "INSERT INTO POI (CREATOR, TITLE, TYPE, LONGITUDE, LATITUDE, DESCRIPTION) "
                    + "VALUES (?, ?, ?, ?, ?, ?);";
            PreparedStatement pstmt = c.prepareStatement(sql);

            pstmt.setString(1, values[0]);
            pstmt.setString(2, values[1]);
            pstmt.setString(3, values[2]);
            pstmt.setDouble(4, Double.parseDouble(values[3]));
            pstmt.setDouble(5, Double.parseDouble(values[4]));
            pstmt.setString(6, values[5]);

            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String convertArrayListOfUsersToString(ArrayList<User> users) {
        if (users.isEmpty()) {
            return "";
        }
        int i = 0;
        String result = "<div class='row'>";
        for (User user : users) {
            result += "<div class='col-md-3 well'>"
                    + "     <table class='table table-bordered table-striped table-hover'>"
                    + "         <tr>"
                    + "             <td>Email</td>"
                    + "             <td>" + user.getEmail() + "</td>"
                    + "         </tr>"
                    + "         <tr>"
                    + "             <td>Username</td>"
                    + "             <td>" + user.getUsername() + "</td>"
                    + "         </tr>"
                    + "         <tr>"
                    + "             <td>Actions</td>"
                    + "             <td><a href='delete_user?user=" + user.getEmail() + "'><button type='button' class='btn btn-danger'>Delete</button></a></td>"
                    + "         </tr>"
                    + "     </table>"
                    + "</div>"
                    + "<div class='col-md-1'></div>";
            if (i != 0 && (i + 1) % 3 == 0) {
                result += "</div>"
                        + "<div class='row'>";
            }
            i++;
        }
        result += "</div>";
        return result;
    }

    public static String convertArrayListOfPOIsToString(ArrayList<POI> pois) {
        if (pois.isEmpty()) {
            return "";
        }
        int i = 0;
        String result = "<div class='row'>";
        for (POI poi : pois) {
            result += "<div class='col-md-3 well'>"
                    + "     <table class='table table-bordered table-striped table-hover'>"
                    + "         <tr>"
                    + "             <td>Contributor</td>"
                    + "             <td>" + poi.getContributor() + "</td>"
                    + "         </tr>"
                    + "         <tr>"
                    + "             <td>Time of creation</td>"
                    + "             <td>" + poi.getTOC() + "</td>"
                    + "         </tr>"
                    + "         <tr>"
                    + "             <td>Title</td>"
                    + "             <td>" + poi.getTitle() + "</td>"
                    + "         </tr>"
                    + "         <tr>"
                    + "             <td>Type</td>"
                    + "             <td>" + poi.getType() + "</td>"
                    + "         </tr>"
                    + "         <tr>"
                    + "             <td>Description</td>"
                    + "             <td>" + poi.getDesc() + "</td>"
                    + "         </tr>"
                    + "         <tr>"
                    + "             <td>Longitude</td>"
                    + "             <td>" + poi.getLon() + "</td>"
                    + "         </tr>"
                    + "         <tr>"
                    + "             <td>Latitude</td>"
                    + "             <td>" + poi.getLat() + "</td>"
                    + "         </tr>"
                    + "     </table>"
                    + "     <a href='edit_poi?POI=" + poi.getId() + "'><button type='button' class='btn btn-default'>Edit POI</button></a> <a href='delete_poi?POI=" + poi.getId() + "'><button type='button' class='btn btn-danger'>Delete POI</button></a>"
                    + "</div>"
                    + "<div class='col-md-1'></div>";
            if (i != 0 && (i + 1) % 3 == 0) {
                result += "</div>"
                        + "<div class='row'>";
            }
            i++;
        }
        result += "</div>";
        return result;
    }
}
