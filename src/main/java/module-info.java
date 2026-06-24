module com.natacion {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.natacion to javafx.fxml;
    opens com.natacion.controller to javafx.fxml;
    opens com.natacion.model to javafx.base;

    exports com.natacion;
    exports com.natacion.controller;
    exports com.natacion.model;
}
