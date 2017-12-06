package encry.nodeView

import encry.SimpleSyncInfo
import encry.transaction.{SimpleBlock, SimpleTransaction}
import scorex.core.settings.{NetworkSettings, ScorexSettings}
import scorex.core.transaction.box.proposition.PublicKey25519Proposition
import scorex.core.{ModifierId, ModifierTypeId, NodeViewHolder, NodeViewModifier}

class SimpleNodeViewHolder(settings: ScorexSettings)
  extends NodeViewHolder[PublicKey25519Proposition, SimpleTransaction, SimpleBlock] {

  override val networkChunkSize: Int = settings.network.networkChunkSize

  override type SI = SimpleSyncInfo
  override type HIS = this.type

}
