package com.twitter.finatra.json.utils

import com.fasterxml.jackson.databind.node.TextNode
import com.fasterxml.jackson.databind.{JsonNode, ObjectMapperCopier, SerializationFeature}
import com.twitter.finatra.jackson.{ScalaObjectMapper, JacksonScalaObjectMapperType}
import com.twitter.inject.Logging
import com.twitter.inject.conversions.boolean._
import scala.util.control.NonFatal

object JsonDiffUtil extends Logging {

  private val mapper = ScalaObjectMapper()

  private lazy val sortingObjectMapper: JacksonScalaObjectMapperType = {
    val newMapper = ObjectMapperCopier.copy(mapper.underlying)
    newMapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true)
    newMapper
  }

  /**
   * Computes the diff for two snippets of json both of expected type T.
   * If a difference is detected a Some(JsonDiffResult) is returned.
   *
   * @param receivedJson - the received json
   * @param expectedJson - the expected json
   * @param normalizer - Optional normalizer
   * @return if a difference is detected a Some(<code>JsonDiffResult</code>)
   *         is returned otherwise a None.
   */
  def jsonDiff[T](
    receivedJson: Any,
    expectedJson: Any,
    normalizer: JsonNode => JsonNode = null
  ): Option[JsonDiffResult] = {

    val receivedJsonStr = jsonString(receivedJson)
    val expectedJsonStr = jsonString(expectedJson)

    val receivedJsonNode = {
      val jsonNode = tryJsonNodeParse(receivedJsonStr)

      if (normalizer != null) {
        normalizer(jsonNode)
      } else {
        jsonNode
      }
    }

    val expectedJsonNode = tryJsonNodeParse(expectedJsonStr)
    (receivedJsonNode != expectedJsonNode).option {
      JsonDiffResult.create(mapper, expectedJsonNode, receivedJsonNode)
    }
  }

  /**
   * Creates a string representation of the given <code>JsonNode</code> with entries
   * sorted alphabetically by key.
   *
   * @param jsonNode - input <code>JsonNode</code>
   * @return string representation of the JsonNode.
   */
  def sortedString(jsonNode: JsonNode): String = {
    if (jsonNode.isTextual) {
      jsonNode.textValue()
    } else {
      val node = sortingObjectMapper.treeToValue(jsonNode, classOf[Object])
      sortingObjectMapper.writeValueAsString(node)
    }
  }

  /* Private */

  private def tryJsonNodeParse(expectedJsonStr: String): JsonNode = {
    try {
      mapper.parse[JsonNode](expectedJsonStr)
    } catch {
      case NonFatal(e) =>
        warn(e.getMessage)
        new TextNode(expectedJsonStr)
    }
  }

  private def jsonString(receivedJson: Any): String = {
    receivedJson match {
      case str: String => str
      case _ => mapper.writeValueAsString(receivedJson)
    }
  }
}
