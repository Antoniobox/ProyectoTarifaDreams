import java.util.ArrayList;

public class Reservas {
	private int cod;
	private String id_cliente;
	private ArrayList<Integer> id_habitacion;

	private String fecha_entrada;
	private String fecha_salida;

	public int getCod() {
		return cod;
	}

	public void setCod(int cod) {
		this.cod = cod;
	}

	public String getId_cliente() {
		return id_cliente;
	}

	public void setId_cliente(String id_cliente) {
		this.id_cliente = id_cliente;
	}

	public ArrayList<Integer> getId_habitacion() {
		return id_habitacion;
	}

	public void setId_habitacion(ArrayList<Integer> id_habitacion) {
		this.id_habitacion = id_habitacion;
	}

	public String getFecha_entrada() {
		return fecha_entrada;
	}

	public void setFecha_entrada(String fecha_entrada) {
		this.fecha_entrada = fecha_entrada;
	}

	public String getFecha_salida() {
		return fecha_salida;
	}

	public void setFecha_salida(String fecha_salida) {
		this.fecha_salida = fecha_salida;
	}

	public Reservas(int cod, String id_cliente, ArrayList<Integer> id_habitacion, String fecha_entrada, String fecha_salida) {
		this.cod = cod;
		this.id_cliente = id_cliente;
		this.id_habitacion = id_habitacion;
		this.fecha_entrada = fecha_entrada;
		this.fecha_salida = fecha_salida;
	}

	public static void mostrarCalendarioReservas(){
		
	}
}
