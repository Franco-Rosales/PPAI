package org.bonvino.Models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "siguiendo")
public class Siguiendo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.DATE)
    private Date fechaInicio;

    @Temporal(TemporalType.DATE)
    private Date fechaFin;

    @ManyToOne
    @JoinColumn(name = "bodega_id")
    private Bodega bodega;

    @ManyToOne
    @JoinColumn(name = "enofilo_id")
    private Enofilo enofilo;

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Bodega getBodega() {
        return bodega;
    }

    public void setBodega(Bodega bodega) {
        this.bodega = bodega;
    }

    public Enofilo getEnofilo() {
        return enofilo;
    }

    public void setEnofilo(Enofilo enofilo) {
        this.enofilo = enofilo;
    }
}

