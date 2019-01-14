package profile.dto;

import projekt.entities.Projekt;
import skills.entities.Skill;

import java.util.Arrays;
import java.util.List;

public class ProfileDTO {

    private String firstName;
    private String lastName;
    private String email;
    private byte[] photo;

    private List<Skill> skills;

    private List<Projekt> projekts;

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

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    public List<Projekt> getProjekts() {
        return projekts;
    }

    public void setProjekts(List<Projekt> projekts) {
        this.projekts = projekts;
    }

    @Override
    public String toString() {
        return "ProfileDTO{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", photo=" + Arrays.toString(photo) +
                ", skills=" + skills.toString() +
                ", projekts=" + projekts.toString() +
                '}';
    }
}
