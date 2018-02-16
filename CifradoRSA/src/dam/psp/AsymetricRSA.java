package dam.psp;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyPairGeneratorSpi;
import java.security.NoSuchAlgorithmException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.Cipher;

import java.util.Base64;


public class AsymetricRSA {

	private static final String ALG="RSA";
	

	public static String encriptar(String mensaje, KeyPair clave) throws Exception{
		
		Cipher cipher =Cipher.getInstance(ALG);
		cipher.init(Cipher.ENCRYPT_MODE,clave.getPublic());
		
		byte[] encVal = cipher.doFinal(mensaje.getBytes("utf8"));
		
		byte[] criptogramaEnBytes = Base64.getEncoder().encode(encVal);
		
		return new String(criptogramaEnBytes);
	}
	
    public static String desencriptar(String criptograma, KeyPair clave) throws Exception{
    	Cipher cipher = Cipher.getInstance(ALG);
		cipher.init(Cipher.DECRYPT_MODE,clave.getPrivate());
		
		byte[] decValue = Base64.getDecoder().decode(criptograma.getBytes("utf8"));
		
		byte[] decryptedValue=cipher.doFinal(decValue);
		
		return new String(decryptedValue);
	}
    
    public static void mostrarInfoClave(KeyPair clave) throws Exception
    {
    	KeyFactory factory = KeyFactory.getInstance(ALG);
    	RSAPublicKeySpec partePublica = factory.getKeySpec(clave.getPublic(), RSAPublicKeySpec.class);
    	RSAPrivateKeySpec partePrivada = factory.getKeySpec(clave.getPrivate(), RSAPrivateKeySpec.class);

    	System.out.println("Clave Publica");
    	System.out.println("Módulus: "+partePublica.getModulus());
    	System.out.println("Exponentus: "+partePublica.getPublicExponent());
    	
    	System.out.println("Clave Privada");
    	System.out.println("Módulus: "+partePrivada.getModulus());
    	System.out.println("Exponentus: "+partePrivada.getPrivateExponent());

    }
	
	public static void main(String[] args) {
		
		String mensaje = "Anda que la que va a liar Paco, va a ser parda";
		System.out.println("Obteniendo el generador de clave RSA");
		
		try {
			KeyPairGenerator keyPairGenerator= KeyPairGenerator.getInstance(ALG);
			System.out.println("Generando la clave RSA");
			KeyPair clave = keyPairGenerator.generateKeyPair();
			
			System.out.println("Mostrar informacion de la clave generada:");
			mostrarInfoClave(clave);
			String criptograma = encriptar(mensaje, clave);
			System.out.println("Mensaje cifrado: "+criptograma);
			mensaje= desencriptar(criptograma, clave);
			System.out.println("Mensaje descifrado: "+mensaje);
			
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			
	}

}
