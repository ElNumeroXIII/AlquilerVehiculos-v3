//Por Joaquin Francisco Sanchez Capel

package org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.ficheros;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IAlquileres;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.lang.model.element.Element;
import javax.naming.OperationNotSupportedException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class Alquileres implements IAlquileres {
	
	private static final File FICHERO_ALQUILERES = new File(
			"C:\\Users\\Archen\\git\\AlquilerVehiculos-v2\\datos\\alquileres.xml");
	private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private static final String RAIZ = "alquileres";
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
		if (instancia == null)
			instancia = new Alquileres();
		return instancia;
	}

	public void comenzar() {

		try {
			leerDom(UtilidadesXml.leerXmlDeFichero(FICHERO_ALQUILERES));
		} catch (OperationNotSupportedException e) {
			e.printStackTrace();
		}

	}

	private void leerDom(Document documentoXml) throws OperationNotSupportedException {

		NodeList nodosClientes = documentoXml.getElementsByTagName(ALQUILER);
		for (int i = 0; i < nodosClientes.getLength(); i++) {
			coleccionAlquileres.add(getAlquiler((Element) nodosClientes.item(i)));
		}
	}

	private Alquiler getAlquiler(Element element) throws OperationNotSupportedException {

		Node nodoCliente = (Node) element;

		String cliente = nodoCliente.getAttributes().getNamedItem(CLIENTE).getTextContent();
		String fechaAlquiler = nodoCliente.getAttributes().getNamedItem(FECHA_ALQUILER).getTextContent();
		String vehiculo = nodoCliente.getAttributes().getNamedItem(VEHICULO).getTextContent();

		Alquiler alquilerNuevo = new Alquiler(Cliente.getClienteConDni(cliente),
				Vehiculo.getVehiculoConMatricula(vehiculo), LocalDate.parse(fechaAlquiler, FORMATO_FECHA));

		if (nodoCliente.getAttributes().getNamedItem(FECHA_DEVOLUCION).getTextContent() != null) {
			String fechaDevolucion = nodoCliente.getAttributes().getNamedItem(FECHA_DEVOLUCION).getTextContent();
			alquilerNuevo.devolver(LocalDate.parse(fechaDevolucion, FORMATO_FECHA));
		}
		return alquilerNuevo;
	}

	public void terminar() {

		UtilidadesXml.escribirXmlAFichero(crearDom(), FICHERO_ALQUILERES);

	}

	private Document crearDom() {
		try {

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document documento = dBuilder.newDocument();
			Element raiz = (Element) documento.createElement(RAIZ);
			documento.appendChild((Node) raiz);
			for (Alquiler alquiler : coleccionAlquileres) {
				Element elementoAlquiler = getElemento(documento, alquiler);
				((Node) raiz).appendChild((Node) elementoAlquiler);
			}

			return documento;

		} catch (ParserConfigurationException e) {
			System.out.println("Error al crear el DOM");
			return null;
		}
	}

	private Element getElemento(Document documentoXml, Alquiler alquiler) {
		Element elementoAlquiler = (Element) documentoXml.createElement("alquiler");
		((DocumentBuilderFactory) elementoAlquiler).setAttribute("cliente", alquiler.getCliente().getDni());
		((DocumentBuilderFactory) elementoAlquiler).setAttribute("fechaAlquiler",
				alquiler.getFechaAlquiler().format(FORMATO_FECHA));
		if (alquiler.getFechaDevolucion() != null) {
			((DocumentBuilderFactory) elementoAlquiler).setAttribute("fechaDevolucion",
					alquiler.getFechaDevolucion().format(FORMATO_FECHA));
		}
		((DocumentBuilderFactory) elementoAlquiler).setAttribute("vehiculo", alquiler.getVehiculo().getMatricula());
		return elementoAlquiler;
	}

	@Override
	public List<Alquiler> get() {
		return new ArrayList<>(coleccionAlquileres);
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
		
		if (cliente==null) {
			throw new NullPointerException("El cliente no puede ser nulo");
		}

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
				throw new OperationNotSupportedException("ERROR: El vehículo está actualmente alquilado.");
			}
			if (get(vehiculo).get(i).getFechaDevolucion().isAfter(fechaAlquiler)
					|| get(vehiculo).get(i).getFechaDevolucion().isEqual(fechaAlquiler)) {
				throw new OperationNotSupportedException("ERROR: El vehículo tiene un alquiler posterior.");
			}
		}

	}

	@Override
	public void devolver(Cliente cliente, LocalDate fechaDevolucion) throws OperationNotSupportedException {
		if (cliente == null) {
			throw new NullPointerException("ERROR: No se puede devolver un alquiler de un cliente nulo.");
		}

		Alquiler alquilerTemp = getAlquilerAbierto(cliente);

		if (alquilerTemp == null)
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

			if (alquilerTemp == null)
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

		if (alquilerTemp == null)
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

			if (alquilerTemp == null)
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
