
// @GENERATOR:play-routes-compiler
// @SOURCE:/Users/wangxu/Desktop/dev/poet-slack-bot/server/conf/routes
// @DATE:Sun Sep 18 14:41:03 AEST 2016


package router {
  object RoutesPrefix {
    private var _prefix: String = "/"
    def setPrefix(p: String): Unit = {
      _prefix = p
    }
    def prefix: String = _prefix
    val byNamePrefix: Function0[String] = { () => prefix }
  }
}
