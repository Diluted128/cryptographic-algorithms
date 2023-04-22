import java.io.*

private const val fileName = "src/main/resources/data.text"

class CustomFileManager{
    private fun getReader(): BufferedReader? {
        try {
            return BufferedReader(FileReader(fileName))
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }

    @Throws(IOException::class)
    private fun getWriter(): BufferedWriter {
        val file = File(fileName)
        val fos = FileOutputStream(file)
        return BufferedWriter(OutputStreamWriter(fos))
    }

    @Throws(IOException::class)
    fun readText(): String {
        val reader = getReader()
        if (reader != null) {
            return reader.readLine()
        }
        return ""
    }

    @Throws(IOException::class)
    fun save(key: String?, text: String?) {
        val bufferedWriter = getWriter()
        bufferedWriter.write(key)
        bufferedWriter.newLine()
        bufferedWriter.write(text)
        bufferedWriter.flush()
        bufferedWriter.close()
    }
}
