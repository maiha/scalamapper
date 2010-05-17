package jp.wota.scalamapper.storage.oo

import jp.wota.scalamapper.storage._

case class StandardStorage(repository:Repository) {
  def this(host:String, port:Int, keyspace: String, standard: String) =
    this(new Repository(host, port, keyspace, standard))

  def column(name:String) = repository.StandardColumn(name)

  def get(key:String, field:String): Option[String] = column(field).get(key)
  def set(key:String, field:String, value:String)   = column(field).set(key, value)
  def del(key:String, field:String)                 = column(field).del(key)

  def count(key:String) = column("dummy").count(key)
}

case class SuperStorage(repository:Repository) {
  def this(host:String, port:Int, keyspace: String, standard: String) =
    this(new Repository(host, port, keyspace, standard))

  def column(name:String, row:String) = repository.SuperColumn(name, row)

  def get(key:String, row:String, field:String): Option[String] = column(field, row).get(key)
  def set(key:String, row:String, field:String, value:String)   = column(field, row).set(key, value)
  def del(key:String, row:String, field:String)                 = column(field, row).del(key)

  def count(key:String, row:String) = column("dummy", row).count(key)
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
