package profile.dto;

import profile.entities.JobTitle;
import profile.entities.ProfileSkillEntry;
import profile.entities.Region;
import projekt.dto.ProjektDTO;

import java.util.List;

public class ProfileDTO {

    private String firstName;
    private String lastName;
    private String email;
    private String photo;

    private Region region;
    private JobTitle jobTitle;

    private List<ProfileSkillEntry> skills;

    private List<ProjektDTO> projekts;

    public ProfileDTO() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public JobTitle getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(JobTitle jobTitle) {
        this.jobTitle = jobTitle;
    }

    public List<ProfileSkillEntry> getSkills() {
        return skills;
    }

    public void setSkills(List<ProfileSkillEntry> skills) {
        this.skills = skills;
    }

    public List<ProjektDTO> getProjekts() {
        return projekts;
    }

    public void setProjekts(List<ProjektDTO> projekts) {
        this.projekts = projekts;
    }

    @Override
    public String toString() {
        return "ProfileDTO{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", photo='" + photo + '\'' +
                ", region=" + region +
                ", jobTitle=" + jobTitle +
                ", skills=" + skills +
                ", projekts=" + projekts +
                '}';
    }
}
