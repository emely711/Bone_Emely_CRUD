package com.natacion.controller;

import com.natacion.dao.ParticipanteDAO;
import com.natacion.model.Participante;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


public class CrudController implements Initializable {


    @FXML private TextField  txtCedula;
    @FXML private TextField  txtNombre;
    @FXML private TextField  txtApellido;
    @FXML private TextField  txtEdad;
    @FXML private TextField  txtCorreo;
    @FXML private ComboBox<String> cmbEstadoCivil;
    @FXML private RadioButton rbMatutina;
    @FXML private RadioButton rbVespertina;
    @FXML private RadioButton rbNocturna;
    @FXML private ComboBox<String> cmbCategoria;
    @FXML private TextArea   txtObservaciones;

    @FXML private TableView<Participante>   tablaParticipantes;
    @FXML private TableColumn<Participante, Integer> colId;
    @FXML private TableColumn<Participante, String>  colCedula;
    @FXML private TableColumn<Participante, String>  colNombre;
    @FXML private TableColumn<Participante, String>  colApellido;
    @FXML private TableColumn<Participante, Integer> colEdad;
    @FXML private TableColumn<Participante, String>  colCorreo;
    @FXML private TableColumn<Participante, String>  colEstadoCivil;
    @FXML private TableColumn<Participante, String>  colJornada;
    @FXML private TableColumn<Participante, String>  colCategoria;


    private final ParticipanteDAO dao = new ParticipanteDAO();
    private ToggleGroup grupoJornada;
    private int idSeleccionado = -1;


    @Override
    public void initialize(URL url, ResourceBundle rb) {


        cmbEstadoCivil.setItems(FXCollections.observableArrayList(
            "Soltero", "Casado", "Divorciado", "Viudo"));
        cmbCategoria.setItems(FXCollections.observableArrayList(
            "Infantil", "Juvenil", "Junior", "Senior", "Master"));


        grupoJornada = new ToggleGroup();
        rbMatutina.setToggleGroup(grupoJornada);
        rbVespertina.setToggleGroup(grupoJornada);
        rbNocturna.setToggleGroup(grupoJornada);


        colId         .setCellValueFactory(new PropertyValueFactory<>("id"));
        colCedula     .setCellValueFactory(new PropertyValueFactory<>("cedula"));
        colNombre     .setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colApellido   .setCellValueFactory(new PropertyValueFactory<>("apellido"));
        colEdad       .setCellValueFactory(new PropertyValueFactory<>("edad"));
        colCorreo     .setCellValueFactory(new PropertyValueFactory<>("correo"));
        colEstadoCivil.setCellValueFactory(new PropertyValueFactory<>("estadoCivil"));
        colJornada    .setCellValueFactory(new PropertyValueFactory<>("jornada"));
        colCategoria  .setCellValueFactory(new PropertyValueFactory<>("categoria"));

        tablaParticipantes.getSelectionModel().selectedItemProperty()
            .addListener((obs, old, sel) -> {
                if (sel != null) cargarEnFormulario(sel);
            });

        cargarTabla();
    }

    @FXML
    private void guardar() {
        try {
            Participante p = construirDesdeFormulario();

            if (dao.correoExiste(p.getCorreo(), -1)) {
                mostrarAlerta(Alert.AlertType.WARNING,
                    "Correo duplicado", "Ese correo ya está registrado.");
                return;
            }

            if (dao.insertar(p)) {
                mostrarAlerta(Alert.AlertType.INFORMATION,
                    "Éxito", "Participante registrado correctamente.");
                limpiar();
                cargarTabla();
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo registrar.");
            }

        } catch (IllegalArgumentException e) {
            mostrarAlerta(Alert.AlertType.WARNING, "Validación", e.getMessage());
        }
    }


    @FXML
    private void actualizar() {
        if (idSeleccionado == -1) {
            mostrarAlerta(Alert.AlertType.WARNING, "Selección",
                "Seleccione un participante de la tabla primero.");
            return;
        }
        try {
            Participante p = construirDesdeFormulario();
            p.idProperty().set(idSeleccionado);  // conservar ID

            if (dao.correoExiste(p.getCorreo(), idSeleccionado)) {
                mostrarAlerta(Alert.AlertType.WARNING,
                    "Correo duplicado", "Ese correo ya está registrado por otro participante.");
                return;
            }

            if (dao.actualizar(p)) {
                mostrarAlerta(Alert.AlertType.INFORMATION,
                    "Éxito", "Participante actualizado.");
                limpiar();
                cargarTabla();
            }

        } catch (IllegalArgumentException e) {
            mostrarAlerta(Alert.AlertType.WARNING, "Validación", e.getMessage());
        }
    }


    @FXML
    private void eliminar() {
        if (idSeleccionado == -1) {
            mostrarAlerta(Alert.AlertType.WARNING, "Selección",
                "Seleccione un participante de la tabla primero.");
            return;
        }

        Alert conf = new Alert(Alert.AlertType.CONFIRMATION);
        conf.setTitle("Confirmar eliminación");
        conf.setHeaderText("¿Eliminar este participante?");
        conf.setContentText("Esta acción no se puede deshacer.");
        Optional<ButtonType> res = conf.showAndWait();

        if (res.isPresent() && res.get() == ButtonType.OK) {
            if (dao.eliminar(idSeleccionado)) {
                mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Participante eliminado.");
                limpiar();
                cargarTabla();
            }
        }
    }

    private void cargarTabla() {
        ObservableList<Participante> lista =
            FXCollections.observableArrayList(dao.listar());
        tablaParticipantes.setItems(lista);
    }


    private void cargarEnFormulario(Participante p) {
        idSeleccionado = p.getId();
        txtCedula.setText(p.getCedula());
        txtNombre.setText(p.getNombre());
        txtApellido.setText(p.getApellido());
        txtEdad.setText(String.valueOf(p.getEdad()));
        txtCorreo.setText(p.getCorreo());
        cmbEstadoCivil.setValue(p.getEstadoCivil());
        switch (p.getJornada()) {
            case "Matutina"   -> rbMatutina.setSelected(true);
            case "Vespertina" -> rbVespertina.setSelected(true);
            case "Nocturna"   -> rbNocturna.setSelected(true);
        }
        cmbCategoria.setValue(p.getCategoria());
        txtObservaciones.setText(p.getObservaciones());
    }

    @FXML
    private void limpiar() {
        idSeleccionado = -1;
        txtCedula.clear();
        txtNombre.clear();
        txtApellido.clear();
        txtEdad.clear();
        txtCorreo.clear();
        cmbEstadoCivil.setValue(null);
        grupoJornada.selectToggle(null);
        cmbCategoria.setValue(null);
        txtObservaciones.clear();
        tablaParticipantes.getSelectionModel().clearSelection();
    }

    // ── Construir Participante desde el formulario con validaciones ───
    private Participante construirDesdeFormulario() {
        String cedula       = txtCedula.getText().trim();
        String nombre       = txtNombre.getText().trim();
        String apellido     = txtApellido.getText().trim();
        String edadTxt      = txtEdad.getText().trim();
        String correo       = txtCorreo.getText().trim();
        String estadoCivil  = cmbEstadoCivil.getValue();
        String categoria    = cmbCategoria.getValue();
        String observaciones = txtObservaciones.getText().trim();


        if (cedula.isEmpty() || nombre.isEmpty() || apellido.isEmpty()
                || edadTxt.isEmpty() || correo.isEmpty()
                || estadoCivil == null || categoria == null
                || grupoJornada.getSelectedToggle() == null) {
            throw new IllegalArgumentException("Todos los campos son obligatorios.");
        }


        if (!cedula.matches("\\d+")) {
            throw new IllegalArgumentException("La cédula debe contener solo números.");
        }


        int edad;
        try {
            edad = Integer.parseInt(edadTxt);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("La edad debe ser un número entero.");
        }
        if (edad <= 5) {
            throw new IllegalArgumentException("La edad debe ser mayor a 5 años.");
        }

        if (!correo.contains("@")) {
            throw new IllegalArgumentException("El correo debe contener '@'.");
        }


        RadioButton rbSel = (RadioButton) grupoJornada.getSelectedToggle();
        String jornada = rbSel.getText();

        return new Participante(0, cedula, nombre, apellido, edad, correo,
                                estadoCivil, jornada, categoria, observaciones);
    }


    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert a = new Alert(tipo);
        a.setTitle(titulo);
        a.setHeaderText(null);
        a.setContentText(mensaje);
        a.showAndWait();
    }
}
