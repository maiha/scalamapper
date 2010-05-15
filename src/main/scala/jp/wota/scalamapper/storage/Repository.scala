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

  def parent            = client.ColumnParent(standard, None)
  def path(name:String) = client.ColumnPath(standard, None, name)

//  sealed case class Column(name:String) {

  def getColumn(row:String, column:String) :Option[String] =
    client(row, path(column))

  def setColumn(row:String, column:String, value:String) =
    client(row, path(column)) = value.toString

  def delColumn(row:String, column:String): Unit = {
    client.remove(row, path(column))
  }
}


