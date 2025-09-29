package bwgroup4.entities;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDateTime;

@Entity
@Table(name = "corse")
public class Corse {

    @Id
    private Long id;

    @ManyToOne(optional = false)
    private Mezzi mezzi;

    @ManyToOne(optional = false)
    private Tratta tratta;

    @Column(nullable = false)
    private LocalDateTime partenza;

    @Column(nullable = false)
    private LocalDateTime arrivo;

    @Column(nullable = false)
    private long durataSec;

    public Corse() {
    }

    public Corse(Mezzi mezzi, Tratta tratta, LocalDateTime partenza, LocalDateTime arrivo) {
        this.mezzi = mezzi;
        this.tratta = tratta;
        this.partenza = partenza;
        this.arrivo = arrivo;
        this.durataSec = Duration.between(partenza, arrivo).getSeconds();
    }

    // Getters & Setters
    public Long getId() {
        return id;
    }

    public Mezzi getMezzi() {
        return mezzi;
    }

    public void setMezzi(Mezzi mezzi) {
        this.mezzi = mezzi;
    }

    public Tratta getTratta() {
        return tratta;
    }

    public void setTratta(Tratta tratta) {
        this.tratta = tratta;
    }

    public LocalDateTime getPartenza() {
        return partenza;
    }

    public void setPartenza(LocalDateTime partenza) {
        this.partenza = partenza;
        calcolaDurata();
    }

    public LocalDateTime getArrivo() {
        return arrivo;
    }

    public void setArrivo(LocalDateTime arrivo) {
        this.arrivo = arrivo;
        calcolaDurata();
    }

    public long getDurataSec() {
        return durataSec;
    }

    private void calcolaDurata() {
        if (partenza != null && arrivo != null) {
            this.durataSec = Duration.between(partenza, arrivo).getSeconds();
        }
    }

    @Override
    public String toString() {
        return "Corse{" +
                "id=" + id +
                ", mezzi=" + (mezzi != null ? mezzi.getId() : null) +
                ", tratta=" + (tratta != null ? tratta.getId() : null) +
                ", partenza=" + partenza +
                ", arrivo=" + arrivo +
                ", durataSec=" + durataSec +
                '}';
    }
}