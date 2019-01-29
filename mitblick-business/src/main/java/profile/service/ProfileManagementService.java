package profile.service;

import com.sun.mail.smtp.SMTPTransport;
import exception.BusinessException;
import exception.ExceptionCode;
import notifications.dao.NotificationPersistenceManager;
import notifications.entities.Notification;
import profile.dao.ProfilePersistenceManager;
import profile.dto.FilterDTO;
import profile.dto.ProfileDTO;
import profile.dto.ProfileDTOHelper;
import profile.entities.JobTitle;
import profile.entities.Profile;
import profile.entities.ProfileSkillEntry;
import profile.entities.Region;
import projekt.dao.ProjektPersistenceManager;
import projekt.entities.Projekt;
import skills.dao.SkillPersistenceManager;
import skills.dto.SkillDTO;
import skills.entities.Skill;
import user.dao.UserPersistenceManager;
import user.entities.User;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.mail.*;
import javax.mail.internet.*;
import java.security.Security;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.Properties;


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


    /**
     * Create a Profile entity using a ProfileDTO.
     *
     * @param profileDTO
     * @return
     * @throws BusinessException
     */
    public ProfileDTO create(ProfileDTO profileDTO) throws BusinessException {
        validateForCreation(profileDTO);
        Profile profile = ProfileDTOHelper.toEntity(profileDTO);
        profilePersistenceManager.create(profile);
        sendNotifications(profile, "Profile created. Please review.", "CREATE");
        return ProfileDTOHelper.fromEntity(profile);
    }

    /**
     * Updates a Profile entity using a ProfileDTO.
     *
     * @param profileDTO
     * @return
     * @throws BusinessException
     */
    public ProfileDTO update(ProfileDTO profileDTO) throws BusinessException {
        Optional<Profile> profileBeforeOptional = profilePersistenceManager.getByEmail(profileDTO.getEmail());

        if (profileBeforeOptional.isPresent()) {
            Profile profileBefore = profileBeforeOptional.get();
            validateForUpdate(profileDTO);
            Profile profileAfter = ProfileDTOHelper.updateEntityWithDTO(profileBefore, profileDTO);

            profilePersistenceManager.update(profileAfter);
            if (profileAfter.getAccepted() == true) {
                profileAfter.setAccepted(false);
                sendNotifications(profileAfter, "Profile updated. Please review changes.", "UPDATE");
            }
            return ProfileDTOHelper.fromEntity(profileAfter);
        } else {
            throw new BusinessException(ExceptionCode.EMAIL_NOT_FOUND);
        }
    }

    /**
     * Sends notification to the supervisor of the person who has this profile.
     *
     * @param profileAfter
     * @param message
     */
    private void sendNotifications(Profile profileAfter, String message, String type) {
        Optional<User> supervisorOptional = userPersistenceMAnager.getSupervisor(profileAfter.getEmail());
        if (supervisorOptional.isPresent()) {
            Notification notification = new Notification();
            notification.setTitle("Profile Review");
            notification.setMessage(message);
            notification.setUserMail(profileAfter.getEmail());
            notification.setRead(false);
            notification.setType(type);
            Notification notif = notificationPersistenceManager.create(notification);
            supervisorOptional.get().getNotifications().add(notif);
            String mailBody = "Notification for employee" + profileAfter.getFirstName() + " " + profileAfter.getLastName() + ". " + message;
            sendMailUsingSSL(supervisorOptional.get().getEmail(), "Profile review", mailBody);
        }

    }

    /**
     * Deletes Profile
     *
     * @param profileDTO
     * @throws BusinessException
     */
    public void delete(ProfileDTO profileDTO) throws BusinessException {
        Optional<Profile> profile = profilePersistenceManager.getByEmail(profileDTO.getEmail());

        if (profile.isPresent()) {
            profilePersistenceManager.delete(profile.get());
        } else {
            throw new BusinessException(ExceptionCode.EMAIL_NOT_FOUND);
        }
    }

    /**
     * Gets all Profiles as a list of ProfileDTOs
     *
     * @return
     */
    public List<ProfileDTO> getAll() {
        return profilePersistenceManager.getAll()
                .stream()
                .map(ProfileDTOHelper::fromEntity)
                .collect(Collectors.toList());
    }

    public FilterDTO filterSupervisor(String supervisorEmail) {
        Optional<User> supervisor = userPersistenceMAnager.getUserByEmail(supervisorEmail);
        FilterDTO filterDTO = new FilterDTO();
        List<Profile> profiles = new ArrayList<Profile>();

        if (supervisor.isPresent()) {
            for (User emp : supervisor.get().getEmployees()) {
                Optional<Profile> empProfile = profilePersistenceManager.getByEmail(emp.getEmail());
                if (empProfile.isPresent()) {
                    profiles.add(empProfile.get());
                }
            }
        }

        filterDTO.setAmount(4);
        filterDTO.setProfiles(ProfileDTOHelper.fromEntity(profiles));
        return filterDTO;
    }

    public FilterDTO filter(int index, int amount, List<String> filterCriteriaNames, List<String> filterCriteriaValues, List<Long> skillIds) throws BusinessException {

        List<Profile> profiles = this.profilePersistenceManager.filter(index, amount, filterCriteriaNames, filterCriteriaValues);
        Integer filterAmount = this.profilePersistenceManager.filterAmount(filterCriteriaNames, filterCriteriaValues);

        if (skillIds != null) {
            if (!skillIds.isEmpty()) {
                int count = 0;
                boolean skillFlag = true;
                List<Profile> newProfiles = new LinkedList<>();
                for (Profile profile : profiles) {
                    for (Long id : skillIds) {
                        if (!profileHasSkill(profile, id)) {
                            skillFlag = false;
                        }
                    }
                    if (skillFlag) {
                        newProfiles.add(profile);
                        count++;
                    }
                    skillFlag = true;
                }
                filterAmount = count;
                profiles = newProfiles;
            }
        }

        FilterDTO filterDTO = new FilterDTO();
        filterDTO.setAmount(filterAmount);
        filterDTO.setProfiles(ProfileDTOHelper.fromEntity(profiles));
        return filterDTO;

    }

    /**
     * Checks if the given Profile has a certain skill.
     *
     * @param profile
     * @param skillID
     * @return
     * @throws BusinessException
     */
    private Boolean profileHasSkill(Profile profile, Long skillID) throws BusinessException {
        Skill skill = this.skillPersistenceManager.getById(skillID).orElseThrow(() -> new BusinessException(ExceptionCode.SKILL_NOT_FOUND));
        List<ProfileSkillEntry> skills = profile.getSkills();
        List<Skill> skillList = skills.stream()
                .map(ProfileSkillEntry::getSkill)
                .collect(Collectors.toList());
        return skillList.contains(skill);
    }


    /**
     * Gets a Profile by ID.
     *
     * @param id
     * @return
     * @throws BusinessException
     */
    public ProfileDTO getById(Long id) throws BusinessException {
        Optional<Profile> profileOptional = profilePersistenceManager.getById(id);
        if (profileOptional.isPresent()) {
            return ProfileDTOHelper.fromEntity(profileOptional.get());
        } else {
            throw new BusinessException(ExceptionCode.EMAIL_NOT_FOUND);
        }
    }

    /**
     * Gets a Profile by Email.
     *
     * @param email
     * @return
     * @throws BusinessException
     */
    public ProfileDTO getByEmail(String email) throws BusinessException {
        Optional<Profile> profileOptional = profilePersistenceManager.getByEmail(email);
        if (profileOptional.isPresent()) {
            return ProfileDTOHelper.fromEntity(profileOptional.get());
        } else {
            throw new BusinessException(ExceptionCode.EMAIL_NOT_FOUND);
        }
    }

    /**
     * Validates Profile for Creation
     *
     * @param profileDTO
     * @throws BusinessException
     */
    private void validateForCreation(ProfileDTO profileDTO) throws BusinessException {
        if (!isValidForCreation(profileDTO)) {
            throw new BusinessException(ExceptionCode.PROFILE_VALIDATION_EXCEPTION);
        }
    }

    /**
     * Validates Fields
     *
     * @param profileDTO
     * @return
     */
    private boolean validateFields(ProfileDTO profileDTO) {
        return
                profileDTO.getEmail() != null
                        && isValidEmail(profileDTO.getEmail());
    }

    /**
     * Checks if Profile is valid for Creation
     *
     * @param profileDTO
     * @return
     * @throws BusinessException
     */
    private boolean isValidForCreation(ProfileDTO profileDTO) throws BusinessException {
        if (profilePersistenceManager.getByEmail(profileDTO.getEmail()).isPresent()) {
            throw new BusinessException(ExceptionCode.EMAIL_EXISTS_ALREADY);
        }
        return validateFields(profileDTO);
    }

    /**
     * Checks if email is valid
     *
     * @param email
     * @return
     */
    private boolean isValidEmail(String email) {
        final Pattern validEmailAddressRegex =
                Pattern.compile("^[a-zA-Z][A-Za-z0-9._]*@[a-zA-z]+.[a-z]+$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = validEmailAddressRegex.matcher(email);
        return matcher.find();
    }

    /**
     * Checks if the list of skills is valid.
     *
     * @param skills
     * @return
     */
    private boolean isValidSkills(List<SkillDTO> skills) {
        return false;
    }

    /**
     * Validates Profile for Update
     *
     * @param profileDTO
     * @throws BusinessException
     */
    private void validateForUpdate(ProfileDTO profileDTO) throws BusinessException {
        if (!validateFields(profileDTO)) {
            throw new BusinessException(ExceptionCode.PROFILE_VALIDATION_EXCEPTION);
        }
    }

    /**
     * Adds skill to profile.
     *
     * @param skillId
     * @param skillAreaName
     * @param rating
     * @param email
     * @return
     * @throws BusinessException
     */
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
                if (profile.getAccepted() == true) {
                    profile.setAccepted(false);
                    sendNotifications(profile, "Skills in profile updated. Please review changes.", "SKILL");
                }
            } else {
                throw new BusinessException(ExceptionCode.SKILL_VALIDATION_EXCEPTION);
            }
            return ProfileDTOHelper.fromEntity(profile);
        } else {
            throw new BusinessException(ExceptionCode.EMAIL_NOT_FOUND);
        }
    }


    /**
     * Adds a project to profile.
     *
     * @param projektName
     * @param email
     * @return
     * @throws BusinessException
     */
    public ProfileDTO addProjekt(String projektName, String email) throws BusinessException {
        Optional<Profile> profileOptional = profilePersistenceManager.getByEmail(email);
        Optional<Projekt> projektOptional = projektPersistenceManager.getByName(projektName);

        if (profileOptional.isPresent() && projektOptional.isPresent()) {
            Profile profile = profileOptional.get();
            Projekt projekt = projektOptional.get();

            if (profile.getProjekts().indexOf(projekt) < 0) {
                profile.getProjekts().add(projekt);
                if (profile.getAccepted() == true) {
                    profile.setAccepted(false);
                    sendNotifications(profile, "Projects in profile updated. Please review changes.", "PROJECT");
                }
            } else {
                throw new BusinessException(ExceptionCode.PROJEKT_VALIDATION_EXCEPTION);
            }
            return ProfileDTOHelper.fromEntity(profile);
        } else {
            throw new BusinessException(ExceptionCode.EMAIL_NOT_FOUND);
        }
    }

    /**
     * Removes skill from Profile
     *
     * @param skillId
     * @param email
     * @return
     * @throws BusinessException
     */
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

    /**
     * Removes a project from profile.
     *
     * @param projektName
     * @param email
     * @return
     * @throws BusinessException
     */
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

            return ProfileDTOHelper.fromEntity(profile);
        } else {
            throw new BusinessException(ExceptionCode.EMAIL_NOT_FOUND);
        }
    }

    /**
     * Accept Profile after reviewing the changes.
     *
     * @param supervisorMail the supervisor who reviews and accepts changes
     * @param userMail       the user whose profile is reviewed.
     * @throws BusinessException
     */
    public void acceptProfile(String supervisorMail, String userMail) throws BusinessException {
        Optional<User> userOptional = userPersistenceMAnager.getUserByEmail(userMail);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.getSupervisorMail().equals(supervisorMail)) {
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

    /**
     * Unaccepts a profile.
     *
     * @param supervisorMail
     * @param userMail
     * @throws BusinessException
     */
    public void unacceptProfile(String supervisorMail, String userMail) throws BusinessException {
        Optional<User> userOptional = userPersistenceMAnager.getUserByEmail(userMail);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.getSupervisorMail().equals(supervisorMail)) {
                Optional<Profile> profileOptional = profilePersistenceManager.getByEmail(userMail);
                if (profileOptional.isPresent()) {
                    if(profileOptional.get().getAccepted() == true)
                        profileOptional.get().setAccepted(false);
                } else
                    throw new BusinessException(ExceptionCode.PROJEKT_VALIDATION_EXCEPTION);
            } else
                throw new BusinessException(ExceptionCode.USER_VALIDATION_EXCEPTION);
        } else
            throw new BusinessException(ExceptionCode.USER_VALIDATION_EXCEPTION);
    }


    /**
     * Get all job titles from the database.
     *
     * @return List of all job titles.
     */
    public List<JobTitle> getAllJobTitles() {
        return profilePersistenceManager.getAllJobTitles();
    }


    /**
     * Get all regions from the database.
     *
     * @return List of regions.
     */
    public List<Region> getAllRegions() {
        return profilePersistenceManager.getAllRegions();
    }


    public void sendMailUsingSSL(String recieverMail, String mailSubject, String mailMessage) {
        String host = "smtp.gmail.com";
        String username = "noreply.mitblick@gmail.com";
        String password = "mitblick123";
        String from = "noreply.mitblick@gmail.com";
        String to = recieverMail;
        String subject = mailSubject;
        String text = mailMessage;
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", "465");
        sendEMail(properties, username, password, from, to,
                subject, text);
    }

    public void sendEMail(Properties properties,
                          String username, String password,
                          String fromEmailAddress, String toEmailAddress,
                          String subject, String messageText) {
        Session session = Session.getInstance(properties,
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication
                    getPasswordAuthentication() {
                        return new PasswordAuthentication(username,
                                password);
                    }
                });
        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(fromEmailAddress));
            msg.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(toEmailAddress));
            msg.setSubject(subject);
            msg.setText(messageText);
            Transport.send(msg);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
