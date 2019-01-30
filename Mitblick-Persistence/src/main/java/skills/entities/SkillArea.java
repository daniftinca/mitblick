package skills.entities;

import user.entities.BaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@NamedQueries(
        {
                @NamedQuery(name = SkillArea.GET_SKILLAREA_BY_NAME, query = "SELECT s FROM SkillArea s WHERE s.name = :name"),
                @NamedQuery(name = SkillArea.GET_ALL_SKILLAREAS, query = "SELECT s FROM SkillArea s")
        }
)
public class SkillArea extends BaseEntity {
    public static final String GET_SKILLAREA_BY_NAME = "get_skillArea_by_name";
    public static final String GET_ALL_SKILLAREAS = "get_skillAreas";

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "description")
    private String description;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(name = "skillArea")
    private List<Skill> skills = new ArrayList<>();

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }


    @Override
    public String toString() {
        return "SkillArea{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", skills=" + skills +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SkillArea skillArea = (SkillArea) o;
        return Objects.equals(name, skillArea.name) &&
                Objects.equals(description, skillArea.description) &&
                Objects.equals(skills, skillArea.skills);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, description, skills);
    }
}
