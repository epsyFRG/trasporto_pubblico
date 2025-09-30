package bwgroup4.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "tessere")
public class Tessera {
    @Id
    @Column(name = "codice_tessera")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int codice;

    @Column(name = "data_scadenza")
    private LocalDate dataScadenza;

    @OneToOne
    private Persona utente;

    public Tessera(){}

    public Tessera(Persona utente){
        this.utente=utente;
        LocalDate today=LocalDate.now();
        this.dataScadenza=today.plusDays(365);
    }

    public int getCodice() {
        return codice;
    }

    public LocalDate getDataScadenza() {
        return dataScadenza;
    }

    public Persona getUtente() {
        return utente;
    }

    public void setDataScadenza(LocalDate dataScadenza) {
        this.dataScadenza = dataScadenza;
    }

    public void setUtente(Persona utente) {
        this.utente = utente;
    }

    @Override
    public String toString() {
        return "Tessera{" +
                "codice=" + codice +
                ", dataScadenza=" + dataScadenza +
                ", utente=" + utente +
                '}';
    }
}
