package profile.service;

import exception.BusinessException;
import exception.ExceptionCode;
import interfaces.DAO;
import interfaces.Service;
import profil.dao.ProfilePersistenceManager;
import profil.entities.Profile;
import profile.dto.ProfileDTO;
import profile.dto.ProfileDTOHelper;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProfileManagementService implements Service<ProfileDTO> {

    private DAO profilePersistenceManager = new ProfilePersistenceManager();

    //must be changed
    private String skillService;

    //must be changed
    private String projektService;


    @Override
    public ProfileDTO create(ProfileDTO profileDTO) throws BusinessException {
        validateProfileForCreation(profileDTO);
        Profile profile = ProfileDTOHelper.toEntity(profileDTO);
        profilePersistenceManager.create(profile);
        return ProfileDTOHelper.fromEntity(profile);
    }

    @Override
    public void update(ProfileDTO profile) {
        profilePersistenceManager.update(profile);
    }

    @Override
    public void delete(ProfileDTO profile) {
        profilePersistenceManager.delete(profile);
    }

    @Override
    public List<ProfileDTO> getAll() {
        return profilePersistenceManager.getAll();
    }

    @Override
    public Optional<ProfileDTO> getById(Long id) {
        return profilePersistenceManager.getById(id);
    }

    private void validateProfileForCreation(ProfileDTO profileDTO) throws BusinessException {
        if (!isValidForCreation(profileDTO)) {
            throw new BusinessException(ExceptionCode.USER_VALIDATION_EXCEPTION);
        }
    }

    private boolean validateFields(ProfileDTO profileDTO) {
        return
                profileDTO.getEmail() != null
                        && isValidEmail(profileDTO.getEmail());
    }

    private boolean isValidForCreation(ProfileDTO profileDTO) throws BusinessException {
        ProfilePersistenceManager p = (ProfilePersistenceManager) profilePersistenceManager;
        if (p.getByEmail(profileDTO.getEmail()).isPresent()) {
            throw new BusinessException(ExceptionCode.EMAIL_EXISTS_ALREADY);
        }
        return validateFields(profileDTO)
                && profileDTO.getName() != null;
    }

    private boolean isValidEmail(String email) {
        final Pattern validEmailAddressRegex =
                Pattern.compile("^[a-zA-Z][A-Za-z0-9._]*@[a-zA-z]+.[a-z]+$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = validEmailAddressRegex.matcher(email);
        return matcher.find();
    }

}
