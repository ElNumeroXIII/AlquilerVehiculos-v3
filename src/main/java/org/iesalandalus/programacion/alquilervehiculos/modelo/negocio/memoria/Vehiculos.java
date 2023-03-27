// Joaquin francisco sanchez capel

package org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.memoria;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IVehiculos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.naming.OperationNotSupportedException;

public class Vehiculos implements IVehiculos {

	private List<Vehiculo> coleccionVehiculos;

	public Vehiculos() {
		coleccionVehiculos = new ArrayList<>();
	}

	@Override
	public List<Vehiculo> get() {
		return Collections.unmodifiableList(coleccionVehiculos);
	}



	@Override
	public void insertar(Vehiculo vehiculo) throws OperationNotSupportedException {
		if (vehiculo == null) {
			throw new NullPointerException("ERROR: No se puede insertar un vehículo nulo.");
		}
		if (coleccionVehiculos.contains(vehiculo)) {
			throw new OperationNotSupportedException("ERROR: Ya existe un vehículo con esa matrícula.");
		} else {
			coleccionVehiculos.add(vehiculo);
		}

	}

	@Override
	public Vehiculo buscar(Vehiculo vehiculo) {
		if(vehiculo == null)
			throw new NullPointerException("ERROR: No se puede buscar un vehículo nulo.");		
		for (Vehiculo vehiculoTemp : coleccionVehiculos) {
			if (vehiculoTemp.equals(vehiculo)) {
				return vehiculoTemp;
			}
		}
		return null;
	}

	@Override
	public void borrar(Vehiculo vehiculo) throws OperationNotSupportedException {
		if (vehiculo == null) {
			throw new NullPointerException("ERROR: No se puede borrar un vehículo nulo.");
		}
		if (!coleccionVehiculos.contains(vehiculo)) {
			throw new OperationNotSupportedException("ERROR: No existe ningún vehículo con esa matrícula.");
		}
		coleccionVehiculos.remove(vehiculo);

	}

}