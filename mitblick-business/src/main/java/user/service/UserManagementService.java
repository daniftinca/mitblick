package user.service;


import exception.BusinessException;
import exception.ExceptionCode;
import user.dao.PermissionPersistenceManager;
import user.dao.UserPersistenceManager;
import user.dto.UserDTO;
import user.dto.UserDTOHelper;
import user.entities.Role;
import user.entities.User;
import utils.Encryptor;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Stateless
public class UserManagementService {
    public static final int MIN_USERNAME_LENGTH = 6;
    private static final int MIN_LAST_NAME_LENGTH = 5;
    private static final int MAX_FAILED_LOGN_ATTEMPTS = 5;


    @EJB
    private UserPersistenceManager userPersistenceManager;

    @EJB
    private PermissionPersistenceManager permissionPersistenceManager;

    /**
     * Creates a user entity using a user DTO.
     *
     * @param userDTO user information
     * @return : the user DTO of the created entity
     * @throws BusinessException
     */
    public UserDTO createUser(UserDTO userDTO) throws BusinessException {
        normalizeUserDTO(userDTO);
        validateUserForCreation(userDTO);
        User user = UserDTOHelper.toEntity(userDTO);
        user.setUsername(user.getEmail());
        user.setActive(true);
        user.setPassword(Encryptor.encrypt(userDTO.getPassword()));
        userPersistenceManager.createUser(user);
        return UserDTOHelper.fromEntity(user);
    }


    /**
     * Validates the DTO. To use before sending it further.
     *
     * @param userDTO
     * @throws BusinessException
     */
    private void validateUserForCreation(UserDTO userDTO) throws BusinessException {
        if (!isValidForCreation(userDTO)) {
            throw new BusinessException(ExceptionCode.USER_VALIDATION_EXCEPTION);
        }


    }


    /**
     * Trims stuff (first and last name)
     *
     * @param userDTO
     */
    private void normalizeUserDTO(UserDTO userDTO) {
        userDTO.setFirstName(userDTO.getFirstName().trim());
        userDTO.setLastName(userDTO.getLastName().trim());
    }


    private boolean validateFields(UserDTO userDTO) {
        return
                userDTO.getEmail() != null
                        && isValidEmail(userDTO.getEmail())
                        && isValidPhoneNumber(userDTO.getPhoneNumber());
    }

    private boolean isValidForCreation(UserDTO userDTO) throws BusinessException {
        //validate if email already exists
        if (userPersistenceManager.getUserByEmail(userDTO.getEmail()).isPresent()) {
            throw new BusinessException(ExceptionCode.EMAIL_EXISTS_ALREADY);
        }
        return validateFields(userDTO)
                && userDTO.getPassword() != null;
    }

    private boolean isValidEmail(String email) {
        final Pattern validEmailAddressRegex =
                Pattern.compile("^[a-zA-Z][A-Za-z0-9._]*@[a-zA-z]+.[a-z]+$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = validEmailAddressRegex.matcher(email);
        return matcher.find();
    }

    public List<User> getAllUsersWithRole(Role role) {
        return userPersistenceManager.getAllUsers()
                .stream()
                .filter(user -> user.getRoles().contains(role))
                .collect(Collectors.toList());
    }

    /**
     * Deactivates a user, removing them the ability to login, but keeping their bugs, comments, etc.
     * Additionally creates the specific notification.
     *
     * @param username
     */
    public void deactivateUser(String username) throws BusinessException {

        Optional<User> userOptional = userPersistenceManager.getUserByUsername(username);
        User user = userOptional.orElseThrow(() -> new BusinessException(ExceptionCode.USERNAME_NOT_VALID));
        user.setActive(false);
        userPersistenceManager.updateUser(user);

    }


    /**
     * Activates a user, granting them the ability to login.
     *
     * @param username
     */
    public void activateUser(String username) throws BusinessException {
        Optional<User> userOptional = userPersistenceManager.getUserByUsername(username);
        User user = userOptional.orElseThrow(() -> new BusinessException(ExceptionCode.USERNAME_NOT_VALID));
        user.setActive(true);
        userPersistenceManager.updateUser(user);

    }

    /**
     * Get a list of all Users that are registered.
     *
     * @return
     */
    public List<UserDTO> getAllUsers() {
        return userPersistenceManager.getAllUsers()
                .stream()
                .map(UserDTOHelper::fromEntity)
                .collect(Collectors.toList());
    }


    /**
     * Takes the username and password of a user and if they are correct, it returns the
     * corresponding DTO. Otherwise it will throw an exception.
     * If there were more than 5 failed login attempts for the user, it will deactivate the user.
     *
     * @param username
     * @param password
     * @return a user DTO if it succeeds.
     * @throws BusinessException
     */
    public UserDTO login(String username, String password) throws BusinessException {
        Optional<User> userOptional = userPersistenceManager.getUserByUsername(username);
        if (!userOptional.isPresent()) {
            throw new BusinessException(ExceptionCode.USERNAME_NOT_VALID);
        }
        if (!Encryptor.encrypt(password).equals(userOptional.get().getPassword())) {
            User user = userOptional.get();
            int failedAttempts = user.getFailedAttempts() == null ? 1 : user.getFailedAttempts() + 1;
            user.setFailedAttempts(failedAttempts);
            if (userOptional.get().getFailedAttempts() > MAX_FAILED_LOGN_ATTEMPTS) {
                deactivateUser(username);
            }
            throw new BusinessException(ExceptionCode.PASSWORD_NOT_VALID);
        }
        if (!userOptional.get().getActive()) {
            throw new BusinessException(ExceptionCode.USER_DEACTIVATED);
        }

        User user = userOptional.get();
        user.setFailedAttempts(0);
        return UserDTOHelper.fromEntity(userOptional.get());
    }


    private boolean isValidPhoneNumber(String phonenumber) {
        final Pattern validPhoneAddressRegex =
                Pattern.compile("(^\\+49)|(^\\+40)|(^0049)|(^0040)|(^0[1-9][1-9])", Pattern.CASE_INSENSITIVE);

        Matcher matcher = validPhoneAddressRegex.matcher(phonenumber);
        return matcher.find();
    }


    /**
     * Returns the user entity with the given username.
     *
     * @param username
     * @return
     * @throws BusinessException
     */
    public User getUserForUsername(String username) throws BusinessException {
        return userPersistenceManager
                .getUserByUsername(username)
                .orElseThrow(() -> new BusinessException(ExceptionCode.USERNAME_NOT_VALID));
    }

    /**
     * Returns the user with the given ID.
     *
     * @param id
     * @return
     * @throws BusinessException
     */
    public User getUserForId(Long id) throws BusinessException {
        Optional<User> userOptional = userPersistenceManager.getUserById(id);
        if (userOptional.isPresent()) {
            return userOptional.get();
        } else {
            throw new BusinessException(ExceptionCode.USERNAME_NOT_VALID);
        }
    }

    /**
     * Updates the user with the contents of the given DTO. If the name of the user has changed it will also
     * change the username.
     * Also creates the notification message and type to be sent to both users.
     *
     * @param userDTO
     * @return
     * @throws BusinessException
     */
    public UserDTO updateUser(UserDTO userDTO) throws BusinessException {

        Optional<User> userBeforeOptional = userPersistenceManager.getUserByUsername(userDTO.getUsername());

        if (userBeforeOptional.isPresent()) {
            User userBefore = userBeforeOptional.get();
            UserDTO userBeforeDto = UserDTOHelper.fromEntity(userBefore);
            normalizeUserDTO(userDTO);
            validateUserForUpdate(userDTO);

            User userAfter = UserDTOHelper.updateEntityWithDTO(userBefore, userDTO);


            userPersistenceManager.updateUser(userAfter);


            return userDTO;
        } else {
            throw new BusinessException(ExceptionCode.USERNAME_NOT_VALID);
        }
    }


    private void validateUserForUpdate(UserDTO userDTO) throws BusinessException {
        if (!validateFields(userDTO)) {
            throw new BusinessException(ExceptionCode.USER_VALIDATION_EXCEPTION);
        }
    }
}
