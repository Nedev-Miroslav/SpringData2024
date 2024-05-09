package Task04;

import jakarta.persistence.*;

import java.util.Set;

@Entity(name = "medicaments")
public class Medicament {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    public Medicament() {
    }

    @ManyToMany(mappedBy = "medicaments")
    private Set<Diagnose> diagnoses;
}
