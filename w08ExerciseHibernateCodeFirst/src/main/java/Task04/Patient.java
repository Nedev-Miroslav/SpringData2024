package Task04;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Set;

@Entity(name = "patients")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "firs_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String address;

    private String email;

    private LocalDate dateOfBirth;

    @Column(columnDefinition = "blob")
    private String picture;

    @Column(name = "has_medical_insurance")
    private boolean hasMedicalInsurance;

    @OneToMany(mappedBy = "patient")
    private Set<Visitation> visitations;

    public Patient() {
    }
}
