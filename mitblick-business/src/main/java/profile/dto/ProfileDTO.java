package profile.dto;

import projekt.dto.ProjektDTO;
import skills.dto.SkillDTO;

import java.util.List;

public class ProfileDTO {

    private String firstName;
    private String lastName;
    private String email;
    private String photo;

    private List<SkillDTO> skills;

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

    public List<SkillDTO> getSkills() {
        return skills;
    }

    public void setSkills(List<SkillDTO> skills) {
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
                ", photo=" + photo +
                ", skills=" + skills.toString() +
                ", projekts=" + projekts.toString() +
                '}';
    }
}
