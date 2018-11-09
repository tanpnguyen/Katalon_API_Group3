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

def crlResponse = WS.sendRequest(findTestObject('Releases/Create Release', [('url') : GlobalVariable.url, ('token') : GlobalVariable.access_token
            , ('project') : GlobalVariable.project, ('release_properties') : GlobalVariable.release_properties]))

assertThat(crlResponse.getStatusCode()).isEqualTo(200)

def crlResponseBody = new JsonSlurper().parseText(crlResponse.getResponseBodyContent())

assertThat(crlResponseBody.name).isEqualTo('Release 01 from API')

GlobalVariable.release_id = crlResponseBody.id

println('==> CREATE RELEASE API - COMPLETED <==')

