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
        @NamedQuery(name = Projekt.GET_PROJEKTS_BY_START_DATE, query = "SELECT p FROM Projekt p WHERE p.startDate = :startDate"),
        @NamedQuery(name = Projekt.GET_PROJEKTS_BY_END_DATE, query = "SELECT p FROM Projekt p WHERE p.endDate = :endDate"),
        @NamedQuery(name = Projekt.GET_PROJEKT_BY_ID, query = "SELECT p FROM Projekt p WHERE p.id = :id"),
}
)
public class Projekt extends BaseEntity {

    public static final String GET_ALL_PROJEKTS = "get_all_projekts";
    public static final String GET_PROJEKT_BY_NAME = "get_projekt_by_name";
    public static final String GET_PROJEKTS_BY_CLIENT = "get_projekts_by_client";
    public static final String GET_PROJEKTS_BY_BRANCH = "get_projekts_by_branch";
    public static final String GET_PROJEKTS_BY_START_DATE = "get_projekts_by_start_date";
    public static final String GET_PROJEKTS_BY_END_DATE = "get_projekts_by_end_date";
    public static final String GET_PROJEKT_BY_ID = "get_projekt_by_id";

    @Column(name = "name")
    private String name;

    @Column(name = "client")
    private String client;

    @Column(name = "branch")
    private String branch;

    @Column(name = "startDate")
    private Date startDate;

    @Column(name = "endDate")
    private Date endDate;

    @Column(name = "description")
    private String description;



    public Projekt() {
    //Empty constructor for a reason.
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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
        return Objects.equals(getName(), projekt.getName()) &&
                Objects.equals(getClient(), projekt.getClient()) &&
                Objects.equals(getBranch(), projekt.getBranch()) &&
                Objects.equals(getStartDate(), projekt.getStartDate()) &&
                Objects.equals(getEndDate(), projekt.getEndDate()) &&
                Objects.equals(getDescription(), projekt.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getName(), getClient(), getBranch(), getStartDate(), getEndDate(), getDescription());
    }

    @Override
    public String toString() {
        return "Projekt{" +
                "name='" + name + '\'' +
                ", client='" + client + '\'' +
                ", branch='" + branch + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", description='" + description + '\'' +
                '}';
    }
}
