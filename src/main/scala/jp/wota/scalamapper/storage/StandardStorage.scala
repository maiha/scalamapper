package jp.wota.scalamapper.storage

case class StandardStorage(repository:Repository) {
  def this(host:String, port:Int, keyspace: String, standard: String) =
    this(new Repository(host, port, keyspace, standard))

  val rep = repository

  // path functions
  def get(key:String, name:String)               = rep.get(key, None, name)
  def set(key:String, name:String, value:String) = rep.set(key, None, name, value)
  def del(key:String, name:String)               = rep.del(key, None, name)

  // parent functions
  def count(key:String) = rep.count(key, None)
  def slice(key:String) = rep.standard_get_range_slices(key,None,None,None)
  def keys(key:String)  = rep.standard_keys(key,None,None,None)

//  def keys(count:Int) = rep.keys(None, None, count)
//  def keys(start:Option[String], finish:Option[String], count:Int) = rep.keys(key)
}
