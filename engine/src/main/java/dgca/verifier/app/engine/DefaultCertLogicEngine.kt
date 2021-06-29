package dgca.verifier.app.engine

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import com.fasterxml.jackson.module.kotlin.readValue
import dgca.verifier.app.engine.data.ExternalParameter
import dgca.verifier.app.engine.data.Rule

/*-
 * ---license-start
 * eu-digital-green-certificates / dgc-certlogic-android
 * ---
 * Copyright (C) 2021 T-Systems International GmbH and all other contributors
 * ---
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ---license-end
 *
 * Created by osarapulov on 11.06.21 11:10
 */
class DefaultCertLogicEngine(private val jsonLogicValidator: JsonLogicValidator) : CertLogicEngine {
    private val objectMapper = ObjectMapper()

    companion object {
        private const val EXTERNAL_KEY = "external"
        private const val PAYLOAD_KEY = "payload"
        private const val DESCRIPTION = "description"
    }

    init {
        objectMapper.findAndRegisterModules()
    }

    private fun prepareData(
        externalParameter: ExternalParameter,
        payload: String
    ): ObjectNode = objectMapper.createObjectNode().apply {
        this.set<JsonNode>(
            EXTERNAL_KEY,
            objectMapper.readValue(objectMapper.writeValueAsString(externalParameter))
        )
        this.set<JsonNode>(
            PAYLOAD_KEY,
            objectMapper.readValue<JsonNode>(payload)
        )
    }

    override fun validate(
        hcertVersionString: String,
        schemaJson: String,
        rules: List<Rule>,
        externalParameter: ExternalParameter,
        payload: String
    ): List<ValidationResult> {
        return if (rules.isNotEmpty()) {
            val validationResults = mutableListOf<ValidationResult>()
            val schemaJsonNode = objectMapper.readValue<JsonNode>(schemaJson)
            val dataJsonNode = prepareData(externalParameter, payload)
            val hcertVersion = hcertVersionString.toVersion()
            rules.forEach { rule ->
                val ruleVersion = rule.version.toVersion()
                val res = when {
                    hcertVersion == null || ruleVersion == null || hcertVersion.first != ruleVersion.first -> Result.OPEN
                    hcertVersion.isGreaterOrEqualThan(ruleVersion) &&
                            jsonLogicValidator.isDataValid(
                        rule.logic,
                        dataJsonNode
                    ) -> Result.PASSED
                    else -> Result.FAIL
                }
                val cur = StringBuilder()
                rule.affectedString.forEach { affectedField ->
                    val description =
                        schemaJsonNode.findValue(affectedField)?.findValue(DESCRIPTION)?.asText()
                    val currentState = dataJsonNode.findValue(affectedField)?.asText()
                    if (description?.isNotBlank() == true && currentState?.isNotBlank() == true) {
                        cur.append(
                            "$description: $currentState\n"
                        )
                    }
                }
                validationResults.add(
                    ValidationResult(
                        rule,
                        res,
                        cur.toString(),
                        null
                    )
                )
            }
            validationResults
        } else {
            emptyList()
        }

    }

    private fun Triple<Int, Int, Int>.isGreaterOrEqualThan(version: Triple<Int, Int, Int>): Boolean =
        first > version.first || (first == version.first && (second > version.second || (second == version.second && third >= version.third)))

    /**
     * Tries to convert String into a version based on pattern majorVersion.minorVersion.patchVersion.
     */
    private fun String.toVersion(): Triple<Int, Int, Int>? = try {
        val versionPieces = this.split('.')
        Triple(versionPieces[0].toInt(), versionPieces[1].toInt(), versionPieces[2].toInt())
    } catch (error: Throwable) {
        null
    }
}