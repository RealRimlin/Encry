package encry

import scorex.core.app.Application

class EncryApp(args: Seq[String]) extends Application {
//  override type P = AnyoneCanSpendProposition.type
//  override type TX = AnyoneCanSpendTransaction
//  override type PMOD = EncryPersistentModifier
//  override type NVHT = EncryNodeViewHolder[_]
}

object ErgoApp extends App {
  new EncryApp(args).run()

  def forceStopApplication(code: Int = 1): Unit =
    new Thread(() => System.exit(code), "encry-shutdown-thread").start()
}
