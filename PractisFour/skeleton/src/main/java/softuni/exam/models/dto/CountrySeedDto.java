package softuni.exam.models.dto.jsons;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.Size;
import java.io.Serializable;


public class CountrySeedDto implements Serializable {

    @Expose
    @Size(min = 2, max = 30)
    private String name;

    @Expose
    @Size(min = 2, max = 19)
    private String countryCode;

    @Expose
    @Size(min = 2, max = 19)
    private String currency;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
