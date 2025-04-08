package com.example.football.models.dto.xmls;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "stats")
@XmlAccessorType(XmlAccessType.FIELD)
public class StatRootDto implements Serializable {

    @XmlElement(name = "stat")
    private List<StatSeedDto> statSeedDtos;

    public List<StatSeedDto> getStatSeedDtos() {
        return statSeedDtos;
    }

    public void setStatSeedDtos(List<StatSeedDto> statSeedDtos) {
        this.statSeedDtos = statSeedDtos;
    }
}
