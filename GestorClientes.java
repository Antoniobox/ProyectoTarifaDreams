import java.util.ArrayList;

public class GestorClientes {
	private ArrayList<Cliente> listadoClientes = new ArrayList<>();

	public GestorClientes() {
	}

	public GestorClientes(ArrayList<Cliente> listadoClientes) {
		this.listadoClientes = listadoClientes;
	}

	public ArrayList<Cliente> getListadoClientes() {
		return listadoClientes;
	}

	public void setListadoClientes(ArrayList<Cliente> listadoClientes) {
		this.listadoClientes = listadoClientes;
	}

	/**
	 * Muestra todos los clientes del listado (la información básica)
	 */
	public void mostrarClientes(){
		for(Cliente cliente : listadoClientes){
			cliente.infoBasica();
		}
	}

	/**
	 * Busca un cliente en el listado de los clientes
	 * @param cliente_a_buscar
	 * @return cliente encontrado en el listado
	 */
	public boolean buscarCliente(Cliente cliente_a_buscar){
		for(Cliente cliente : listadoClientes){
			if(cliente_a_buscar.equals(cliente)) return true;
		}
		return false;
	}

	/**
	 * Añade un cliente al listado de clientes del gestor
	 * @param cliente
	 */
	public void agregarCliente(Cliente cliente){
		listadoClientes.add(cliente);
	}

	/**
	 * Genera 7 clientes y los introduce en el arraylist
	 */
	public void generarClientesBase(){
		/*
		******************----------------- LA INFORMACIÓN PERSONAL DE LOS CLIENTES ESTA ALTERADA POR SU PRIVACIDAD, PARA MÁS INFORMACIÓN CONTACTAR CON EL ADMINISTRADOR -----------------******************
		 */
		listadoClientes.add(new Cliente("Antonio", "Box Sanchez", "antonioboxsanchez@gmail.com", "693810626", "29594264A", "07/04/2004", "PPPP78"));
		listadoClientes.add(new Cliente("Sergio", "Manresa Bernabeu", "sergiomb8@gmail.com", "666777888", "87960573N", "08/01/2004", "PPPP77"));
		listadoClientes.add(new Cliente("Jorge", "Carmona Girona", "jocargi@gmail.com", "69381067", "29594265A", "27/12/2004", "PPPP76"));
		listadoClientes.add(new Cliente("Manuel", "García Santamaría", "manugs475@gmail.com", "693810629", "29594269A", "12/03/2003", "PPPP75"));
		listadoClientes.add(new Cliente("Alexis Javier", "Escolano Mora", "alexiscis@gmail.com", "693810621", "47362564A", "15/11/2004", "PPPP74"));
		listadoClientes.add(new Cliente("Iker", "Berna Morales", "iker@gmail.com", "623810626", "29593264A", "17/04/2004", "PPPP73"));
		listadoClientes.add(new Cliente("Maria del Carmen", "Ortuño", "maricarmenortuno@gmail.com", "693810616", "29594294A", "23/08/1990", "PPPP72"));
	}
}
