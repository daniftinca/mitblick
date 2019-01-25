package skills.test;

import exception.BusinessException;
import exception.ExceptionCode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import skills.dao.SkillAreaPersistenceManager;
import skills.dto.SkillAreaDTO;
import skills.service.SkillAreaManagementService;

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


    @Test
    public void validateSkillAreaForUpdate() {
    }

    @Test
    public void validateSkillAreaForDelete() {
    }

    @Test
    public void validateSkillAreaForCreation() {
    }

    @Test
    public void createSkillArea() {
    }

    @Test
    public void updateSkillArea() {
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
    }

    @Test
    public void removeSkillFromSkillArea() {
    }
}