#!/bin/sh

wget --user=RcsUserInapp --password=iscorpInapp http://soais-ts.corp.rcs.group:4001/ws/RCSTest.ws.producer.concat?wsdl -O src/main/resources/wsdls/RCSTest_ws_producer_concat_Port.wsdl

wget --user=RcsUserInapp --password=iscorpInapp http://soais-ts.corp.rcs.group:4001/ws/RCSCatalog.ws.producer.V1_0.catalog?wsdl -O src/main/resources/wsdls/RCSCatalog.ws.producer.V1_0.catalog.wsdl

