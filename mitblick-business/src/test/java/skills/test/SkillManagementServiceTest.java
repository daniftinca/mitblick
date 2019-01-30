package skills.test;

import exception.BusinessException;
import exception.ExceptionCode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import skills.dao.SkillAreaPersistenceManager;
import skills.dao.SkillPersistenceManager;
import skills.dto.SkillDTO;
import skills.entities.Skill;
import skills.entities.SkillArea;
import skills.service.SkillManagementService;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SkillManagementServiceTest {

    @InjectMocks
    private SkillManagementService skillManagementService;

    @Mock
    private SkillPersistenceManager skillPersistenceManager;

    @Mock
    private SkillAreaPersistenceManager skillAreaPersistenceManager;

    @Test
    public void createSkill_Success() {
        Skill skill = new Skill();
        skill.setId(1L);
        skill.setName("a");

        SkillDTO skillDTO = new SkillDTO();
        skillDTO.setId(1L);
        skillDTO.setName("a");

        when(skillPersistenceManager.create(any(Skill.class)))
                .thenReturn(skill);
        when(skillAreaPersistenceManager.getByName(any(String.class)))
                .thenReturn(Optional.of(new SkillArea()));


        try {

            SkillDTO createdSkill = skillManagementService.createSkill(skillDTO, "aa");
            assertEquals(skill.getName(), createdSkill.getName());
        } catch (BusinessException e) {
            fail("Should not reach this point.");
        }
    }

    @Test
    public void createSkill_ExceptionExpected() {
        Skill skill = new Skill();
        skill.setId(1L);
        skill.setName("a");

        SkillDTO skillDTO = new SkillDTO();
        skillDTO.setId(1L);
        skillDTO.setName("a");

        when(skillPersistenceManager.create(any(Skill.class)))
                .thenReturn(skill);
        when(skillAreaPersistenceManager.getByName(any(String.class)))
                .thenReturn(Optional.empty());


        try {

            skillManagementService.createSkill(skillDTO, "aa");
            fail("Should not reach this point.");
        } catch (BusinessException e) {
            assertEquals(ExceptionCode.SKILL_SKILLAREA_VALIDATION_EXCEPTION, e.getExceptionCode());
        }
    }

    @Test
    public void deleteSkill_Success() {
        Skill skill = new Skill();
        skill.setId(1L);

        when(skillPersistenceManager.getById(any(Long.class)))
                .thenReturn(Optional.of(skill));

        try {

            skillManagementService.deleteSkill(1L);

        } catch (BusinessException e) {
            fail("Should not reach this point.");
        }
    }

    @Test
    public void deleteSkill_ExceptionExpected() {

        when(skillPersistenceManager.getById(any(Long.class)))
                .thenReturn(Optional.empty());

        try {

            skillManagementService.deleteSkill(1L);
            fail("Should not reach this point.");
        } catch (BusinessException e) {
            assertEquals(ExceptionCode.SKILL_NOT_FOUND, e.getExceptionCode());
        }
    }
}
