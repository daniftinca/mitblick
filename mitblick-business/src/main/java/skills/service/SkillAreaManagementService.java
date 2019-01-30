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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Stateless
public class SkillAreaManagementService {

    @EJB
    private SkillAreaPersistenceManager skillAreaPersistenceManager;

    @EJB
    private SkillPersistenceManager skillPersistenceManager;


    private void validateSkillAreaForUpdate(SkillAreaDTO skillAreaDTO) throws BusinessException {
        if (skillAreaPersistenceManager.getByName(skillAreaDTO.getName()).isPresent())
            throw new BusinessException(ExceptionCode.SKILLAREA_VALIDATION_EXCEPTION);
    }

    private void validateSkillAreaForCreation(SkillAreaDTO skillAreaDTO) throws BusinessException {
        if (skillAreaPersistenceManager.getByName(skillAreaDTO.getName()).isPresent())
            throw new BusinessException(ExceptionCode.SKILLAREA_VALIDATION_EXCEPTION);
    }

    /**
     * Creates a skillArea entity from a skillAreaDTO.
     * @param skillAreaDTO
     * @return
     * @throws BusinessException
     */
    public SkillAreaDTO createSkillArea(SkillAreaDTO skillAreaDTO) throws BusinessException {
        validateSkillAreaForCreation(skillAreaDTO);
        SkillArea skillArea = SkillAreaDTOHelper.toEntity(skillAreaDTO);
        skillAreaPersistenceManager.create(skillArea);
        return SkillAreaDTOHelper.fromEntity(skillArea);
    }

    /**
     * Updates a skillArea by its old name, using a skillAreaDTO.
     * @param oldName
     * @param skillAreaDTO
     * @return
     * @throws BusinessException
     */
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

    /**
     * Deletes a skillArea using a skillAreaDTO.
     * @param skillAreaDTO
     * @throws BusinessException
     */
    public void deleteSkillArea(SkillAreaDTO skillAreaDTO) throws BusinessException {
        Optional<SkillArea> skillAreaBeforeOptional = skillAreaPersistenceManager.getByName(skillAreaDTO.getName());

        if (skillAreaBeforeOptional.isPresent()) {
            SkillArea skillAreaBefore = skillAreaBeforeOptional.get();
            skillAreaPersistenceManager.delete(skillAreaBefore);
        } else {
            throw new BusinessException(ExceptionCode.SKILLAREA_VALIDATION_EXCEPTION);
        }
    }

    /**
     * Adds a skill (given by ID) to a skillArea(given by Name)
     * @param skillId
     * @param skillAreaName
     * @throws BusinessException
     */
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

    /**
     * Removes a skill (given by ID) from a skillArea(given by Name)
     * @param skillId
     * @param skillAreaName
     * @throws BusinessException
     */
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

    /**
     * Gets all SkillAreas
     * @return
     */
    public Optional<List<SkillArea>> getAllSkillAreas() {
        return skillAreaPersistenceManager.getAll();
    }

    /**
     * Gets all skills from a skillArea given by name.
     * @param skillAreaName
     * @return
     * @throws BusinessException
     */
    public List<Skill> getSkillsFromSkillArea(String skillAreaName) throws BusinessException {
        Optional<SkillArea> skillArea = skillAreaPersistenceManager.getByName(skillAreaName);
        if (skillArea.isPresent())
            return skillArea.get().getSkills();
        else
            throw new BusinessException(ExceptionCode.SKILL_NOT_IN_SKILLAREA_VALIDATION_EXCEPTION);
    }

    /**
     * Gets the skillArea to which a skill belongs
     * @param skill
     * @return
     * @throws BusinessException
     */
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
