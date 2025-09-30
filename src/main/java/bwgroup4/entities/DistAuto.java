package bwgroup4.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "distributori_automatici")
public class DistAuto extends Venditore {
    @Column(name = "è_in_servizio")
    private boolean inServizio;

    public DistAuto(){}

    public DistAuto(String nome, boolean serv){
        super(nome);
        this.inServizio=serv;

    }

    public boolean isInServizio() {
        return inServizio;
    }

    public void setInServizio(boolean inServizio) {
        this.inServizio = inServizio;
    }

    @Override
    public String toString() {
        return "DistAuto{" +
                "inServizio=" + inServizio +
                "} " + super.toString();
    }
}
