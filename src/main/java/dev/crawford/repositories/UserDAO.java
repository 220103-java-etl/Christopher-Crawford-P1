package dev.crawford.repositories;

import dev.crawford.models.User;
import dev.crawford.util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    static ConnectionFactory cu = ConnectionFactory.getInstance();
    /**
     * Should retrieve a User from the DB with the corresponding username or an empty optional if there is no match.
     */
    public static User getByUsername(String username) {
        String sql = "select * from users where username = ?";
        try (Connection conn = cu.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User u = new User(
                        rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("username"),
                        rs.getString("passwrd"),
                        rs.getString("role")
                );
                return u;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "select * from users";
        try(Connection conn = cu.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                User u = new User(
                        rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("username"),
                        rs.getString("passwrd"),
                        rs.getString("role")
                );
                users.add(u);
            }
            return users;
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * <ul>
     *     <li>Should Insert a new User record into the DB with the provided information.</li>
     *     <li>Should throw an exception if the creation is unsuccessful.</li>
     *     <li>Should return a User object with an updated ID.</li>
     * </ul>
     *
     * Note: The userToBeRegistered will have an id=0, and username and password will not be null.
     * Additional fields may be null.
     */
    public static User create(User userToBeRegistered) {
        String sql = "insert into users (id, first_name, last_name, username, passwrd, role) values (default, ?, ?, ?, ?, ?)";
        try(Connection conn = cu.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, userToBeRegistered.getFirstName());
            ps.setString(2, userToBeRegistered.getLastName());
            ps.setString(3, userToBeRegistered.getUsername());
            ps.setString(4, userToBeRegistered.getPassword());
            ps.setString(5, userToBeRegistered.getRole());
            ps.executeUpdate();
            return userToBeRegistered;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return userToBeRegistered;
    }
}
