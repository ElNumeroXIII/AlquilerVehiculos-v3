//Joaquin Francisco Sanchez Capel

package org.iesalandalus.programacion.alquilervehiculos.vista.texto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.iesalandalus.programacion.alquilervehiculos.controlador.Controlador;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Autobus;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Furgoneta;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Turismo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.utilidades.Entrada;

public class Consola {

	private static final String PATRON_FECHA = "dd/MM/yyyy";
	private static final String PATRON_MES = "MM/yyyy";
	private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern(PATRON_FECHA);

	private Consola() {
	}

	public static void mostrarCabecera(String mensaje) {
		
		System.out.printf("%n%s%n", mensaje);
		StringBuilder underline = new StringBuilder();
		for (int i = 0; i < mensaje.length(); i++) {
			underline.append("-");
		}
		System.out.printf("%n%s%n", underline);

	}

	public static void mostrarMenu() {
		for (Accion accion : Accion.values()) {
			if (accion.ordinal() != 0) {
				System.out.printf("%s%n", accion);
			}
		}
		System.out.println(Accion.SALIR);
	}

	public static Accion elegirAcccion() {
		Accion opcionElegida = null;
		do {
			try {
				opcionElegida = Accion.get(leerEntero("Has elegido la opcion: "));
			} catch (IllegalArgumentException e) {
				System.out.println("Error: " + e.getMessage());
			}
		} while (opcionElegida == null);
		return opcionElegida;
	}

	private static String leerCadena(String mensaje) {
		System.out.printf("%s%n", mensaje);
		return Entrada.cadena();
	}

	private static Integer leerEntero(String mensaje) {
		System.out.printf("%s%n", mensaje);
		return Entrada.entero();
	}

	private static LocalDate leerFecha(String mensaje, String patron) {
		System.out.printf("La fecha leida y su patrón son estos, de izquierda a derecha: %s (%s)", mensaje, patron);
		LocalDate fecha = null;

		try {
			if (patron.equals(PATRON_FECHA)) {
				fecha = LocalDate.parse(Entrada.cadena(), FORMATO_FECHA);
			} else if (patron.equals(PATRON_MES)) {
				fecha = LocalDate.parse("01/" + Entrada.cadena(), FORMATO_FECHA);
			}
		} catch (DateTimeParseException e) {
			System.out.println("Error al leer la fecha: " + e.getMessage());
		}

		if (fecha == null)
			throw new IllegalArgumentException();

		return fecha;
	}

	public static Cliente leerCliente() {
		return new Cliente(leerNombre(), leerCadena("Con DNI:"), leerTelefono());
	}

	public static Cliente leerClienteDni() {
		return Cliente.getClienteConDni(leerCadena("Con DNI:"));
	}

	public static String leerNombre() {
		return leerCadena("Dame el nombre");
	}

	public static String leerTelefono() {
		return leerCadena("Dame el numero");
	}

	public static Vehiculo leerVehiculo() {
		mostrarMenuTiposVehiculos();
		return leerVehiculo(elegirTipoVehiculo());

	}

	private static void mostrarMenuTiposVehiculos() {
		Consola.mostrarCabecera("Tipos:");
		for (TipoVehiculo tipo : TipoVehiculo.values())
			System.out.printf("%s%n", tipo);
	}

	private static TipoVehiculo elegirTipoVehiculo() {
		return TipoVehiculo.get(leerEntero("Tipo:"));

	}

	public static Vehiculo leerVehiculo(TipoVehiculo tipoVehiculo) {
		Vehiculo vehiculoSalida = null;
		String marca = leerCadena("De la marca:");
		String modelo = leerCadena("Del modelo:");
		String matricula = leerCadena("Y con matrícula:");

		if (tipoVehiculo == TipoVehiculo.TURISMO) {
			vehiculoSalida = new Turismo(marca, modelo, leerEntero("Turismo con cilindrada:"), matricula);
		}
		if (tipoVehiculo == TipoVehiculo.FURGONETA) {
			vehiculoSalida = new Furgoneta(marca, modelo, leerEntero("Con Peso Máximo Autorizado:"),
					leerEntero("Con número de plazas:"), matricula);
		}

		if (tipoVehiculo == TipoVehiculo.AUTOBUS) {
			vehiculoSalida = new Autobus(marca, modelo, leerEntero("Con número de plazas:"), matricula);
		}

		return vehiculoSalida;
	}

	public static Vehiculo leerVehiculoMatricula() {
		return Vehiculo.getVehiculoConMatricula(leerCadena("Vehiculo con matrícula:"));
	}

	public static Alquiler leerAlquiler() {
		// cambiado el parámetro de la fecha
		return new Alquiler(leerCliente(), leerVehiculo(), LocalDate.now());

	}

	public static LocalDate leerFechaDevolucion() {
		return leerFecha("Fecha Devolucion", PATRON_FECHA);
	}

	public static LocalDate leerMes() {
		return leerFecha("Fecha mes:", PATRON_MES);

	}
}
