import java.math.BigInteger

data class RsaKey(
    val publicKeyE: BigInteger,
    val privateKeyE: BigInteger,
    val n: BigInteger
)