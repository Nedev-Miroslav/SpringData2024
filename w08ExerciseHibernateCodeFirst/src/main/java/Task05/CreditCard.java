package Task05;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Entity(name = "credit_cards")
public class CreditCard extends BillingDetails{

    @Column
    @Enumerated(EnumType.STRING)
    private CardType type;

    @Column(name = "expiration_month")
    private int expirationMonth;
    @Column(name = "expiration_year")
    private int expirationYear;

}
