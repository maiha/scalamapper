package jp.wota.scalamapper.storage

case class SuperStorage(repository:Repository) {
  def this(host:String, port:Int, keyspace: String, standard: String) =
    this(new Repository(host, port, keyspace, standard))

  // path functions
  def get(key:String, sub:String, name:String)               = repository.get(key, Some(sub), name)
  def set(key:String, sub:String, name:String, value:String) = repository.set(key, Some(sub), name, value)
  def del(key:String, sub:String, name:String)               = repository.del(key, Some(sub), name)

  // parent functions
  def count(key:String, sub:String) = repository.count(key, Some(sub))
}

