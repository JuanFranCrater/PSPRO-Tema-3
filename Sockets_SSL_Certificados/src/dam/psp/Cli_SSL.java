package dam.psp;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.UnknownHostException;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class Cli_SSL {
	static final String DESTINO = "localhost";
	static final int PUERTO = 5555; //Puerto ddestino del servido al que conectar
	
	
private void mostrarCifrados(SSLSocket socket)
{
	String[] protocolos = socket.getEnabledProtocols();
	System.out.println("Protocolos habilitados:");
	for(int i =0; i<protocolos.length;i++)
		System.out.println("Protocolo numero "+i+": "+protocolos[i]);

	String[] protocolosSoportados = socket.getSupportedProtocols();
	System.out.println("Protocolos disponibles:");
	for(int i =0; i<protocolosSoportados.length;i++)
		System.out.println("Protocolo numero "+i+": "+protocolosSoportados[i]);

	String[] protocolosDeseados = new String[1];
	protocolosDeseados[0]="TLSv1.2";
	socket.setEnabledProtocols(protocolosDeseados);
	
	protocolos = socket.getEnabledProtocols();
	System.out.println("Protocolo activos: ");
	for(int i =0; i<protocolos.length;i++)
		System.out.println("Protocolo numero "+i+": "+protocolos[i]);
	
	
}
	
 public Cli_SSL(String mensaje) throws UnknownHostException, IOException {
		System.out.println("Obteniendo factoria del socket del cliente...");
		SSLSocketFactory sokectCLIFactory=(SSLSocketFactory) SSLSocketFactory.getDefault();
		
		System.out.println("Creando el socket del cliente");
		SSLSocket socket = (SSLSocket) sokectCLIFactory.createSocket(DESTINO, PUERTO);
		
		mostrarCifrados(socket);//Mostramos los cifrados que actualmente tenemos disponibles/ habilitados
	
		PrintWriter pw = new PrintWriter(new BufferedOutputStream(socket.getOutputStream()));
		pw.println(mensaje);
		pw.flush();//Por seguridad
		System.out.println("Mensaje enviado: "+mensaje);
		BufferedReader br= new BufferedReader(new InputStreamReader(socket.getInputStream()));
		System.out.println("Mensaje cifrado recibido: "+br.readLine());
		System.out.println("Cerrando la conexión");
		pw.close();
		br.close();
		socket.close();
		System.out.println("Finalizando");
 }
	public static void main(String[] args)  {
		try {
			System.setProperty("javax.net.ssl.keyStore", "./cert/AlmacenCLI");
			System.setProperty("javax.net.ssl.keyStorePassword", "123456");
			System.setProperty("javax.net.ssl.trustStore", "./cert/AlmacenCLI");
			System.setProperty("javax.net.ssl.trustStorePassword", "123456");
			new Cli_SSL("Mensaje");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
 }
}
