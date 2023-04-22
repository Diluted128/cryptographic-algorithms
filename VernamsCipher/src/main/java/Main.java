import java.io.IOException;

public class Main {
    private static final long firstPrimeNumber = 97001;
    private static final long secondPrimeNumber = 42187;

    public static void main(String[] args) throws IOException {

        CustomFileReader customFileReader = new CustomFileReader();

        String text = customFileReader.readText();

        VernamsEncodingService vernamsEncodingService = new VernamsEncodingService.VernamsProperties()
                .withText(text)
                .withFirstPrimeNumber(firstPrimeNumber)
                .withSecondPrimeNumber(secondPrimeNumber)
                .build();

        vernamsEncodingService.start();
    }
}
