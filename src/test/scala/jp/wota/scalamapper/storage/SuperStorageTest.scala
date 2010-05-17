package jp.wota.scalamapper.tests

import org.specs._
import jp.wota.scalamapper.storage._

class SuperStorageTest extends Specification {  

  doBeforeSpec {
  }

  val storage = new SuperStorage("127.0.0.1", 9160, "Test", "Super1")

  def lang_scala_ver_28_exist {
    storage.set("lang", "Scala", "ver", "2.8")
    storage.get("lang", "Scala", "ver") must beSome("2.8")
  }

  "SuperStorage" should {
    "write and read string value by set/get" in {
      lang_scala_ver_28_exist
      storage.set("lang", "Scala", "ver", "2.7.7")
      storage.get("lang", "Scala", "ver") must beSome("2.7.7")
    }

    "delete an entry" in {
      lang_scala_ver_28_exist
      storage.del("lang", "Scala", "ver")
      storage.get("lang", "Scala", "ver") must beNone
    }

    "count entries" in {
      "(case no entries)" in {
	storage.set("lang", "Scala", "ver", "2.8")
	storage.set("lang", "Scala", "author", "odersky")
	storage.count("lang", "Scala") must equalTo(2)
      }

      "(case parent not found))" in {
	storage.count("lang", "___should_not_exist___") must equalTo(0)
      }
    }

  }
}
