package user.dto;

import notifications.entities.Notification;

import java.util.List;

public class UserDTO {
    private String password;
    private String email;
    private Boolean isActive;
    private String supervisorEmail;

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }

    private List<Notification> notifications;




    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", isActive=" + isActive +
                ", supervisorEmail='" + supervisorEmail + '\'' +
                ", notifications=" + notifications +
                '}';
    }

    public String getSupervisorEmail() {
        return supervisorEmail;
    }

    public void setSupervisorEmail(String supervisorEmail) {
        this.supervisorEmail = supervisorEmail;
    }
}

