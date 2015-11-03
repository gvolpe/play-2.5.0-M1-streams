package controllers

import akka.stream.scaladsl.Source
import akka.util.ByteString
import play.api.mvc._

class StreamController extends Controller {

  val largeSource: Source[ByteString, _] = {
    val source = Source.single(ByteString("asdfghjkl" * 100))
    (1 to 9).foldLeft(source){(acc, _) => (acc ++ source).mapMaterializedValue(_ => ())}
  }

  val numbers: Source[String, Unit] = {
    val list = (1 to 100).map { n => s"${n.toString} " }
    Source(list)
  }

  def streamFile = Action {
    Ok.chunked(largeSource)
  }

  def streamNumbers = Action {
    Ok.chunked(numbers)
  }

}
