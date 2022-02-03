package dev.crawford.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dev.crawford.models.Reimbursement;
import dev.crawford.models.Status;
import dev.crawford.models.User;
import dev.crawford.util.ConnectionFactory;

public class ReimbursementDAO {

    static ConnectionFactory cu = ConnectionFactory.getInstance();

    /**
     * Should retrieve a Reimbursement from the DB with the corresponding id or an empty optional if there is no match.
     */
    public static Reimbursement getById(int id) {
        String sql = "select * from reimbursement_requests where id = ?";
        try (Connection conn = cu.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Reimbursement r = new Reimbursement(
                        rs.getInt("id"),
                        Status.valueOf(rs.getString("status")),
                        (rs.getString("author") == null) ? null : UserDAO.getByUsername(rs.getString("author")),
                        (rs.getString("resolver") == null) ? null : UserDAO.getByUsername(rs.getString("resolver")),
                        rs.getDouble("cost"),
                        rs.getString("date"),
                        rs.getString("time"),
                        rs.getString("location"),
                        rs.getString("description"),
                        rs.getString("justification"),
                        rs.getDouble("course_type"),
                        rs.getString("grade")
                );
                return r;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Should retrieve a List of Reimbursements from the DB with the corresponding Status or an empty List if there are no matches.
     */
    public List<Reimbursement> getByStatus(Status status) {
        String sql = "select * from reimbursement_requests where status = ?";
        try (Connection conn = cu.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, status.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Reimbursement r = new Reimbursement(
                        rs.getInt("id"),
                        Status.valueOf(rs.getString("status")),
                        (rs.getString("author") == null) ? null : UserDAO.getByUsername(rs.getString("author")),
                        (rs.getString("resolver") == null) ? null : UserDAO.getByUsername(rs.getString("resolver")),
                        rs.getDouble("cost"),
                        rs.getString("date"),
                        rs.getString("time"),
                        rs.getString("location"),
                        rs.getString("description"),
                        rs.getString("justification"),
                        rs.getDouble("course_type"),
                        rs.getString("grade")
                );
                return Collections.singletonList(r);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    /**
     * <ul>
     *     <li>Should Update an existing Reimbursement record in the DB with the provided information.</li>
     *     <li>Should throw an exception if the update is unsuccessful.</li>
     *     <li>Should return a Reimbursement object with updated information.</li>
     * </ul>
     */
    public static Reimbursement create(Reimbursement unprocessedReimbursement) {
    	String sql = "insert into reimbursement_requests (id, status, author, resolver, cost, date, time, location, description, justification, course_type) values (default, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try(Connection conn = cu.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, unprocessedReimbursement.getStatus().toString());
            ps.setString(2, unprocessedReimbursement.getAuthor().getUsername());
            ps.setString(3, unprocessedReimbursement.getResolver().getUsername());
            ps.setString(4, unprocessedReimbursement.getAmount().toString());
            ps.setString(5, unprocessedReimbursement.getDate());
            ps.setString(6, unprocessedReimbursement.getTime());
            ps.setString(7, unprocessedReimbursement.getLocation());
            ps.setString(8, unprocessedReimbursement.getDescription());
            ps.setString(9, unprocessedReimbursement.getJustification());
            ps.setString(10, unprocessedReimbursement.getCourseType().toString());

            ps.executeUpdate();
            return unprocessedReimbursement;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return unprocessedReimbursement;
    }

    public static Reimbursement userUpdate(Reimbursement unprocessedReimbursement) {
    	String sql = "update reimbursement_requests SET grade = ? where id = ?";
        try(Connection conn = cu.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, unprocessedReimbursement.getGrade());
            ps.setInt(2, unprocessedReimbursement.getId());

            ps.executeUpdate();
            return unprocessedReimbursement;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return unprocessedReimbursement;
    }

    public static Reimbursement update(Reimbursement unprocessedReimbursement) {
    	String sql = "update reimbursement_requests SET status = ?, resolver = ?, cost = ? where id = ?";
        try(Connection conn = cu.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, unprocessedReimbursement.getStatus().toString());
            ps.setString(2, unprocessedReimbursement.getResolver().getUsername());
            ps.setString(3, unprocessedReimbursement.getAmount().toString());
            ps.setInt(4, unprocessedReimbursement.getId());

            ps.executeUpdate();
            return unprocessedReimbursement;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return unprocessedReimbursement;
    }

    public static void updateAllowance(Reimbursement newAllowance, double costPercentage) {

        String sql = "update users set reimbursement_allowed = ? where id = ?";

        try(Connection conn = cu.getConnection()) {

            User author = UserDAO.getByUsername(newAllowance.getAuthor().getUsername());

            int newAllowanceInt = (int) (getAllowance(author) - Math.round(newAllowance.getAmount() * costPercentage));

            if (getAllowance(author) < newAllowance.getAmount()) {
                newAllowanceInt = 0;
            }

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setDouble(1, newAllowanceInt);
            ps.setInt(2, author.getId());
            ps.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

    public static void updateAllowance(double cost, User author, Reimbursement oldReimbursement) {

        String sql = "update users set reimbursement_allowed = ? where id = ?";

        try(Connection conn = cu.getConnection()) {

            int newAllowanceInt = (int) (getAllowance(author) + Math.round(cost * oldReimbursement.getCourseType()));

            if (newAllowanceInt > 1000) {
                newAllowanceInt = 1000;
            }

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setDouble(1, newAllowanceInt);
            ps.setInt(2, author.getId());
            ps.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

    public static int getAllowance(User author) {
        String sql = "select * from users where id = ?";
        try(Connection conn = cu.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, author.getId());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("reimbursement_allowed");
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static List<Reimbursement> getByUser(String author) {
        List<Reimbursement> reimbursements = new ArrayList<>();
        String sql = "select * from reimbursement_requests where author = ?";
        try(Connection conn = cu.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, author);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Reimbursement r = new Reimbursement(
                        rs.getInt("id"),
                        Status.valueOf(rs.getString("status")),
                        (rs.getString("author") == null) ? null : UserDAO.getByUsername(rs.getString("author")),
                        (rs.getString("resolver") == null) ? null : UserDAO.getByUsername(rs.getString("resolver")),
                        rs.getDouble("cost"),
                        rs.getString("date"),
                        rs.getString("time"),
                        rs.getString("location"),
                        rs.getString("description"),
                        rs.getString("justification"),
                        rs.getDouble("course_type"),
                        rs.getString("grade")
                );
                reimbursements.add(r);
            }
            return reimbursements;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public static List<Reimbursement> getAll() {
        List<Reimbursement> reimbursements = new ArrayList<>();
        String sql = "select * from reimbursement_requests";
        try(Connection conn = cu.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Reimbursement r = new Reimbursement(
                        rs.getInt("id"),
                        Status.valueOf(rs.getString("status")),
                        (rs.getString("author") == null) ? null : UserDAO.getByUsername(rs.getString("author")),
                        (rs.getString("resolver") == null) ? null : UserDAO.getByUsername(rs.getString("resolver")),
                        rs.getDouble("cost"),
                        rs.getString("date"),
                        rs.getString("time"),
                        rs.getString("location"),
                        rs.getString("description"),
                        rs.getString("justification"),
                        rs.getDouble("course_type"),
                        rs.getString("grade")
                );
                reimbursements.add(r);
            }
            return reimbursements;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
}
