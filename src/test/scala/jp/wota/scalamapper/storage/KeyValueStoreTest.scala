package jp.wota.scalamapper.tests

import org.specs._
import jp.wota.scalamapper.storage._

class KeyValueStoreTest extends Specification {  

  doBeforeSpec {
//    Setting.delete("port")
  }

  "KeyValueStore" should {
    "write and read string value like KVS" in {
//      val kvs = new KeyValueStorage("127.0.0.1", 9160, "Keyspace1", "Standard2")
//      kvs.set("port", "80")
//      kvs.get("port") // => "80"
    }
  }


/*
  class Setting(key:String)  extends KeyValue {
    def primary_key = key
    
    def value = 
    
  }

  class Setting(key:String)  extends Column {
    def primary_key = key
    
    def value = 
    
  }

  class User extends Column {
    
  }
*/

}
