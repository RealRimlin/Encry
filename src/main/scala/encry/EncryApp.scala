package encry

import encry.transaction.{EncryBaseTransaction, SimpleBlock}
import scorex.core.app.Application
import scorex.core.transaction.box.proposition.PublicKey25519Proposition

class EncryApp(args: Seq[String]) extends Application {
  override type P = PublicKey25519Proposition
  override type TX = EncryBaseTransaction
  override type PMOD = SimpleBlock
  override type NVHT =
}

object EncryApp extends App {
  new EncryApp(args).run()

  def forceStopApplication(code: Int = 1): Unit =
    new Thread(() => System.exit(code), "encry-shutdown-thread").start()
}
