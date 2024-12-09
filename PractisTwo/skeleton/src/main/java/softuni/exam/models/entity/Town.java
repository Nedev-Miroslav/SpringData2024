package softuni.exam.models.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "towns")
public class Town extends BaseEntity{

    @Column(nullable = false)
    private int population;


    @Column(name = "town_name", nullable = false, unique = true)
    private String townName;

    @OneToMany(mappedBy = "town")
    private Set<Agent> agentSet;

    public Set<Apartment> getApartments() {
        return apartments;
    }

    public void setApartments(Set<Apartment> apartments) {
        this.apartments = apartments;
    }

    @OneToMany(mappedBy = "townAp")
    private Set<Apartment> apartments;

    public Set<Agent> getAgentSet() {
        return agentSet;
    }

    public void setAgentSet(Set<Agent> agentSet) {
        this.agentSet = agentSet;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public String getTownName() {
        return townName;
    }

    public void setTownName(String townName) {
        this.townName = townName;
    }
}
