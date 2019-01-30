package user.service;

import exception.BusinessException;
import exception.ExceptionCode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import profile.dao.ProfilePersistenceManager;
import profile.entities.Profile;
import user.dao.UserPersistenceManager;
import user.dto.UserDTO;
import user.entities.Role;
import user.entities.User;
import utils.Encryptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserManagementServiceTest {

    @InjectMocks
    private UserManagementService userManagementService;

    @Mock
    private UserPersistenceManager userPersistenceManager;

    @Mock
    private ProfilePersistenceManager profilePersistenceManager;


    @Test
    public void createUser_Success() {
        //to do
    }

    @Test
    public void createUser_ExpectedException() {
        User user = new User();
        user.setEmail("a@mitblick.com");
        user.setPassword("dsfaa12!");

        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("a@mitblick.com");
        userDTO.setPassword("dsfaa12!");

        when(userPersistenceManager.createUser(any(User.class)))
                .thenReturn(user);
        when(userPersistenceManager.getUserByEmail(any(String.class)))
                .thenReturn(Optional.of(user));
        when(profilePersistenceManager.create(any(Profile.class)))
                .thenReturn(new Profile());

        try {

            userManagementService.createUser(userDTO);
            fail("Should not reach this point.");
        } catch (BusinessException e) {
            assertEquals(ExceptionCode.EMAIL_EXISTS_ALREADY, e.getExceptionCode());
        }
    }

    @Test
    public void getAllUsersWithRole() {
        Role role = new Role();

        User user = new User();
        user.setRoles(new ArrayList<>());

        User user1 = new User();
        user1.setRoles(new ArrayList<>());

        List<User> userList = new ArrayList<>();
        userList.add(user);
        userList.add(user1);
        user.getRoles().add(role);

        when(userPersistenceManager.getAllUsers())
                .thenReturn(userList);

        assertEquals(1, userManagementService.getAllUsersWithRole(role).size());
    }

    @Test
    public void deactivateUser_Success() {
        User user = new User();
        user.setPassword("a");
        user.setEmail("admin@mitblick.com");
        user.setActive(true);

        UserDTO userDTO = new UserDTO();
        userDTO.setPassword("a1@");
        userDTO.setEmail("admin@mitblick.com");

        when(userPersistenceManager.getUserByEmail(any(String.class)))
                .thenReturn(Optional.of(user));

        try {
            userManagementService.deactivateUser("admin@mitblick.com");
            assertEquals(false, userManagementService.getUserForEmail("admin@mitblick.com").getActive());
        } catch (BusinessException e) {
            fail("Should not reach this point.");
        }
    }

    @Test
    public void deactivateUser_ExpectedException() {
        when(userPersistenceManager.getUserByEmail(any(String.class)))
                .thenReturn(Optional.empty());

        try {
            userManagementService.deactivateUser("admin@mitblick.com");
            fail("Should not reach this point.");
        } catch (BusinessException e) {
            assertEquals(ExceptionCode.EMAIL_NOT_FOUND, e.getExceptionCode());
        }
    }

    @Test
    public void activateUser_Success() {
        User user = new User();
        user.setPassword("a");
        user.setEmail("admin@mitblick.com");
        user.setActive(false);

        UserDTO userDTO = new UserDTO();
        userDTO.setPassword("a1@");
        userDTO.setEmail("admin@mitblick.com");

        when(userPersistenceManager.getUserByEmail(any(String.class)))
                .thenReturn(Optional.of(user));

        try {
            userManagementService.activateUser("admin@mitblick.com");
            assertEquals(true, userManagementService.getUserForEmail("admin@mitblick.com").getActive());
        } catch (BusinessException e) {
            fail("Should not reach this point.");
        }
    }

    @Test
    public void activateUser_ExpectedException() {

        when(userPersistenceManager.getUserByEmail(any(String.class)))
                .thenReturn(Optional.empty());

        try {
            userManagementService.activateUser("admin@mitblick.com");
            fail("Should not reach this point.");
        } catch (BusinessException e) {
            assertEquals(ExceptionCode.EMAIL_NOT_FOUND, e.getExceptionCode());
        }
    }

    @Test
    public void getAllUsers() {

        User user = new User();
        User user1 = new User();

        List<User> userList = new ArrayList<>();
        userList.add(user);
        userList.add(user1);

        when(userPersistenceManager.getAllUsers())
                .thenReturn(userList);

        assertEquals(2, userManagementService.getAllUsers().size());
    }

    @Test
    public void login_Success() {
        User user = mock(User.class);
        user.setEmail("a@mitblick.com");

        when(userPersistenceManager.getUserByEmail(any(String.class)))
                .thenReturn(Optional.of(user));
        when(user.getEmail()).thenReturn("a@mitblick.com");
        when(user.getPassword()).thenReturn(Encryptor.encrypt("secret"));
        when(user.getActive()).thenReturn(true);

        try {
            UserDTO userDTO = userManagementService.login("a@mitblick.com", "secret");
            assertEquals("a@mitblick.com", userDTO.getEmail());
        } catch (BusinessException e) {
            fail("Should not reach this point.");
        }

    }

    @Test
    public void login_ExpectedException_EmailNotFound() {

        when(userPersistenceManager.getUserByEmail(any(String.class)))
                .thenReturn(Optional.empty());

        try {
            userManagementService.login("a@mitblick.com", "secret");
            fail("Should not reach this point.");
        } catch (BusinessException e) {
            assertEquals(ExceptionCode.EMAIL_NOT_FOUND, e.getExceptionCode());
        }
    }

    @Test
    public void login_ExpectedException_UserDeactivated() {
        User user = mock(User.class);

        when(userPersistenceManager.getUserByEmail(any(String.class)))
                .thenReturn(Optional.of(user));
        when(user.getEmail()).thenReturn("a@mitblick.com");
        when(user.getPassword()).thenReturn(Encryptor.encrypt("secret"));

        try {
            userManagementService.login("a@mitblick.com", "secret");
            fail("Should not reach this point.");
        } catch (BusinessException e) {
            assertEquals(ExceptionCode.USER_DEACTIVATED, e.getExceptionCode());
        }
    }

    @Test
    public void login_ExpectedException_PasswordNotValid() {
        User user = mock(User.class);

        when(userPersistenceManager.getUserByEmail(any(String.class)))
                .thenReturn(Optional.of(user));
        when(user.getEmail()).thenReturn("a@mitblick.com");
        when(user.getPassword()).thenReturn(Encryptor.encrypt("secret"));
        when(user.getActive()).thenReturn(true);

        try {
            userManagementService.login("a@mitblick.com", "bla");
            fail("Should not reach this point.");
        } catch (BusinessException e) {
            assertEquals(ExceptionCode.PASSWORD_NOT_VALID, e.getExceptionCode());
        }
    }

    @Test
    public void getUserForEmail_Success() {
        User user = new User();
        user.setId(1L);
        user.setEmail("a@mitblick.com");

        when(userPersistenceManager.getUserByEmail("a@mitblick.com"))
                .thenReturn(Optional.of(user));

        try {
            assertEquals(1L, (long) userManagementService.getUserForEmail("a@mitblick.com").getId());
        } catch (BusinessException e) {
            fail("Should not reach this point.");
        }
    }

    @Test
    public void getUserForEmail_ExpectedException() {

        when(userPersistenceManager.getUserByEmail("a@mitblick.com"))
                .thenReturn(Optional.empty());

        try {
            userManagementService.getUserForEmail("a@mitblick.com");
            fail("Should not reach this point.");
        } catch (BusinessException e) {
            assertEquals(ExceptionCode.EMAIL_NOT_FOUND, e.getExceptionCode());
        }
    }

    @Test
    public void getUserForId_Success() {
        User user = new User();
        user.setId(1L);
        user.setEmail("a@mitblick.com");

        when(userPersistenceManager.getUserById(1L))
                .thenReturn(Optional.of(user));

        try {
            assertEquals(user.getEmail(), userManagementService.getUserForId(1L).getEmail());
        } catch (BusinessException e) {
            fail("Should not reach this point.");
        }
    }

    @Test
    public void getUserForId_ExpectedException() {
        when(userPersistenceManager.getUserById(1L))
                .thenReturn(Optional.empty());

        try {
            userManagementService.getUserForId(1L);
            fail("Should not reach this point.");
        } catch (BusinessException e) {
            assertEquals(ExceptionCode.EMAIL_NOT_FOUND, e.getExceptionCode());
        }
    }

    @Test
    public void updateUser_Success() {
        User user = new User();
        user.setPassword("a");
        user.setEmail("admin@mitblick.com");

        UserDTO userDTO = new UserDTO();
        userDTO.setPassword("a1@");
        userDTO.setEmail("admin@mitblick.com");

        when(userPersistenceManager.getUserByEmail(any(String.class)))
                .thenReturn(Optional.of(user));

        try {
            assertEquals("a1@", userManagementService.updateUser(userDTO).getPassword());
        } catch (BusinessException e) {
            fail("Should not reach this point.");
        }
    }

    @Test
    public void updateUser_ExpectedException() {
        UserDTO userDTO = new UserDTO();
        userDTO.setPassword("a1@");
        userDTO.setEmail("admin@mitblick.com");

        when(userPersistenceManager.getUserByEmail(any(String.class)))
                .thenReturn(Optional.empty());

        try {
            userManagementService.updateUser(userDTO);
            fail("Should not reach this point.");
        } catch (BusinessException e) {
            assertEquals(ExceptionCode.EMAIL_NOT_FOUND, e.getExceptionCode());
        }
    }
}
