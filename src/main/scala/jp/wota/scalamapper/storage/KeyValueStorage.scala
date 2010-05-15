package jp.wota.scalamapper.storage

import com.nodeta.scalandra._
import org.apache.cassandra.thrift.{NotFoundException, ThriftGlue, ConsistencyLevel}
import com.nodeta.scalandra.serializer.StringSerializer

/* ----------------------------------------------------------------------
 [Storage class]

   import jp.wota.scalamapper._

   // KVS
   val kvs = new KeyValueStorage("127.0.0.1", 9160, "Keyspace1", "Standard2")
   kvs.set("port", "80")
   kvs.get("port") // => "80"

 */

case class KeyValueStorage(repository:Repository) {
/*
  def apply(host:String, port:Int, keyspace: String, standard: String): KeyValueStorage =
    this(new Repository(new Connection(host, port), keyspace, standard))
    */
}

object KeyValueStorage {
/*
  def apply(repository:Repository) = new KeyValueStorage(repository)
  def apply(connection:Connection, keyspace: String, standard: String) =
    this(new Repository(connection, keyspace, standard))
  def apply(keyspace: String, standard: String) = this(Connection(), keyspace, standard)
  def apply(): KeyValueStorage = this("Keyspace1", "Standard2")
*/
}

