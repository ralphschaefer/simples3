package my.s3test

import com.amazonaws.services.s3.model.Bucket
import com.typesafe.scalalogging.StrictLogging
import my.s3test.s3.{CreateBucket, Entry, Login}
import pureconfig._
import pureconfig.generic.auto._

import scala.jdk.CollectionConverters._
import scala.util.{Failure, Success, Try}

object Main extends App with StrictLogging {

  case class S3Config(user:String, secret:String, endpoint:String)
  case class Config(objectStoreConf:S3Config)

  logger.info("start")
  val config = ConfigSource.default.load[Config].toOption.get
  logger.info(config.toString)
  val login = new Login(config.objectStoreConf)


  val buckets: Try[Seq[Bucket]] = (for {
    client <- login.s3client
    b <- Try(client.listBuckets())
  } yield b).map(_.asScala.toSeq)

  buckets match {
    case Success(b) =>
      println("--> buckets: " + b.map(_.getName).mkString(", "))
    case Failure(exception) =>
      logger.error(exception.getMessage)
  }

  val newBucket = "new-bucket"
  val createBucket = for {
    client <- login.s3client
    bucketCreate <- new CreateBucket(client).create(newBucket)
  } yield bucketCreate

  createBucket.recover{
    case e =>
      logger.error(e.getMessage)
  }

  val chuck = "chuck-norris"
  val readChuck = for {
    client <- login.s3client
    entry = new Entry(client,newBucket)
    fact <- entry.get(chuck)
  } yield fact

  readChuck match {
    case Success(value) =>
      println(s"--> $newBucket::$chuck ($value)")
    case Failure(e) =>
      logger.error(e.getMessage)
  }

  val writeChuck = for {
    client <- login.s3client
    entry = new Entry(client,newBucket)
    res <- entry.create(chuck)
  } yield res

  writeChuck.recover{
    case e =>
      logger.error(e.getMessage)
  }

}
