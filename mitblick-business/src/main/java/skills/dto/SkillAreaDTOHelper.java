package skills.dto;

import skills.entities.SkillArea;

public class SkillAreaDTOHelper {

    private SkillAreaDTOHelper() {
        //empty private constructor to hide the public implicit one
    }

    public static SkillAreaDTO fromEntity(SkillArea skillArea) {
        SkillAreaDTO skillAreaDTO = new SkillAreaDTO();
        skillAreaDTO.setName(skillArea.getName());
        skillAreaDTO.setSkills(skillArea.getSkills());
        skillAreaDTO.setDescription(skillArea.getDescription());
        return skillAreaDTO;
    }

    public static SkillArea toEntity(SkillAreaDTO skillAreaDTO) {
        SkillArea skillArea = new SkillArea();
        skillArea.setName(skillAreaDTO.getName());
        skillArea.setSkills(skillAreaDTO.getSkills());
        skillArea.setDescription(skillAreaDTO.getDescription());
        return skillArea;
    }

    public static SkillArea updateEntityWIthDTO(SkillArea skillArea, SkillAreaDTO skillAreaDTO) {
        skillArea.setName(skillAreaDTO.getName());
        skillArea.setSkills(skillAreaDTO.getSkills());
        skillArea.setDescription(skillAreaDTO.getDescription());
        return skillArea;
    }
}
