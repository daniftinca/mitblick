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
import skills.dto.SkillAreaDTO;
import skills.entities.Skill;
import skills.entities.SkillArea;
import skills.service.SkillAreaManagementService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SkillAreaManagementServiceTest {

    @InjectMocks
    private SkillAreaManagementService skillAreaManagementService;

    @Mock
    private SkillAreaPersistenceManager skillAreaPersistenceManager;

    @Mock
    private SkillPersistenceManager skillPersistenceManager;

    @Test
    public void createSkillArea() {
        SkillArea skillArea = new SkillArea();
        skillArea.setName("dev");
        skillArea.setDescription("development skills");

        SkillAreaDTO skillAreaDTO = new SkillAreaDTO();
        skillAreaDTO.setName("dev");
        skillAreaDTO.setDescription("development skills");

        when(skillAreaPersistenceManager.getByName(any(String.class)))
                .thenReturn(Optional.empty());
        when(skillAreaPersistenceManager.create(any(SkillArea.class)))
                .thenReturn(skillArea);

        try {
            SkillAreaDTO createdSkillArea = skillAreaManagementService.createSkillArea(skillAreaDTO);
            assertEquals(skillAreaDTO.getName(), createdSkillArea.getName());
        } catch (BusinessException e) {
            fail("Should not reach this point");
        }
    }

    @Test
    public void createSkillArea_ExpectedException() {
        SkillArea skillArea = new SkillArea();
        skillArea.setName("dev");
        skillArea.setDescription("development skills");


        SkillAreaDTO skillAreaDTO = new SkillAreaDTO();
        skillAreaDTO.setName("dev");
        skillAreaDTO.setDescription("development skills");

        when(skillAreaPersistenceManager.getByName(any(String.class)))
                .thenReturn(Optional.of(skillArea));
        when(skillAreaPersistenceManager.create(any(SkillArea.class)))
                .thenReturn(skillArea);

        try {
            skillAreaManagementService.createSkillArea(skillAreaDTO);
            fail("Should not reach this point");
        } catch (BusinessException e) {
            assertEquals(ExceptionCode.SKILLAREA_VALIDATION_EXCEPTION, e.getExceptionCode());
        }
    }

    @Test
    public void updateSkillArea() {
        SkillArea skillArea = new SkillArea();
        skillArea.setName("dev");
        skillArea.setDescription("development skills");

        SkillAreaDTO skillAreaDTO = new SkillAreaDTO();
        skillAreaDTO.setName("dev");
        skillAreaDTO.setDescription("a");

        when(skillAreaPersistenceManager.getByName(any(String.class)))
                .thenReturn(Optional.of(skillArea));
        try {

            SkillAreaDTO updatedSkillArea = skillAreaManagementService.updateSkillArea("dev", skillAreaDTO);
            assertEquals(skillAreaDTO.getDescription(), updatedSkillArea.getDescription());
        } catch (BusinessException e) {
            fail("Should not reach this point");
        }
    }

    @Test
    public void updateSkillArea_ExpectedException() {
        SkillArea skillArea = new SkillArea();
        skillArea.setName("dev");
        skillArea.setDescription("development skills");

        SkillAreaDTO skillAreaDTO = new SkillAreaDTO();
        skillAreaDTO.setName("dev");
        skillAreaDTO.setDescription("a");

        when(skillAreaPersistenceManager.getByName(any(String.class)))
                .thenReturn(Optional.empty());

        try {

            skillAreaManagementService.updateSkillArea("dev", skillAreaDTO);
        } catch (BusinessException e) {
            assertEquals(ExceptionCode.SKILLAREA_VALIDATION_EXCEPTION, e.getExceptionCode());
        }
    }

    @Test
    public void deleteSkillArea_Success() {
        SkillArea skillArea = new SkillArea();
        skillArea.setName("dev");
        skillArea.setDescription("development skills");


        SkillAreaDTO skillAreaDTO = new SkillAreaDTO();
        skillAreaDTO.setName("dev");
        skillAreaDTO.setDescription("development skills");

        when(skillAreaPersistenceManager.getByName(any(String.class)))
                .thenReturn(Optional.of(skillArea));

        try {
            skillAreaManagementService.deleteSkillArea(skillAreaDTO);
        } catch (BusinessException e) {
            fail("Should not reach this point");
        }
    }

    @Test
    public void deleteSkillArea_Test() {
        SkillAreaDTO skillAreaDTO = new SkillAreaDTO();
        skillAreaDTO.setName("dev");
        skillAreaDTO.setDescription("development skills");

        when(skillAreaPersistenceManager.getByName(any(String.class)))
                .thenReturn(Optional.empty());

        try {
            skillAreaManagementService.deleteSkillArea(skillAreaDTO);
            fail("Should not reach this point");
        } catch (BusinessException e) {
            assertEquals(ExceptionCode.SKILLAREA_VALIDATION_EXCEPTION, e.getExceptionCode());
        }
    }

    @Test
    public void addSkillToSkillArea() {
        Skill skill = new Skill();
        skill.setId(1L);

        SkillArea skillArea = new SkillArea();
        skillArea.setName("a");

        when(skillAreaPersistenceManager.getByName("a"))
                .thenReturn(Optional.of(skillArea));
        when(skillPersistenceManager.getById(1L))
                .thenReturn(Optional.of(skill));

        try {
            skillAreaManagementService.addSkillToSkillArea(1L, "a");
        } catch (BusinessException e) {
            fail("Should not reach this point");
        }
    }

    @Test
    public void addSkillToSkillArea_ExpectedException() {
        Skill skill = new Skill();
        skill.setId(1L);

        SkillArea skillArea = new SkillArea();
        skillArea.setName("a");

        when(skillAreaPersistenceManager.getByName(any(String.class)))
                .thenReturn(Optional.empty());
        when(skillPersistenceManager.getById(1L))
                .thenReturn(Optional.of(skill));

        try {
            skillAreaManagementService.addSkillToSkillArea(1L, "aa");
            fail("Should not reach this point");
        } catch (BusinessException e) {
            assertEquals(ExceptionCode.SKILLAREA_OR_SKILL_VALIDATION_EXCEPTION, e.getExceptionCode());
        }
    }

    @Test
    public void removeSkillFromSkillArea() {
        Skill skill = new Skill();
        skill.setId(1L);

        SkillArea skillArea = new SkillArea();
        skillArea.setName("a");
        skillArea.getSkills().add(skill);

        when(skillAreaPersistenceManager.getByName("a"))
                .thenReturn(Optional.of(skillArea));
        when(skillPersistenceManager.getById(1L))
                .thenReturn(Optional.of(skill));

        try {
            skillAreaManagementService.removeSkillFromSkillArea(1L, "a");
        } catch (BusinessException e) {
            fail("Should not reach this point");
        }
    }

    @Test
    public void removeSkillFromSkillArea_ExpectedException() {
        Skill skill = new Skill();
        skill.setId(1L);

        SkillArea skillArea = new SkillArea();
        skillArea.setName("a");

        when(skillAreaPersistenceManager.getByName("a"))
                .thenReturn(Optional.of(skillArea));
        when(skillPersistenceManager.getById(1L))
                .thenReturn(Optional.of(skill));

        try {
            skillAreaManagementService.removeSkillFromSkillArea(1L, "a");
            fail("Should not reach this point");
        } catch (BusinessException e) {
            assertEquals(ExceptionCode.SKILL_NOT_IN_SKILLAREA_VALIDATION_EXCEPTION, e.getExceptionCode());
        }
    }

    @Test
    public void getAllSkillAreas_Success() {
        SkillArea skillArea = new SkillArea();
        List<SkillArea> skillAreaList = new ArrayList<>();
        skillAreaList.add(skillArea);
        when(skillAreaPersistenceManager.getAll()).thenReturn(Optional.of(skillAreaList));
        assertEquals(1, skillAreaManagementService.getAllSkillAreas().get().size());
    }

    @Test
    public void getSkillsFromSkillArea_Success() {
        SkillArea skillArea = new SkillArea();
        skillArea.getSkills().add(new Skill());

        when(skillAreaPersistenceManager.getByName(any(String.class)))
                .thenReturn(Optional.of(skillArea));

        try {
            assertEquals(1, skillAreaManagementService.getSkillsFromSkillArea("a").size());
        } catch (BusinessException e) {
            fail("Should not reach this point.");
        }
    }

    @Test
    public void getSkillsFromSkillArea_ExpectedException() {

        when(skillAreaPersistenceManager.getByName(any(String.class)))
                .thenReturn(Optional.empty());

        try {
            assertEquals(1, skillAreaManagementService.getSkillsFromSkillArea("a").size());
            fail("Should not reach this point.");
        } catch (BusinessException e) {
            assertEquals(ExceptionCode.SKILL_NOT_IN_SKILLAREA_VALIDATION_EXCEPTION, e.getExceptionCode());
        }
    }

    @Test
    public void getSkillAreaBySkill_Success() {
        Skill skill = new Skill();
        skill.setId(1L);

        SkillArea skillArea = new SkillArea();
        skillArea.setName("a");
        skillArea.getSkills().add(skill);
        List<SkillArea> skillAreaList = new ArrayList<>();
        skillAreaList.add(skillArea);
        when(skillAreaPersistenceManager.getAll()).thenReturn(Optional.of(skillAreaList));

        try {
            skillAreaManagementService.getSkillAreaBySkill(skill);
        } catch (BusinessException e) {
            fail("Should not reach this point.");
        }
    }

    @Test
    public void getSkillAreaBySkill_ExpectedException() {
        Skill skill = new Skill();
        skill.setId(1L);

        SkillArea skillArea = new SkillArea();
        skillArea.setName("a");

        List<SkillArea> skillAreaList = new ArrayList<>();
        skillAreaList.add(skillArea);
        when(skillAreaPersistenceManager.getAll()).thenReturn(Optional.of(skillAreaList));

        try {
            skillAreaManagementService.getSkillAreaBySkill(skill);
            fail("Should not reach this point.");
        } catch (BusinessException e) {
            assertEquals(ExceptionCode.SKILL_NOT_IN_SKILLAREA_VALIDATION_EXCEPTION, e.getExceptionCode());
        }
    }

}