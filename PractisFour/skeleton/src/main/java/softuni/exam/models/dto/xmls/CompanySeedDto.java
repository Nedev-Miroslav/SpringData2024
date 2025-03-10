package softuni.exam.models.dto.xmls;

import softuni.exam.util.adaptors.LocalDateAdaptor;

import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;

@XmlRootElement(name = "company")
@XmlAccessorType(XmlAccessType.FIELD)
public class CompanySeedDto implements Serializable {
    @XmlElement(name = "companyName")
    @Size(min = 2, max = 40)
    private String name;

    @XmlElement(name = "dateEstablished")
    @XmlJavaTypeAdapter(LocalDateAdaptor.class)
    private LocalDate localDate;

    @XmlElement
    @Size(min = 2, max = 30)
    private String website;

    @XmlElement(name = "countryId")
    private long country;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public long getCountry() {
        return country;
    }

    public void setCountry(long country) {
        this.country = country;
    }
}
