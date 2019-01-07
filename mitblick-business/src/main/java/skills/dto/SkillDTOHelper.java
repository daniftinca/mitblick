package skills.dto;

import skills.entities.Skill;

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

    public static Skill updateEntityWIthDTO(Skill skill, SkillDTO skillDTO) {
        skill.setName(skillDTO.getName());
        return skill;
    }


}
