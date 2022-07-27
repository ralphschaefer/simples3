package my.s3test.s3

import com.amazonaws.services.s3.AmazonS3Client

import scala.jdk.CollectionConverters._
import scala.util.Try

class CreateBucket(client: AmazonS3Client) {

  def buckets: Try[Seq[String]] =
    Try(client.listBuckets())
      .map(_.asScala)
      .map(_.toSeq.map(_.getName))

  def create(name:String) = for {
    hasBucket <- buckets.map(_.contains(name))
    res = if (!hasBucket) {
      client.createBucket(name)
      ()
    } else
      ()
  } yield res

}
