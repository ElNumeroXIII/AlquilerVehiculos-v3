//Joaquin Francisco Sanchez Capel
package org.iesalandalus.programacion.alquilervehiculos.vista.texto;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Autobus;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Furgoneta;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Turismo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;

public enum TipoVehiculo {
	TURISMO("Turismo"), AUTOBUS("Autobus"), FURGONETA("Furgoneta");

	private String nombre;

	private TipoVehiculo(String nombrePasado) {
		nombre = nombrePasado;
	}

	private static boolean esOrdinalValido(int ordinal) {
		return (ordinal <= 0 || ordinal > values().length);
	}

	public static TipoVehiculo get(int ordinal) {
		if (!esOrdinalValido(ordinal))
			throw new IllegalArgumentException("Ordinal no válido");
		else
			return values()[ordinal];
	}

	public static TipoVehiculo get(Vehiculo vehiculo) {
		if (vehiculo == null)
			throw new IllegalArgumentException("Vehículo no válido");

		TipoVehiculo ordinal = null;

		if (vehiculo instanceof Turismo turismo)
			ordinal = TipoVehiculo.TURISMO;
		else if (vehiculo instanceof Autobus bus)
			ordinal = TipoVehiculo.AUTOBUS;
		else if (vehiculo instanceof Furgoneta furgo)
			ordinal = TipoVehiculo.FURGONETA;

		return ordinal;
	}

	@Override
	public String toString() {
		return String.format("%d - %s", ordinal(), nombre);
	}
}
