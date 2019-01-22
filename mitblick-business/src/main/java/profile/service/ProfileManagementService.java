package profile.service;

import exception.BusinessException;
import exception.ExceptionCode;
import profile.dao.ProfilePersistenceManager;
import profile.dto.ProfileDTO;
import profile.dto.ProfileDTOHelper;
import profile.entities.Profile;
import skills.dao.SkillPersistenceManager;
import skills.dto.SkillDTO;
import skills.entities.Skill;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Stateless
public class ProfileManagementService {

    @EJB
    private ProfilePersistenceManager profilePersistenceManager;

    @EJB
    private SkillPersistenceManager skillPersistenceManager;


    public ProfileDTO create(ProfileDTO profileDTO) throws BusinessException {
        validateForCreation(profileDTO);
        Profile profile = ProfileDTOHelper.toEntity(profileDTO);
        profilePersistenceManager.create(profile);
        return ProfileDTOHelper.fromEntity(profile);
    }

    public ProfileDTO update(ProfileDTO profileDTO) throws BusinessException {
        Optional<Profile> profileBeforeOptional = profilePersistenceManager.getByEmail(profileDTO.getEmail());

        if (profileBeforeOptional.isPresent()) {
            Profile profileBefore = profileBeforeOptional.get();
            validateForUpdate(profileDTO);
            Profile profileAfter = ProfileDTOHelper.updateEntityWithDTO(profileBefore, profileDTO);

            profilePersistenceManager.update(profileAfter);

            return profileDTO;
        } else {
            throw new BusinessException(ExceptionCode.EMAIL_NOT_FOUND);
        }
    }

    public void delete(ProfileDTO profileDTO) throws BusinessException {
        Optional<Profile> profile = profilePersistenceManager.getByEmail(profileDTO.getEmail());

        if (profile.isPresent()) {
            profilePersistenceManager.delete(profile.get());
        } else {
            throw new BusinessException(ExceptionCode.EMAIL_NOT_FOUND);
        }
    }

    public List<ProfileDTO> getAll() {
        return profilePersistenceManager.getAll()
                .stream()
                .map(ProfileDTOHelper::fromEntity)
                .collect(Collectors.toList());
    }

    public ProfileDTO getById(Long id) throws BusinessException {
        Optional<Profile> profileOptional = profilePersistenceManager.getById(id);
        if (profileOptional.isPresent()) {
            return ProfileDTOHelper.fromEntity(profileOptional.get());
        } else {
            throw new BusinessException(ExceptionCode.EMAIL_NOT_FOUND);
        }
    }

    public ProfileDTO getByEmail(String email) throws BusinessException {
        Optional<Profile> profileOptional = profilePersistenceManager.getByEmail(email);
        if (profileOptional.isPresent()) {
            return ProfileDTOHelper.fromEntity(profileOptional.get());
        } else {
            throw new BusinessException(ExceptionCode.EMAIL_NOT_FOUND);
        }
    }

    private void validateForCreation(ProfileDTO profileDTO) throws BusinessException {
        if (!isValidForCreation(profileDTO)) {
            throw new BusinessException(ExceptionCode.PROFILE_VALIDATION_EXCEPTION);
        }
    }

    private boolean validateFields(ProfileDTO profileDTO) {
        return
                profileDTO.getEmail() != null
                        && isValidEmail(profileDTO.getEmail());
    }

    private boolean isValidForCreation(ProfileDTO profileDTO) throws BusinessException {
        if (profilePersistenceManager.getByEmail(profileDTO.getEmail()).isPresent()) {
            throw new BusinessException(ExceptionCode.EMAIL_EXISTS_ALREADY);
        }
        return validateFields(profileDTO);
    }

    private boolean isValidEmail(String email) {
        final Pattern validEmailAddressRegex =
                Pattern.compile("^[a-zA-Z][A-Za-z0-9._]*@[a-zA-z]+.[a-z]+$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = validEmailAddressRegex.matcher(email);
        return matcher.find();
    }

    private boolean isValidSkills(List<SkillDTO> skills) {
        return false;
    }

    private void validateForUpdate(ProfileDTO profileDTO) throws BusinessException {
        if (!validateFields(profileDTO)) {
            throw new BusinessException(ExceptionCode.PROFILE_VALIDATION_EXCEPTION);
        }
    }

    public ProfileDTO addSkill(String skillName, String email) throws BusinessException {
        Optional<Profile> profileOptional = profilePersistenceManager.getByEmail(email);
        Optional<Skill> skillOptional = skillPersistenceManager.getByName(skillName);

        if (profileOptional.isPresent() && skillOptional.isPresent()) {
            Profile profile = profileOptional.get();
            Skill skill = skillOptional.get();

            if (profile.getSkills().indexOf(skill) < 0) {
                profile.getSkills().add(skill);
            } else {
                throw new BusinessException(ExceptionCode.PROJEKT_VALIDATION_EXCEPTION);
            }


            profilePersistenceManager.update(profile);

            return ProfileDTOHelper.fromEntity(profile);
        } else {
            throw new BusinessException(ExceptionCode.EMAIL_NOT_FOUND);
        }
    }

}
