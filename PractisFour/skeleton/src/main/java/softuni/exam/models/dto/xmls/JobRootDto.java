package softuni.exam.models.dto.xmls;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "jobs")
@XmlAccessorType(XmlAccessType.FIELD)
public class JobRootDto {

    @XmlElement(name = "job")
    private List<JobSeedDto> jobSeedDto;

    public List<JobSeedDto> getJobSeedDto() {
        return jobSeedDto;
    }

    public void setJobSeedDto(List<JobSeedDto> jobSeedDto) {
        this.jobSeedDto = jobSeedDto;
    }
}



