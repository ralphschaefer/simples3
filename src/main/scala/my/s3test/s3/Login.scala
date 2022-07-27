package my.s3test.s3

import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.{AmazonS3Client, S3ClientOptions}
import my.s3test.Main.S3Config

import scala.util.Try


class Login(s3config: S3Config) {

  lazy val s3client: Try[AmazonS3Client] = Try{
    val credentials = new BasicAWSCredentials(s3config.user, s3config.secret)
    val client = new AmazonS3Client(credentials)
    client.setEndpoint(s3config.endpoint)
    client.setS3ClientOptions(S3ClientOptions.builder().setPathStyleAccess(true).build())
    client
  }

}
