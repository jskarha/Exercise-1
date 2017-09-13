package edu.matc.persistence;

import edu.matc.entity.User;
import org.apache.log4j.*;
import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;
import java.util.*;

import com.google.gson.Gson;
import java.time.*;

/**
 * Access users in the user table.
 *
 * @author pwaite, jskarha
 */
public class UserData {

    private final Logger logger = Logger.getLogger(this.getClass());

    Map<String, String> stolenMap = new Gson().fromJson("{'lastName':'last_name'," +
                                                              "'firstName':'first_name'," +
                                                              "'personAge':'age'," +
                                                              "'userId':'id'}", HashMap.class);

    public List<User> getAllUsers() {
        String sql = "SELECT * FROM users";

        return executeSelect(sql);
    }

    public List<User> search(String queryString) {
        List<User> users = new ArrayList<User>();
        Database database = Database.getInstance();
        Connection connection = null;
        String sql = "SELECT * FROM users WHERE ";
        String[] queryArray = queryString.split("&");
        Map<String, String> searchTerms = new HashMap<String, String>();

        for (String s : queryArray) {
            String[] kp = s.split("=");
            if (kp.length > 1) {
                searchTerms.put(stolenMap.get(kp[0]), kp[1]);
            }
        }

        if (searchTerms.get("age") != null) {
            int age = Integer.parseInt(searchTerms.get("age"));

            LocalDate endBirthDate = LocalDate.now().minusYears(age);
            LocalDate startBirthDate = endBirthDate.minusYears(1).plusDays(1);
            searchTerms.remove("age");
            convertTermsToQuery(searchTerms);
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            searchTerms.put("date_of_birth", " BETWEEN CAST('"+ startBirthDate.format(format)
                    + "' AS date) AND CAST('" + endBirthDate.format(format) + "' AS date)");
        } else {
            convertTermsToQuery(searchTerms);
        }

        List<String> searchList = new ArrayList<String>();

        for(Map.Entry<String, String> e : searchTerms.entrySet()) {
            searchList.add(e.getKey() + e.getValue());
        }

        sql += String.join(" OR ", searchList);

        return executeSelect(sql);
    }

    private void convertTermsToQuery(Map<String, String> map) {
        for (Map.Entry<String, String> e : map.entrySet()) {
            map.put(e.getKey(), " LIKE '%" + e.getValue() + "%' ");
        }
    }

    private List<User> executeSelect(String sql) {
        logger.info("Search sql: " + sql);
        List<User> users = new ArrayList<User>();
        Database database = Database.getInstance();
        Connection connection = null;
        try {
            database.connect();
            connection = database.getConnection();
            Statement selectStatement = connection.createStatement();
            ResultSet results = selectStatement.executeQuery(sql);
            while (results.next()) {
                User employee = createUserFromResults(results);
                users.add(employee);
            }
            database.disconnect();
        } catch (SQLException e) {
            logger.error("SearchUser.executeSelect()...SQL Exception: ", e);
        } catch (Exception e) {
            logger.error("SearchUser.executeSelect()...Exception: ", e);
        }
        return users;
    }

    private User createUserFromResults(ResultSet results) throws SQLException {
        User user = new User(results.getString("first_name"),
                results.getString("last_name"),
                results.getString("id"),
                results.getString("date_of_birth"));
        return user;
    }

}