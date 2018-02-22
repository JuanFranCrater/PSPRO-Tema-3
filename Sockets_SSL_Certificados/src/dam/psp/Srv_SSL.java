package dam.psp;


import java.io.IOException;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

public class Srv_SSL  {

	static int PUERTO = 5555;
	public Srv_SSL() throws IOException
	{
		System.out.println("Obteniendo factoria del socket para el servidor");
		SSLServerSocketFactory factory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
		
		System.out.println("Creado el Socket");
		SSLServerSocket socketSRV = (SSLServerSocket) factory.createServerSocket(PUERTO);
		
		while(true)
		{
			System.out.println("Aceptando Conexiones...");
			SSLSocket socketAtencion = (SSLSocket) socketSRV.accept();
			System.out.println("Atendiendo una nueva conexion con hilo dedicado");
			new Srv_SSLThread(socketAtencion).start();
		}
		
	}
	
	public static void main(String[] args)  {
			try {
				new Srv_SSL();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	
	//Comandos usados en la clase 
	/*
	 * keytool -genkeypair -keystore AlmacenSRV -alias servidor -keyalg RSA -validity 90 -storetype pkcs12
	 * keytool -export -keystore AlmacenSRV -alias servidor -file servidor.cer
	 * keytool -import -keystore AlmacenSRV -file cliente.cer 
	 * rm *.cer
	 */


}
