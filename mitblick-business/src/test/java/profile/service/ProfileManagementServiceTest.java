package profile.service;

import exception.BusinessException;
import exception.ExceptionCode;
import notifications.dao.NotificationPersistenceManager;
import notifications.entities.Notification;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import profile.dao.ProfilePersistenceManager;
import profile.dto.ProfileDTO;
import profile.dto.ProfileDTOHelper;
import profile.entities.Profile;
import projekt.dao.ProjektPersistenceManager;
import projekt.entities.Projekt;
import skills.dao.SkillPersistenceManager;
import skills.entities.Skill;
import user.dao.UserPersistenceManager;
import user.entities.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProfileManagementServiceTest {

    @InjectMocks
    private ProfileManagementService profileManagementService;

    @Mock
    private ProfilePersistenceManager profilePersistenceManager;

    @Mock
    private SkillPersistenceManager skillPersistenceManager;

    @Mock
    private ProjektPersistenceManager projektPersistenceManager;

    @Mock
    private UserPersistenceManager userPersistenceManager;

    @Mock
    private NotificationPersistenceManager notificationPersistenceManager;

    /**
     * Halo. Create nu mai merge pentru ca a aparut sendMail() in sendNotificationul vietii().
     * Se pare ca merge :D
     */
    @Test
    public void create_Success() {
        User user = new User();
        user.setNotifications(new ArrayList<>());
        user.setEmail("admin@mitblick.com");
        Notification notification = new Notification();
        when(profilePersistenceManager.getByEmail(any(String.class)))
                .thenReturn(Optional.empty());
        when(userPersistenceManager.getSupervisor(any(String.class)))
                .thenReturn(Optional.of(user));
        when(notificationPersistenceManager.create(any(Notification.class)))
                .thenReturn(notification);

        ProfileDTO profileDTO = new ProfileDTO();

        profileDTO.setFirstName("qwer");
        profileDTO.setLastName("asdf");
        profileDTO.setEmail("s@mitbkick.com");
        profileDTO.setSkills(new ArrayList<>());
        profileDTO.setProjekts(new ArrayList<>());
        profileDTO.setPhoto("");


        try {

            ProfileDTO createdProfile = profileManagementService.create(profileDTO);
            assertEquals(profileDTO.getEmail(), createdProfile.getEmail());
        } catch (BusinessException e) {
            fail("Should not reach this point.");
        }
    }

    @Test
    public void update_Success() {
        Profile profile = new Profile();

        profile.setFirstName("qwer");
        profile.setLastName("asdf");
        profile.setEmail("s@mitbkick.com");
        profile.setSkills(new ArrayList<>());
        profile.setProjekts(new ArrayList<>());
        profile.setPhoto(new byte[0]);

        when(profilePersistenceManager.getByEmail(any(String.class)))
                .thenReturn(Optional.of(profile));
        when(userPersistenceManager.getSupervisor(any(String.class)))
                .thenReturn(Optional.empty());

        try {

            profilePersistenceManager.create(profile);
            profile.setFirstName("a");
            ProfileDTO profileDTO = ProfileDTOHelper.fromEntity(profile);

            ProfileDTO updatedProfile = profileManagementService.update(profileDTO);

            assertEquals(profileDTO.getFirstName(), updatedProfile.getFirstName());
        } catch (BusinessException e) {
            fail("Should not reach this point.");
        }
    }

    @Test
    public void delete_Success() {
        Profile profile = new Profile();
        profile.setEmail("a@mitblick.com");
        when(profilePersistenceManager.getByEmail("a@mitblick.com"))
                .thenReturn(Optional.empty());

        ProfileDTO profileDTO = new ProfileDTO();

        profileDTO.setEmail("a@mitblick.com");

        try {

            profileManagementService.delete(profileDTO);
            fail("Should not reach this point.");
        } catch (BusinessException e) {
            assertEquals(ExceptionCode.EMAIL_NOT_FOUND, e.getExceptionCode());
        }
    }

    @Test
    public void delete_ExpectedException() {
        when(profilePersistenceManager.getByEmail(any(String.class)))
                .thenReturn(Optional.empty());
        when(userPersistenceManager.getSupervisor(any(String.class)))
                .thenReturn(Optional.empty());

        ProfileDTO profileDTO = new ProfileDTO();

        profileDTO.setFirstName("qwer");
        profileDTO.setLastName("asdf");
        profileDTO.setEmail("dsasdfasd@mitbkick.com");
        profileDTO.setSkills(new ArrayList<>());
        profileDTO.setProjekts(new ArrayList<>());
        profileDTO.setPhoto("");

        try {

            profileManagementService.delete(profileDTO);
            fail("Should not reach this point.");
        } catch (BusinessException e) {
            assertEquals(ExceptionCode.EMAIL_NOT_FOUND, e.getExceptionCode());
        }
    }

    @Test
    public void getAll() {
        Profile profile = new Profile();
        profile.setFirstName("doreld");
        List<Profile> profileList = new ArrayList<>();
        profileList.add(profile);
        when(profilePersistenceManager.getAll()).thenReturn(profileList);
        assertEquals(1, profileManagementService.getAll().size());
    }

    @Test
    public void filter() {
        //to do
    }

    @Test
    public void getById_Success() {
        Profile profile = new Profile();
        profile.setId(9L);
        profile.setEmail("a@mitblick.com");
        when(profilePersistenceManager.getById(9L))
                .thenReturn(Optional.of(profile));

        try {
            assertEquals("a@mitblick.com", profileManagementService.getById(9L).getEmail());
        } catch (BusinessException e) {
            fail("Should not reach this point");
        }
    }

    @Test
    public void getById_ExpectedException() {
        Profile profile = new Profile();
        profile.setId(9L);
        profile.setEmail("a@mitblick.com");
        when(profilePersistenceManager.getById(9L))
                .thenReturn(Optional.of(profile));
        when(profilePersistenceManager.getById(any(Long.class)))
                .thenReturn(Optional.empty());

        try {
            profileManagementService.getById(1L);
            fail("Should not reach this point");
        } catch (BusinessException e) {
            assertEquals(ExceptionCode.EMAIL_NOT_FOUND, e.getExceptionCode());
        }
    }

    @Test
    public void getByEmail_Success() {
        Profile profile = new Profile();
        profile.setEmail("a@mitblick.com");
        when(profilePersistenceManager.getByEmail("a@mitblick.com"))
                .thenReturn(Optional.of(profile));

        try {
            assertEquals("a@mitblick.com", profileManagementService.getByEmail("a@mitblick.com").getEmail());
        } catch (BusinessException e) {
            fail("Should not reach this point");
        }
    }

    @Test
    public void getByEmail_ExpectedException() {
        Profile profile = new Profile();
        profile.setEmail("a@mitblick.com");
        when(profilePersistenceManager.getByEmail("a@mitblick.com"))
                .thenReturn(Optional.of(profile));
        when(profilePersistenceManager.getByEmail(any(String.class)))
                .thenReturn(Optional.empty());


        try {
            profileManagementService.getByEmail("asdfa");
            fail("Should not reach this point");
        } catch (BusinessException e) {
            assertEquals(ExceptionCode.EMAIL_NOT_FOUND, e.getExceptionCode());
        }
    }

    @Test
    public void addSkill_Success() {
        Skill skill = new Skill();
        skill.setId(1L);
        skill.setName("asdas");

        Profile profile = new Profile();

        profile.setFirstName("qwer");
        profile.setLastName("asdf");
        profile.setEmail("a@mitblick.com");
        profile.setSkills(new ArrayList<>());
        profile.setProjekts(new ArrayList<>());
        profile.setPhoto(new byte[0]);

        when(profilePersistenceManager.getByEmail(any(String.class)))
                .thenReturn(Optional.of(profile));
        when(skillPersistenceManager.getById(any(Long.class)))
                .thenReturn(Optional.of(skill));

        try {

            profileManagementService.addSkill(1L, "a", 1, "a@mitblick.com");

        } catch (BusinessException e) {
            fail("Should not reach this point.");
        }
    }

    @Test
    public void addProject_Success() {
        Projekt projekt = new Projekt();
        projekt.setName("a");

        Profile profile = new Profile();

        profile.setFirstName("qwer");
        profile.setLastName("asdf");
        profile.setEmail("a@mitblick.com");
        profile.setSkills(new ArrayList<>());
        profile.setProjekts(new ArrayList<>());
        profile.setPhoto(new byte[0]);

        when(profilePersistenceManager.getByEmail(any(String.class)))
                .thenReturn(Optional.of(profile));
        when(projektPersistenceManager.getByName(any(String.class)))
                .thenReturn(Optional.of(projekt));

        try {

            profileManagementService.addProjekt("a", "a@mitblick.com");

        } catch (BusinessException e) {
            fail("Should not reach this point.");
        }
    }

    @Test
    public void removeSkill_Success() {
        Skill skill = new Skill();
        skill.setId(1L);
        skill.setName("asdas");

        Profile profile = new Profile();

        profile.setFirstName("qwer");
        profile.setLastName("asdf");
        profile.setEmail("a@mitblick.com");
        profile.setSkills(new ArrayList<>());
        profile.setProjekts(new ArrayList<>());
        profile.setPhoto(new byte[0]);

        when(profilePersistenceManager.getByEmail(any(String.class)))
                .thenReturn(Optional.of(profile));
        when(skillPersistenceManager.getById(any(Long.class)))
                .thenReturn(Optional.of(skill));

        try {

            profileManagementService.addSkill(1L, "a", 1, "a@mitblick.com");
            profileManagementService.removeSkill(1L, "a@mitblick.com");
        } catch (BusinessException e) {
            fail("Should not reach this point.");
        }
    }

    @Test
    public void removeSkill_ExpectedException() {
        when(profilePersistenceManager.getByEmail(any(String.class)))
                .thenReturn(Optional.empty());

        try {
            profileManagementService.removeSkill(1L, "a@mitblick.com");
            fail("Should not reach this point");
        } catch (BusinessException e) {
            assertEquals(ExceptionCode.EMAIL_NOT_FOUND, e.getExceptionCode());
        }
    }

    @Test
    public void removeProject_Success() {
        Projekt projekt = new Projekt();
        projekt.setName("a");

        Profile profile = new Profile();

        profile.setFirstName("qwer");
        profile.setLastName("asdf");
        profile.setEmail("a@mitblick.com");
        profile.setSkills(new ArrayList<>());
        profile.setProjekts(new ArrayList<>());
        profile.setPhoto(new byte[0]);

        when(profilePersistenceManager.getByEmail(any(String.class)))
                .thenReturn(Optional.of(profile));
        when(projektPersistenceManager.getByName(any(String.class)))
                .thenReturn(Optional.of(projekt));

        try {

            profileManagementService.addProjekt("a", "a@mitblick.com");
            profileManagementService.removeProjekt("a", "a@mitblick.com");

        } catch (BusinessException e) {
            fail("Should not reach this point.");
        }
    }

    @Test
    public void removeProject_ExpectedException() {
        when(profilePersistenceManager.getByEmail(any(String.class)))
                .thenReturn(Optional.empty());

        try {
            profileManagementService.removeProjekt("a", "a@mitblick.com");
            fail("Should not reach this point");
        } catch (BusinessException e) {
            assertEquals(ExceptionCode.EMAIL_NOT_FOUND, e.getExceptionCode());
        }

    }


}