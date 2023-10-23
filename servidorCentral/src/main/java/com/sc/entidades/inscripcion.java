package com.sc.entidades;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class inscripcion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private LocalDate fecha;

    @Column
    private int canT;

    @Column
    private int costo;

    @ManyToOne
    @JoinColumn(name = "turista_nickname")
    private turista turista;

    @ManyToOne
    @JoinColumn(name = "salida_nombre")
    private salida salida;

    public inscripcion() {
        // Constructor vacío requerido por JPA
    }

    // public inscripcion(Date fecha, int canT, int costo, turista turista, salida salida) {
        public inscripcion(LocalDate fecha, int canT, int costo) {
        this.fecha = fecha;
        this.canT = canT;
        this.costo = costo;
        this.turista = null;
        this.salida = null;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public int getCanT() {
        return canT;
    }

    public void setCanT(int canT) {
        this.canT = canT;
    }

    public int getCosto() {
        return costo;
    }

    public void setCosto(int costo) {
        this.costo = costo;
    }

    public turista getTurista() {
        return turista;
    }

    public void setTurista(turista turista) {
        this.turista = turista;
    }

    public salida getSalida() {
        return salida;
    }

    public void setSalida(salida salida) {
        this.salida = salida;
    }
}
