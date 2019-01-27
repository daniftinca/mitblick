package profile.dto;

import profile.entities.Profile;
import projekt.dto.ProjektDTOHelper;

import java.util.ArrayList;

public class ProfileDTOHelper {

    private ProfileDTOHelper() {
        //empty private constructor to hide the public implicit one
    }

    public static ProfileDTO fromEntity(Profile profile) {

        ProfileDTO profileDTO = new ProfileDTO();

        profileDTO.setFirstName(profile.getFirstName());
        profileDTO.setLastName(profile.getLastName());
        profileDTO.setEmail(profile.getEmail());
        if (profile.getPhoto() != null) {
            profileDTO.setPhoto(new String(profile.getPhoto()));
        }
        profileDTO.setRegion(profile.getRegion());
        profileDTO.setJobTitle(profile.getJobTitle());
        profileDTO.setSkills(profile.getSkills());
        profileDTO.setProjekts(ProjektDTOHelper.fromEntity(profile.getProjekts()));

        return profileDTO;
    }

    public static Profile toEntity(ProfileDTO profileDTO) {

        Profile profile = new Profile();

        profile.setFirstName(profileDTO.getFirstName());
        profile.setLastName(profileDTO.getLastName());
        profile.setEmail(profileDTO.getEmail());
        if (profileDTO.getPhoto() != null) {
            profile.setPhoto(profileDTO.getPhoto().getBytes());
        }
        profile.setRegion(profileDTO.getRegion());
        profile.setJobTitle(profileDTO.getJobTitle());
        profile.setSkills(profileDTO.getSkills());
        profile.setProjekts(ProjektDTOHelper.toEntity(profileDTO.getProjekts()));

        return profile;
    }

    public static Profile updateEntityWithDTO(Profile profile, ProfileDTO profileDTO) {
        profile.setEmail(profileDTO.getEmail());

        if (profileDTO.getFirstName() != null) {
            profile.setFirstName(profileDTO.getFirstName());
        }
        if (profileDTO.getLastName() != null) {
            profile.setLastName(profileDTO.getLastName());
        }
        if (profileDTO.getPhoto() != null) {
            profile.setPhoto(profileDTO.getPhoto().getBytes());
        }
        if (profileDTO.getRegion() != null) {
            profile.setRegion(profileDTO.getRegion());
        }
        if (profileDTO.getJobTitle() != null) {
            profile.setJobTitle(profileDTO.getJobTitle());
        }
        if (profileDTO.getSkills() != null) {
            profile.setSkills(profileDTO.getSkills());
        }
        if (profileDTO.getProjekts() != null) {
            profile.setProjekts(ProjektDTOHelper.toEntity(profileDTO.getProjekts()));
        }
        return profile;
    }

    public static ProfileDTO generateGenericProfileDTO(String email) {
        ProfileDTO profileDTO = new ProfileDTO();

        profileDTO.setFirstName("qwer");
        profileDTO.setLastName("asdf");
        profileDTO.setEmail(email);
        profileDTO.setSkills(new ArrayList<>());
        profileDTO.setProjekts(new ArrayList<>());
        profileDTO.setPhoto("");

        return profileDTO;
    }

}
