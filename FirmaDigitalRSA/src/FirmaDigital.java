import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.util.Base64;


public class FirmaDigital {

	//private static final String ALGID="DSA";
	private static final String ALGIR="RSA";
	
	//private static final String PROTO1="DSA";
	private static final String PROTO2="SHA256WithRSA";
	
	
	//DSA is faster for signing, RSA is faster for verifying
	//DSA can only be used for signing/verification
	//whereas RSA can be used for encryption/decryption as well
	
	private static String firmar(String mensaje, KeyPair clave) throws Exception {
		System.out.println("Creando un objeto de tipo Signature");
		Signature firmaContenido = Signature.getInstance(PROTO2);
		
		firmaContenido.initSign(clave.getPrivate());
		firmaContenido.update(mensaje.getBytes("utf8"));
		
		byte[] firma = firmaContenido.sign();
		
		return Base64.getEncoder().encodeToString(firma);
	
	}
	
	private static boolean esValida(String mensajeEnClaro, String firma, KeyPair clave) throws Exception {
		System.out.println("Creando un objeto de tipo Signature");
		Signature firmaContenido = Signature.getInstance(PROTO2);
		
		byte[] mensajeBytes=Base64.getDecoder().decode(firma.getBytes("utf8"));
		
		System.out.println("Verificando la firma con la parte PUBLICA de la clave asimetrica");
		firmaContenido.initVerify(clave.getPublic());
		
		firmaContenido.update(mensajeEnClaro.getBytes("utf8"));
		
		return firmaContenido.verify(mensajeBytes);
		
	}
	
	public static void main(String[] args) {

		String mensaje = "En un lugar de la Mancha, de cuyo nombre ni me acuerdo";
		System.out.println("Obteniendo el generador de claves: "+ALGIR);
		try {
			KeyPairGenerator keyPairGenerator =KeyPairGenerator.getInstance(ALGIR);
			KeyPair clave= keyPairGenerator.generateKeyPair();
			System.out.println("Firmando el mensaje '"+mensaje+"' con la parte privada de la clave");
			String firma=firmar(mensaje, clave);
			
			System.out.println("Resultado de la firma: "+firma);
			System.out.println("Comprobando la validez de esa firma....");
			
			if(esValida(mensaje, firma, clave))
			{
				System.out.println("Verificado");
			}else {
				System.out.println("Firma no valida");
			}
			
			System.out.println("Ahora manipulamos el texto y debe invalidar la firma");
			String mensajeAlterado = mensaje+"\n";
			
			if(esValida(mensajeAlterado, firma, clave))
			{
				System.out.println("Verificado");
			}else {
				System.out.println("Firma no valida");
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
