package Task05;

import jakarta.persistence.*;

@Entity(name = "bank_account")
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    @Column(name = "swift_code")
    private String swiftCode;


}
