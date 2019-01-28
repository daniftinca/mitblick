package profile.dto;

import java.util.List;
import java.util.Objects;

public class FilterDTO {
    private List<ProfileDTO> profiles;
    private Integer amount;

    public List<ProfileDTO> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<ProfileDTO> profiles) {
        this.profiles = profiles;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FilterDTO filterDTO = (FilterDTO) o;
        return Objects.equals(profiles, filterDTO.profiles) &&
                Objects.equals(amount, filterDTO.amount);
    }

    @Override
    public int hashCode() {

        return Objects.hash(profiles, amount);
    }

    @Override
    public String toString() {
        return "FilterDTO{" +
                "profiles=" + profiles +
                ", amount=" + amount +
                '}';
    }
}
