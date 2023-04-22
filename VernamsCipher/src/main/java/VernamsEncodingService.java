import java.io.IOException;

public class VernamsEncodingService {
    private final VernamsProperties vernamsProperties;
    private final VernamEncoder vernamEncoder;

    private VernamsEncodingService(VernamsProperties vernamsProperties) {
        this.vernamsProperties = vernamsProperties;
        this.vernamEncoder = new VernamEncoder();
    }

    public void start() throws IOException {

        CustomFileReader customFileReader = new CustomFileReader();

        System.out.println("--- VERNAM'S CIPHER ---");
        System.out.println("(Plain) Input text: " + vernamsProperties.text);

        String binaryText = vernamEncoder.getBitRepresentation(vernamsProperties.text);
        System.out.println("(Binary) Text format:   " + binaryText);

        String generatedKey = vernamEncoder.generateKey(vernamsProperties.firstPrimeNumber *
                vernamsProperties.secondPrimeNumber, binaryText.length());
        System.out.println("(Binary) Generated key: " + generatedKey);

        String encodedText = vernamEncoder.XORTextWithKey(binaryText, generatedKey);

        customFileReader.save(generatedKey, encodedText);

        System.out.println("(Binary) Encoded Text:  " + encodedText);

        String decodedText = vernamEncoder.XORTextWithKey(encodedText, generatedKey);
        System.out.println("(Binary) Decoded Text:  " + decodedText);

        String decodedBitsToText = vernamEncoder.decodeBitsToPlain(decodedText);
        System.out.println("(Plain) Decoded Text: " + decodedBitsToText);
    }

    public static class VernamsProperties {
        private String text;
        private long firstPrimeNumber;
        private long secondPrimeNumber;

        public VernamsProperties() {
        }

        public VernamsProperties withFirstPrimeNumber(long firstPrimeNumber) {
            this.firstPrimeNumber = firstPrimeNumber;
            return this;
        }

        public VernamsProperties withSecondPrimeNumber(long secondPrimeNumber) {
            this.secondPrimeNumber = secondPrimeNumber;
            return this;
        }

        public VernamsProperties withText(String param3) {
            this.text = param3;
            return this;
        }

        public VernamsEncodingService build() {
            return new VernamsEncodingService(this);
        }
    }
}
