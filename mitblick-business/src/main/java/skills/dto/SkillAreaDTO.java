package skills.dto;

import skills.entities.Skill;

import java.util.List;

public class SkillAreaDTO {

    private String name;
    private List<Skill> skills;
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "SkillAreaDTO{" +
                "name='" + name + '\'' +
                ", skills=" + skills +
                ", description='" + description + '\'' +
                '}';
    }
}
