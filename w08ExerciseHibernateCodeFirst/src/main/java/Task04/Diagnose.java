package Task04;

import jakarta.persistence.*;

import java.util.Set;

@Entity(name = "diagnoses")
public class Diagnose {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String comments;

    @OneToMany(mappedBy = "diagnose")
    private Set<Visitation> visitations;

    public Diagnose() {
    }


    @ManyToMany
    @JoinTable(name = "diagnoses_medicaments",
    joinColumns = @JoinColumn(name = "diagnose_id", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "medicament_id", referencedColumnName = "id"))
    private Set<Medicament> medicaments;

}
