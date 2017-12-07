package encry.modifiers.mempool

import encry.transaction.EncryBaseTransaction
import scorex.core.ModifierId
import scorex.core.transaction.MemoryPool

import scala.collection.concurrent.TrieMap
import scala.collection.mutable
import scala.util.Try

class BasicMempool extends MemoryPool[EncryBaseTransaction, BasicMempool] {

  private val unconfTxs: TrieMap[TxKey, EncryBaseTransaction] = TrieMap()
  private type TxKey = scala.collection.mutable.WrappedArray.ofByte

  private def key(id: Array[Byte]): TxKey = new mutable.WrappedArray.ofByte(id)

  //getters
  override def getById(id: ModifierId): Option[EncryBaseTransaction] = unconfTxs.get(key(id))

  override def contains(id: ModifierId): Boolean = unconfTxs.contains(key(id))

  override def putWithoutCheck(txs: Iterable[EncryBaseTransaction]): BasicMempool = {
    txs.foreach(tx => unconfTxs.put(key(tx.id), tx))
    this
  }

  //modifiers
  override def put(tx: EncryBaseTransaction): Try[BasicMempool] = put(Seq(tx))

  override def put(txs: Iterable[EncryBaseTransaction]): Try[BasicMempool] = Try {
    txs.foreach(tx => require(!unconfTxs.contains(key(tx.id))))
    putWithoutCheck(txs)
  }

  override def take(limit: Int): Iterable[EncryBaseTransaction] =
    unconfTxs.keys.take(limit).flatMap(k => unconfTxs.get(k))

  override def remove(tx: EncryBaseTransaction): BasicMempool = filter(t => t.id sameElements tx.id)

  override def getAll(ids: Seq[ModifierId]): Seq[EncryBaseTransaction] = unconfTxs.values.toSeq

  override type NVCT = BasicMempool

  override def filter(condition: (EncryBaseTransaction) => Boolean): BasicMempool = {
    unconfTxs.filter(tx => condition(tx._2)).foreach(tx => unconfTxs.remove(tx._1))
    this
  }

  override def size: Int = unconfTxs.size
}
