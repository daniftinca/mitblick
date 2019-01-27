package profile.service;

import exception.BusinessException;
import exception.ExceptionCode;
import notifications.dao.NotificationPersistenceManager;
import notifications.entities.Notification;
import profile.dao.ProfilePersistenceManager;
import profile.dto.ProfileDTO;
import profile.dto.ProfileDTOHelper;
import profile.entities.Profile;
import profile.entities.ProfileSkillEntry;
import projekt.dao.ProjektPersistenceManager;
import projekt.entities.Projekt;
import skills.dao.SkillPersistenceManager;
import skills.dto.SkillDTO;
import skills.entities.Skill;
import user.dao.UserPersistenceManager;
import user.entities.User;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.ArrayList;
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

    @EJB
    private ProjektPersistenceManager projektPersistenceManager;

    @EJB
    private UserPersistenceManager userPersistenceMAnager;

    @EJB
    private NotificationPersistenceManager notificationPersistenceManager;


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

            sendNotifications(profileAfter);
            return ProfileDTOHelper.fromEntity(profileAfter);
        } else {
            throw new BusinessException(ExceptionCode.EMAIL_NOT_FOUND);
        }
    }

    private void sendNotifications(Profile profileAfter) {
        Optional<User> supervisorOptional = userPersistenceMAnager.getSupervisor(profileAfter.getEmail());
        Notification notification = new Notification();
        notification.setTitle("Profile Review");
        notification.setMessage("Profile updated. Please review changes.");
        notification.setUserMail(profileAfter.getEmail());
        notification.setRead(false);
        Notification notif = notificationPersistenceManager.create(notification);

        if (supervisorOptional.isPresent())
            supervisorOptional.get().getNotifications().add(notif);

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




    //TODO : make to lists
    public List<Profile> filter(List<String> filterCriteriaNames, List<String> filterCriteriaValues) {
        return new ArrayList<>();
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

    public ProfileDTO addSkill(Long skillId, String skillAreaName, Integer rating, String email) throws BusinessException {
        Optional<Profile> profileOptional = profilePersistenceManager.getByEmail(email);
        Optional<Skill> skillOptional = skillPersistenceManager.getById(skillId);

        if (profileOptional.isPresent() && skillOptional.isPresent()) {
            Profile profile = profileOptional.get();

            Skill skill = skillOptional.get();
            ProfileSkillEntry skillEntry = new ProfileSkillEntry();
            skillEntry.setSkill(skill);
            skillEntry.setRating(rating);
            skillEntry.setSkillAreaName(skillAreaName);
            if (profile.getSkills().indexOf(skillEntry) < 0) {
                profile.getSkills().add(skillEntry);
            } else {
                throw new BusinessException(ExceptionCode.SKILL_VALIDATION_EXCEPTION);
            }


            profilePersistenceManager.update(profile);

            return ProfileDTOHelper.fromEntity(profile);
        } else {
            throw new BusinessException(ExceptionCode.EMAIL_NOT_FOUND);
        }
    }

    public ProfileDTO addProjekt(String projektName, String email) throws BusinessException {
        Optional<Profile> profileOptional = profilePersistenceManager.getByEmail(email);
        Optional<Projekt> projektOptional = projektPersistenceManager.getByName(projektName);

        if (profileOptional.isPresent() && projektOptional.isPresent()) {
            Profile profile = profileOptional.get();
            Projekt projekt = projektOptional.get();

            if (profile.getProjekts().indexOf(projekt) < 0) {
                profile.getProjekts().add(projekt);
            } else {
                throw new BusinessException(ExceptionCode.PROJEKT_VALIDATION_EXCEPTION);
            }


            profilePersistenceManager.update(profile);

            return ProfileDTOHelper.fromEntity(profile);
        } else {
            throw new BusinessException(ExceptionCode.EMAIL_NOT_FOUND);
        }
    }


    public ProfileDTO removeSkill(Long skillId, String email) throws BusinessException {
        Optional<Profile> profileOptional = profilePersistenceManager.getByEmail(email);
        Optional<Skill> skillOptional = skillPersistenceManager.getById(skillId);

        if (profileOptional.isPresent() && skillOptional.isPresent()) {
            Profile profile = profileOptional.get();
            Skill skill = skillOptional.get();

            profile.setSkills(
                    profile.getSkills().stream()
                            .filter(profileSkillEntry -> !profileSkillEntry.getSkill().getId().equals(skill.getId()))
                            .collect(Collectors.toList())
            );

            return ProfileDTOHelper.fromEntity(profile);
        } else {
            throw new BusinessException(ExceptionCode.EMAIL_NOT_FOUND);
        }
    }

    public ProfileDTO removeProjekt(String projektName, String email) throws BusinessException {
        Optional<Profile> profileOptional = profilePersistenceManager.getByEmail(email);
        Optional<Projekt> projektOptional = projektPersistenceManager.getByName(projektName);

        if (profileOptional.isPresent() && projektOptional.isPresent()) {
            Profile profile = profileOptional.get();
            Projekt projekt = projektOptional.get();

            if (profile.getProjekts().indexOf(projekt) != -1) {
                profile.getProjekts().remove(projekt);
            } else {
                throw new BusinessException(ExceptionCode.PROJEKT_VALIDATION_EXCEPTION);
            }


            profilePersistenceManager.update(profile);

            return ProfileDTOHelper.fromEntity(profile);
        } else {
            throw new BusinessException(ExceptionCode.EMAIL_NOT_FOUND);
        }
    }

    public void acceptProfile(String supervisorMail, String userMail) throws BusinessException {
        Optional<User> userOptional = userPersistenceMAnager.getUserByEmail(userMail);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.getSupervisorMail() == supervisorMail) {
                Optional<Profile> profileOptional = profilePersistenceManager.getByEmail(userMail);
                if (profileOptional.isPresent()) {
                    profileOptional.get().setAccepted(true);
                } else
                    throw new BusinessException(ExceptionCode.PROJEKT_VALIDATION_EXCEPTION);
            } else
                throw new BusinessException(ExceptionCode.USER_VALIDATION_EXCEPTION);
        } else
            throw new BusinessException(ExceptionCode.USER_VALIDATION_EXCEPTION);
    }
}
