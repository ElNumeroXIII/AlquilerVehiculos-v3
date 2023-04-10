//Por Joaquin Francisco Sanchez Capel

package org.iesalandalus.programacion.alquilervehiculos.modelo.dominio;

public class Turismo extends Vehiculo {

	private static final int FACTOR_CILINDRADA = 10;
	private int cilindrada;

	public Turismo(String marca, String modelo, int cilindrada, String matricula) {
		super(marca,modelo,matricula);
		setCilindrada(cilindrada);
	}

	public Turismo(Turismo turismo) {
		super(turismo);
		cilindrada = turismo.getCilindrada();
	}

	public int getCilindrada() {
		return cilindrada;
	}

	private void setCilindrada(int cilindrada) {
		if (cilindrada > 0 && 5000 >= cilindrada)
			this.cilindrada = cilindrada;

		else
			throw new IllegalArgumentException("ERROR: La cilindrada no es correcta.");
	}

	@Override
	 public int getFactorPrecio() {
		return getCilindrada() / FACTOR_CILINDRADA;
	}

	@Override
	public String toString() {
		return String.format("%s %s (%d cc) - %s", this.getMarca(), this.getModelo(), this.getCilindrada(),
				this.getMatricula());
	}

}
