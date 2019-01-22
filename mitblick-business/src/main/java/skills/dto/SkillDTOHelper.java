package skills.dto;

import skills.entities.Skill;

import java.util.ArrayList;
import java.util.List;

public class SkillDTOHelper {

    private SkillDTOHelper() {

    }

    public static SkillDTO fromEntity(Skill skill) {
        SkillDTO skillDTO = new SkillDTO();
        skillDTO.setName(skill.getName());
        return skillDTO;
    }

    public static Skill toEntity(SkillDTO skillDTO) {
        Skill skill = new Skill();
        skill.setName(skillDTO.getName());
        return skill;
    }

    public static List<SkillDTO> fromEntity(List<Skill> skills) {
        List<SkillDTO> skillDTOs = new ArrayList<SkillDTO>();
        for (Skill skill : skills) {
            skillDTOs.add(fromEntity(skill));
        }
        return skillDTOs;
    }

    public static List<Skill> toEntity(List<SkillDTO> skillDTOs) {
        List<Skill> skills = new ArrayList<Skill>();
        for (SkillDTO skill : skillDTOs) {
            skills.add(toEntity(skill));
        }
        return skills;
    }

    public static Skill updateEntityWithDTO(Skill skill, SkillDTO skillDTO) {
        skill.setName(skillDTO.getName());
        return skill;
    }


}
