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

  class AbstractColumn(name:String, group:Option[String]) {
    val parent = client.ColumnParent(standard, group)
    val path   = client.ColumnPath(standard, group, name)

    def get(key:String): Option[String] = client.get(key, path)
    def set(key:String, value:String)   = client.update(key, path, value.toString)
    def del(key:String)                 = client.remove(key, path)
    def count(key:String)               = client.count(key, parent)
  }

  case class StandardColumn(name:String)            extends AbstractColumn(name, None)
  case class SuperColumn(name:String, group:String) extends AbstractColumn(name, Some(group))
}


