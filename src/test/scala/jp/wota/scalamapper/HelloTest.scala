package jp.wota.scalamapper.tests

import org.specs._
import jp.wota.scalamapper._

class HelloTest extends Specification {  
  "Hello" should {
    "say hello world" in {
      println("hello world via test")
    }
  }
}
