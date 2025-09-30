package bwgroup4.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "persone")
public class Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    private String nome;
    private String cognome;
    @Column(name = "è_admin")
    private boolean isAdmin;
    @Column(name = "data_scadenza_tessera")
    private LocalDate scadenzaTessera;

    public Persona(){}

    public Persona(String nome, String cognome, boolean admin, LocalDate scadenza){
        this.nome=nome;
        this.cognome=cognome;
        this.scadenzaTessera=scadenza;
        this.isAdmin=admin;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public LocalDate getScadenzaTessera() {
        return scadenzaTessera;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public void setScadenzaTessera(LocalDate scadenzaTessera) {
        this.scadenzaTessera = scadenzaTessera;
    }

    @Override
    public String toString() {
        return "Persona{" +
                "tessera=" + id +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", isAdmin=" + isAdmin +
                ", scadenzaTessera=" + scadenzaTessera +
                '}';
    }
}
