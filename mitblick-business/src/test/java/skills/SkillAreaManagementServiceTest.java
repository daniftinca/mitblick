package skills;

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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class SkillAreaManagementServiceTest {

    @InjectMocks
    private SkillAreaManagementService skillAreaManagementController;

    @Mock
    private SkillAreaPersistenceManager skillAreaPersistenceManager;


    @Test
    public void deleteSkillArea_Expected_SkillAreaValidationException() {
        SkillAreaDTO skillAreaDTO = new SkillAreaDTO();
        skillAreaDTO.setName("dev");
        skillAreaDTO.setDescription("development skills");

        when(skillAreaPersistenceManager.getByName(any(String.class)))
                .thenReturn(Optional.empty());

        try{
            skillAreaManagementController.deleteSkillArea(skillAreaDTO);
            fail("Should not reach this point");
        }
        catch (BusinessException e){
            assertEquals(ExceptionCode.SKILLAREA_VALIDATION_EXCEPTION, e.getExceptionCode());
        }



    }

    @org.junit.jupiter.api.Test
    void addSkillToSkillArea() {
    }

    @org.junit.jupiter.api.Test
    void removeSkillFromSkillArea() {
    }

}