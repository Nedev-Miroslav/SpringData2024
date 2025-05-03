package softuni.exam.models.dto.xmls;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "volcanologists")
@XmlAccessorType(XmlAccessType.FIELD)
public class VolcanologistRootDto implements Serializable {

    @XmlElement(name = "volcanologist")
    private List<VolcanologistSeedDto> volcanologistSeedDto;

    public List<VolcanologistSeedDto> getVolcanologistSeedDto() {
        return volcanologistSeedDto;
    }

    public void setVolcanologistSeedDto(List<VolcanologistSeedDto> volcanologistSeedDto) {
        this.volcanologistSeedDto = volcanologistSeedDto;
    }
}
