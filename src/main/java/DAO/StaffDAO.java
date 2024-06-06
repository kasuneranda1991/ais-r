/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Models.Administration;
import Models.Management;
import Models.Staff;
import Models.User;
import Utils.DbConnectionManager;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.sql.Statement;
import java.sql.ResultSet;

/**
 *
 * @author kinkinigamage
 */
public class StaffDAO {

    public StaffDAO() {
    }

    public boolean insertStaff(User user) {
        String sql = "INSERT INTO Staff (firstName, lastName, address, phone, email, username, password, role, branch, management_lvl, employment_type, type) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Connection jdbcConnection = DbConnectionManager.shared().getConnection();

        try {
            PreparedStatement statement = jdbcConnection.prepareStatement(sql);
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getAddress());
            statement.setInt(4, user.getPhone());
            statement.setString(5, user.getEmail());
            statement.setString(6, user.getUsername());
            statement.setString(7, user.getPassword());
            statement.setString(8, user.getRole());
            statement.setString(9, user.getBranch());

            if (user instanceof Management) {
                Management managementStaff = (Management) user;
                statement.setString(10, managementStaff.getManagement_lvl());
                statement.setNull(11, Types.VARCHAR);
                statement.setString(12, "ManagementStaff");
            } else if (user instanceof Administration) {
                Administration administrationStaff = (Administration) user;
                statement.setNull(10, Types.VARCHAR);
                statement.setString(11, administrationStaff.getEmployment_type());
                statement.setString(12, "AdministrationStaff");
            }
            
            boolean rowInserted = statement.executeUpdate() > 0;
            statement.close();
            return rowInserted;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public ArrayList<User> listAllStaff() throws SQLException {
        ArrayList<User> listUsers = new ArrayList<>();
        Connection jdbcConnection = DbConnectionManager.shared().getConnection();

        String sql = "SELECT * FROM Staff";

        Statement statement = jdbcConnection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        
        while (resultSet.next()) {
            int staffID = resultSet.getInt("userID");
            String firstName = resultSet.getString("firstName");
            String lastName = resultSet.getString("lastName");
            String address = resultSet.getString("address");
            int phone = resultSet.getInt("phone");
            String email = resultSet.getString("email");
            String username = resultSet.getString("username");
            String password = resultSet.getString("password");
            String role = resultSet.getString("role");
            String branch = resultSet.getString("branch");
            String management_lvl = resultSet.getString("management_lvl");
            String employment_type = resultSet.getString("employment_type");
            String type = resultSet.getString("type");

            User user;
            if ("ManagementStaff".equals(type)) {
                user = new Management(firstName, lastName, address, phone, email, username, password, management_lvl, branch);
            } else if ("AdministrationStaff".equals(type)) {
                user = new Administration(firstName, lastName, address, phone, email, username, password, employment_type);
            } else {
                user = new User(firstName, lastName, address, email, phone, username, password, role);
            }

            listUsers.add(user);
        }

        resultSet.close();
        statement.close();
        return listUsers;
    }
    
    public static ArrayList<User> listAllStaffForReport() throws SQLException {
        ArrayList<User> listUsers = new ArrayList<>();
        Connection jdbcConnection = DbConnectionManager.shared().getConnection();

        String sql = "SELECT * FROM Staff;";

        Statement statement = jdbcConnection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        
        while (resultSet.next()) {
            int staffID = resultSet.getInt("userID");
            String firstName = resultSet.getString("firstName");
            String lastName = resultSet.getString("lastName");
            String address = resultSet.getString("address");
            int phone = resultSet.getInt("phone");
            String email = resultSet.getString("email");
            String username = resultSet.getString("username");
            String password = resultSet.getString("password");
            String role = resultSet.getString("role");
            String branch = resultSet.getString("branch");
            String management_lvl = resultSet.getString("management_lvl");
            String employment_type = resultSet.getString("employment_type");
            String type = resultSet.getString("type");

            User user;
            if ("ManagementStaff".equals(type)) {
                user = new Management(firstName, lastName, address, phone, email, username, password, management_lvl, branch);
            } else if ("AdministrationStaff".equals(type)) {
                user = new Administration(firstName, lastName, address, phone, email, username, password, employment_type);
            } else {
                user = new User(firstName, lastName, address, email, phone, username, password, role);
            }

            listUsers.add(user);
        }

        resultSet.close();
        statement.close();
        return listUsers;
    }

    public User login(String username, String password) {
        String sql = "SELECT * FROM Staff WHERE username = ? AND password = ?";
        Connection jdbcConnection = DbConnectionManager.shared().getConnection();
        try {
            PreparedStatement statement = jdbcConnection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();
            Staff staff = null;

            if (resultSet.next()) {
                int staffID = resultSet.getInt("userID");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String address = resultSet.getString("address");
                int phone = resultSet.getInt("phone");
                String email = resultSet.getString("email");
                String fetchedUsername = resultSet.getString("username");
                String fetchedPassword = resultSet.getString("password");
                String role = resultSet.getString("role");
                String branch = resultSet.getString("branch");
                String management_lvl = resultSet.getString("management_lvl");
                String employment_type = resultSet.getString("employment_type");
                String type = resultSet.getString("type");

                if ("ManagementStaff".equals(type)) {
                    staff = new Management(firstName, lastName, address, phone, email, fetchedUsername, fetchedPassword, management_lvl, branch);
                } else if ("AdministrationStaff".equals(type)) {
                    staff = new Administration(firstName, lastName, address, phone, email, fetchedUsername, fetchedPassword, employment_type);
                }
            }

            resultSet.close();
            statement.close();
            return staff;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }
    
    public User getStaffById(int id) {
        String sql = "SELECT * FROM Staff WHERE userID = ?";
        Connection jdbcConnection = DbConnectionManager.shared().getConnection();
        try {
            PreparedStatement statement = jdbcConnection.prepareStatement(sql);
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();
            Staff staff = null;

            if (resultSet.next()) {
                int staffID = resultSet.getInt("userID");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String address = resultSet.getString("address");
                int phone = resultSet.getInt("phone");
                String email = resultSet.getString("email");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String role = resultSet.getString("role");
                String branch = resultSet.getString("branch");
                String management_lvl = resultSet.getString("management_lvl");
                String employment_type = resultSet.getString("employment_type");
                String type = resultSet.getString("type");

                if ("ManagementStaff".equals(type)) {
                    staff = new Management(firstName, lastName, address, phone, email, username, password, management_lvl, branch);
                } else if ("AdministrationStaff".equals(type)) {
                    staff = new Administration(firstName, lastName, address, phone, email, username, password, employment_type);
                }
            }

            resultSet.close();
            statement.close();
            return staff;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public boolean updateStaff(User user) {
        String sql = "UPDATE Staff SET firstName = ?, lastName = ?, address = ?, phone = ?, email = ?, username = ?, password = ?, role = ?, branch = ?, management_lvl = ?, employment_type = ?, type = ? WHERE userID = ?";
        Connection jdbcConnection = DbConnectionManager.shared().getConnection();

        try {
            PreparedStatement statement = jdbcConnection.prepareStatement(sql);
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getAddress());
            statement.setInt(4, user.getPhone());
            statement.setString(5, user.getEmail());
            statement.setString(6, user.getUsername());
            statement.setString(7, user.getPassword());
            statement.setString(8, user.getRole());
            statement.setString(9, user.getBranch());

            if (user instanceof Management) {
                Management managementStaff = (Management) user;
                statement.setString(10, managementStaff.getManagement_lvl());
                statement.setNull(11, Types.VARCHAR);
                statement.setString(12, "ManagementStaff");
            } else if (user instanceof Administration) {
                Administration administrationStaff = (Administration) user;
                statement.setNull(10, Types.VARCHAR);
                statement.setString(11, administrationStaff.getEmployment_type());
                statement.setString(12, "AdministrationStaff");
            }

            statement.setString(13, user.getId()); // Assuming User has a getId method

            boolean rowUpdated = statement.executeUpdate() > 0;
            statement.close();
            return rowUpdated;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public boolean deleteStaff(int id) {
        String sql = "DELETE FROM Staff WHERE userID = ?";
        Connection jdbcConnection = DbConnectionManager.shared().getConnection();

        try {
            PreparedStatement statement = jdbcConnection.prepareStatement(sql);
            statement.setInt(1, id);

            boolean rowDeleted = statement.executeUpdate() > 0;
            statement.close();
            return rowDeleted;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }
}
