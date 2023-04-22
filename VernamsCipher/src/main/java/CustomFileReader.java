import java.io.*;

public class CustomFileReader {

    private static final String fileName = "src/main/resources/data.txt";

    private BufferedReader getReader(){

        try {
            return new BufferedReader(new FileReader(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private BufferedWriter getWriter() throws IOException {
        File file = new File(fileName);
        FileOutputStream fos = new FileOutputStream(file);
        return new BufferedWriter(new OutputStreamWriter(fos));
    }

    public String readText() throws IOException {

        BufferedReader reader = getReader();

        return reader.readLine();
    }

    public void save(String key, String text) throws IOException {
        BufferedWriter bufferedWriter = getWriter();
        bufferedWriter.write(key);
        bufferedWriter.newLine();
        bufferedWriter.write(text);
        bufferedWriter.flush();
        bufferedWriter.close();
    }
}
