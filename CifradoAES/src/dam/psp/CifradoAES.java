package dam.psp;

import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

public class CifradoAES {
	
private static String cifrado ="AES";
	
	public static SecretKey obtenerClaveOpaca(int longitud) throws Exception {
		KeyGenerator claveInstancia = KeyGenerator.getInstance(cifrado);
		claveInstancia.init(longitud);
		//por defecto es 128 bits por restricciones del gobierno de los estados unidos
		return claveInstancia.generateKey();
	}
	
	public static SecretKeySpec obtenerClaveTransparente(String miClave) throws Exception{
		byte[] miClaveEnBytes = miClave.getBytes("utf8");
		System.out.println("El hash SHA2 de la clave es: "+ DigestUtils.sha256Hex(miClaveEnBytes));
		byte[] miClaveSha256 = Arrays.copyOf(DigestUtils.sha256(miClaveEnBytes),16);
		return new SecretKeySpec(miClaveSha256, cifrado);
	}

	public static String encriptar(String mensaje, SecretKey clave) throws Exception{
		Cipher cipher = Cipher.getInstance(cifrado);
		cipher.init(Cipher.ENCRYPT_MODE,clave);
		byte[] encVal = cipher.doFinal(mensaje.getBytes("utf8"));
		byte[] criptogramaEnBytes = Base64.encodeBase64(encVal);
		return new String(criptogramaEnBytes);
	}
	public static String desencriptar(String criptograma, SecretKey clave) throws Exception {
		Cipher cipher = Cipher.getInstance(cifrado);
		cipher.init(Cipher.DECRYPT_MODE,clave);
		byte[] decValue = Base64.decodeBase64(criptograma.getBytes("utf8"));
		byte[] decryptedValue=cipher.doFinal(decValue);
		return new String(decryptedValue);
	}
	
	public static void main(String[] args) {
		
		String mensaje = "Vaya melón que tiene Ciceró un viernes por la tarde en el Tivoli";
		String miClave="123;abc";
		try {
			SecretKey miClaveOpaca = CifradoAES.obtenerClaveOpaca(128);
			System.out.println("Mensaje en Claro: "+mensaje);
			
			String criptograma = CifradoAES.encriptar(mensaje, miClaveOpaca);
			System.out.println("Criptograma: "+criptograma);
			System.out.println("Desencriptando: "+ CifradoAES.desencriptar(criptograma, miClaveOpaca));
			
			//Ahora repetimos usando una clave transparente
			
			SecretKeySpec claveT = CifradoAES.obtenerClaveTransparente(miClave);
			
			String criptogramaT = CifradoAES.encriptar(mensaje, claveT);
			System.out.println("CriptogramaT: "+criptogramaT);
			System.out.println("DesencriptandoT: "+ CifradoAES.desencriptar(criptogramaT, claveT));
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

}

