package controllers

import play.api.libs.json._

case class InputAssistanceReport(colered: String, completions: Seq[String], errors: Seq[String])

object JsonWriter {

  implicit object InputAssistanceReportJson extends Writes[InputAssistanceReport] {
    def writes(o: InputAssistanceReport): JsValue = JsString("Add implementation") //Add implementation
  }
}
