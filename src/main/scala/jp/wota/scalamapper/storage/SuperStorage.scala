package jp.wota.scalamapper.storage

case class SuperStorage(repository:Repository) {
  def this(host:String, port:Int, keyspace: String, standard: String) =
    this(new Repository(host, port, keyspace, standard))

  def get(key:String, sub:String, name:String): Option[String] = repository.get(key,sub,name)
  def set(key:String, sub:String, name:String, value:String)   = repository.set(key,sub,name,value)
  def del(key:String, sub:String, name:String)                 = repository.del(key,sub,name)
  def count(key:String, sub:String)                            = repository.count(key,sub)
}

