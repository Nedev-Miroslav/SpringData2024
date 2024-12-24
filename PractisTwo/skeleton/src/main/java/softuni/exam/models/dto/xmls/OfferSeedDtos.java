package softuni.exam.models.dto.xmls;


import softuni.exam.util.LocalDateAdaptor;

import javax.validation.constraints.Positive;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@XmlRootElement(name = "offer")
@XmlAccessorType(XmlAccessType.FIELD)
public class OfferSeedDtos implements Serializable {

    @XmlElement(name = "price")
    @Positive
    private BigDecimal price;

    @XmlElement(name = "agent")
    private AgentDto agent;

    @XmlElement(name = "apartment")
    private ApartmentDto apartment;

    @XmlElement(name = "publishedOn")
    @XmlJavaTypeAdapter(LocalDateAdaptor.class)
    private LocalDate publishedOn;

    public LocalDate getPublishedOn() {
        return publishedOn;
    }

    public OfferSeedDtos() {
    }

    public void setPublishedOn(LocalDate publishedOn) {
        this.publishedOn = publishedOn;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }



    public AgentDto getAgent() {
        return agent;
    }

    public void setAgent(AgentDto agent) {
        this.agent = agent;
    }

    public ApartmentDto getApartment() {
        return apartment;
    }

    public void setApartment(ApartmentDto apartment) {
        this.apartment = apartment;
    }


}
