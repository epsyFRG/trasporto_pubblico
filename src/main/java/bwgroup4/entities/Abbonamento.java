package bwgroup4.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "abbonamenti")
public class Abbonamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codice_univoco")
    private int codiceUnivoco;
    @Column(name = "data_emissione")
    private LocalDate dataEmissione;
    @Column(name = "data_scadenza")
    private LocalDate dataScadenza;

    @OneToOne
    @JoinColumn(name = "tessera_utente")
    private Persona persona;

    @ManyToOne
    @JoinColumn(name = "emittente")
    private Venditore emittente;

    public Abbonamento(){}

    public Abbonamento(Venditore venditore,Persona utente, boolean mensile){
        this.persona=utente;
        this.emittente=venditore;
        LocalDate today=LocalDate.now();
        this.dataEmissione=today;
        if(mensile){
            this.dataScadenza=today.plusDays(30);
        } else{
            this.dataScadenza=today.plusDays(7);
        }
    }

    public int getCodiceUnivoco() {
        return codiceUnivoco;
    }

    public LocalDate getDataEmissione() {
        return dataEmissione;
    }

    public LocalDate getDataScadenza() {
        return dataScadenza;
    }

    public Persona getPersona() {
        return persona;
    }

    public Venditore getEmittente() {
        return emittente;
    }

    public void setDataEmissione(LocalDate dataEmissione) {
        this.dataEmissione = dataEmissione;
    }

    public void setDataScadenza(LocalDate dataScadenza) {
        this.dataScadenza = dataScadenza;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public void setEmittente(Venditore emittente) {
        this.emittente = emittente;
    }

    @Override
    public String toString() {
        return "Abbonamento{" +
                "codiceUnivoco=" + codiceUnivoco +
                ", dataEmissione=" + dataEmissione +
                ", dataScadenza=" + dataScadenza +
                ", persona=" + persona +
                ", emittente=" + emittente +
                '}';
    }
}
