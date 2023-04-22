import java.math.BigInteger
import java.util.Random

class Rsa {

    private fun divideTextToBlocks(blockSize: Int, text: String): List<String>{
        var blocksOfChars = mutableListOf<String>()
        var i = 0

        while(i < text.length){
            var block = text.substring(i, minOf(i+blockSize, text.length))
            blocksOfChars.add(block)
            i += blockSize
        }

        return blocksOfChars
    return blocksOfChars
    }

    private fun textToAscii(text: String): List<Long>{
        var result = mutableListOf<Long>()

        for(i in text.toCharArray()){
            result.add(i.code.toLong())
        }

        return result
    }

    private fun decryptText(text: List<BigInteger>, key: RsaKey): List<BigInteger>{
        var result = mutableListOf<BigInteger>()

        for(i in text){
            result.add(i.modPow(key.privateKeyE, key.n))
        }
        return result
    }

    private fun encryptText(text: List<Long>, key: RsaKey): List<BigInteger> {
        var result = mutableListOf<BigInteger>()

        for(i in text){
            val bigText = BigInteger.valueOf(i)
            result.add(bigText.modPow(key.publicKeyE, key.n))
        }
        return result
    }


    private fun generateRsaKey(): RsaKey{

        val rand1 = Random(System.currentTimeMillis())
        val rand2 = Random(System.currentTimeMillis()*10)

        var firstPrime = BigInteger.probablePrime(384, rand1)
        var secondPrime = BigInteger.probablePrime(384, rand2)

        println("Generated first prime: $firstPrime")
        println("Generated second prime: $secondPrime")

        val n = firstPrime.multiply(secondPrime)

        firstPrime = firstPrime.subtract(BigInteger.valueOf(1))
        secondPrime = secondPrime.subtract(BigInteger.valueOf(1))

        val v = firstPrime.multiply(secondPrime)
        val k = generateProbablyPrimeNumber(v)
        val d = k.modInverse(v)

        println("Generated public key: ($k, $n)")
        println("Generated private key: ($d, $n)")

        return RsaKey(k, d, n)
    }

    private fun generateProbablyPrimeNumber(number: BigInteger): BigInteger{

        for (i in 2..number.toLong()) {
            val generated = BigInteger.valueOf(i)
            if (generated.gcd(number) == BigInteger.ONE) {
                return generated
            }
        }

        return BigInteger.valueOf(-1)
    }


    fun generateRSA(){

        var fileManager = CustomFileManager()

        var text = fileManager.readText()

        println("Text to encrypt: $text")

        val blocks: List<String> = divideTextToBlocks(10, text)
        val asciiBlocks: List<List<Long>> = blocks.map { block -> textToAscii(block) }.toList()
        println("Text in ascii: $asciiBlocks")

        val rsaKey: RsaKey = generateRsaKey()

        val encryptedText = asciiBlocks.map { block -> encryptText(block, rsaKey) }.toList()

        println("Encrypted text with public key: $encryptedText")

        val decryptedText = encryptedText.map { blocks -> decryptText(blocks, rsaKey) }.toList()

        println("Decrypted text with private key $decryptedText")
    }
}