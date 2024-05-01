package Models;

import java.time.LocalDate;

import Enum.Roles;

public class Applicant extends User {
    private LocalDate interviewDate;
    private String createdBy;
    private String createdAt;
    private String createdBranch;
    private String status;

    public Applicant(
            LocalDate interviewDate) {
        this.interviewDate = interviewDate;
    }

    public Applicant(
            LocalDate interviewDate,
            String createdBy,
            String createdAt,
            String createdBranch,
            String status) {
        this.interviewDate = interviewDate;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.createdBranch = createdBranch;
        this.status = status;
    }
    
    
    public Applicant(String firstName, String lastName, String address, String email, int phone, String username,
            String password, LocalDate interviewDate) {
        super(firstName, lastName, address, email, phone, username, password, Roles.APPLICANT.getValue());
        this.interviewDate = interviewDate;
    }

    public Applicant(String firstName, String lastName, String address, String email, int phone, String username,
            String password, LocalDate interviewDate, String createdBy, String createdAt, String createdBranch,
            String status) {
        super(firstName, lastName, address, email, phone, username, password, Roles.APPLICANT.getValue());
        this.interviewDate = interviewDate;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.createdBranch = createdBranch;
    }

    public LocalDate getInterviewDate() {
        return interviewDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getCreatedBranch() {
        return createdBranch;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setInterviewDate(LocalDate interviewDate) {
        this.interviewDate = interviewDate;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setCreatedBranch(String createdBranch) {
        this.createdBranch = createdBranch;
    }

    @Override
    public String getCSV() {
        return super.getCSV() +
                ", " + getInterviewDate() +
                ", " + getCreatedBranch() +
                ", " + getCreatedBy() +
                ", " + getCreatedAt() +
                ", " + getStatus();
    }

}
