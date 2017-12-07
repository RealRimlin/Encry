package encry.nodeView

import encry.{SimpleBlockchain, SimpleSyncInfo}
import encry.transaction.{EncryBaseTransaction, EncryMinimalState, SimpleBlock}
import scorex.core.settings.{NetworkSettings, ScorexSettings}
import scorex.core.transaction.box.proposition.PublicKey25519Proposition
import scorex.core.{ModifierId, ModifierTypeId, NodeViewHolder, NodeViewModifier}

class SimpleNodeViewHolder[StateType <: EncryMinimalState[StateType]](settings: ScorexSettings)
  extends NodeViewHolder[PublicKey25519Proposition, EncryBaseTransaction, SimpleBlock] {

  override val networkChunkSize: Int = settings.network.networkChunkSize

  override type SI = SimpleSyncInfo
  override type HIS = SimpleBlockchain
  override type MS = StateType
  override type MP = this.type

}
