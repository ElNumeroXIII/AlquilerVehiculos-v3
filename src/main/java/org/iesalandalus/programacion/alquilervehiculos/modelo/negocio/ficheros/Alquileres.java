//Por Joaquin Francisco Sanchez Capel

package org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.ficheros;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IAlquileres;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.naming.OperationNotSupportedException;

public class Alquileres implements IAlquileres {
	private static final File FICHERO_ALQUILERES;
	private static final DateTimeFormatter FORMATO_FECHA;
	private static final String RAIZ = "";
	private static final String ALQUILER = "";
	private static final String CLIENTE = "";
	private static final String VEHICULO = "";
	private static final String FECHA_ALQUILER = "";
	private static final String FECHA_DEVOLUCION = "";
	private static Alquileres instancia;
	private List<Alquiler> coleccionAlquileres;

	public Alquileres() {
		coleccionAlquileres = new ArrayList<>();
	}
	
	static Alquileres getInstancia() {
		if(instancia==null)
			instancia = new Alquileres();
		return instancia;
	}

	@Override
	public List<Alquiler> get() {
		return Collections.unmodifiableList(coleccionAlquileres);
	}

	@Override
	public List<Alquiler> get(Cliente cliente) {
		List<Alquiler> alquileresCliente = new ArrayList<>();
		for (Alquiler alquiler : coleccionAlquileres) {
			if (alquiler.getCliente().equals(cliente)) {
				alquileresCliente.add(alquiler);
			}
		}
		return alquileresCliente;
	}

	@Override
	public List<Alquiler> get(Vehiculo vehiculo) {
		List<Alquiler> alquilerTurismo = new ArrayList<>();
		for (Alquiler alquiler : coleccionAlquileres) {
			if (alquiler.getVehiculo().equals(vehiculo)) {
				alquilerTurismo.add(alquiler);
			}
		}
		return alquilerTurismo;
	}



	@Override
	public void insertar(Alquiler alquiler) throws OperationNotSupportedException {
		if (alquiler == null) {
			throw new NullPointerException("ERROR: No se puede insertar un alquiler nulo.");
		}
		comprobarAlquiler(alquiler.getCliente(), alquiler.getVehiculo(), alquiler.getFechaAlquiler());
		coleccionAlquileres.add(alquiler);
	}

	private void comprobarAlquiler(Cliente cliente, Vehiculo vehiculo, LocalDate fechaAlquiler)
			throws OperationNotSupportedException {

		for (int i = 0; i < get(cliente).size(); i++) {

			if (get(cliente).get(i).getFechaDevolucion() == null) {
				throw new OperationNotSupportedException("ERROR: El cliente tiene otro alquiler sin devolver.");
			}
			if (get(cliente).get(i).getFechaDevolucion().isAfter(fechaAlquiler)
					|| get(cliente).get(i).getFechaDevolucion().isEqual(fechaAlquiler)) {
				throw new OperationNotSupportedException("ERROR: El cliente tiene un alquiler posterior.");
			}

		}

		for (int i = 0; i < get(vehiculo).size(); i++) {

			if (get(vehiculo).get(i).getFechaDevolucion() == null) {
				throw new OperationNotSupportedException("ERROR: El turismo está actualmente alquilado.");
			}
			if (get(vehiculo).get(i).getFechaDevolucion().isAfter(fechaAlquiler)
					|| get(vehiculo).get(i).getFechaDevolucion().isEqual(fechaAlquiler)) {
				throw new OperationNotSupportedException("ERROR: El turismo tiene un alquiler posterior.");
			}
		}

	}

	@Override
	public void devolver(Cliente cliente, LocalDate fechaDevolucion) throws OperationNotSupportedException {
		if (cliente == null) {
			throw new NullPointerException("ERROR: No se puede devolver un alquiler de un cliente nulo.");
		}
		
		Alquiler alquilerTemp = getAlquilerAbierto(cliente);
		
		if(alquilerTemp == null)
			throw new OperationNotSupportedException("ERROR: No existe ningún alquiler abierto para ese cliente.");
			
		buscar(alquilerTemp).devolver(fechaDevolucion);
		
	}

	private Alquiler getAlquilerAbierto(Cliente cliente) throws OperationNotSupportedException {
		
		if (cliente == null)
			throw new NullPointerException("ERROR: No se puede devolver un alquiler de un cliente nulo.");
		
		Alquiler alquilerTemp = null;
		Iterator<Alquiler> iterador = coleccionAlquileres.iterator();
		while (alquilerTemp == null && iterador.hasNext()) {
			Alquiler alquiler = iterador.next();
			if (alquiler.getCliente().equals(cliente) && alquiler.getFechaDevolucion() == null) {
				alquilerTemp = alquiler;
				break;
			}
			
		if(alquilerTemp==null)
			throw new OperationNotSupportedException();
		}
		return alquilerTemp;
	}

	@Override
	public void devolver(Vehiculo vehiculo, LocalDate fechaDevolucion) throws OperationNotSupportedException {
		if (vehiculo == null) {
			throw new NullPointerException("ERROR: No se puede devolver un alquiler de un vehículo nulo.");
		}
		
		Alquiler alquilerTemp = getAlquilerAbierto(vehiculo);
		
		if(alquilerTemp == null)
			throw new OperationNotSupportedException("ERROR: No existe ningún alquiler abierto para ese vehículo.");
			
		buscar(alquilerTemp).devolver(fechaDevolucion);
	}

	private Alquiler getAlquilerAbierto(Vehiculo vehiculo) throws OperationNotSupportedException {
		Alquiler alquilerTemp = null;

		Iterator<Alquiler> iterador = coleccionAlquileres.iterator();
		while (alquilerTemp == null && iterador.hasNext()) {
			Alquiler alquiler = iterador.next();
			if (alquiler.getVehiculo().equals(vehiculo) && alquiler.getFechaDevolucion() == null) {
				alquilerTemp = alquiler;
				break;
			}
			
		if(alquilerTemp==null)
			throw new OperationNotSupportedException();
		}

		return alquilerTemp;
	}

	@Override
	public Alquiler buscar(Alquiler alquileres) {
		if (alquileres == null) {
			throw new NullPointerException("ERROR: No se puede buscar un alquiler nulo.");
		}
		for (Alquiler alquiler : coleccionAlquileres) {
			if (alquiler.equals(alquileres)) {
				return alquiler;
			}
		}
		return null;
	}

	@Override
	public void borrar(Alquiler alquiler) throws OperationNotSupportedException {
		if (alquiler == null) {
			throw new NullPointerException("ERROR: No se puede borrar un alquiler nulo.");
		}
		if (!coleccionAlquileres.contains(alquiler)) {
			throw new OperationNotSupportedException("ERROR: No existe ningún alquiler igual.");
		}
		coleccionAlquileres.remove(alquiler);
	}

}
