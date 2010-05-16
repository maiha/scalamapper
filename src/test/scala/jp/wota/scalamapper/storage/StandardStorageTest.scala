package jp.wota.scalamapper.tests

import org.specs._
import jp.wota.scalamapper.storage._

class StandardStorageTest extends Specification {  

  doBeforeSpec {
  }

  val storage = new StandardStorage("127.0.0.1", 9160, "Test", "Standard1")

  "StandardStorage" should {
    "write and read string value by set/get" in {
      // set another value
      storage.set("Scala", "ver", "2.8")
      storage.get("Scala", "ver") must beSome("2.8")

      // when
      storage.set("Scala", "ver", "2.7.7")

      // Then
      storage.get("Scala", "ver") must beSome("2.7.7")
      /*
       get Keyspace1.Standard2['test']['http']
       => (column=http, value=8080, timestamp=1273957637234)
       */
    }

    "delete an entry" in {
      // Given
      storage.set("Scala", "ver", "2.8")
      storage.get("Scala", "ver") must beSome("2.8")

      // When
      storage.del("Scala", "ver")

      // Then
      storage.get("Scala", "ver") must beNone
      /*
       get Keyspace1.Standard2['test']['http']
       => (column=http, value=8080, timestamp=1273957637234)
       */
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

/*
    "fetch keys from the entry" in {
      storage.set("Scala", "ver", "2.8")
      storage.set("Scala", "author", "odersky")
      storage.keys("Scala") must equalTo(List("author","ver"))
    }
*/

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
