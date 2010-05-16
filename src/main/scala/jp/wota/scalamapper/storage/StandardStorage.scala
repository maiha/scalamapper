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

  def column(name:String) = repository.StandardColumn(name)

  def get(key:String, field:String): Option[String] = column(field).get(key)
  def set(key:String, field:String, value:String)   = column(field).set(key, value)
  def del(key:String, field:String)                 = column(field).del(key)
  def count(key:String)                             = column("dummy").count(key)
}

object StandardStorage {
}

/*
 * Following code does not work (>_<)

case class Storage(client:Client[A,B,C], standard:String)

trait StandardColumn {
  this:Storage =>

  def path = client.columnPath(standard)
  def get(key:String): Option[String] = client.get(key, path)
  def set(key:String, value:String)   = client.update(key, path, value.toString)
  def del(key:String)                 = client.remove(key, path)
  def count(key:String): Int          = client.count(key, path)
//    def exist(key:String): Nothing
}

 */
