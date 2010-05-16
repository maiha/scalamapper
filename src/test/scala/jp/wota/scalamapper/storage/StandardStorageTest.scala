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
      storage.set("lang", "ver", "2.8")
      storage.get("lang", "ver") must beSome("2.8")

      // when
      storage.set("lang", "ver", "2.7.7")

      // Then
      storage.get("lang", "ver") must beSome("2.7.7")
      /*
       get Keyspace1.Standard2['test']['http']
       => (column=http, value=8080, timestamp=1273957637234)
       */
    }

    "delete an entry" in {
      // Given
      storage.set("lang", "ver", "2.8")
      storage.get("lang", "ver") must beSome("2.8")

      // When
      storage.del("lang", "ver")

      // Then
      storage.get("lang", "ver") must beNone
      /*
       get Keyspace1.Standard2['test']['http']
       => (column=http, value=8080, timestamp=1273957637234)
       */
    }

    "count entries" in {
      "(case no entries)" in {
	storage.set("lang", "ver", "2.8")
	storage.count("lang") must equalTo(1)
      }

      "(case parent not found))" in {
	storage.count("___should_not_exist___") must equalTo(0)
      }
    }


/*
    "respond whether specifed entry exist or not" in {
      // Given
      val col = storage
      storage.set("lang", "ver", "2.8")

      // Then
      storage.exist("lang", "ver") must beTrue
    }

*/
  }
}
