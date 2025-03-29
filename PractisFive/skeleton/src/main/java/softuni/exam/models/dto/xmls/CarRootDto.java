package softuni.exam.models.dto.xmls;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "cars")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarRootDto implements Serializable {

    @XmlElement(name = "car")
    private List<CarSeedDto> carSeedDto;

    public List<CarSeedDto> getCarSeedDto() {
        return carSeedDto;
    }

    public void setCarSeedDto(List<CarSeedDto> carSeedDto) {
        this.carSeedDto = carSeedDto;
    }
}
