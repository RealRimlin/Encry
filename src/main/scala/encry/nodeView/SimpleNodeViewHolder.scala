package encry.nodeView

import encry.{SimpleBlockchain, SimpleSyncInfo}
import encry.transaction.{EncryBaseTransaction, SimpleBlock}
import scorex.core.settings.{NetworkSettings, ScorexSettings}
import scorex.core.transaction.box.proposition.PublicKey25519Proposition
import scorex.core.{ModifierId, ModifierTypeId, NodeViewHolder, NodeViewModifier}

class SimpleNodeViewHolder(settings: ScorexSettings)
  extends NodeViewHolder[PublicKey25519Proposition, EncryBaseTransaction, SimpleBlock] {

  override val networkChunkSize: Int = settings.network.networkChunkSize

  override type SI = SimpleSyncInfo
  override type HIS = SimpleBlockchain


}
