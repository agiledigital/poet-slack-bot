
// @GENERATOR:play-routes-compiler
// @SOURCE:/Users/wangxu/Desktop/poet-slack-bot/server/conf/routes
// @DATE:Wed Sep 28 23:27:09 AEST 2016


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
