import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import static java.awt.SystemColor.text;

public class UtilitatsXifrar {

        public static SecretKey keygenKeyGeneration(int keySize){
            SecretKey sKey = null;
            if ((keySize == 128)||(keySize == 192)||(keySize == 256)) {
                try {
                    KeyGenerator kgen = KeyGenerator.getInstance("AES");
                    kgen.init(keySize);
                    sKey = kgen.generateKey();

                } catch (NoSuchAlgorithmException ex) {
                    System.err.println("Generador no disponible.");
                }
            }
            return sKey;
        }
        public static SecretKey passwordKeyGeneration(String text, int keySize){
            SecretKey sKey = null;
            if ((keySize == 128)||(keySize == 192)||(keySize == 256)) {
                try {
                    byte[] data = text.getBytes("UTF-8");
                    MessageDigest md = MessageDigest.getInstance("SHA-256");
                    byte[] hash = md.digest(data);
                    byte[] key = Arrays.copyOf(hash, keySize/8);
                    sKey = new SecretKeySpec(key, "AES");
                } catch (Exception ex) {
                    System.err.println("Error generant la clau:" + ex);
                }
            }
            return sKey;
        }
        public static byte[] encryptData(byte[] data, SecretKey key){
            byte[] encryptedData = null;
            try {
                Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
                cipher.init(Cipher.ENCRYPT_MODE, key);
                encryptedData =  cipher.doFinal(data);
            } catch (Exception  ex) {
                System.err.println("Error xifrant les dades: " + ex);
            }
            return encryptedData;
        }

        public static byte[] decryptData(byte[] data, SecretKey key){
            byte[] encryptedData = data;
            try {
                Cipher cipher = Cipher.getInstance("AES");
                cipher.init(Cipher.DECRYPT_MODE, key);
                encryptedData =  cipher.doFinal(data);

            } catch (Exception  e) {
                System.out.println("Error al desencriptar les dades, Password Incorrecte." + e);
            }
            return encryptedData;
        }
}