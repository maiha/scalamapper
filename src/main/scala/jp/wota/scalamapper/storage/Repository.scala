package jp.wota.scalamapper.storage

import com.nodeta.scalandra._
import org.apache.cassandra.thrift.{NotFoundException, ThriftGlue, ConsistencyLevel}
import com.nodeta.scalandra.serializer.StringSerializer

/* ----------------------------------------------------------------------
 [Repository]
 */

case class Repository(connection:Connection, keyspace: String, standard: String) {
  def this(host:String, port:Int, keyspace: String, standard: String) =
    this(new Connection(host, port), keyspace, standard)

  val serialization = Serialization(StringSerializer, StringSerializer, StringSerializer)
  val consistency   = ConsistencyLevels.quorum
  val client        = Client(connection, keyspace, serialization, consistency)

  def parentFor(sub:Option[String]) = client.ColumnParent(standard, sub)
  def pathFor(sub:Option[String], name:String) = client.ColumnPath(standard, sub, name)

  /* ----------------------------------------------------------------------
   * Functional API
   */
  // abstract function
  def get(key:String,sub:Option[String], name:String) =
    client.get(key, pathFor(sub,name))
  def set(key:String,sub:Option[String], name:String, value:String) =
    client.update(key, pathFor(sub,name), value.toString)
  def del(key:String,sub:Option[String], name:String) =
    client.remove(key, pathFor(sub,name))
  def count(key:String, sub:Option[String]) =
    client.count(key, parentFor(sub))

  // for standard column
  def get(key:String, name:String): Option[String] = get(key, None, name)
  def set(key:String, name:String, value:String):Unit = set(key, None, name, value)
  def del(key:String, name:String) { del(key, None, name) }
  def count(key:String): Int = count(key, None)

  // for super column
  def get(key:String, sub:String, name:String): Option[String] = get(key, Some(sub), name)
  def set(key:String, sub:String, name:String, value:String):Unit = set(key, Some(sub), name, value)
  def del(key:String, sub:String, name:String) { del(key, Some(sub), name) }
  def count(key:String, sub:String): Int = count(key, Some(sub))

  /* ----------------------------------------------------------------------
   * Column Oriented API
   */
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


