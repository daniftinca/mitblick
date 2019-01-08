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
    private SkillAreaPersistenceManager skillAreaPersistenceManager;

    /**
     * Creats a skill entity using a skill DTO.
     *
     * @param skillDTO skill information
     * @return the skill DTO of the created entity
     * @throws BusinessException
     */
    public SkillDTO createSkill(SkillDTO skillDTO, String skillAreaName) throws BusinessException {
        validateSkillForCreation(skillDTO, skillAreaName);
        Skill skill = SkillDTOHelper.toEntity(skillDTO);
        skillPersistenceManager.create(skill);
        Optional<SkillArea> skillArea = skillAreaPersistenceManager.getByName(skillAreaName);
        skillArea.get().getSkills().add(skill);
        return SkillDTOHelper.fromEntity(skill);
    }

    private void validateSkillForCreation(SkillDTO skillDTO, String skillAreaName) throws BusinessException {
        if (!(skillPersistenceManager.getByName(skillDTO.getName()).isPresent()) || skillAreaPersistenceManager.getByName(skillAreaName).isPresent()) {
            throw new BusinessException(ExceptionCode.SKILL_VALIDATION_EXCEPTION);
        }
    }

    public void validateSkillForUpdate(SkillDTO skillDTO) throws BusinessException {
        if (!(skillPersistenceManager.getByName(skillDTO.getName()).isPresent()))
            throw new BusinessException(ExceptionCode.SKILL_VALIDATION_EXCEPTION);
    }

    public void validateSkillForDElete(SkillDTO skillDTO) throws BusinessException {
        if (skillPersistenceManager.getByName(skillDTO.getName()).isPresent())
            throw new BusinessException(ExceptionCode.SKILL_VALIDATION_EXCEPTION);
    }

    public SkillDTO updateSkill(SkillDTO skillDTO) throws BusinessException {

        Optional<Skill> skillBeforeOptional = skillPersistenceManager.getByName(skillDTO.getName());

        if (skillBeforeOptional.isPresent()) {
            Skill skillBefore = skillBeforeOptional.get();
            validateSkillForUpdate(skillDTO);
            Skill skillAfter = SkillDTOHelper.updateEntityWithDTO(skillBefore, skillDTO);

            skillPersistenceManager.update(skillAfter);

            return skillDTO;
        } else {
            throw new BusinessException(ExceptionCode.SKILL_NOT_FOUND);
        }
    }

    public void deleteSkill(SkillDTO skillDTO) throws BusinessException {
        Optional<Skill> skillBeforeOptional = skillPersistenceManager.getByName(skillDTO.getName());

        if (skillBeforeOptional.isPresent()) {
            Skill skillBefore = skillBeforeOptional.get();
            validateSkillForDElete(skillDTO);

            Skill skill = SkillDTOHelper.toEntity(skillDTO);
            skillAreaPersistenceManager.deleteSkill(skill);

            skillPersistenceManager.update(skill);
        } else {
            throw new BusinessException(ExceptionCode.SKILL_NOT_FOUND);
        }
    }


}
