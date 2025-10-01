package bwgroup4.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "tratte")
public class Tratta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String zonaPartenza;

    @Column(nullable = false)
    private String capolinea;

    @Column(nullable = false)
    private int tempoPrevistoMin;

    public Tratta() {
    }

    public Tratta(String zonaPartenza, String capolinea, int tempoPrevistoMin) {
        this.zonaPartenza = zonaPartenza;
        this.capolinea = capolinea;
        this.tempoPrevistoMin = tempoPrevistoMin;
    }

    public Long getId() {
        return id;
    }

    public String getZonaPartenza() {
        return zonaPartenza;
    }

    public void setZonaPartenza(String zonaPartenza) {
        this.zonaPartenza = zonaPartenza;
    }

    public String getCapolinea() {
        return capolinea;
    }

    public void setCapolinea(String capolinea) {
        this.capolinea = capolinea;
    }

    public int getTempoPrevistoMin() {
        return tempoPrevistoMin;
    }

    public void setTempoPrevistoMin(int tempoPrevistoMin) {
        this.tempoPrevistoMin = tempoPrevistoMin;
    }

    @Override
    public String toString() {
        return "Tratta{" + "id = " + id +
                ", zonaPartenza='" + zonaPartenza + '\'' +
                ", capolinea=" + capolinea + '\'' +
                ", tempoPrevistoMin=" + tempoPrevistoMin +
                '}';
    }
}