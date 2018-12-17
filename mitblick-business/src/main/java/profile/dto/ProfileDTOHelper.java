package profile.dto;

import profil.entities.Profile;

public class ProfileDTOHelper {

    private ProfileDTOHelper() {
        //empty private constructor to hide the public implicit one
    }

    public static ProfileDTO fromEntity(Profile profile) {

        return new ProfileDTO(profile.getName(), profile.getEmail(), profile.getPhoto(),
                profile.getSkills(), profile.getProjekts());
    }

    public static Profile toEntity(ProfileDTO profileDTO) {

        return new Profile(profileDTO.getName(), profileDTO.getEmail(), profileDTO.getPhoto(),
                profileDTO.getSkills(), profileDTO.getProjekts());

    }

}
