package controllers

case class InputAssistanceReport(colered: String, completions: Seq[String], errors: Seq[String])

object JsonWriter {

  implicit object InputAssistanceReportJson extends Writer[InputAssistanceReport] {
    writes(o: InputAssistanceReport): JsValue = //Add implementation
  }
}
