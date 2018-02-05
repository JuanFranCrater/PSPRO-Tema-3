package dam.psp;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class CirculoCLI {

	public static String servidor = "192.168.3.57";//3.57
	public static int puerto = 8888;
	
	public static void main(String[] args) throws RemoteException {
		ICirculo circulo= null;
		System.out.println("Localizando en red el objeto remoto");
		Registry registry = LocateRegistry.getRegistry(servidor, puerto);
		
		System.out.println("Obteniendo el falso objeto <stub> del remote");
		try {
			circulo=(ICirculo) registry.lookup("Circulo");
			if(circulo!=null)
			{
			System.out.println("Objeto recogido");
			circulo.set_radio(5000);
			System.out.println("Area: "+circulo.area());
			System.out.println("Longitud: "+circulo.longitud());
			}
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
