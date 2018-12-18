package skills.entities;

import user.entities.BaseEntity;

import javax.persistence.*;
import java.util.Objects;


@Entity
@Table(name = "skills")
@NamedQueries(
        {
                @NamedQuery(name = Skill.GET_SKILL_BY_NAME, query = "SELECT s FROM Skill s WHERE s.name = :name")
        }
)
public class Skill extends BaseEntity {

    public static final String GET_SKILL_BY_NAME = "get_skill_by_name";
    @Column(name = "name", unique = true)
    private String name;

    public Skill(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Skill skill = (Skill) o;
        return Objects.equals(name, skill.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
