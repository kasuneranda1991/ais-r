package Models;

import java.time.LocalDate;

import Controllers.Enum.Roles;
import Controllers.Enum.Status;

public class Applicant extends User {
    private LocalDate interviewDate;
    private String createdBy;
    private String createdAt;
    private String createdBranch;
    private String status;
    private String edu;

    public Applicant(
            LocalDate interviewDate, String edu) {
        this.interviewDate = interviewDate;
        this.edu = edu;
    }

    public Applicant(
            LocalDate interviewDate, String createdBy, String createdAt, String createdBranch, String status,
            String edu) {
        this.interviewDate = interviewDate;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.createdBranch = createdBranch;
        this.status = status;
        this.edu = edu;
    }

    public Applicant(String firstName, String lastName, String address, String email, int phone, String username,
            String password, LocalDate interviewDate, String edu) {
        super(firstName, lastName, address, email, phone, username, password, Roles.APPLICANT.getValue());
        this.interviewDate = interviewDate;
        this.edu = edu;
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

    public String getEdu() {
        return edu;
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

    public Boolean isApproved() {
        return (status).trim().equals((Status.APPROVED.getValue()).trim());
    }

    @Override
    public String getCSV() {
        return super.getCSV() +
                ", " + getBranch() +
                ", " + getEdu() +
                ", " + getInterviewDate() +
                ", " + getCreatedBranch() +
                ", " + getCreatedBy() +
                ", " + getCreatedAt() +
                ", " + getStatus();
    }

}
