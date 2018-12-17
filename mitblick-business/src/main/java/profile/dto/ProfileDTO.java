package profile.dto;

import com.sun.mail.iap.ByteArray;

import java.util.List;
import java.util.Objects;

public class ProfileDTO {

    private String name;
    private String email;
    private ByteArray photo;

    //must be changed
    private List<String> skills;

    //must be changed
    private List<String> projekts;

    public ProfileDTO() {
    }

    public ProfileDTO(String name, String email, ByteArray photo, List<String> skills, List<String> projekts) {
        this.name = name;
        this.email = email;
        this.photo = photo;
        this.skills = skills;
        this.projekts = projekts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ByteArray getPhoto() {
        return photo;
    }

    public void setPhoto(ByteArray photo) {
        this.photo = photo;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public List<String> getProjekts() {
        return projekts;
    }

    public void setProjekts(List<String> projekts) {
        this.projekts = projekts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProfileDTO that = (ProfileDTO) o;
        return getName().equals(that.getName()) &&
                getEmail().equals(that.getEmail()) &&
                Objects.equals(getPhoto(), that.getPhoto()) &&
                Objects.equals(getSkills(), that.getSkills()) &&
                Objects.equals(getProjekts(), that.getProjekts());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getEmail(), getPhoto(), getSkills(), getProjekts());
    }

    @Override
    public String toString() {
        return "ProfileDTO{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", photo=" + photo.toString() +
                ", skills=" + skills.toString() +
                ", projekts=" + projekts.toString() +
                '}';
    }
}
