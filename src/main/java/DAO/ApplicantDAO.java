/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Models.Administration;
import Models.Applicant;
import Models.Management;
import Models.Staff;
import Models.User;
import Utils.DbConnectionManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.sql.Statement;
import java.sql.ResultSet;
import java.time.LocalDate;

/**
 *
 * @author kinkinigamage
 */
public class ApplicantDAO {

    public ApplicantDAO() {
    }

    public boolean insertApplicant(User user) {
        String sql = "INSERT INTO Applicants (firstName, lastName, address, phone, email, username, password, role, branch, interviewDate, createdBy, createdAt, createdBranch, status, edu, department, workingExperience, secondaryDepartments, other, applicantID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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

            if (user instanceof Applicant) {
                Applicant applicant = (Applicant) user;
                statement.setString(10, applicant.getInterviewDate().toString());
                statement.setString(11, applicant.getCreatedBy());
                statement.setString(12, applicant.getCreatedAt());
                statement.setString(13, applicant.getCreatedBranch() != null ? applicant.getCreatedBranch() : "");
                statement.setString(14, applicant.getStatus() != null ? applicant.getStatus() : "");
                statement.setString(15, applicant.getEdu() != null ? applicant.getEdu() : "");
                statement.setString(16, applicant.getDepartment() != null ? applicant.getDepartment() : "");
                statement.setString(17, applicant.getWorkingEx());
                statement.setString(18, applicant.getSecondaryDepartments());
                statement.setString(19, applicant.getOther());
            }
            statement.setString(20, user.getId());
            boolean rowInserted = statement.executeUpdate() > 0;
            statement.close();
            return rowInserted;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public ArrayList<User> listAllApplicants() throws SQLException {
        ArrayList<User> listApplicants = new ArrayList<>();
        Connection jdbcConnection = DbConnectionManager.shared().getConnection();

        String sql = "SELECT * FROM Applicants";

        Statement statement = jdbcConnection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {
            String applicantID = resultSet.getString("applicantID");
            String firstName = resultSet.getString("firstName");
            String lastName = resultSet.getString("lastName");
            String address = resultSet.getString("address");
            int phone = resultSet.getInt("phone");
            String email = resultSet.getString("email");
            String username = resultSet.getString("username");
            String password = resultSet.getString("password");
            String role = resultSet.getString("role");
            String branch = resultSet.getString("branch");

            String interviewDate = resultSet.getString("interviewDate");
            String createdBy = resultSet.getString("createdBy");
            String createdAt = resultSet.getString("createdAt");
            String createdBranch = resultSet.getString("createdBranch");
            String status = resultSet.getString("status");
            String edu = resultSet.getString("edu");
            String department = resultSet.getString("department");
            
            String workingExperience = resultSet.getString("workingExperience");
            String secondaryDepartments = resultSet.getString("secondaryDepartments");
            String other = resultSet.getString("other");

            Applicant applicant = new Applicant(firstName, lastName, address, email, phone, username, password,
                    LocalDate.parse(interviewDate), createdBy, createdAt, createdBranch, status);
                applicant.setDepartment(department);
                applicant.setEdu(edu);
                applicant.setWorkingEx(workingExperience);
                applicant.setSecondaryDepartments(secondaryDepartments);
                applicant.setOther(other);
            applicant.setId(applicantID);
            listApplicants.add(applicant);
        }

        resultSet.close();
        statement.close();
        return listApplicants;
    }

    public static ArrayList<Applicant> listAllApplicantsForReport() throws SQLException {
        ArrayList<Applicant> listApplicants = new ArrayList<>();
        Connection jdbcConnection = DbConnectionManager.shared().getConnection();

        String sql = "SELECT *, count(applicantID) FROM Applicants GROUP BY branch, email ORDER BY lastName DESC;";

        Statement statement = jdbcConnection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {
            String applicantID = resultSet.getString("applicantID");
            String firstName = resultSet.getString("firstName");
            String lastName = resultSet.getString("lastName");
            String address = resultSet.getString("address");
            int phone = resultSet.getInt("phone");
            String email = resultSet.getString("email");
            String username = resultSet.getString("username");
            String password = resultSet.getString("password");
            String role = resultSet.getString("role");
            String branch = resultSet.getString("branch");

            String interviewDate = resultSet.getString("interviewDate");
            String createdBy = resultSet.getString("createdBy");
            String createdAt = resultSet.getString("createdAt");
            String createdBranch = resultSet.getString("createdBranch");
            String status = resultSet.getString("status");
            String edu = resultSet.getString("edu");
            String department = resultSet.getString("department");

            Applicant applicant = new Applicant(firstName, lastName, address, email, phone, username, password,
                    LocalDate.parse(interviewDate), createdBy, createdAt, createdBranch, status);
                    ((Applicant) applicant).setDepartment(department);
                    ((Applicant) applicant).setEdu(edu);
                    ((Applicant) applicant).setBranch(branch);
            applicant.setId(applicantID);
            listApplicants.add(applicant);
        }

        System.out.println(listApplicants);
        resultSet.close();
        statement.close();
        return listApplicants;
    }

    // ObservableList<Recruit> recruitList = FXCollections.observableArrayList();

    public Map<String, Integer> getApplicantCountByDepartment() throws SQLException {
        Map<String, Integer> departmentCounts = new HashMap<>();
        Connection jdbcConnection = DbConnectionManager.shared().getConnection();

        String sql = "SELECT department, COUNT(*) as count FROM Applicants GROUP BY department";

        Statement statement = jdbcConnection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {
            String department = resultSet.getString("department");
            int count = resultSet.getInt("count");
            departmentCounts.put(department, count);
        }

        resultSet.close();
        statement.close();
        return departmentCounts;
    }

    public User login(String username, String password) {
        String sql = "SELECT * FROM Applicants WHERE username = ? AND password = ?";
        Connection jdbcConnection = DbConnectionManager.shared().getConnection();
        try {
            PreparedStatement statement = jdbcConnection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();
            Applicant applicant = null;

            if (resultSet.next()) {
                String applicantID = resultSet.getString("applicantID");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String address = resultSet.getString("address");
                int phone = resultSet.getInt("phone");
                String email = resultSet.getString("email");
                String fetchedUsername = resultSet.getString("username");
                String fetchedPassword = resultSet.getString("password");
                String interviewDate = resultSet.getString("interviewDate");
                String createdBy = resultSet.getString("createdBy");
                String createdAt = resultSet.getString("createdAt");
                String createdBranch = resultSet.getString("createdBranch");
                String status = resultSet.getString("status");

                applicant = new Applicant(firstName, lastName, address, email, phone, fetchedUsername, fetchedPassword,
                        LocalDate.parse(interviewDate), createdBy, createdAt, createdBranch, status);
                applicant.setId(applicantID);

            }

            resultSet.close();
            statement.close();
            return applicant;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public Applicant getApplicantById(int id) {
        String sql = "SELECT * FROM Applicants WHERE applicantID = ?";
        Connection jdbcConnection = DbConnectionManager.shared().getConnection();
        try {
            PreparedStatement statement = jdbcConnection.prepareStatement(sql);
            statement.setString(1, String.valueOf(id));

            ResultSet resultSet = statement.executeQuery();
            Applicant applicant = null;

            if (resultSet.next()) {
                String applicantID = resultSet.getString("applicantID");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String address = resultSet.getString("address");
                int phone = resultSet.getInt("phone");
                String email = resultSet.getString("email");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String role = resultSet.getString("role");
                String branch = resultSet.getString("branch");
                String interviewDate = resultSet.getString("interviewDate");
                String createdBy = resultSet.getString("createdBy");
                String createdAt = resultSet.getString("createdAt");
                String createdBranch = resultSet.getString("createdBranch");
                String status = resultSet.getString("status");
                String edu = resultSet.getString("edu");
                String department = resultSet.getString("department");
                String workingExperience = resultSet.getString("workingExperience");
                String secondaryDepartments = resultSet.getString("secondaryDepartments");
                String other = resultSet.getString("other");
                String oneTimeToken = resultSet.getString("oneTimeToken");

                applicant = new Applicant(firstName, lastName, address, email, phone, username, password,
                        LocalDate.parse(interviewDate), createdBy, createdAt, createdBranch, status);
                applicant.setDepartment(department);
                applicant.setWorkingEx(workingExperience);
                applicant.setSecondaryDepartments(secondaryDepartments);
                applicant.setOther(other);
                applicant.setOneTimeToken(oneTimeToken);
                applicant.setId(applicantID);
            }

            resultSet.close();
            statement.close();
            return applicant;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public boolean updateApplicant(Applicant applicant) {
        String sql = "UPDATE Applicants SET firstName = ?, lastName = ?, address = ?, phone = ?, email = ?, username = ?, password = ?, role = ?, branch = ?, interviewDate = ?, createdBy = ?, createdAt = ?, createdBranch = ?, status = ?, edu = ?, department = ?, workingExperience = ?, secondaryDepartments = ?, other=?, oneTimeToken = ? WHERE applicantID = ?";
//        String sql = "UPDATE Applicants SET firstName = 'csdcsd' WHERE applicantID = '99749b31-250c-4997-a686-4d3bfa65f1fb'";
//        String sql = "UPDATE Applicants SET firstName = ? WHERE applicantID = ?";

        Connection jdbcConnection = DbConnectionManager.shared().getConnection();

        try {
            PreparedStatement statement = jdbcConnection.prepareStatement(sql);
            statement.setString(1, applicant.getFirstName());
            statement.setString(2, applicant.getLastName());
            statement.setString(3, applicant.getAddress());
            statement.setInt(4, applicant.getPhone());
            statement.setString(5, applicant.getEmail());
            statement.setString(6, applicant.getUsername());
            statement.setString(7, applicant.getPassword());
            statement.setString(8, applicant.getRole());
            statement.setString(9, applicant.getBranch());
            statement.setString(10, applicant.getInterviewDate().toString());
            statement.setString(11, applicant.getCreatedBy());
            statement.setString(12, applicant.getCreatedAt());
            statement.setString(13, applicant.getCreatedBranch());
            statement.setString(14, applicant.getStatus());
            statement.setString(15, applicant.getEdu());
            statement.setString(16, applicant.getDepartment());
            statement.setString(17, applicant.getWorkingEx());
            statement.setString(18, applicant.getSecondaryDepartments());
            statement.setString(19, applicant.getOther());
            statement.setString(20, applicant.getOneTimeToken());
            statement.setString(21, applicant.getId());

            boolean rowUpdated = statement.executeUpdate() > 0;
            System.out.println("isUpdated: " + rowUpdated);
            System.out.println("applicant ID: " + applicant.getId());
            statement.close();
            return rowUpdated;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public boolean deleteApplicant(int id) {
        String sql = "DELETE FROM Applicants WHERE applicantID = ?";
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
