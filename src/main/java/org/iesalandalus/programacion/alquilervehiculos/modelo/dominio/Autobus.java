//Por joaquin Francisco Sanchez Capel

package org.iesalandalus.programacion.alquilervehiculos.modelo.dominio;

public class Autobus extends Vehiculo {
	private static final int FACTOR_PLAZAS = 2;
	private int plazas;

	public Autobus(String marca, String modelo, int plazas, String matricula) {
		super(marca, modelo, matricula);
		if (plazas < 7 || plazas > 100)
			throw new IllegalArgumentException("ERROR: Las plazas no son correctas.");
		setPlazas(plazas);
	}

	public Autobus(Autobus bus) {
		super(bus);
		if (bus == null)
			throw new NullPointerException("ERROR: No es posible copiar un autobus nulo.");
		plazas = bus.getPlazas();
	}

	public int getPlazas() {
		return plazas;
	}

	private void setPlazas(int plazas) {
		this.plazas = plazas;
	}

	@Override
	int getFactorPrecio() {
		return getPlazas() * FACTOR_PLAZAS;
	}

	@Override
	public String toString() {
		return String.format("%s %s (%d plazas) - %s", this.getMarca(), this.getModelo(), this.getPlazas(),
				this.getMatricula());
	}

}
