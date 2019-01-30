package profile.entities;

import skills.entities.Skill;
import user.entities.BaseEntity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "profileSkillEntry")
public class ProfileSkillEntry extends BaseEntity {

    @ManyToOne
    @JoinColumn(name="Skill_ID")
    private Skill skill;

    @Column
    private String skillAreaName;

    @Column
    private Integer rating;

    public Skill getSkill() {
        return skill;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }

    public String getSkillAreaName() {
        return skillAreaName;
    }

    public void setSkillAreaName(String skillAreaName) {
        this.skillAreaName = skillAreaName;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ProfileSkillEntry that = (ProfileSkillEntry) o;
        return Objects.equals(skill, that.skill) &&
                Objects.equals(skillAreaName, that.skillAreaName) &&
                Objects.equals(rating, that.rating);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), skill, skillAreaName, rating);
    }

    @Override
    public String toString() {
        return "ProfileSkillEntry{" +
                "skill=" + skill +
                ", skillAreaName='" + skillAreaName + '\'' +
                ", rating=" + rating +
                '}';
    }
}
