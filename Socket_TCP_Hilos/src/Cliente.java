import java.awt.TexturePaint;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import javax.xml.stream.util.EventReaderDelegate;


public class Cliente {
	
	static final String HOST = "192.168.3.57";
	static final int PUERTO = 5000;
	Scanner entrada = null;
	
	private String leerMensaje() {
		
		System.out.println("Introduce el mensaje para enviar");
		String mensaje = entrada.nextLine();
		return mensaje;
	}
	
	public Cliente() {
		try {
			Socket skCli= new Socket(HOST, PUERTO);
			
			//InputStreamReader inputStreamReader = 
			
			BufferedReader bReader = new BufferedReader(new InputStreamReader(skCli.getInputStream(),"utf8"));
			BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in,"utf8"));
			PrintWriter pWriter = new PrintWriter(new OutputStreamWriter(skCli.getOutputStream(),"utf8"),true);//sin autoflush, no avisa al otro lado de que puede leer
			
			System.out.println(bReader.readLine());//Recibimos saludo del servidor
			
			//enviar mensaje pedido por consola al servidor
			System.out.println("Mensaje para enviar?");
			String mensaje=teclado.readLine();
			System.out.println("Enviando al servidor el mensaje: "+mensaje);
			
			pWriter.println(mensaje);
			
			if(pWriter !=null)
			{
				pWriter.flush();//nunca deberia hacer falta esto
				pWriter.close();
			}
			skCli.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
	}
	public static void main(String[] args) {
		new Cliente();
	}

}
