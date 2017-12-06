import supertagged.TaggedType

package object encry {

  object GenerationSignature extends TaggedType[Array[Byte]]

  object BaseTarget extends TaggedType[Long]

  object Value extends TaggedType[Long]

  object Nonce extends TaggedType[Long]

  type GenerationSignature = GenerationSignature.Type

  type BaseTarget = BaseTarget.Type

  type Value = Value.Type

  type Nonce = Nonce.Type

}
