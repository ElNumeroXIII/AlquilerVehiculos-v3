//Joaquin Francisco Sanchez Capel
package org.iesalandalus.programacion.alquilervehiculos.vista.texto;

import java.time.LocalDate;
import java.util.EnumMap;
import java.util.Map;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.vista.Vista;

public class VistaTexto extends Vista {

	public VistaTexto() {
		Accion.setVista(this);
	}

	@Override
	public void comenzar() {
		Accion opcionElegida;
		do {
			Consola.mostrarCabecera("Elige una opción del menú");
			Consola.mostrarMenu();
			opcionElegida = Consola.elegirAcccion();
			opcionElegida.ejecutar();
		} while (opcionElegida != Accion.SALIR);

	}

	@Override
	public void terminar() {
		String salida = "Hasta la próxima, usuario";
		System.out.printf("%n%s%n", salida);
	}

	public void insertarCliente() {
		Consola.mostrarCabecera("Dame un cliente, por favor");
		try {
			getControlador().insertar(Consola.leerCliente());
			System.out.println("El cliente se ha insertado");
		} catch (OperationNotSupportedException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	public void insertarVehiculo() {
		Consola.mostrarCabecera("Dame un vehículo, por favor");
		try {
			getControlador().insertar(Consola.leerVehiculo());
			System.out.println("El vehículo se ha insertado");
		} catch (OperationNotSupportedException e) {
			System.out.println("Error: " + e.getMessage());
		}

	}

	public void insertarAlquiler() {
		Consola.mostrarCabecera("Dame un alquiler, por favor");
		try {
			getControlador().insertar(Consola.leerAlquiler());
			System.out.println("El alquiler se ha insertado");
		} catch (OperationNotSupportedException e) {
			System.out.println("Error: " + e.getMessage());
		}

	}

	public void buscarCliente() {
		Consola.mostrarCabecera("Dame un cliente, por favor");
		try {
				System.out.printf("El cliente encontrado es: %s",getControlador().buscar(Consola.leerCliente()).toString() );

		} catch (OperationNotSupportedException e) {
			System.out.println("Error: " + e.getMessage());
		}

	}

	public void buscarVehiculo() {
		Consola.mostrarCabecera("Dame un vehículo, por favor");
		try {
			System.out.printf("El vehiculo encontrado es: %s",getControlador().buscar(Consola.leerVehiculo()));
		} catch (OperationNotSupportedException e) {
			System.out.println("Error: " + e.getMessage());
		}

	}

	public void buscarAlquiler() {
		Consola.mostrarCabecera("Dame un alquiler, por favor");
		try {
			System.out.printf("El alquiler encontrado es: %s",getControlador().buscar(Consola.leerAlquiler()));
		} catch (OperationNotSupportedException e) {
			System.out.println("Error: " + e.getMessage());
		}

	}

	public void modificarCliente() {
		Consola.mostrarCabecera("Dame el DNI del cliente, por favor");
		try {
			getControlador().modificar(Consola.leerCliente(), Consola.leerNombre(), Consola.leerTelefono());
			System.out.println("Cliente modificado");
		} catch (OperationNotSupportedException e) {
			System.out.println("Error: " + e.getMessage());
		}

	}

	public void devolverAlquilerCliente() {
		Consola.mostrarCabecera("Dame el DNi del cliente para la devolucion, por favor");
		try {
			getControlador().devolver(Consola.leerClienteDni(), LocalDate.now());
		} catch (OperationNotSupportedException e) {
			System.out.println("Error: " + e.getMessage());
		}

	}

	public void devolverAlquilerVehiculo() {
		Consola.mostrarCabecera("Dame la matricula del vehiculo a devolver, por favor");
		try {
			getControlador().devolver(Consola.leerVehiculoMatricula(), LocalDate.now());
		} catch (OperationNotSupportedException e) {
			System.out.println("Error: " + e.getMessage());
		}

	}

	public void borrarCliente() {
		Consola.mostrarCabecera("Dame el DNI del cliente a borrar, por favor");
		try {
			getControlador().borrar(Consola.leerClienteDni());
			System.out.println("Cliente borrado");
		} catch (OperationNotSupportedException e) {
			System.out.println("Error: " + e.getMessage());
		}

	}

	public void borrarVehiculo() {
		Consola.mostrarCabecera("Dame la matrícula del vehículo a borrar, por favor");
		try {
			getControlador().borrar(Consola.leerVehiculoMatricula());
			System.out.println("Vehiculo borrado");
		} catch (OperationNotSupportedException e) {
			System.out.println("Error: " + e.getMessage());
		}

	}

	public void borrarAlquiler() {
		Consola.mostrarCabecera("Dame un alquiler, por favor");
		try {
			getControlador().borrar(Consola.leerAlquiler());
			System.out.println("Alquiler borrado");
		} catch (OperationNotSupportedException e) {
			System.out.println("Error: " + e.getMessage());
		}

	}

	public void listarClientes() {
		getControlador().getClientes();

	}

	public void listarVehiculos() {
		getControlador().getVehiculos();

	}

	public void listarAlquileres() {
		getControlador().getAlquileres();

	}

	public void listarAlquileresCliente() {
		Consola.mostrarCabecera("Dame un cliente, por favor");
		getControlador().getAlquileres(Consola.leerCliente());

	}

	public void listarAlquileresVehiculo() {
		Consola.mostrarCabecera("Dame un vehículo, por favor");
		getControlador().getAlquileres(Consola.leerVehiculo());

	}

	public void mostrarEstadisticasMensualesTipoVehiculo() {
		Map<TipoVehiculo, Integer> estadisticas = inicializarEstadisticas();
		LocalDate mes = Consola.leerMes();
		for (Alquiler alquiler : getControlador().getAlquileres()) {
			if (alquiler.getFechaAlquiler().getMonth().equals(mes.getMonth())
					&& alquiler.getFechaAlquiler().getYear() == (mes.getYear())) {
				estadisticas.put(TipoVehiculo.get(alquiler.getVehiculo()),
						estadisticas.get(TipoVehiculo.get(alquiler.getVehiculo())) + 1);
			}
		}
			for (Map.Entry<TipoVehiculo, Integer> elemento : estadisticas.entrySet()) {
				TipoVehiculo tipo = elemento.getKey();
				Integer cantidad = elemento.getValue();
				System.out.printf("Del tipo %s hay %d%n", tipo, cantidad);
			}
	}

	private Map<TipoVehiculo, Integer> inicializarEstadisticas() {
		Map<TipoVehiculo, Integer> mapa = new EnumMap<>(TipoVehiculo.class);
		for (int i = 0; i < TipoVehiculo.values().length; i++) {
			mapa.put(TipoVehiculo.get(i), 0);
		}

		return mapa;
	}

}
