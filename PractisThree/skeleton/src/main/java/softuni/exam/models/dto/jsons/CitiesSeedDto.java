package softuni.exam.models.dto.jsons;

import com.google.gson.annotations.Expose;

import javax.persistence.Column;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class CitiesSeedDto implements Serializable {
    @Expose
    @Column(name = "city_name")
    @Size(min = 2, max = 60)
    private String cityName;

    @Expose
    @Size(min = 2)
    private String description;

    @Expose
    @Min(500)
    private int population;

    @Expose
    private long country;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public long getCountry() {
        return country;
    }

    public void setCountry(long country) {
        this.country = country;
    }
}
