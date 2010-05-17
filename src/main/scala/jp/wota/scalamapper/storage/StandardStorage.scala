package jp.wota.scalamapper.storage

case class StandardStorage(repository:Repository) {
  def this(host:String, port:Int, keyspace: String, standard: String) =
    this(new Repository(host, port, keyspace, standard))

  def get(key:String, name:String): Option[String] = repository.get(key,name)
  def set(key:String, name:String, value:String)   = repository.set(key,name,value)
  def del(key:String, name:String)                 = repository.del(key,name)
  def count(key:String)                            = repository.count(key)
}
