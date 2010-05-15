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

  def columnParent            = client.ColumnParent(standard, None)
  def columnPath(name:String) = client.ColumnPath(standard, None, name)

  case class StandardColumn(name:String) {
    def path = columnPath(name)
    def get(key:String): Option[String] = client.get(key, path)
    def set(key:String, value:String)   = client.update(key, path, value.toString)
    def del(key:String)                 = client.remove(key, path)
//    def count(key:String): Nothing
//    def exist(key:String): Nothing
  }

//  def StandardColumn(name:String) = new StandardColumn(name)
}


