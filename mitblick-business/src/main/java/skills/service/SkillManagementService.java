package skills.service;

import exception.BusinessException;
import exception.ExceptionCode;
import skills.dao.SkillPersistenceManager;
import skills.dto.SkillDTO;
import skills.dto.SkillDTOHelper;
import skills.entities.Skill;

import javax.ejb.EJB;

public class SkillManagementService {

    @EJB
    private SkillPersistenceManager skillPersistenceManager;

    /**
     * Creats a skill entity using a skill DTO.
     *
     * @param skillDTO skill information
     * @return the skill DTO of the created entity
     * @throws BusinessException
     */
    public SkillDTO createSkill(SkillDTO skillDTO) throws BusinessException {
        validateSkillForCreation(skillDTO);
        Skill skill = SkillDTOHelper.toEntity(skillDTO);
        skillPersistenceManager.create(skill);
        return SkillDTOHelper.fromEntity(skill);
    }

    private void validateSkillForCreation(SkillDTO skillDTO) throws BusinessException {
        if (!isValidForCreation(skillDTO)) {
            throw new BusinessException(ExceptionCode.SKILL_VALIDATION_EXCEPTION);
        }
    }

    private boolean isValidForCreation(SkillDTO skillDTO) throws BusinessException {
        //validate Skill

        return true;
    }


}
