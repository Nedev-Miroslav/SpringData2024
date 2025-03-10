package softuni.exam.models.dto.xmls;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "job")
@XmlAccessorType(XmlAccessType.FIELD)
public class JobSeedDto {


    @XmlElement
    @Size(min = 2, max = 40)
    private String jobTitle;

    @XmlElement
    @DecimalMin("10")
    private Double hoursAWeek;

    @XmlElement
    @DecimalMin("300")
    private Double salary;

    @XmlElement
    @Size(min = 5)
    private String description;

    @XmlElement
    private long companyId;


    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public Double getHoursAWeek() {
        return hoursAWeek;
    }

    public void setHoursAWeek(Double hoursAWeek) {
        this.hoursAWeek = hoursAWeek;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }
}
