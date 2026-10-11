import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;

public class App {

    public static void main(String[] args) {
        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(2048);

            KeyPair pair = generator.generateKeyPair();
            PrivateKey privateKey = pair.getPrivate();
            PublicKey publicKey = pair.getPublic();


            String message = "Transfer $1000 to Ahmed";
            System.out.println("\nOriginal Message: " + message);

            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initSign(privateKey);
            signature.update(message.getBytes(StandardCharsets.UTF_8));

            byte[] digitalSignature = signature.sign();

            System.out.println("\nDigital Signature:");
            System.out.println(Base64.getEncoder().encodeToString(digitalSignature));

            Signature verifier = Signature.getInstance("SHA256withRSA");
            verifier.initVerify(publicKey);
            verifier.update(message.getBytes(StandardCharsets.UTF_8));

            boolean valid = verifier.verify(digitalSignature);
            System.out.println("\nOriginal Message Verification: " + valid);

            String modifiedMessage = "Transfer $1000 to Ali";
            System.out.println("\nModified Message: " + modifiedMessage);

            verifier.initVerify(publicKey);
            verifier.update(modifiedMessage.getBytes(StandardCharsets.UTF_8));

            boolean modifiedValid = verifier.verify(digitalSignature);
            System.out.println("Modified Message Verification: " + modifiedValid);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
    }
}
