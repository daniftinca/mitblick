package notifications.entities;


import user.entities.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

import java.util.Objects;

@Entity
public class Notification extends BaseEntity {

    @Column(name = "title")
    private String title;

    @Column(name = "message")
    private String message;

    @Column(name = "userMail")
    private  String userMail;

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

    @Override
    public String toString() {
        return "Notification{" +
                "title='" + title + '\'' +
                ", message='" + message + '\'' +
                ", userMail='" + userMail + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Notification that = (Notification) o;
        return Objects.equals(title, that.title) &&
                Objects.equals(message, that.message) &&
                Objects.equals(userMail, that.userMail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), title, message, userMail);
    }
}
