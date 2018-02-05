package dam.psp;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class CalculadoraCLI {
	
	public static String servidor = "192.168.2.11";//3.57
	public static int puerto = 8888;
	
	public static void main(String[] args) throws RemoteException {
		ICalculadora calculadora= null;
		System.out.println("Localizando en red el objeto remoto");
		Registry registry = LocateRegistry.getRegistry(servidor, puerto);
		
		System.out.println("Obteniendo el falso objeto <stub> del remote");
		try {
			calculadora=(ICalculadora) registry.lookup("Calculadora");
			System.out.println("Division 10/100="+calculadora.division(10, 100));
			System.out.println("Suma 10+50="+calculadora.suma(10, 50));
			System.out.println("Resta 90-25="+calculadora.resta(90, 25));
			System.out.println("Producto 12.5*593.547="+calculadora.producto(12.5f,593.547f));
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
