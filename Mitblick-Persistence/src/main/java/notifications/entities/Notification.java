package notifications.entities;


import user.entities.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import java.util.Objects;

@Entity
@NamedQueries({
        @NamedQuery(name = Notification.GET_NOTIFICATION_BY_ID, query = "SELECT n FROM Notification n WHERE n.id = :id")
})
public class Notification extends BaseEntity {
    public static final String GET_NOTIFICATION_BY_ID = "get_notification_by_id";
    @Column(name = "title")
    private String title;

    @Column(name = "message")
    private String message;

    @Column(name = "userMail")
    private String userMail;

    @Column(name = "isRead", nullable = false)
    private Boolean isRead = false;


    public Notification() {
        //DO NOT TOUCH! THIS SHOULD BE EMPTY
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public Boolean getRead() {
        return isRead;
    }

    public void setRead(Boolean read) {
        isRead = read;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Notification that = (Notification) o;
        return Objects.equals(title, that.title) &&
                Objects.equals(message, that.message) &&
                Objects.equals(userMail, that.userMail) &&
                Objects.equals(isRead, that.isRead);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), title, message, userMail, isRead);
    }

    @Override
    public String toString() {
        return "Notification{" +
                "title='" + title + '\'' +
                ", message='" + message + '\'' +
                ", userMail='" + userMail + '\'' +
                ", isRead=" + isRead +
                '}';
    }
}
