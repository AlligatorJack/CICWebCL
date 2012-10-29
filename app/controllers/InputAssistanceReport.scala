package controllers

import play.api.libs.json._

case class InputAssistanceReport(colored: String, completions: Seq[String], errors: Seq[String])

object InputAssistanceReport {

  implicit object InputAssistanceReportJson extends Writes[InputAssistanceReport] {
    def writes(o: InputAssistanceReport): JsValue =
      Json.toJson(
        Map(
          "colored"     -> Json.toJson(o.colored),
          "completions" -> Json.toJson(o.completions),
          "errors"      -> Json.toJson(o.errors)
        )
      )
  }
}
