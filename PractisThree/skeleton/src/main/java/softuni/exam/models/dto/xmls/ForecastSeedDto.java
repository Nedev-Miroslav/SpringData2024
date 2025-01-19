package softuni.exam.models.dto.xmls;

import softuni.exam.util.LocalTimeAdaptor;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalTime;
import java.util.Objects;

@XmlRootElement(name = "forecast")
@XmlAccessorType(XmlAccessType.FIELD)
public class ForecastSeedDto implements Serializable {

    @XmlElement(name = "day_of_week")
    @NotNull
    private String dayOfWeek;

    @XmlElement(name = "max_temperature")
    @DecimalMin(value = "-20")
    @DecimalMax(value = "60")
    private Double maxTemperature;

    @XmlElement(name = "min_temperature")
    @DecimalMin(value = "-50")
    @DecimalMax(value = "40")
    private Double minTemperature;

    @XmlElement
    @XmlJavaTypeAdapter(LocalTimeAdaptor.class)
    private LocalTime sunrise;

    @XmlElement
    @XmlJavaTypeAdapter(LocalTimeAdaptor.class)
    private LocalTime sunset;

    @XmlElement
    private long city;


    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public Double getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(Double maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public Double getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(Double minTemperature) {
        this.minTemperature = minTemperature;
    }

    public LocalTime getSunrise() {
        return sunrise;
    }

    public void setSunrise(LocalTime sunrise) {
        this.sunrise = sunrise;
    }

    public LocalTime getSunset() {
        return sunset;
    }

    public void setSunset(LocalTime sunset) {
        this.sunset = sunset;
    }

    public long getCity() {
        return city;
    }

    public void setCity(long city) {
        this.city = city;
    }

}
