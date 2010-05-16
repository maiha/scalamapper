package jp.wota.scalamapper.storage

import com.nodeta.scalandra._
import org.apache.cassandra.thrift.{NotFoundException, ThriftGlue, ConsistencyLevel}
import com.nodeta.scalandra.serializer.StringSerializer

/* ----------------------------------------------------------------------
 [Repository]
 */

case class Repository(connection:Connection, keyspace: String, standard: String) {
  val serialization = Serialization(StringSerializer, StringSerializer, StringSerializer)
  val consistency   = ConsistencyLevels.quorum
  val client        = Client(connection, keyspace, serialization, consistency)

//  case class Row(key:String)

  case class StandardColumn(name:String) {
    val parent = client.ColumnParent(standard, None)
    val path   = client.ColumnPath(standard, None, name)

    def get(key:String): Option[String] = client.get(key, path)
    def set(key:String, value:String)   = client.update(key, path, value.toString)
    def del(key:String)                 = client.remove(key, path)
    def count(key:String)               = client.count(key, parent)
//    def exist(key:String): Nothing
  }
  
  case class SuperColumn(name:String, row:String) {
    val parent = client.ColumnParent(standard, Some(row))
    val path   = client.ColumnPath(standard, Some(row), name)

    def get(key:String): Option[String] = client.get(key, path)
    def set(key:String, value:String)   = client.update(key, path, value.toString)
    def del(key:String)                 = client.remove(key, path)
    def count(key:String)               = client.count(key, parent)
//    def exist(key:String): Nothing
  }
}


