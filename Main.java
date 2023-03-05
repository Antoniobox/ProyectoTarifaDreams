import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		String opcion="";
		String nombre="", apellidos="", email="", telefono="", fechaNacimiento="",dni="",frase="";
		Cliente cliente = new Cliente();
		boolean usuarioLogueado=false;
		Scanner sc = new Scanner(System.in);
		GestorClientes gc = new GestorClientes();
		gc.generarClientesBase();
		System.out.println("Bienvenido a Antonio DREAMS");
		do {
			System.out.println("Selecciones una de las siguientes opciones\n" +
					"1. Registrar un usuario\n"+
					"2. Iniciar Sesión");
			opcion=sc.nextLine();
		}while(!opcion.equals("1") && !opcion.equals("2"));
		if(opcion.equals("1")){
			do {
				System.out.println("Introduce tu nombre:");
				nombre=sc.nextLine();
			}while(!Validaciones.nombreYApellidos(nombre, false));
			do {
				System.out.println("Introduce tus apellidos");
				apellidos=sc.nextLine();
			}while(!Validaciones.nombreYApellidos(apellidos, true));
			do {
				System.out.println("Introduce el email");
				email=sc.nextLine();
			}while(!Validaciones.email(email));
			do {
				System.out.println("Introduce el numero de telefono");
				telefono=sc.nextLine();
			}while(!Validaciones.telefono(telefono));
			do {
				System.out.println("Introduce la fecha de nacimiento");
				fechaNacimiento=sc.nextLine();
			}while(!Validaciones.fecha(fechaNacimiento, false));
			do{
				System.out.println("Introduce tu dni");
				dni=sc.nextLine();
			}while(!Validaciones.dni(dni));
			do {
				System.out.println("Introduce cuatro palabras separadas por espacios");
				frase=sc.nextLine();
			}while(Validaciones.control(frase).equals("ERROR"));
			System.out.println("Para iniciar sesión, tendrás que usar las siguientes credenciales:\n"+
					"email: "+email+"\n" +
					"Codigo Secreto: "+Validaciones.control(frase));
			Cliente cliente__ = new Cliente(nombre, apellidos, email, telefono, dni, fechaNacimiento, Validaciones.control(frase));
			gc.agregarCliente(cliente__);
			System.out.println("¿Deseas iniciar sesión?(S/N): ");
			opcion=sc.nextLine();
			if(opcion.equals("S")||opcion.equals("s")) opcion="2";
			else System.out.println("Finalizando...");
		}
		while(opcion.equals("2")){
			System.out.println("Introduce tu correo electronico");
			email = sc.nextLine();
			System.out.println("Introduce tu codigo secreto");
			frase = sc.nextLine();
			ArrayList<Cliente> clientes = gc.getListadoClientes();
			for(Cliente cliente_ : clientes){
				if(cliente_.getEmail().equals(email) && cliente_.getCodigoAcceso().equals(frase)){
					cliente = cliente_;
				}
			}
			for(Cliente c : clientes){
				if(c.getEmail().equals(email) && c.getCodigoAcceso().equals(frase)){
					System.out.println("Bienvenido, "+c.getNombre());
					usuarioLogueado=true;
					opcion="";
				}
			}
			if(!usuarioLogueado){
				System.out.println("No se ha encontrado ningún usuario que coincida, ¿desea seguir intentándolo?(S/N): ");
				opcion = sc.nextLine();
				if(opcion.equals("S") || opcion.equals("s")) opcion="2";
				else{
					opcion="";
					System.out.println("Saliendo...");
				}
			}
		}
		if(usuarioLogueado) {
			String personasReserva = "", fechaEntrada = "", fechaSalida = "";
			String opcionHabitacion = "";
			do {
				System.out.println("1. Reservar habitación");
				System.out.println("2. FAQS");
				System.out.println("0. Salir");
				opcionHabitacion = sc.nextLine();
			} while (!opcionHabitacion.equals("1") && !opcionHabitacion.equals("2") && !opcionHabitacion.equals("0"));

			if (opcionHabitacion.equals("1")) {
				System.out.println("***********  RESERVAR HABITACIÓN  ***********");
				boolean todoCorrecto = true;
				do {
					todoCorrecto = true;
					System.out.println("¿Para cuántas personas se hace la reserva?");
					personasReserva = sc.nextLine();
					if(!Validaciones.esUnNumero(personasReserva, true)){
						System.out.println("Introduce un número, por favor");
						todoCorrecto = false;
					}
					else if(personasReserva.length() > 2){
						System.out.println("No se permite ese número de personas, máximo 99 por reserva");
						todoCorrecto = false;
					}
					else if(Integer.parseInt(personasReserva)<=0){
						System.out.println("La reserva debe de hacerse para un número de personas mayor");
						todoCorrecto=false;
					}
				} while (!todoCorrecto);
				do {
					System.out.println("¿Cuál es la fecha de entrada?");
					fechaEntrada = sc.nextLine();
					System.out.println("¿Cuál es la fecha de salida?");
					fechaSalida = sc.nextLine();
				} while (!Validaciones.fecha(fechaEntrada, true) || !Validaciones.fecha(fechaSalida, true));
				int personasTotalReserva = Integer.parseInt(personasReserva);
				int opcionHabitaciones = 0;
				ArrayList<Habitacion> habitaciones = new ArrayList<>();
				habitaciones.addAll(Habitacion.generarHabitacionesBase());
				ArrayList<Habitacion> habitacionesRepetidas = new ArrayList<>();
				HashMap<Integer, String> opcionesHabitacion = new HashMap<>();
				//nOpciones determina el número de habitaciones que se muestran,
				//para saber si el usuario puede seleccionar alguna
				boolean seguirBuscando = true;
				int nOpciones = 0;
				do{
					nOpciones=0;
					for (Habitacion h : habitaciones) {
						if (h.getMax_personas() == personasTotalReserva && h.comprobarDisponibilidadHabitacion(fechaEntrada + ":" + fechaSalida)) {
							String precios = "";
							System.out.println("* Opcion " + (++opcionHabitaciones));
							System.out.println("Habitación " + h.getNombre() + " para " + h.getMax_personas());
							nOpciones++;
							precios += h.getPrecio();
							System.out.println("Precio final: " + precios);
							opcionesHabitacion.put(opcionHabitaciones, "" + h.getId());
						}
					}
					for (Habitacion h : habitaciones) {
						String precios = "";
						habitacionesRepetidas.add(h);
						for (Habitacion h_ : habitaciones) {
							boolean habitacionRepetida = false;
							for (Habitacion hr : habitacionesRepetidas) {
								if (h_.equals(hr)) habitacionRepetida = true;
							}
							if (!habitacionRepetida) {
								if (h.getMax_personas() + h_.getMax_personas() == personasTotalReserva) {
									System.out.println("* Opcion " + (++opcionHabitaciones));
									System.out.println("Habitación " + h.getNombre() + " para " + h.getMax_personas());
									System.out.println("Habitación " + h_.getNombre() + " para " + h_.getMax_personas());
									nOpciones++;
									precios += h.getPrecio() + "+" + h_.getPrecio();
									System.out.println("Precio final: " + precios);
									opcionesHabitacion.put(opcionHabitaciones, "" + h.getId() + "," + h_.getId());
								}
							}
						}
					}
					if(nOpciones==0){
						System.out.println("No se han encontrado habitaciones disponibles para esos criterios");
						System.out.println("¿Desea seguir intentando?");
						seguirBuscando = sc.nextLine().equals("S") || sc.nextLine().equals("s");
					}
				}while(nOpciones==0 && seguirBuscando);
				boolean habitacionCorrecta = false;
				if(nOpciones>0){
					do {
						System.out.println("¿Qué opción desea?");
						opcionHabitacion = sc.nextLine();
						if(Validaciones.esUnNumero(opcionHabitacion, false)){
							for(int opcion_ : opcionesHabitacion.keySet()){
								if(opcion_==Integer.parseInt(opcionHabitacion)){
									System.out.println("Habitaciones a la espera del pago...");
									habitacionCorrecta = true;
								}
								else{
									habitacionCorrecta = false;
								}
							}
							opcionHabitacion = habitacionCorrecta ? opcionHabitacion : "ERROR";
						}
						else{
							opcionHabitacion="ERROR";
						}
					}while(opcionHabitacion.equals("ERROR"));
				}
				boolean pagoRealizado = false;
				if(habitacionCorrecta){
					String metodoPago = "";
					System.out.println("Elige un método de pago\n" +
							"1.Tarjeta de crédito\n" +
							"2.Bizum");
					metodoPago = sc.nextLine();
					if(metodoPago.equals("1")){
						String tarjetaCredito = "";
						do{
							System.out.println("Introduce el número de tarjeta de crédito");
							tarjetaCredito = sc.nextLine();
						}while(!Validaciones.validarTarjeta(tarjetaCredito));
						String visa = "415006";
						String americanExpress = "375699";
						String masterCard = " 515878";
						if(tarjetaCredito.substring(0, 6).equals(visa)){
							System.out.println("Tienes una VISA");
							pagoRealizado=true;
						}
						else if(tarjetaCredito.substring(0, 6).equals(americanExpress)){
							System.out.println("Tienes una American Express");
							pagoRealizado=true;
						}
						else if(tarjetaCredito.substring(0, 6).equals(masterCard)){
							System.out.println("Tienes una MasterCard");
							pagoRealizado=true;
						}
						else{
							System.out.println("No se ha detectado el tipo de tu tarjeta de crédito");
						}
					}
					else if(metodoPago.equals("2")){
						String bizum = "";
						System.out.println("Bizum");
						System.out.println("Introduce tu número de teléfono");
						bizum = sc.nextLine();
						if(Validaciones.telefono(bizum)){
							System.out.println("Pago realizado");
							pagoRealizado=true;
						}else{
							System.out.println("Número inválida");
						}
					}
				}
				if(pagoRealizado){
					Reservas reserva = new Reservas((int)(Math.random()*1000+1), cliente.getDni(), Habitacion.getIdsListado(opcionesHabitacion.get(Integer.parseInt(opcionHabitacion))), fechaEntrada, fechaSalida);
					cliente.infoBasica();
					DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
					LocalDateTime now = LocalDateTime.now();
					System.out.println("Fecha de facturación: "+dtf.format(now));
					double precioTotalReserva = 0;
					for(int x : reserva.getId_habitacion()){
						for(Habitacion h : habitaciones){
							if(h.getId()==x){
								System.out.println("Habitacion "+h.getNombre()+" para "+h.getMax_personas());
								h.setFechasOcupadas(fechaEntrada+":"+fechaSalida);
								precioTotalReserva+=h.getPrecio();
							}
						}
					}
					System.out.println("Precio total: "+(precioTotalReserva+precioTotalReserva*0.21));
				}
			}
			else if(opcionHabitacion.equals("2")){
				ArrayList<String> preguntasFrecuentes = new ArrayList<>();
				ArrayList<String> respuestas = new ArrayList<>();
				String respuesta;
				preguntasFrecuentes.add("¿Cómo puedo llamar fuera del hotel con el teléfono de la habitación?");
				preguntasFrecuentes.add("¿Es gratis la comida de la nevera de la habitación?");
				respuestas.add("Marcando #0, y seguido de eso, el número");
				respuestas.add("No, los precios de los productos están en un folleto de la habitación");
				System.out.println("***** PREGUNTAS FRECUENTES *****");
				System.out.println("0. Salir");
				for(int i=0; i<preguntasFrecuentes.size(); i++){
					System.out.println((i+1)+" "+preguntasFrecuentes.get(i));
				}
				System.out.println("Selecciona una pregunta");
				respuesta = sc.nextLine();
				if(Validaciones.esUnNumero(respuesta, false) && !respuesta.equals("0")){
					System.out.println(respuestas.get(Integer.parseInt(respuesta)-1));
				}
				else if(respuesta.equals("0")) System.out.println("Saliendo");
			}
		}
	}
}
