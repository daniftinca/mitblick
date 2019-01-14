package skills.service;

import exception.BusinessException;
import exception.ExceptionCode;
import skills.dao.SkillAreaPersistenceManager;
import skills.dao.SkillPersistenceManager;
import skills.dto.SkillAreaDTO;
import skills.dto.SkillAreaDTOHelper;
import skills.entities.SkillArea;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.Optional;

@Stateless
public class SkillAreaManagementService {

    @EJB
    private SkillAreaPersistenceManager skillAreaPersistenceManager;

    @EJB
    private SkillPersistenceManager skillPersistenceManager;


    public void validateSkillAreaForUpdate(SkillAreaDTO skillAreaDTO)throws BusinessException{
        if (skillAreaPersistenceManager.getByName(skillAreaDTO.getName()).isPresent())
            throw new BusinessException(ExceptionCode.SKILLAREA_VALIDATION_EXCEPTION);
    }

    public void validateSkillAreaForDelete(SkillAreaDTO skillAreaDTO)throws BusinessException{
        if (skillAreaPersistenceManager.getByName(skillAreaDTO.getName()).isPresent())
            throw new BusinessException(ExceptionCode.SKILLAREA_VALIDATION_EXCEPTION);
    }

    public void validateSkillAreaForCreation(SkillAreaDTO skillAreaDTO)throws BusinessException{
        if (skillAreaPersistenceManager.getByName(skillAreaDTO.getName()).isPresent())
            throw new BusinessException(ExceptionCode.SKILLAREA_VALIDATION_EXCEPTION);
    }

    public SkillAreaDTO createSkillArea(SkillAreaDTO skillAreaDTO) throws BusinessException {
        validateSkillAreaForCreation(skillAreaDTO);
        SkillArea skillArea = SkillAreaDTOHelper.toEntity(skillAreaDTO);
        skillAreaPersistenceManager.create(skillArea);
        return SkillAreaDTOHelper.fromEntity(skillArea);
    }

    public SkillAreaDTO updateSkillArea(SkillAreaDTO skillAreaDTO) throws BusinessException {

        Optional<SkillArea> skillAreaBeforeOptional = skillAreaPersistenceManager.getByName(skillAreaDTO.getName());

        if (skillAreaBeforeOptional.isPresent()) {
            SkillArea skillAreaBefore = skillAreaBeforeOptional.get();
            validateSkillAreaForUpdate(skillAreaDTO);
            SkillArea skillAreaAfter = SkillAreaDTOHelper.updateEntityWIthDTO(skillAreaBefore, skillAreaDTO);

            skillAreaPersistenceManager.update(skillAreaAfter);

            return skillAreaDTO;
        } else {
            throw new BusinessException(ExceptionCode.SKILL_NOT_FOUND);
        }
    }

    public void deleteSkillArea(SkillAreaDTO skillAreaDTO) throws BusinessException {
        Optional<SkillArea> skillAreaBeforeOptional = skillAreaPersistenceManager.getByName(skillAreaDTO.getName());

        if (skillAreaBeforeOptional.isPresent()) {
            //SkillArea skillAreaBefore = skillAreaBeforeOptional.get();
            validateSkillAreaForDelete(skillAreaDTO);
            SkillArea skillArea = SkillAreaDTOHelper.toEntity(skillAreaDTO);
            skillArea.getSkills().forEach(skill -> skillPersistenceManager.delete(skill));
            skillAreaPersistenceManager.delete(skillArea);

            skillAreaPersistenceManager.update(skillArea);
        } else {
            throw new BusinessException(ExceptionCode.SKILL_NOT_FOUND);
        }
    }

}
