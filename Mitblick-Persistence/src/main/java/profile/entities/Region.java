package profile.entities;

import user.entities.BaseEntity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "region")
@NamedQueries({
        @NamedQuery(name = Region.GET_ALL_REGIONS, query = "SELECT p FROM Region p"),
        @NamedQuery(name = Region.GET_REGION_BY_NAME, query = "SELECT p FROM Region p WHERE p.name = :name"),
        @NamedQuery(name = Region.GET_REGION_BY_ID, query = "SELECT p FROM Region p WHERE p.id = :id"),
}
)
public class Region extends BaseEntity {
    public static final String GET_ALL_REGIONS = "get_all_regions";
    public static final String GET_REGION_BY_NAME = "get_region_by_name";
    public static final String GET_REGION_BY_ID = "get_region_by_id";
    @Column(name = "name")
    private String name;

    public Region() {
    }

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
        Region region = (Region) o;
        return Objects.equals(getName(), region.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getName());
    }

    @Override
    public String toString() {
        return "Region{" +
                "name='" + name + '\'' +
                '}';
    }
}
