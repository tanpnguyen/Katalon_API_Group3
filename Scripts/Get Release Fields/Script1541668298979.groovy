import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable
import groovy.json.JsonSlurper as JsonSlurper
import static org.assertj.core.api.Assertions.*

def releasefieldResponse = WS.sendRequest(findTestObject('Releases/Get Release Fields', [
	('url') : GlobalVariable.url, 
	('token') : GlobalVariable.access_token, 
	('project') : GlobalVariable.project]))

assertThat(releasefieldResponse.getStatusCode()).isEqualTo(200)

def releasefieldResponseBody = new JsonSlurper().parseText(releasefieldResponse.getResponseBodyContent())
def properties = "\"properties\": ["
releasefieldResponseBody.each({ property ->
		String attribute_type = property.attribute_type.toString()
		if (property.containsKey('allowed_values')){
			switch (attribute_type) {
				case "ArrayNumber":
					properties = properties + 
						"{\"field_id\":\"" + property.id + "\", " +
						"\"field_value\":\"[" + property.allowed_values.value[0] + "]\"},"
					break
				case "Number":
					properties = properties + 
						"{\"field_id\":\"" + property.id + "\", " +
						"\"field_value\":\"" + property.allowed_values.value[0] + "\"},"
					break
				default:
					properties = properties +
						"{\"field_id\":\"" + property.id + "\", " +
						"\"field_value\":\"" + "Tuan Test String" + "\"},"
					break
			}
		} else {
			switch (attribute_type) {
				case "Number":
					properties = properties +
						"{\"field_id\":\"" + property.id + "\", " +
						"\"field_value\":\"" + Math.abs(new Random().nextInt() % 100 + 1) + "\"},"
					break
				case "Date":
					properties = properties +
						"{\"field_id\":\"" + property.id + "\", " +
						"\"field_value\":\"" + "2018-11-08T17:00:00+07:00" + "\"},"
					break
				case "DateTime":
					properties = properties +
						"{\"field_id\":\"" + property.id + "\", " +
						"\"field_value\":\"" + "2018-11-08T17:00:00+07:00" + "\"},"
					break
				case "Url":
					properties = properties +
						"{\"field_id\":\"" + property.id + "\", " +
						"\"field_value\":\"" + "www.google.com" + "\"},"
					break
				default:
					properties = properties +
						"{\"field_id\":\"" + property.id + "\", " +
						"\"field_value\":\"" + "Tuan Test String" +"\"},"
					break
			}
		}
	}
)	
properties = properties.substring(0, properties.length() - 1) + "]"

GlobalVariable.release_properties = properties

println('==> GET RELEASE FIELDS API - COMPLETED <==')


