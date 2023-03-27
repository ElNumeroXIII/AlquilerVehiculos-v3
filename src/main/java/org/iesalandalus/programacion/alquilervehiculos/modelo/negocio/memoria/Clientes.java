
//Por Joaquin Francisco Sanchez Capel

package org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.memoria;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IClientes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.naming.OperationNotSupportedException;

public class Clientes implements IClientes {

	private List<Cliente> coleccionClientes;

	public Clientes() {
		coleccionClientes = new ArrayList<>();
	}

	@Override
	public List<Cliente> get() {
		return Collections.unmodifiableList(coleccionClientes);
	}



	@Override
	public void insertar(Cliente cliente) throws OperationNotSupportedException {
		if (cliente == null) {
			throw new NullPointerException("ERROR: No se puede insertar un cliente nulo.");
		}
		if (coleccionClientes.contains(cliente)) {
			throw new OperationNotSupportedException("ERROR: Ya existe un cliente con ese DNI.");
		} else {
			coleccionClientes.add(cliente);
		}

	}

	@Override
	public Cliente buscar(Cliente clientes) {
		if(clientes == null)
			throw new NullPointerException("ERROR: No se puede buscar un cliente nulo.");		
		for (Cliente cliente : coleccionClientes) {
			if (cliente.equals(clientes)) {
				return cliente;
			}
		}
		return null;
	}

	@Override
	public void borrar(Cliente cliente) throws OperationNotSupportedException {
		if (cliente == null) {
			throw new NullPointerException("ERROR: No se puede borrar un cliente nulo.");
		}
		if (!coleccionClientes.contains(cliente)) {
			throw new OperationNotSupportedException("ERROR: No existe ningún cliente con ese DNI.");
		}
		coleccionClientes.remove(cliente);

	}

	@Override
	public void modificar(Cliente cliente, String nombre, String telefono) throws OperationNotSupportedException {
		if (cliente == null)
			throw new NullPointerException("ERROR: No se puede modificar un cliente nulo.");
		if (!coleccionClientes.contains(cliente))
			throw new OperationNotSupportedException("ERROR: No existe ningún cliente con ese DNI.");
		
		int indice = coleccionClientes.indexOf(cliente);

		if (nombre != null && !nombre.isBlank()) {
			coleccionClientes.get(indice).setNombre(nombre);
		}
		if (telefono != null && !telefono.isBlank()) {
			coleccionClientes.get(indice).setTelefono(telefono);
		}
		
	}
}