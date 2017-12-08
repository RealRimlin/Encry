package encry.nodeView

import encry.modifiers.mempool.BasicMempool
import encry.{SimpleBlockchain, SimpleSyncInfo}
import encry.transaction.{EncryBaseTransaction, EncryMinimalState, SimpleBlock, SimpleBlockCompanion}
import scorex.core.serialization.Serializer
import scorex.core.settings.{NetworkSettings, ScorexSettings}
import scorex.core.transaction.box.proposition.PublicKey25519Proposition
import scorex.core.{ModifierId, ModifierTypeId, NodeViewHolder, NodeViewModifier}

class SimpleNodeViewHolder[StateType <: EncryMinimalState[StateType]](settings: ScorexSettings)
  extends NodeViewHolder[PublicKey25519Proposition, EncryBaseTransaction, SimpleBlock] {

  override val networkChunkSize: Int = settings.network.networkChunkSize

  override type SI = SimpleSyncInfo
  override type HIS = SimpleBlockchain
  override type MS = StateType
  override type MP = BasicMempool
  override type VL = this.type

  override lazy val modifierSerializers: Map[ModifierTypeId, Serializer[_ <: NodeViewModifier]] =
    Map(SimpleBlock.ModifierTypeId -> SimpleBlockCompanion)

  override def restoreState(): Option[(HIS, MS, VL, MP)] = None

  override protected def genesisState: (HIS, MS, VL, MP) = ???

}
