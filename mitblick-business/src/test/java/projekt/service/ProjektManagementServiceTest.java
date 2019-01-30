package projekt.service;

import exception.BusinessException;
import exception.ExceptionCode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import profile.dao.ProfilePersistenceManager;
import profile.entities.Profile;
import projekt.dao.ProjektPersistenceManager;
import projekt.dto.ProjektDTO;
import projekt.dto.ProjektDTOHelper;
import projekt.entities.Projekt;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProjektManagementServiceTest {

    @InjectMocks
    private ProjektManagementService projektManagementService;

    @Mock
    private ProjektPersistenceManager projektPersistenceManager;

    @Mock
    private ProfilePersistenceManager profilePersistenceManager;

    @Test
    public void create_Success() {

        Projekt projekt = new Projekt();
        projekt.setId(1L);
        projekt.setName("a");
        projekt.setClient("c");
        projekt.setBranch("d");
        projekt.setStartDate(new Date());
        projekt.setEndDate(new Date());

        ProjektDTO projektDTO = new ProjektDTO();
        projektDTO.setId(1L);
        projektDTO.setName("a");
        projektDTO.setClient("c");
        projektDTO.setBranch("d");
        projektDTO.setStartDate(new Date());
        projektDTO.setEndDate(new Date());

        when(projektPersistenceManager.create(any(Projekt.class)))
                .thenReturn(projekt);


        try {

            ProjektDTO createdProjekt = projektManagementService.create(projektDTO);
            assertEquals(projekt.getName(), createdProjekt.getName());
        } catch (BusinessException e) {
            fail("Should not reach this point.");
        }
    }

    @Test
    public void create_ExpectedException() {
        Projekt projekt = new Projekt();
        projekt.setId(1L);
        projekt.setName("a");


        ProjektDTO projektDTO = new ProjektDTO();
        projektDTO.setId(1L);
        projektDTO.setName("a");

        when(projektPersistenceManager.create(any(Projekt.class)))
                .thenReturn(projekt);


        try {

            projektManagementService.create(projektDTO);

            fail("Should not reach this point.");
        } catch (BusinessException e) {
            assertEquals(ExceptionCode.PROFILE_VALIDATION_EXCEPTION, e.getExceptionCode());
        }
    }

    @Test
    public void update_Success() {
        Projekt projekt = new Projekt();
        projekt.setId(1L);
        projekt.setName("a");
        projekt.setClient("c");
        projekt.setBranch("d");
        projekt.setStartDate(new Date());
        projekt.setEndDate(new Date());


        when(projektPersistenceManager.create(any(Projekt.class)))
                .thenReturn(projekt);
        when(projektPersistenceManager.getByName(any(String.class)))
                .thenReturn(Optional.of(projekt));
        try {

            projektPersistenceManager.create(projekt);
            projekt.setName("aaaaaaaa");
            ProjektDTO projektDTO = ProjektDTOHelper.fromEntity(projekt);

            ProjektDTO updatedProjekt = projektManagementService.update(projektDTO);

            assertEquals(projektDTO.getName(), updatedProjekt.getName());
        } catch (BusinessException e) {
            fail("Should not reach this point.");
        }
    }

    @Test
    public void update_ExpectedException() {
        ProjektDTO projektDTO = new ProjektDTO();
        projektDTO.setId(1L);
        projektDTO.setName("a");
        projektDTO.setClient("c");
        projektDTO.setBranch("d");
        projektDTO.setStartDate(new Date());
        projektDTO.setEndDate(new Date());

        when(projektPersistenceManager.getByName(any(String.class)))
                .thenReturn(Optional.empty());
        try {

            projektManagementService.update(projektDTO);
            fail("Should not reach this point.");
        } catch (BusinessException e) {
            assertEquals(ExceptionCode.NAME_NOT_FOUND, e.getExceptionCode());
        }
    }

    @Test
    public void delete_Success() {
        Projekt projekt = new Projekt();
        projekt.setId(1L);
        projekt.setName("a");
        projekt.setClient("c");
        projekt.setBranch("d");
        projekt.setStartDate(new Date());
        projekt.setEndDate(new Date());

        Profile profile = new Profile();
        profile.setEmail("a@mitblick.com");

        when(projektPersistenceManager.getByName("a"))
                .thenReturn(Optional.of(projekt));
        when(profilePersistenceManager.getByEmail("a@mitblick.com"))
                .thenReturn(Optional.of(profile));

        ProjektDTO projektDTO = new ProjektDTO();

        projektDTO.setName("a");

        try {

            projektManagementService.delete(projektDTO, "a@mitblick.com");

        } catch (BusinessException e) {
            fail("Should not reach this point.");
        }
    }

    @Test
    public void delete_ExpectedException() {
        Profile profile = new Profile();
        profile.setEmail("a@mitblick.com");

        when(projektPersistenceManager.getByName("a"))
                .thenReturn(Optional.empty());
        when(profilePersistenceManager.getByEmail("a@mitblick.com"))
                .thenReturn(Optional.of(profile));

        ProjektDTO projektDTO = new ProjektDTO();

        projektDTO.setName("a");

        try {

            projektManagementService.delete(projektDTO, "a@mitblick.com");
            fail("Should not reach this point.");
        } catch (BusinessException e) {
            assertEquals(ExceptionCode.NAME_NOT_FOUND, e.getExceptionCode());
        }
    }

    @Test
    public void getAll() {
        Projekt projekt = new Projekt();
        projekt.setId(1L);
        projekt.setName("a");
        projekt.setClient("c");
        projekt.setBranch("d");
        projekt.setStartDate(new Date());
        projekt.setEndDate(new Date());
        List<Projekt> projektList = new ArrayList<>();
        projektList.add(projekt);
        when(projektPersistenceManager.getAll()).thenReturn(projektList);
        assertEquals(1, projektManagementService.getAll().size());
    }

    @Test
    public void getById_Success() {
        Projekt projekt = new Projekt();
        projekt.setId(1L);
        projekt.setName("a");
        projekt.setClient("c");
        projekt.setBranch("d");
        projekt.setStartDate(new Date());
        projekt.setEndDate(new Date());
        when(projektPersistenceManager.getById(9L))
                .thenReturn(Optional.of(projekt));

        try {
            assertEquals("a", projektManagementService.getById(9L).getName());
        } catch (BusinessException e) {
            fail("Should not reach this point");
        }
    }

    @Test
    public void getById_ExpectedException() {

        when(projektPersistenceManager.getById(9L))
                .thenReturn(Optional.empty());

        try {
            assertEquals("a", projektManagementService.getById(9L).getName());
            fail("Should not reach this point");
        } catch (BusinessException e) {
            assertEquals(ExceptionCode.NAME_NOT_FOUND, e.getExceptionCode());
        }
    }

    @Test
    public void getByName_Success() {
        Projekt projekt = new Projekt();
        projekt.setId(1L);
        projekt.setName("a");
        projekt.setClient("c");
        projekt.setBranch("d");
        projekt.setStartDate(new Date());
        projekt.setEndDate(new Date());
        when(projektPersistenceManager.getByName(any(String.class)))
                .thenReturn(Optional.of(projekt));

        try {
            assertEquals(1L, (long) projektManagementService.getByName("a").getId());
        } catch (BusinessException e) {
            fail("Should not reach this point");
        }
    }

    @Test
    public void getByName_ExpectedException() {

        when(projektPersistenceManager.getByName(any(String.class)))
                .thenReturn(Optional.empty());

        try {
            projektManagementService.getByName("a");
            fail("Should not reach this point");
        } catch (BusinessException e) {
            assertEquals(ExceptionCode.NAME_NOT_FOUND, e.getExceptionCode());
        }
    }

}
