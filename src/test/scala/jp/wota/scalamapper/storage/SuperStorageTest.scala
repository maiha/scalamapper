package jp.wota.scalamapper.tests

import org.specs._
import jp.wota.scalamapper.storage._

class SuperStorageTest extends Specification {  

  doBeforeSpec {
  }

  val storage = new SuperStorage("127.0.0.1", 9160, "Test2", "Super1")

  "SuperStorage" should {
    "write and read string value by set/get" in {
      // set another value
      storage.set("lang", "Scala", "ver", "2.8")
      storage.get("lang", "Scala", "ver") must beSome("2.8")

      // when
      storage.set("lang", "Scala", "ver", "2.7.7")

      // Then
      storage.get("lang", "Scala", "ver") must beSome("2.7.7")
      /*
       get Keyspace1.Super2['test']['http']
       => (column=http, value=8080, timestamp=1273957637234)
       */
    }

    "delete an entry" in {
      // Given
      storage.set("lang", "Scala", "ver", "2.8")
      storage.get("lang", "Scala", "ver") must beSome("2.8")

      // When
      storage.del("lang", "Scala", "ver")

      // Then
      storage.get("lang", "Scala", "ver") must beNone
      /*
       get Keyspace1.Super2['test']['http']
       => (column=http, value=8080, timestamp=1273957637234)
       */
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
