package profile.entities;

import projekt.entities.Projekt;
import skills.entities.Skill;
import user.entities.BaseEntity;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "profile")
@NamedQueries({
                @NamedQuery(name = Profile.GET_ALL_PROFILES, query = "SELECT p FROM Profile p"),
                @NamedQuery(name = Profile.GET_PROFILE_BY_FIRST_NAME, query = "SELECT p FROM Profile p WHERE p.firstName = :firstName"),
                @NamedQuery(name = Profile.GET_PROFILE_BY_LAST_NAME, query = "SELECT p FROM Profile p WHERE p.lastName = :lastName"),
                @NamedQuery(name = Profile.GET_PROFILE_BY_EMAIL, query = "SELECT p FROM Profile p WHERE p.email = :email"),
        @NamedQuery(name = Profile.GET_PROFILE_BY_ID, query = "SELECT p FROM Profile p WHERE p.id = :id"),
        }
)
public class Profile extends BaseEntity {

    public static final String GET_ALL_PROFILES = "get_all_profiles";
    public static final String GET_PROFILE_BY_FIRST_NAME = "get_profile_by_first_name";
    public static final String GET_PROFILE_BY_LAST_NAME = "get_profile_by_last_name";
    public static final String GET_PROFILE_BY_EMAIL = "get_profile_by_email";
    public static final String GET_PROFILE_BY_ID = "get_profile_by_id";

    @Column(name = "firstName", unique = true)
    private String firstName;

    @Column(name = "lastName", unique = true)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "photo")
    private byte[] photo;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Skill> skills;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Projekt> projekts;

    public Profile() {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Profile profile = (Profile) o;
        return getFirstName().equals(profile.getFirstName()) &&
                getLastName().equals(profile.getLastName()) &&
                getEmail().equals(profile.getEmail()) &&
                Arrays.equals(getPhoto(), profile.getPhoto()) &&
                Objects.equals(getSkills(), profile.getSkills()) &&
                Objects.equals(getProjekts(), profile.getProjekts());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(super.hashCode(), getFirstName(), getLastName(), getEmail(), getSkills(), getProjekts());
        result = 31 * result + Arrays.hashCode(getPhoto());
        return result;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", photo=" + Arrays.toString(photo) +
                ", skills=" + skills.toString() +
                ", projekts=" + projekts.toString() +
                '}';
    }
}
