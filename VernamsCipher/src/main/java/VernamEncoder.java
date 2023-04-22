import java.math.BigInteger;
import java.util.Random;

public class VernamEncoder {
    String getBitRepresentation(String text){

        StringBuilder binaryString = new StringBuilder();
        StringBuilder characterBinary = new StringBuilder();

        for (char c : text.toCharArray()) {
            characterBinary.append(Integer.toBinaryString(c));

            while (characterBinary.length() < 8) {
                characterBinary.insert(0, "0");
            }

            binaryString.append(characterBinary);
            characterBinary = new StringBuilder();
        }

        return binaryString.toString();
    }

    String decodeBitsToPlain(String bits){

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < bits.length(); i += 8) {
            String byteString = bits.substring(i, Math.min(i + 8, bits.length()));

            BigInteger byteValue = new BigInteger(byteString, 2);

            char byteChar = (char) byteValue.intValue();

            result.append(byteChar);
        }

        return result.toString();
    }

    String XORTextWithKey(String textBinary, String keyBinary){
        StringBuilder encodedText = new StringBuilder();

        for (int i = 0; i < textBinary.length(); i++)
        {
            if (textBinary.charAt(i) == keyBinary.charAt(i)){
                encodedText.append("0");
            }
            else{
                encodedText.append("1");
            }
        }
        return encodedText.toString();
    }

    String generateKey(long seed, long length){
        BigInteger sum = BigInteger.valueOf(seed);

        BigInteger randomNumber = nextRandomBigInteger(sum);

        StringBuilder key = new StringBuilder();

        BigInteger X0 = (randomNumber.multiply(randomNumber)).mod(sum);

        BigInteger result = (X0.multiply(X0)).mod(sum);

        String X1bit = result.mod(BigInteger.valueOf(2)).toString();

        key.append(X1bit);

        for(int i=0; i<length - 1; i++){
            result = result.multiply(result).mod(sum);
            key.append(result.mod(BigInteger.valueOf(2)));
        }

        return key.toString();
    }

    private static BigInteger nextRandomBigInteger(BigInteger max) {
        Random rand = new Random();
        BigInteger result = new BigInteger(max.bitLength(), rand);
        while( result.compareTo(max) >= 0 ) {
            result = new BigInteger(max.bitLength(), rand);
        }
        return result;
    }
}
