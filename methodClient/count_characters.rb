require 'soap/wsdlDriver'
require 'pp'
wsdl = 'http://localhost:8080/apse503/wsdl/MethodExample.wsdl'
driver = SOAP::WSDLDriverFactory.new(wsdl).create_rpc_driver

driver.wiredump_file_base = "count_characters"

response = driver.countCharactersWrapper(:userName => 'user', :password => 'testing', :toCount => 'this should return 21')

pp(response)