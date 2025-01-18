package softuni.exam.models.dto.jsons;

import com.google.gson.annotations.Expose;

import javax.persistence.Column;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class CountrySeedDto implements Serializable {

    @Expose
    @Column(name = "country_name")
    @Size(min = 2, max = 60)
    private String countryName;

    @Expose
    @Size(min = 2, max = 20)
    private String currency;


    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
