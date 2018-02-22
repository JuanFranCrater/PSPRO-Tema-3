package dam.psp;

import java.io.IOException;
import java.net.UnknownHostException;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class Cli_SSL {
	static final String DESTINO = "localhost";
	static final int PUERTO = 5555; //Puerto ddestino del servido al que conectar
	
 public Cli_SSL() throws UnknownHostException, IOException {
		System.out.println("Obteniendo factoria del socket del cliente...");
		SSLSocketFactory sokectCLIFactory=(SSLSocketFactory) SSLSocketFactory.getDefault();
		
		System.out.println("Creando el socket del cliente");
		SSLSocket socket = (SSLSocket) sokectCLIFactory.createSocket(DESTINO, PUERTO);
		
	
	}
	public static void main(String[] args)  {
		try {
			new Cli_SSL();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
 }
}
