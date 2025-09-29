package bwgroup4.entities;

import jakarta.persistence.*;

@Entity
@Table
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Venditore {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)

    protected int id;
    protected String nome;

    public Venditore(){

    }

    public Venditore( String nome){
        this.nome=nome;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "Venditore{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                '}';
    }
}
