
//Por Joaquin Francisco Sanchez Capel

package org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.ficheros;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IClientes;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Element;
import javax.naming.OperationNotSupportedException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class Clientes implements IClientes {

	private static final File FICHERO_CLIENTES = new File(
			"C:\\Users\\Archen\\git\\AlquilerVehiculos-v2\\datos\\clientes.xml");
	private static final String RAIZ = "clientes";
	private static final String CLIENTE = "";
	private static final String NOMBRE = "";
	private static final String DNI = "";
	private static final String TELEFONO = "";
	private static Clientes instancia;

	private List<Cliente> coleccionClientes;

	public Clientes() {
		coleccionClientes = new ArrayList<>();
	}

	static Clientes getInstancia() {
		if (instancia == null)
			instancia = new Clientes();
		return instancia;
	}

	public void comenzar() {

		leerDom(UtilidadesXml.leerXmlDeFichero(FICHERO_CLIENTES));

	}

	private void leerDom(Document documentoXml) {

		NodeList nodosClientes = documentoXml.getElementsByTagName(CLIENTE);
		for (int i = 0; i < nodosClientes.getLength(); i++) {
			coleccionClientes.add(getCliente((Element) nodosClientes.item(i)));
		}
	}

	private Cliente getCliente(Element element) {
		Node nodoCliente = (Node) element;

		String dni = nodoCliente.getAttributes().getNamedItem(DNI).getTextContent();
		String nombre = nodoCliente.getAttributes().getNamedItem(NOMBRE).getTextContent();
		String telefono = nodoCliente.getAttributes().getNamedItem(TELEFONO).getTextContent();

		return new Cliente(dni, nombre, telefono);

	}

	public void terminar() {

		UtilidadesXml.escribirXmlAFichero(crearDom(), FICHERO_CLIENTES);

	}

	private Document crearDom() {
		try {

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document documento = dBuilder.newDocument();
			Element raiz = (Element) documento.createElement(RAIZ);
			documento.appendChild((Node) raiz);
			for (Cliente cliente : coleccionClientes) {
				Element elementoCliente = getElemento(documento, cliente);
				((Node) raiz).appendChild((Node) elementoCliente);
			}

			return documento;

		} catch (ParserConfigurationException e) {
			System.out.println("Error al crear el DOM");
			return null;
		}
	}

	private Element getElemento(Document documentoXml, Cliente cliente) {
		Element elementoCliente = (Element) documentoXml.createElement("cliente");
		((DocumentBuilderFactory) elementoCliente).setAttribute("dni", cliente.getDni());
		((DocumentBuilderFactory) elementoCliente).setAttribute("nombre", cliente.getNombre());
		((DocumentBuilderFactory) elementoCliente).setAttribute("telefono", cliente.getTelefono());
		return elementoCliente;
	}

	@Override
	public List<Cliente> get() {
		return new ArrayList<>(coleccionClientes);
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
	public Cliente buscar(Cliente cliente) {
		if (cliente == null)
			throw new NullPointerException("ERROR: No se puede buscar un cliente nulo.");
		if (coleccionClientes.indexOf(cliente) != -1) {
			return coleccionClientes.get(coleccionClientes.indexOf(cliente));
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