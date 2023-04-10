//Joaquin Francisco Sanchez Capel

package org.iesalandalus.programacion.alquilervehiculos.controlador;

import java.time.LocalDate;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.Modelo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.vista.Vista;

public class Controlador {

	
	private Vista vista;
	private Modelo modelo;

	public Controlador(Modelo modelo, Vista vista) {
		if (modelo == null) {
			throw new NullPointerException("ERROR: no se puede crear un modelo nulo.");
		}
		if (vista == null) {
			throw new NullPointerException("ERROR: no se puede crear una vista nula.");
		}
		this.modelo = modelo;
		this.vista = vista;
		vista.setControlador(this);

	}

	public void comenzar() {
		modelo.comenzar();
		vista.comenzar();
	
	}

	public void terminar() {
		modelo.terminar();
		vista.terminar();
	}

	public void insertar(Cliente cliente) throws OperationNotSupportedException {
		modelo.insertar(new Cliente(cliente));
	}

	public void insertar(Vehiculo vehiculo) throws OperationNotSupportedException {
		modelo.insertar(Vehiculo.copiar(vehiculo));
	}

	public void insertar(Alquiler alquiler) throws OperationNotSupportedException {
		modelo.insertar(new Alquiler(alquiler));
	}

	public Cliente buscar(Cliente cliente) throws OperationNotSupportedException {
		return modelo.buscar(new Cliente(cliente));
	}

	public Vehiculo buscar(Vehiculo vehiculo) throws OperationNotSupportedException {
		return modelo.buscar(Vehiculo.copiar(vehiculo));
	}

	public Alquiler buscar(Alquiler alquiler) throws OperationNotSupportedException {
		return modelo.buscar(new Alquiler(alquiler));
	}

	public void modificar(Cliente cliente, String nombre, String telefono) throws OperationNotSupportedException {
		modelo.modificar(new Cliente(cliente), nombre, telefono);
	}

	public void devolver(Cliente cliente, LocalDate fechaDevolucion) throws OperationNotSupportedException {
		modelo.devolver(new Cliente(cliente), fechaDevolucion);
	}

	public void devolver(Vehiculo vehiculo, LocalDate fechaDevolucion) throws OperationNotSupportedException {
		modelo.devolver(Vehiculo.copiar(vehiculo), fechaDevolucion);
	}

	public void borrar(Cliente cliente) throws OperationNotSupportedException {
		modelo.borrar(new Cliente(cliente));
	}

	public void borrar(Vehiculo vehiculo) throws OperationNotSupportedException {
		modelo.borrar(Vehiculo.copiar(vehiculo));
	}

	public void borrar(Alquiler alquiler) throws OperationNotSupportedException {
		modelo.borrar(new Alquiler(alquiler));
	}

	public List<Cliente> getClientes() {
		return modelo.getListaClientes();
	}

	public List<Vehiculo> getVehiculos() {
		return modelo.getListaVehiculos();
	}

	public List<Alquiler> getAlquileres() {
		return modelo.getListaAlquileres();
	}

	public List<Alquiler> getAlquileres(Cliente cliente) {
		return modelo.getListaAlquileres(cliente);
	}

	public List<Alquiler> getAlquileres(Vehiculo vehiculo) {
		return modelo.getListaAlquileres(Vehiculo.copiar(vehiculo));
	}

}
