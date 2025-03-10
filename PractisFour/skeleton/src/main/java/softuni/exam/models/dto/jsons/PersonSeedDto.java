package softuni.exam.models.dto.jsons;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class PersonSeedDto implements Serializable {

    @Expose
    @Email
    private String email;

    @Expose
    @Size(min = 2, max = 30)
    private String firstName;

    @Expose
    @Size(min = 2, max = 30)
    private String lastName;

    @Expose
    @Size(min = 2, max = 13)
    private String phone;

    @Expose
    private String statusType;

    @Expose
    private long country;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStatusType() {
        return statusType;
    }

    public void setStatusType(String statusType) {
        this.statusType = statusType;
    }

    public long getCountry() {
        return country;
    }

    public void setCountry(long country) {
        this.country = country;
    }
}
