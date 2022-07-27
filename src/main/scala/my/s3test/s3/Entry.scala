package my.s3test.s3

import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.PutObjectResult
import com.github.javafaker.Faker

import scala.util.Try


class Entry(client: AmazonS3Client, bucketName:String) {

  private val faker = new Faker()

  def create(name:String): Try[PutObjectResult] =
    create(name, faker.chuckNorris().fact())

  def create(name:String, content:String): Try[PutObjectResult] = Try{
    client.putObject(bucketName, name, content)
  }

  def get(name:String): Try[String] = Try {
    client.getObjectAsString(bucketName, name)
  }

}
