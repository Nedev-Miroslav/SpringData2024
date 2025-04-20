package softuni.exam.models.dto.xmls;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "astronomers")
@XmlAccessorType(XmlAccessType.FIELD)
public class AstronomerRootDto implements Serializable {

    @XmlElement(name = "astronomer")
    private List<AstronomerSeedDto> astronomerSeedDto;

    public List<AstronomerSeedDto> getAstronomerSeedDto() {
        return astronomerSeedDto;
    }

    public void setAstronomerSeedDto(List<AstronomerSeedDto> astronomerSeedDto) {
        this.astronomerSeedDto = astronomerSeedDto;
    }
}
