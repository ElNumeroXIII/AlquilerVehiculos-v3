package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controladores;

import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controlador;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controladores;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class VentanaPrincipal extends Controlador{


    @FXML
    private Button btAcercaDe;
	
    @FXML
    private Button btSalir;

    @FXML
    void salir(ActionEvent event) {
    	Platform.exit();
    }
    
    @FXML
    void abrirAcercaDe(ActionEvent event) {
    	Controlador VentanaSobre = Controladores.get("vistas/VentanaSobre.fxml", "Ventana Sobre", null);
    	VentanaSobre.getEscenario().showAndWait();
    }
    
    
    @FXML
    void initialize() {
    	System.out.println("Iniciada ejecución visual");
    }

}