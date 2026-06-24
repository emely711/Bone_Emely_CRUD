package com.natacion.model;

import javafx.beans.property.*;

/**
 * Modelo Participante – representa un registro de la tabla participantes.
 * Usa JavaFX Properties para que el TableView se actualice automáticamente.
 */
public class Participante {

    private final IntegerProperty id           = new SimpleIntegerProperty();
    private final StringProperty  cedula       = new SimpleStringProperty();
    private final StringProperty  nombre       = new SimpleStringProperty();
    private final StringProperty  apellido     = new SimpleStringProperty();
    private final IntegerProperty edad         = new SimpleIntegerProperty();
    private final StringProperty  correo       = new SimpleStringProperty();
    private final StringProperty  estadoCivil  = new SimpleStringProperty();
    private final StringProperty  jornada      = new SimpleStringProperty();
    private final StringProperty  categoria    = new SimpleStringProperty();
    private final StringProperty  observaciones = new SimpleStringProperty();

    // ── Constructor completo ──────────────────────────────────────────
    public Participante(int id, String cedula, String nombre, String apellido,
                        int edad, String correo, String estadoCivil,
                        String jornada, String categoria, String observaciones) {
        this.id.set(id);
        this.cedula.set(cedula);
        this.nombre.set(nombre);
        this.apellido.set(apellido);
        this.edad.set(edad);
        this.correo.set(correo);
        this.estadoCivil.set(estadoCivil);
        this.jornada.set(jornada);
        this.categoria.set(categoria);
        this.observaciones.set(observaciones);
    }

    // ── Getters / Setters ─────────────────────────────────────────────
    public int getId()               { return id.get(); }
    public IntegerProperty idProperty() { return id; }

    public String getCedula()        { return cedula.get(); }
    public StringProperty cedulaProperty() { return cedula; }
    public void setCedula(String v)  { cedula.set(v); }

    public String getNombre()        { return nombre.get(); }
    public StringProperty nombreProperty() { return nombre; }
    public void setNombre(String v)  { nombre.set(v); }

    public String getApellido()      { return apellido.get(); }
    public StringProperty apellidoProperty() { return apellido; }
    public void setApellido(String v){ apellido.set(v); }

    public int getEdad()             { return edad.get(); }
    public IntegerProperty edadProperty() { return edad; }
    public void setEdad(int v)       { edad.set(v); }

    public String getCorreo()        { return correo.get(); }
    public StringProperty correoProperty() { return correo; }
    public void setCorreo(String v)  { correo.set(v); }

    public String getEstadoCivil()   { return estadoCivil.get(); }
    public StringProperty estadoCivilProperty() { return estadoCivil; }
    public void setEstadoCivil(String v){ estadoCivil.set(v); }

    public String getJornada()       { return jornada.get(); }
    public StringProperty jornadaProperty() { return jornada; }
    public void setJornada(String v) { jornada.set(v); }

    public String getCategoria()     { return categoria.get(); }
    public StringProperty categoriaProperty() { return categoria; }
    public void setCategoria(String v){ categoria.set(v); }

    public String getObservaciones() { return observaciones.get(); }
    public StringProperty observacionesProperty() { return observaciones; }
    public void setObservaciones(String v){ observaciones.set(v); }
}
