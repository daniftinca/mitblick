package profile.entities;

import user.entities.BaseEntity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "jobTitle")
@NamedQueries({
        @NamedQuery(name = JobTitle.GET_ALL_JOB_TITLES, query = "SELECT p FROM JobTitle p"),
        @NamedQuery(name = JobTitle.GET_JOB_TITLE_BY_NAME, query = "SELECT p FROM JobTitle p WHERE p.name = :name"),
        @NamedQuery(name = JobTitle.GET_JOB_TITLE_BY_ID, query = "SELECT p FROM JobTitle p WHERE p.id = :id"),
}
)
public class JobTitle extends BaseEntity {
    public static final String GET_ALL_JOB_TITLES = "get_all_job_titles";
    public static final String GET_JOB_TITLE_BY_NAME = "get_job_title_by_name";
    public static final String GET_JOB_TITLE_BY_ID = "get_job_title_by_id";

    @Column(name = "name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        JobTitle jobTitle = (JobTitle) o;
        return Objects.equals(getName(), jobTitle.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getName());
    }

    @Override
    public String toString() {
        return "JobTitle{" +
                "name='" + name + '\'' +
                '}';
    }
}
