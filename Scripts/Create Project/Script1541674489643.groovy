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

def cpResponse = WS.sendRequest(findTestObject('Project/Create Project', [('url') : GlobalVariable.url, ('token') : GlobalVariable.access_token]))

assertThat(cpResponse.getStatusCode()).isEqualTo(201)

def cpResponseBody = new JsonSlurper().parseText(cpResponse.getResponseBodyContent())

assertThat(cpResponseBody.containsKey('id'))

assertThat(cpResponseBody.containsKey('name'))

GlobalVariable.project = cpResponseBody.id

println('==> CREATE PROJECT API - COMPLETED <==')

