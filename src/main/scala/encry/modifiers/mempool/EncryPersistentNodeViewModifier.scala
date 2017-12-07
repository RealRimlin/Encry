package encry.modifiers.mempool

import scorex.core.{ModifierId, PersistentNodeViewModifier}

trait EncryPersistentNodeViewModifier extends PersistentNodeViewModifier {

  // TODO: Do we need version field for all modifiers?
  // val version: Version

  //TODO: Likely to be removed soon in the base class
  def parentId: ModifierId = null

}
