package projekt.entities;

import user.entities.BaseEntity;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "projekt")
@NamedQueries({
        @NamedQuery(name = Projekt.GET_ALL_PROJEKTS, query = "SELECT p FROM Projekt p"),
        @NamedQuery(name = Projekt.GET_PROJEKT_BY_NAME, query = "SELECT p FROM Projekt p WHERE p.name = :name"),
        @NamedQuery(name = Projekt.GET_PROJEKTS_BY_CLIENT, query = "SELECT p FROM Projekt p WHERE p.client = :client"),
        @NamedQuery(name = Projekt.GET_PROJEKTS_BY_BRANCH, query = "SELECT p FROM Projekt p WHERE p.branch = :branch"),
        @NamedQuery(name = Projekt.GET_PROJEKTS_BY_DATE, query = "SELECT p FROM Projekt p WHERE p.date = :date"),
        @NamedQuery(name = Projekt.GET_PROJEKT_BY_ID, query = "SELECT p FROM Projekt p WHERE p.id = :id"),
}
)
public class Projekt extends BaseEntity {

    public static final String GET_ALL_PROJEKTS = "get_all_projekts";
    public static final String GET_PROJEKT_BY_NAME = "get_projekt_by_name";
    public static final String GET_PROJEKTS_BY_CLIENT = "get_projekts_by_client";
    public static final String GET_PROJEKTS_BY_BRANCH = "get_projekts_by_branch";
    public static final String GET_PROJEKTS_BY_DATE = "get_projekts_by_date";
    public static final String GET_PROJEKT_BY_ID = "get_projekt_by_id";

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "client", nullable = false)
    private String client;

    @Column(name = "branch", nullable = false)
    private String branch;

    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "description")
    private String description;

    public Projekt() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Projekt projekt = (Projekt) o;
        return getName().equals(projekt.getName()) &&
                getClient().equals(projekt.getClient()) &&
                getBranch().equals(projekt.getBranch()) &&
                getDate().equals(projekt.getDate()) &&
                Objects.equals(getDescription(), projekt.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getName(), getClient(), getBranch(), getDate(), getDescription());
    }

    @Override
    public String toString() {
        return "Projekt{" +
                "name='" + name + '\'' +
                ", client='" + client + '\'' +
                ", branch='" + branch + '\'' +
                ", date='" + date.toString() + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
