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

case class StandardStorage(repository:Repository) {
  def this(host:String, port:Int, keyspace: String, standard: String) =
    this(new Repository(new Connection(host, port), keyspace, standard))

  def get(key:String, field:String): Option[String] =
    repository.getColumn(key, field)

  def set(key:String, field:String, value:String): Unit =
    repository.setColumn(key, field, value)

  def del(key:String, field:String): Unit =
    repository.delColumn(key, field)
}

object StandardStorage {
/*
  def apply(repository:Repository) = new StandardStorage(repository)
  def apply(connection:Connection, keyspace: String, standard: String) =
    new StandardStorage(new Repository(connection, keyspace, standard))
  def apply(keyspace: String, standard: String) =
    new StandardStorage(new Repository(Connection(), keyspace, standard))
  def apply(): StandardStorage =
    new StandardStorage(new Repository(Connection(), "Keyspace1", "Standard2"))
    */
}

