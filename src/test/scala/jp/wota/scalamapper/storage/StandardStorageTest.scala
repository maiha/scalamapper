package jp.wota.scalamapper.tests

import org.specs._
import jp.wota.scalamapper.storage._

class StandardStorageTest extends Specification {  

  doBeforeSpec {
  }

  val storage = new StandardStorage("127.0.0.1", 9160, "Test", "Standard1")
  def scala_ver_28_exist {
    storage.set("Scala", "ver", "2.8")
    storage.get("Scala", "ver") must beSome("2.8")
  }

  "StandardStorage" should {
    "write and read string value by set/get" in {
      scala_ver_28_exist
      storage.set("Scala", "ver", "2.7.7")
      storage.get("Scala", "ver") must beSome("2.7.7")
    }

    "delete an entry" in {
      scala_ver_28_exist
      storage.del("Scala", "ver")
      storage.get("Scala", "ver") must beNone
    }

    "count entries" in {
      "(case no entries)" in {
	storage.set("Scala", "ver", "2.8")
	storage.set("Scala", "author", "odersky")
	storage.count("Scala") must equalTo(2)
      }

      "(case parent not found))" in {
	storage.count("___should_not_exist___") must equalTo(0)
      }
    }

    "slice entries" in {
      storage.set("Scala", "ver", "2.8") // target key
      storage.set("Scala", "author", "odersky")
      storage.set("Ruby" , "ver", "1.8") // anothor key

      storage.slice("Scala") must equalTo(Map("author"->"odersky", "ver"->"2.8"))
    }

    "fetch keys from the entry" in {
      storage.set("Scala", "ver", "2.8") // target key
      storage.set("Scala", "author", "odersky")
      storage.set("Ruby" , "ver", "1.8") // anothor key

      storage.keys("Scala")  must equalTo(List("author","ver"))
    }


/*
    "respond whether specifed entry exist or not" in {
      // Given
      val col = storage
      storage.set("Scala", "ver", "2.8")

      // Then
      storage.exist("Scala", "ver") must beTrue
    }

*/
  }
}
