package bwgroup4.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "biglietti")
public class Biglietto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codice_univoco")
    private int codiceUnivoco;
    @Column(name = "data_emissione")
    private LocalDate dataEmissione;

    @ManyToOne
    @JoinColumn(name = "emittente")
    private Venditore emittente;

    public Biglietto(){}

    public Biglietto(Venditore venditore){
        this.emittente=venditore;
        this.dataEmissione=LocalDate.now();
    }

    public int getCodiceUnivoco() {
        return codiceUnivoco;
    }

    public Venditore getEmittente() {
        return emittente;
    }

    public LocalDate getDataEmissione() {
        return dataEmissione;
    }

    public void setDataEmissione(LocalDate dataEmissione) {
        this.dataEmissione = dataEmissione;
    }

    public void setEmittente(Venditore emittente) {
        this.emittente = emittente;
    }

    @Override
    public String toString() {
        return "Biglietto{" +
                "codiceUnivoco=" + codiceUnivoco +
                ", dataEmissione=" + dataEmissione +
                ", emittente=" + emittente +
                '}';
    }
}
