package composition;

import inheritance.entities.Truck;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "drivers")
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "full_name")
    private String fullNma;

    @ManyToMany(mappedBy = "drivers")
    private List<Truck> trucks;

    public Driver() {}
}
