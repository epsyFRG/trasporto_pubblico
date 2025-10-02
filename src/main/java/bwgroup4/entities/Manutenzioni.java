package bwgroup4.entities;

import jakarta.persistence.*;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "manutenzioni")
public class Manutenzioni {

    @Id
    @Column(name = "id_mezzo")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(name = "data_inizio")
    private LocalDateTime dataInizio;
    @Column(name = "data_fine")
    private LocalDateTime dataFine;
    @Column(name = "descrizione")
    private String descrizione;

    @ManyToOne
    @JoinColumn
    private Mezzi mezzi;

    public Manutenzioni() {
    }

    public Manutenzioni(String descrizione, LocalDateTime dataInizio, LocalDateTime dataFine, Mezzi mezzi) {

        this.mezzi = mezzi;
        this.descrizione=descrizione;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
    }

    public Long getId() {
        return Id;
    }

    public LocalDateTime getInizio() {
        return dataInizio;
    }

    public void setInizio(LocalDateTime datainizio) {
        this.dataInizio = datainizio;
    }

    public LocalDateTime getdatafine() {
        return dataFine;
    }

    public void setFine(LocalDateTime fine) {
        this.dataFine = dataFine;
    }

    public Mezzi getMezzi() {
        return mezzi;
    }

    public void setMezzo(Mezzi mezzi) {
        this.mezzi = mezzi;
    }

    @Override
    public String toString() {
        return "Manutenzioni{" +
                "Id=" + Id +
                ", descrizione=" + descrizione +
                ", dataInizio=" + dataInizio +
                ", dataFine=" + dataFine +
                ", mezzi=" + (mezzi != null ? mezzi.getTipo() + " (id=" + mezzi.getId() + ")" : null) +
                '}';
    }

}
