package skills.entities;

import user.entities.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import java.util.Objects;


@Entity
@NamedQueries(
        {
                @NamedQuery(name = Skill.GET_SKILL_BY_NAME, query = "SELECT s FROM Skill s WHERE s.name = :name"),
                @NamedQuery(name = Skill.GET_SKILL_BY_ID, query = "SELECT s FROM Skill s WHERE s.id = :id")
        }
)
public class Skill extends BaseEntity {

    public static final String GET_SKILL_BY_NAME = "get_skill_by_name";
    public static final String GET_SKILL_BY_ID = "get_skill_by_id";
    @Column(name = "name")
    private String name;


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
