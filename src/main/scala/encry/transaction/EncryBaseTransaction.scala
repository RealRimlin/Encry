package encry.transaction

import com.google.common.primitives.Longs
import encry.modifiers.mempool.MempoolModifier
import io.circe.Json
import io.circe.syntax._
import scorex.core.serialization.Serializer
import scorex.core.transaction.BoxTransaction
import scorex.core.transaction.box.BoxUnlocker
import scorex.core.transaction.box.proposition.{Constants25519, PublicKey25519Proposition}
import scorex.crypto.authds.ADKey
import scorex.crypto.encode.Base58
import scorex.crypto.signatures.PublicKey

import scala.util.Try


case class EncryBaseTransaction(input: PublicKey25519Proposition,   // TODO: Should txn contain multiple inputs   ??
                                output: PublicKey25519Proposition,  // TODO: Should txn contain multiple outputs  ??
                                amount: Long,
                                fee: Long,
                                nonce: Long,
                                timestamp: Long)
  extends BoxTransaction[PublicKey25519Proposition, PublicKey25519NoncedBox] with MempoolModifier {

  override type M = this.type

  // TODO: Can this txn be deserialized from json  ??
  override lazy val json: Json = Map(
    "sender" -> Base58.encode(input.pubKeyBytes).asJson,
    "recipient" -> Base58.encode(output.pubKeyBytes).asJson,
    "amount" -> amount.asJson,
    "fee" -> fee.asJson,
    "nonce" -> nonce.asJson,
    "timestamp" -> timestamp.asJson
  ).asJson

  override def toString: String = s"AnyoneCanSpendTransaction(${json.noSpaces})"

  override lazy val messageToSign: Array[Byte] = id

  override def serializer: Serializer[EncryBaseTransaction] = EncryTransactionSerializer

  // How to implement `unlock`
  override val unlockers: Traversable[BoxUnlocker[PublicKey25519Proposition]] = ???

  // Список новых выходов, которые будут созданы транзакцией и помещены в `state`.
  override val newBoxes: Traversable[PublicKey25519NoncedBox] = ???

  // Список ID выходов, на которые необходимо сослаться для осуществления транзакции.
  // `ADKey` is the identifier of the `Box`.
  lazy val boxIdsToOpen: IndexedSeq[ADKey] = ???

}

object EncryTransactionSerializer extends Serializer[EncryBaseTransaction] {
  val TransactionLength: Int = 2 * Constants25519.PubKeyLength + 32

  override def toBytes(m: EncryBaseTransaction): Array[Byte] = {
    m.input.bytes ++
      m.output.bytes ++
      Longs.toByteArray(m.amount) ++
      Longs.toByteArray(m.fee) ++
      Longs.toByteArray(m.nonce) ++
      Longs.toByteArray(m.timestamp)
  }.ensuring(_.length == TransactionLength)

  override def parseBytes(bytes: Array[Byte]): Try[EncryBaseTransaction] = Try {
    val sender = PublicKey25519Proposition(PublicKey @@ bytes.slice(0, Constants25519.PubKeyLength))
    val recipient = PublicKey25519Proposition(PublicKey @@ bytes.slice(Constants25519.PubKeyLength, 2 * Constants25519.PubKeyLength))
    val s = 2 * Constants25519.PubKeyLength
    val amount = Longs.fromByteArray(bytes.slice(s, s + 8))
    val fee = Longs.fromByteArray(bytes.slice(s + 8, s + 16))
    val nonce = Longs.fromByteArray(bytes.slice(s + 16, s + 24))
    val timestamp = Longs.fromByteArray(bytes.slice(s + 24, s + 32))
    EncryBaseTransaction(sender, recipient, amount, fee, nonce, timestamp)
  }
}

