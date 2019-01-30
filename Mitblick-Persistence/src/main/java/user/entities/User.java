package user.entities;

import notifications.entities.Notification;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
@NamedQueries(
        {
                @NamedQuery(name = User.GET_ALL_USERS, query = "SELECT u FROM User u"),
                @NamedQuery(name = User.GET_USER_BY_EMAIL, query = "SELECT u from User u where u.email = :email "),
                @NamedQuery(name = User.GET_USER_BY_ID, query = "SELECT u from User u where u.id=:id"),
        }
)
public class User extends BaseEntity {

    public static final String GET_ALL_USERS = "get_All_Users";
    public static final String GET_USER_BY_USERNAME = "get_User_By_Username";
    public static final String GET_USER_BY_EMAIL = "get_User_By_Email";
    public static final String GET_USER_BY_ID = "get_User_By_Id";
    public static final String GET_NOTIFICATIONS_BY_USERID = "get_Notifications_By_UserID";


    @Column(name = "email", nullable = false, unique = true)
    private String email;


    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "isActive", nullable = false)
    private Boolean isActive;

    @Column
    private Integer failedAttempts;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Role> roles;

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "userId")
    private List<Notification> notifications;

    public List<User> getEmployees() {
        return employees;
    }

    public void setEmployees(List<User> employees) {
        this.employees = employees;
    }

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "supervisorId")
    private List<User> employees;

    public String getSupervisorMail() {
        return supervisorMail;
    }

    public void setSupervisorMail(String supervisorMail) {
        this.supervisorMail = supervisorMail;
    }

    @Column
    private String supervisorMail;

    public User() {

        // THIS IS EMPTY FOR A REASON !!!
        //Empty constructor needed for Entity
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Integer getFailedAttempts() {
        return failedAttempts;
    }

    public void setFailedAttempts(Integer failedAttempts) {
        this.failedAttempts = failedAttempts;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return Objects.equals(email, user.email) &&
                Objects.equals(password, user.password) &&
                Objects.equals(isActive, user.isActive) &&
                Objects.equals(failedAttempts, user.failedAttempts) &&
                Objects.equals(roles, user.roles) &&
                Objects.equals(notifications, user.notifications) &&
                Objects.equals(employees, user.employees) &&
                Objects.equals(supervisorMail, user.supervisorMail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), email, password, isActive, failedAttempts, roles, notifications, employees, supervisorMail);
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", isActive=" + isActive +
                ", failedAttempts=" + failedAttempts +
                ", roles=" + roles +
                ", notifications=" + notifications +
                ", employees=" + employees +
                ", supervisorMail='" + supervisorMail + '\'' +
                '}';
    }
}