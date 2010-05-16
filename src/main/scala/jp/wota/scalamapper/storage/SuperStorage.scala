package jp.wota.scalamapper.storage

import com.nodeta.scalandra._
import org.apache.cassandra.thrift.{NotFoundException, ThriftGlue, ConsistencyLevel}
import com.nodeta.scalandra.serializer.StringSerializer

case class SuperStorage(repository:Repository) {
  def this(host:String, port:Int, keyspace: String, standard: String) =
    this(new Repository(new Connection(host, port), keyspace, standard))

  def column(name:String, row:String) = repository.SuperColumn(name, row)

  def get(key:String, row:String, field:String): Option[String] = column(field, row).get(key)
  def set(key:String, row:String, field:String, value:String)   = column(field, row).set(key, value)
  def del(key:String, row:String, field:String)                 = column(field, row).del(key)

  def count(key:String, row:String) = column("dummy", row).count(key)
}

