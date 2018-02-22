package dam.psp;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.SSLSocket;

public class Srv_SSLThread extends Thread {
	SSLSocket miSocket;
	
	public Srv_SSLThread(SSLSocket socketAtencion) {
			miSocket=socketAtencion;
	}

	@Override
	public void run() {
		try {
			InputStreamReader isr = new InputStreamReader(miSocket.getInputStream(),"utf8");
			BufferedReader br= new BufferedReader(isr);
			
			String mensajeRecibido=br.readLine();
			System.out.println("Mensaje Recibido: "+mensajeRecibido);
			
			//Enviamos como respuesta el mensaje en hash SHA256
			
			PrintWriter pw= new PrintWriter(new BufferedOutputStream(miSocket.getOutputStream()),true);
			
			byte[] mensajeEnBytes = mensajeRecibido.getBytes("utf8");
			MessageDigest sha = MessageDigest.getInstance("SHA-256");
			
			pw.print(sha.digest(mensajeEnBytes));
			pw.flush();
			pw.close();
			System.out.println("Cerrando socket y el hilo de atención");
			miSocket.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
}
