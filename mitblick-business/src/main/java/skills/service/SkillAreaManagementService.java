package skills.service;

import exception.BusinessException;
import exception.ExceptionCode;
import skills.dao.SkillAreaPersistenceManager;
import skills.dao.SkillPersistenceManager;
import skills.dto.SkillAreaDTO;
import skills.dto.SkillAreaDTOHelper;
import skills.entities.Skill;
import skills.entities.SkillArea;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;
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
        if (!(skillAreaPersistenceManager.getByName(skillAreaDTO.getName()).isPresent()))
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

    public SkillAreaDTO updateSkillArea(String oldName, SkillAreaDTO skillAreaDTO) throws BusinessException {

        Optional<SkillArea> skillAreaBeforeOptional = skillAreaPersistenceManager.getByName(oldName);

        if (skillAreaBeforeOptional.isPresent()) {
            SkillArea skillAreaBefore = skillAreaBeforeOptional.get();
            if(!oldName.equals(skillAreaDTO.getName()))
                validateSkillAreaForUpdate(skillAreaDTO);
            SkillArea skillAreaAfter = SkillAreaDTOHelper.updateEntityWIthDTO(skillAreaBefore, skillAreaDTO);

            skillAreaPersistenceManager.update(skillAreaAfter);

            return skillAreaDTO;
        } else {
            throw new BusinessException(ExceptionCode.SKILLAREA_VALIDATION_EXCEPTION);
        }
    }

    public void deleteSkillArea(SkillAreaDTO skillAreaDTO) throws BusinessException {
        Optional<SkillArea> skillAreaBeforeOptional = skillAreaPersistenceManager.getByName(skillAreaDTO.getName());

        if (skillAreaBeforeOptional.isPresent()) {
            SkillArea skillAreaBefore = skillAreaBeforeOptional.get();
            skillAreaBefore.getSkills().forEach(skill -> {
                List<SkillArea> skillAreas = skillAreaPersistenceManager.getBySkill(skill);
                if(!skillAreas.isEmpty() && skillAreas.size() == 1){
                    skillPersistenceManager.delete(skill);
                }
            });
            skillAreaPersistenceManager.delete(skillAreaBefore);
        } else {
            throw new BusinessException(ExceptionCode.SKILLAREA_VALIDATION_EXCEPTION);
        }
    }

    public void addSkillToSkillArea(String skillName, String skillAreaName)throws BusinessException{
        Optional<SkillArea> skillAreaBeforeOptional = skillAreaPersistenceManager.getByName(skillAreaName);
        Optional<Skill> skillBeforeOptional = skillPersistenceManager.getByName(skillName);

        if(skillAreaBeforeOptional.isPresent() && skillBeforeOptional.isPresent()){
            SkillArea skillArea = skillAreaBeforeOptional.get();
            Skill skill = skillBeforeOptional.get();
            if(!skillArea.getSkills().contains(skill))
                skillArea.getSkills().add(skill);
            else
                throw new BusinessException(ExceptionCode.SKILL_IN_SKILLAREA_VALIDATION_EXCEPTION);
        }
        else
            throw new BusinessException(ExceptionCode.SKILLAREA_OR_SKILL_VALIDATION_EXCEPTION);
    }

    public void removeSkillFromSkillArea(String skillName, String skillAreaName)throws BusinessException{
        Optional<SkillArea> skillAreaBeforeOptional = skillAreaPersistenceManager.getByName(skillAreaName);
        Optional<Skill> skillBeforeOptional = skillPersistenceManager.getByName(skillName);

        if(skillAreaBeforeOptional.isPresent() && skillBeforeOptional.isPresent()){
            SkillArea skillArea = skillAreaBeforeOptional.get();
            Skill skill = skillBeforeOptional.get();
            if(skillArea.getSkills().contains(skill)) {
                List<SkillArea> skillAreas = skillAreaPersistenceManager.getBySkill(skill);
                if(!skillAreas.isEmpty() && skillAreas.size() == 1){
                    skillArea.getSkills().remove(skill);
                    skillPersistenceManager.delete(skill);
                }
                else
                    skillArea.getSkills().remove(skill);
            }
            else
                throw new BusinessException(ExceptionCode.SKILL_NOT_IN_SKILLAREA_VALIDATION_EXCEPTION);
        }
        else
            throw new BusinessException(ExceptionCode.SKILLAREA_OR_SKILL_VALIDATION_EXCEPTION);
    }

}
