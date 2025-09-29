package bwgroup4.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "punti_vendita")
public class PuntoVendita extends Venditore {

    public  PuntoVendita(){

    }
    public PuntoVendita(String nome){
        super(nome);
    }
}
