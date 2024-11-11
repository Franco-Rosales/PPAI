package org.bonvino.Models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "vino")
public class Vino {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "imagen_etiqueta")
    private String imagenEtiqueta;

    @Column(name = "precio_ars")
    private double precioARS;

    @Column(name = "nota_cata_bodega")
    private double notaCataBodega;

    @Column(name = "aniada")
    private int aniada;

    // Relación ManyToOne con Bodega
    @ManyToOne
    @JoinColumn(name = "bodega_id")
    private Bodega bodega;

    // Relación ManyToOne con Varietal (cada vino tiene un único varietal)
    @OneToOne
    @JoinColumn(name = "varietal_id")
    private Varietal varietal;

    // Relación ManyToMany con Maridaje (cada vino puede tener múltiples maridajes)
    @ManyToMany
    @JoinTable(
            name = "vino_maridaje",
            joinColumns = @JoinColumn(name = "vino_id"),
            inverseJoinColumns = @JoinColumn(name = "maridaje_id")
    )
    private List<Maridaje> maridajes = new ArrayList<>();

// Constructor
    public Vino() {
    }

    public Vino(String nombre, String imagenEtiqueta, double precioARS, double notaCataBodega, int aniada, Bodega bodega, Varietal varietal, List<Maridaje> maridajes) {
        this.nombre = nombre;
        this.imagenEtiqueta = imagenEtiqueta;
        this.precioARS = precioARS;
        this.notaCataBodega = notaCataBodega;
        this.aniada = aniada;
        this.bodega = bodega;
        this.varietal = varietal;
        this.maridajes = maridajes;
    }

    public String formatoResumen() {
        StringBuilder resumen = new StringBuilder();
        resumen.append("Nombre: ").append(nombre)
                .append(", Añada: ").append(aniada)
                .append(", Precio: ").append(precioARS)
                .append(", Nota Cata: ").append(notaCataBodega);

        // Incluir información del varietal
        if (varietal != null) {
            resumen.append(", Varietal: ").append(varietal.getDescripcion());
        }

        // Incluir información de los maridajes
        if (!maridajes.isEmpty()) {
            resumen.append(", Maridajes: ");
            for (Maridaje maridaje : maridajes) {
                resumen.append(maridaje.getNombre()).append(", ");
            }
            resumen.setLength(resumen.length() - 2); // Quita la última coma y espacio
        }

        return resumen.toString();
    }



    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImagenEtiqueta() {
        return imagenEtiqueta;
    }

    public void setImagenEtiqueta(String imagenEtiqueta) {
        this.imagenEtiqueta = imagenEtiqueta;
    }

    public double getPrecioARS() {
        return precioARS;
    }

    public void setPrecioARS(double precioARS) {
        this.precioARS = precioARS;
    }

    public double getNotaCataBodega() {
        return notaCataBodega;
    }

    public void setNotaCataBodega(double notaCataBodega) {
        this.notaCataBodega = notaCataBodega;
    }

    public int getAniada() {
        return aniada;
    }

    public void setAniada(int aniada) {
        this.aniada = aniada;
    }

    public Bodega getBodega() {
        return bodega;
    }

    public void setBodega(Bodega bodega) {
        this.bodega = bodega;
    }

    public Varietal getVarietal() {
        return varietal;
    }

    public void setVarietal(Varietal varietales) {
        this.varietal = varietales;
    }

    public List<Maridaje> getMaridajes() {
        return maridajes;
    }

    public void setMaridajes(List<Maridaje> maridajes) {
        this.maridajes = maridajes;
    }
}

