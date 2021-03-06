import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class ServidorHilo extends Thread{

	private Socket elSocket=null;
	private int numClient=0;
	String mensaje="Bienvenido/a a mi canal";
	
	public ServidorHilo(Socket skAtencion, int nCli) {
		this.elSocket=skAtencion;
		this.numClient=nCli;
	}
	
	@Override
	public void run()
	{
		//BufferedInputStream bufferedInputStream;
		//BufferedOutputStream bOutputStream;
		PrintWriter printWriter=null;
		BufferedReader bReader;
		
		try {
			//bOutputStream=
			printWriter=new PrintWriter(new OutputStreamWriter(elSocket.getOutputStream(),"utf8"),true);//al ser true, limpia el buffer
			printWriter.println(numClient+ ": "+this.mensaje);
			
			//y ahora espera una respuesta en forma de string desde el cliente
			bReader=new BufferedReader(new InputStreamReader(elSocket.getInputStream(),"utf8"));
			
			System.out.print("Mensaje Recibido desde el cliente: ");//se muestra y espera
			System.out.println(bReader.readLine());
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(printWriter !=null)
		{
			printWriter.flush();//nunca deberia hacer falta esto
			printWriter.close();
		}
	}

}
