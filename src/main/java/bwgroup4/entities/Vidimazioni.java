package bwgroup4.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "vidimazioni")
public class Vidimazioni {
    @Id
    @OneToOne
    @JoinColumn(name = "codice_biglietto")
    private Biglietto biglietto;

    @ManyToOne
    @JoinColumn(name = "codice_mezzo")
    private Mezzi mezzo;

    @Column(name = "data_vidimazione")
    private LocalDate dataVidimazione;

    public Vidimazioni(){}

    public Biglietto getBiglietto() {
        return biglietto;
    }

    public Mezzi getMezzo() {
        return mezzo;
    }

    public LocalDate getDataVidimazione() {
        return dataVidimazione;
    }

    public void setDataVidimazione(LocalDate dataVidimazione) {
        this.dataVidimazione = dataVidimazione;
    }

    public void setMezzo(Mezzi mezzo) {
        this.mezzo = mezzo;
    }

    @Override
    public String toString() {
        return "Vidimazioni{" +
                "dataVidimazione=" + dataVidimazione +
                ", biglietto=" + biglietto +
                '}';
    }
}
