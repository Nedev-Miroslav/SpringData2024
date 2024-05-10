package Task05;

import jakarta.persistence.*;

import java.util.Set;

@Entity(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "firs_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    private String email;
    @Column(nullable = false)
    private String password;


    @OneToMany(mappedBy = "user")
    private Set<BillingDetails> billingDetails;

}
