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
   * Functional API (powered by scalandra)
   */
  def get(key:String,sub:Option[String], name:String) =
    client.get(key, pathFor(sub,name))
  def set(key:String,sub:Option[String], name:String, value:String) =
    client.update(key, pathFor(sub,name), value.toString)
  def del(key:String,sub:Option[String], name:String) =
    client.remove(key, pathFor(sub,name))
  def count(key:String, sub:Option[String]) =
    client.count(key, parentFor(sub))

  // slice

  import client.StandardSlice
  val inf = 2147483647 // 2**32-1

  def standard_get_range_slices(key:String, sub:Option[String], start:Option[String], finish:Option[String], count:Int = inf) =
    client.get(key, parentFor(sub), StandardSlice(Range(start, finish, Ascending, count)))
  def standard_keys(key:String, sub:Option[String], start:Option[String], finish:Option[String], count:Int = 2147483647) =
    standard_get_range_slices(key,sub,start,finish,count).keys.toList

  /* ----------------------------------------------------------------------
   * Cassandra Thrift API
   */
/*
Requires Cassandra 0.6
      list<KeySlice> get_range_slices(keyspace, column_parent, predicate, range, consistency_level) 
*/

/*
  def get_range_slices(
    key         : String,
    sub         : Option[String],
    predicate   : Option[String],
    range       : Range,
    consistency : ConsistencyLevels
  ) = 1
  */
  
  
}


