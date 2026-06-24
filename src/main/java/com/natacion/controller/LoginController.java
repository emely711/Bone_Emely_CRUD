package com.natacion.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML private TextField     txtUsuario;
    @FXML private PasswordField txtPassword;
    @FXML private Label         lblError;

    @FXML
    private void ingresar() {
        String usuario = txtUsuario.getText().trim();
        String password = txtPassword.getText().trim();

        try {
            if (usuario.isEmpty() || password.isEmpty()) {
                throw new IllegalArgumentException("Por favor complete todos los campos.");
            }


            if (!usuario.equals("admin") || !password.equals("1234")) {
                throw new IllegalArgumentException("Usuario o contraseña incorrectos.");
            }

            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/natacion/view/crud.fxml"));
            Stage stage = (Stage) txtUsuario.getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Registro de Participantes");
            stage.setMaximized(true);

        } catch (IllegalArgumentException e) {
            lblError.setText(e.getMessage());
        } catch (IOException e) {
            lblError.setText("Error al cargar la ventana CRUD.");
            e.printStackTrace();
        }
    }

    @FXML
    private void salir() {
        System.exit(0);
    }
}
