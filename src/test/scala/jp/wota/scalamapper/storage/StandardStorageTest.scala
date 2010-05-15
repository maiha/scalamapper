package jp.wota.scalamapper.tests

import org.specs._
import jp.wota.scalamapper.storage._

class StandardStorageTest extends Specification {  

  doBeforeSpec {
  }

  def storage = new StandardStorage("127.0.0.1", 9160, "Keyspace1", "Standard2")

  "StandardStorage" should {
    "write and read string value by set/get" in {
      // Given
      val col = storage
      // set another value
      col.set("test", "http", "80")
      col.get("test", "http") must beSome("80")

      // when
      col.set("test", "http", "8080")

      // Then
      col.get("test", "http") must beSome("8080")
      /*
       get Keyspace1.Standard2['test']['http']
       => (column=http, value=8080, timestamp=1273957637234)
       */
    }

    "delete an entry" in {
      // Given
      val col = storage
      col.set("test", "http", "80")
      col.get("test", "http") must beSome("80")

      // When
      col.del("test", "http")

      // When
      col.get("test", "http") must beNone
      /*
       get Keyspace1.Standard2['test']['http']
       => (column=http, value=8080, timestamp=1273957637234)
       */
    }

    "count entries" in {
      // Given
      val col = storage

      // Then
//      col.count("test") must equalTo(1)
    }


/*
    "respond whether specifed entry exist or not" in {
      // Given
      val col = storage
      col.set("test", "http", "80")

      // Then
      col.exist("test", "http") must beTrue
    }

*/
  }
}
