package Task04;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Set;

@Entity(name = "visitation")
public class Visitation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDate date;

    private String comments;

    @ManyToOne
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "diagnose_id", referencedColumnName = "id")
    private Diagnose diagnose;



}
