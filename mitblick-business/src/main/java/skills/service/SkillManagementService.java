package skills.service;

import exception.BusinessException;
import exception.ExceptionCode;
import skills.dao.SkillAreaPersistenceManager;
import skills.dao.SkillPersistenceManager;
import skills.dto.SkillDTO;
import skills.dto.SkillDTOHelper;
import skills.entities.Skill;
import skills.entities.SkillArea;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.Optional;

@Stateless
public class SkillManagementService {

    @EJB
    private SkillPersistenceManager skillPersistenceManager;
    @EJB
    private SkillAreaPersistenceManager skillAreaPersistenceManager;

    /**
     * Creats a skill entity using a skill DTO.
     *
     * @param skillDTO skill information
     * @return the skill DTO of the created entity
     * @throws BusinessException
     */
    public SkillDTO createSkill(SkillDTO skillDTO, String skillAreaName) throws BusinessException {
        Optional<SkillArea> skillArea = validateSkillForCreation(skillDTO, skillAreaName);
        Skill skill = SkillDTOHelper.toEntity(skillDTO);
        skillPersistenceManager.create(skill);
        skillArea.get().getSkills().add(skill);
        return SkillDTOHelper.fromEntity(skill);
    }

    private Optional<SkillArea> validateSkillForCreation(SkillDTO skillDTO, String skillAreaName) throws BusinessException {

        Optional<SkillArea> skillAreaOptional = skillAreaPersistenceManager.getByName(skillAreaName);
        if (skillDTO.getName()=="") {
            throw new BusinessException(ExceptionCode.SKILL_VALIDATION_EXCEPTION);
        }
        if(!(skillAreaOptional.isPresent()))
            throw new BusinessException(ExceptionCode.SKILL_SKILLAREA_VALIDATION_EXCEPTION);
        return skillAreaOptional;
    }

//    public void validateSkillForUpdate(SkillDTO skillDTO) throws BusinessException {
//        if (!(skillPersistenceManager.getByName(skillDTO.getName()).isPresent()))
//            throw new BusinessException(ExceptionCode.SKILL_VALIDATION_EXCEPTION);
//    }

    public void validateSkillForDelete(SkillDTO skillDTO) throws BusinessException {
        if (!(skillPersistenceManager.getByName(skillDTO.getName()).isPresent()))
            throw new BusinessException(ExceptionCode.SKILL_VALIDATION_EXCEPTION);
    }

//    public SkillDTO updateSkill(String oldName, String newName) throws BusinessException {
//
//        Optional<Skill> skillBeforeOptional = skillPersistenceManager.getByName(oldName);
//
//        if (skillBeforeOptional.isPresent()) {
//            Skill skillBefore = skillBeforeOptional.get();
//            SkillDTO skillDTO = new SkillDTO();
//            skillDTO.setName(newName);
//            Skill skillAfter = SkillDTOHelper.updateEntityWithDTO(skillBefore, skillDTO);
//
//            skillPersistenceManager.update(skillAfter);
//
//            return skillDTO;
//        } else {
//            throw new BusinessException(ExceptionCode.SKILL_NOT_FOUND);
//        }
//    }

    public void deleteSkill(Long skillID) throws BusinessException {
        Optional<Skill> skillBeforeOptional = skillPersistenceManager.getById(skillID);

        if (skillBeforeOptional.isPresent()) {
            Skill skill = skillBeforeOptional.get();

            skillPersistenceManager.delete(skill);
        } else {
            throw new BusinessException(ExceptionCode.SKILL_NOT_FOUND);
        }
    }


}
