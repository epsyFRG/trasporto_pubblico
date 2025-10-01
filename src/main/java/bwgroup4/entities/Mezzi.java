package bwgroup4.entities;

import jakarta.persistence.*;

import jakarta.persistence.*;
import java.util.List;


@Entity
@Table(name = "mezzi")
public class Mezzi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "targa_pk", nullable = false)
    private String targaPK;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TipoMezzi tipo;

    @Column(name = "capienza", nullable = false)
    private int capienza;

    // relazioni

    @OneToMany(mappedBy = "mezzi")
    private List<Manutenzioni> manutenzioni;

    @OneToMany(mappedBy = "mezzi")
    private List<Corse> corse;

    // costruttori

    public Mezzi() {
    }

    public Mezzi(String targaPK, TipoMezzi tipo, int capienza) {
        this.targaPK = targaPK;
        this.tipo = tipo;
        this.capienza = capienza;
    }

    public Mezzi(String targaPK, TipoMezzi tipo) {
        this.targaPK = targaPK;
        this.tipo = tipo;
        if(tipo==TipoMezzi.TRAM){
            this.capienza=30;
        } else if(tipo== TipoMezzi.AUTOBUS){
            this.capienza=35;
        } else{
            this.capienza=40;
        }
    }

    // getter e setter per relazioni

    public List<Manutenzioni> getManutenzioni() {
        return manutenzioni;
    }

    public void setManutenzioni(List<Manutenzioni> manutenzioni) {
        this.manutenzioni = manutenzioni;
    }

    public List<Corse> getCorse() {
        return corse;
    }

    public void setCorse(List<Corse> corse) {
        this.corse = corse;
    }

    // getter e setter

    public String getTargaPK() {
        return targaPK;
    }

    public void setTargaPK(String targaPK) {
        this.targaPK = targaPK;
    }

    public TipoMezzi getTipo() {
        return tipo;
    }

    public void setTipo(TipoMezzi tipo) {
        this.tipo = tipo;
    }

    public int getCapienza() {
        return capienza;
    }

    public void setCapienza(int capienza) {
        this.capienza = capienza;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Mezzi{" +
                "targaPK=" + targaPK +
                ", tipo=" + tipo +
                ", capienza=" + capienza +
                '}';
    }


}