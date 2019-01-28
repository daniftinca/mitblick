package profile.entities;

import projekt.entities.Projekt;
import user.entities.BaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
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

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "photo")
    private byte[] photo;

    @Column(name = "isAccepted", nullable = false)
    private Boolean isAccepted = false;

    @ManyToOne()
    @JoinColumn(name = "region")
    private Region region;

    @ManyToOne()
    @JoinColumn(name = "jobTitle")
    private JobTitle jobTitle;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "Profile_ID")
    private List<ProfileSkillEntry> skills;


    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "profileID")
    private List<Projekt> projekts = new ArrayList<>();

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

    public List<Projekt> getProjekts() {
        return projekts;
    }

    public void setProjekts(List<Projekt> projekts) {
        this.projekts = projekts;
    }

    public Boolean getAccepted() {
        return isAccepted;
    }

    public void setAccepted(Boolean accepted) {
        isAccepted = accepted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Profile profile = (Profile) o;
        return Objects.equals(firstName, profile.firstName) &&
                Objects.equals(lastName, profile.lastName) &&
                Objects.equals(email, profile.email) &&
                Arrays.equals(photo, profile.photo) &&
                Objects.equals(isAccepted, profile.isAccepted) &&
                Objects.equals(region, profile.region) &&
                Objects.equals(jobTitle, profile.jobTitle) &&
                Objects.equals(skills, profile.skills) &&
                Objects.equals(projekts, profile.projekts);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(super.hashCode(), firstName, lastName, email, isAccepted, region, jobTitle, skills, projekts);
        result = 31 * result + Arrays.hashCode(photo);
        return result;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", photo=" + Arrays.toString(photo) +
                ", isAccepted=" + isAccepted +
                ", region=" + region +
                ", jobTitle=" + jobTitle +
                ", skills=" + skills +
                ", projekts=" + projekts +
                '}';
    }
}
