package profile.dto;

import profile.entities.Profile;
import projekt.dto.ProjektDTOHelper;

import java.util.ArrayList;
import java.util.List;

public class ProfileDTOHelper {
    private static String genericPhoto = "data:image/gif;base64,R0lGODlhyAB+AMQAAM3V5e3w9sXO4dzi7dXc6eXp8vn6/PT1+crS4+ru9PL0+OLm8Nje6t/k7v39/tTa6Nvg7Pb4+tHY5/7+/+/y9+js8/v8/fj5+/z8/cfQ4s/X5uzv9cvT5MzU5P///8TN4CwAAAAAyAB+AAAF/+AnjmRpnmiqrmzrvnAsz3Rt33iu73zv/8CgcEgsGo/IpHLJbDqf0Kh0Sq1abQIAATJoXL9gGwISuDg8aE8izG6nOIxKeo5euO/uTiFC71PwgFcSBX2FaACBiVEDfIaGA4qRSwuOlWgakplEDBiWngyaoT0IB56maqKpNwOnrRcZqrEwAa21HgSyuSkZBra2XrrBIh2+xWvCuRrFy3/IqhzL0cfOoWfRyxDUmpTX0QLakt3XduCJrOLR5YkU6NGI6ni97cXA8G0I88vT9mHK+b4K+LUh8M/XAYFsCFYiUWuEKQsIwzRY+AFNRVMOPVyshOFbxCvcDG3UOFLkHBGWLP94/FglZKGSJV/SiUlHJUsrLj3RtLQzjc2bVHIW9PQTqBShQysVNQplok6UGKESXcn0yYOnGSlm5Ui1ahOFjk5oZWjpgtcoYF+iELm20sGzVseWYCvWLdwnxMK27ZPC0tu7TPzpnTuYbKUNgJsAwCq1cM85iBMvyWBN66mthyVPruyrcbECmpd0Wvb4VLbQRwRAmJBOXIAOqItksHDNc7TTsYPkbd0OdO4glNOVrlXv9w95xYbXwmUcSCnhmH1xaA4kQdLO1H84vX4KQ/YfablbavadBzTSJqL5Ls8DfV1fkNjzYG1rRbEH8nk0asWi2Lv8OZzDX3+1GAAgDxs0ROD/KQ5gcuAOz0VlXysSPMgDIZctaAosFuoQnlx7eRJBhzvgk+GEpuxD4g378aShJaCsmAOGjIVYo4w1SDBgX6eMiGMO9EmYXiu4/WiDHDsadgqHRtqwmHhpBNQkDi1yx9yUNjAApQdmYYmDAlD+52UNAgQ51Hpj2kDjUGnmoGVSDrSJg45JSSlnjtf5eCcNbw41wZ41bFfQn4DOsGZBXRXagnXXwaYoDGBeh9+jL1zAXZGUqlAmd2hmuot4KnpaQgYMsMMdBg04KGoJh4rH5KoimLqlB6rC+gFnUMZoqwCzplGBrSJ8KJ6BwCLZqweJZkrbsR7Et6pgx/7lKQPIMZvAzHSUDoArs2gEUGubGSDF7RwKXOllB7SM+5CzRjIQqbq1LIDtigssC28xFHwrnwaM3ttNBMV9x0CV/nYzQQIIUAeAuAWjo4CuoT3wbsPXWVDAq2dl0MC2FHMXwKRVAZBux9xeEHBE7pJc8AIJIwRBtSr7+5o9q8Uc88zaEACzzST/iowAsvIsNMSxCCu0zQHkwvDRMS+lSatMH+1AsolAEPXVc0CkyXlYdx1qIBF2jXWFkfQpNtZdKjLx2VeTHUgGbJ/tcyACxn11nEGEAAA7";


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
        profileDTO.setAccepted(profile.getAccepted());

        return profileDTO;
    }

    public static List<ProfileDTO> fromEntity(List<Profile> profiles) {

        List<ProfileDTO> profileDTOs = new ArrayList<>();

        for (Profile profile : profiles) {
            profileDTOs.add(fromEntity(profile));
        }

        return profileDTOs;
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
        profile.setAccepted(profileDTO.getAccepted());

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
        if (profileDTO.getAccepted() != null) {
            profile.setAccepted(profileDTO.getAccepted());
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
        profileDTO.setPhoto(genericPhoto);

        return profileDTO;
    }

}
