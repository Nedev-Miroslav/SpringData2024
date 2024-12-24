package softuni.exam.models.dto.xmls;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "offers")
@XmlAccessorType(XmlAccessType.FIELD)
public class OfferRootDto implements Serializable {

    @XmlElement(name = "offer")
    private List<OfferSeedDtos> offers;

    public List<OfferSeedDtos> getOffers() {
        return offers;
    }

    public OfferRootDto() {
    }

    public void setOffers(List<OfferSeedDtos> offers) {
        this.offers = offers;
    }
}
