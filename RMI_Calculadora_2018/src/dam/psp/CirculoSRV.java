package dam.psp;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class CirculoSRV implements ICirculo {
	
	public static void main(String[] args) throws RemoteException {
		
			final int puerto =8888;
			System.setProperty("java.rmi.server.hostname","192.168.2.11");
			System.setProperty("java.net.preferIPv4Stack", "true");
			
			Registry registry = LocateRegistry.createRegistry(puerto);
			new CirculoSRV(registry);
	}
	
	private final double PI = Math.PI;
	private double _radio;
	
	public CirculoSRV(Registry registry) {
	
		System.out.println("Creando Objeto Circulo y su inscripción en el registro");
		
			try {
				registry.bind("Circulo", (ICirculo)UnicastRemoteObject.exportObject(this, 0));
			} catch (RemoteException | AlreadyBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	
	@Override
	public void set_radio(double radio) throws RemoteException {
		this._radio=radio;

	}

	@Override
	public double area() throws RemoteException {
		
		return PI*_radio*_radio;
	}

	@Override
	public double longitud() throws RemoteException {

		return 2*PI*_radio;
	}

}
