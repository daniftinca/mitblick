package profil.entities;

import com.sun.mail.iap.ByteArray;
import user.entities.BaseEntity;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "profiles")
@NamedQueries(
        {
                @NamedQuery(name = Profile.GET_ALL_PROFILES, query = "SELECT p FROM Prfolie p"),
                @NamedQuery(name = Profile.GET_PROFILE_BY_NAME, query = "SELECT p FROM Profiles p where p.name = :name"),
                @NamedQuery(name = Profile.GET_PROFILE_BY_EMAIL, query = "SELECT p FROM Profiles p where p.email = :email"),
                @NamedQuery(name = Profile.GET_PROFILE_BY_ID, query = "SELECT p from Profile p where p.id=:id"),
        }
)
public class Profile extends BaseEntity {

    public static final String GET_ALL_PROFILES = "get_all_profiles";
    public static final String GET_PROFILE_BY_NAME = "get_profile_by_name";
    public static final String GET_PROFILE_BY_EMAIL = "get_profile_by_email";
    public static final String GET_PROFILE_BY_ID = "gte_profile_by_id";

    @Transient
    private static final int MAX_STRING_LENGTH = 200;
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "photo")
    private ByteArray photo;

    //must be changed
    private List<String> skills;

    //must be changed
    private List<String> projekts;

    public Profile() {
    }

    public Profile(String name, String email, ByteArray photo, List<String> skills, List<String> projekts) {
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
        Profile profile = (Profile) o;
        return getName().equals(profile.getName()) &&
                getEmail().equals(profile.getEmail()) &&
                Objects.equals(getPhoto(), profile.getPhoto()) &&
                Objects.equals(getSkills(), profile.getSkills()) &&
                Objects.equals(getProjekts(), profile.getProjekts());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getEmail(), getPhoto(), getSkills(), getProjekts());
    }

    @Override
    public String toString() {
        return "Profile{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", photo='" + photo.toString() + '\'' +
                ", skills=" + skills.toString() +
                ", projekts=" + projekts.toString() +
                '}';
    }
}
