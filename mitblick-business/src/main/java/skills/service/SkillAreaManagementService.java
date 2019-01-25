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
import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Stateless
public class SkillAreaManagementService {

    @EJB
    private SkillAreaPersistenceManager skillAreaPersistenceManager;

    @EJB
    private SkillPersistenceManager skillPersistenceManager;


    public void validateSkillAreaForUpdate(SkillAreaDTO skillAreaDTO) throws BusinessException {
        if (skillAreaPersistenceManager.getByName(skillAreaDTO.getName()).isPresent())
            throw new BusinessException(ExceptionCode.SKILLAREA_VALIDATION_EXCEPTION);
    }

    public void validateSkillAreaForDelete(SkillAreaDTO skillAreaDTO) throws BusinessException {
        if (!(skillAreaPersistenceManager.getByName(skillAreaDTO.getName()).isPresent()))
            throw new BusinessException(ExceptionCode.SKILLAREA_VALIDATION_EXCEPTION);
    }

    public void validateSkillAreaForCreation(SkillAreaDTO skillAreaDTO) throws BusinessException {
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
            if (!oldName.equals(skillAreaDTO.getName()))
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
            skillAreaPersistenceManager.delete(skillAreaBefore);
        } else {
            throw new BusinessException(ExceptionCode.SKILLAREA_VALIDATION_EXCEPTION);
        }
    }

    public void addSkillToSkillArea(Long skillId, String skillAreaName) throws BusinessException {
        Optional<SkillArea> skillAreaBeforeOptional = skillAreaPersistenceManager.getByName(skillAreaName);
        Optional<Skill> skillBeforeOptional = skillPersistenceManager.getById(skillId);

        if (skillAreaBeforeOptional.isPresent() && skillBeforeOptional.isPresent()) {
            SkillArea skillArea = skillAreaBeforeOptional.get();
            Skill skill = skillBeforeOptional.get();
            if (!skillArea.getSkills().contains(skill))
                skillArea.getSkills().add(skill);
            else
                throw new BusinessException(ExceptionCode.SKILL_IN_SKILLAREA_VALIDATION_EXCEPTION);
        } else
            throw new BusinessException(ExceptionCode.SKILLAREA_OR_SKILL_VALIDATION_EXCEPTION);
    }

    public void removeSkillFromSkillArea(Long skillId, String skillAreaName) throws BusinessException {
        Optional<SkillArea> skillAreaBeforeOptional = skillAreaPersistenceManager.getByName(skillAreaName);
        Optional<Skill> skillBeforeOptional = skillPersistenceManager.getById(skillId);

        if (skillAreaBeforeOptional.isPresent() && skillBeforeOptional.isPresent()) {
            SkillArea skillArea = skillAreaBeforeOptional.get();
            Skill skill = skillBeforeOptional.get();
            if (skillArea.getSkills().contains(skill)) {
                skillArea.getSkills().remove(skill);
            } else
                throw new BusinessException(ExceptionCode.SKILL_NOT_IN_SKILLAREA_VALIDATION_EXCEPTION);
        } else
            throw new BusinessException(ExceptionCode.SKILLAREA_OR_SKILL_VALIDATION_EXCEPTION);
    }

    public Optional<List<SkillArea>> getAllSkillareas() {
        return skillAreaPersistenceManager.getAll();
    }

    public List<Skill> getSkillsFromSkillArea(String skillAreaName) throws BusinessException {
        List<Skill> skills = skillAreaPersistenceManager.getSkillsFromSkillArea(skillAreaName);
        if (skills != null)
            return skills;
        else
            throw new BusinessException(ExceptionCode.SKILL_NOT_IN_SKILLAREA_VALIDATION_EXCEPTION);
    }

    public SkillArea getSkillAreaBySkill(Skill skill) throws BusinessException {
        Optional<List<SkillArea>> optionalSkillAreas = skillAreaPersistenceManager.getAll();
        List<SkillArea> finalSkillAreas = new ArrayList<>();
        if (optionalSkillAreas.isPresent()) {
            List<SkillArea> skillAreas = optionalSkillAreas.get();
            if (!skillAreas.isEmpty()) {
                skillAreas.forEach(skillArea -> {
                    if (skillArea.getSkills().contains(skill)) {
                        finalSkillAreas.add(skillArea);
                    }
                });
                if (!finalSkillAreas.isEmpty())
                    return finalSkillAreas.get(0);
                else throw new BusinessException(ExceptionCode.SKILL_NOT_IN_SKILLAREA_VALIDATION_EXCEPTION);
            }else
                throw new BusinessException(ExceptionCode.SKILL_NOT_IN_SKILLAREA_VALIDATION_EXCEPTION);
        } else
            throw new BusinessException(ExceptionCode.SKILL_NOT_IN_SKILLAREA_VALIDATION_EXCEPTION);
    }
}
