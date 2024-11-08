#!/bin/sh
cd `dirname $0`
ROOT_PATH=`pwd`
java -Dtalend.component.manager.m2.repository=$ROOT_PATH/../lib -Xms256M -Xmx1024M -Dfile.encoding=UTF-8 -cp .:$ROOT_PATH:$ROOT_PATH/../lib/routines.jar:$ROOT_PATH/../lib/log4j-slf4j-impl-2.17.1.jar:$ROOT_PATH/../lib/log4j-api-2.17.1.jar:$ROOT_PATH/../lib/log4j-core-2.17.1.jar:$ROOT_PATH/../lib/log4j-1.2-api-2.17.1.jar:$ROOT_PATH/../lib/dom4j-2.1.3.jar:$ROOT_PATH/../lib/commons-math3-3.6.1.jar:$ROOT_PATH/../lib/commons-io-2.15.1.jar:$ROOT_PATH/../lib/talend_file_enhanced-1.3.1.jar:$ROOT_PATH/../lib/jboss-marshalling-2.0.12.Final.jar:$ROOT_PATH/../lib/json-smart-2.4.11.jar:$ROOT_PATH/../lib/talendCBP-1.1.4.jar:$ROOT_PATH/../lib/talendExcel-1.15-20230117.jar:$ROOT_PATH/../lib/javassist-3.30.2-GA.jar:$ROOT_PATH/../lib/slf4j-api-1.7.34.jar:$ROOT_PATH/../lib/job-audit-1.5.jar:$ROOT_PATH/../lib/poi-4.1.2-20200903124306_modified_talend.jar:$ROOT_PATH/../lib/commons-codec-1.14.jar:$ROOT_PATH/../lib/poi-ooxml-schemas-4.1.2-20200903124306_modified_talend.jar:$ROOT_PATH/../lib/xmlbeans-3.1.0.jar:$ROOT_PATH/../lib/accessors-smart-2.4.11.jar:$ROOT_PATH/../lib/SparseBitSet-1.2.jar:$ROOT_PATH/../lib/talendagent-1.0.2.jar:$ROOT_PATH/../lib/commons-collections4-4.4.jar:$ROOT_PATH/../lib/audit-log4j2-1.16.1.jar:$ROOT_PATH/../lib/commons-compress-1.26.0.jar:$ROOT_PATH/../lib/logging-event-layout-1.16.1.jar:$ROOT_PATH/../lib/external_sort-1.1.jar:$ROOT_PATH/../lib/curvesapi-1.06.jar:$ROOT_PATH/../lib/talendboot-1.0.8.jar:$ROOT_PATH/../lib/asm-9.5.jar:$ROOT_PATH/../lib/commons-lang3-3.10.jar:$ROOT_PATH/../lib/system-routines.jar:$ROOT_PATH/../lib/poi-scratchpad-5.2.2.jar:$ROOT_PATH/../lib/crypto-utils-7.1.20.jar:$ROOT_PATH/../lib/poi-ooxml-4.1.2-20200903124306_modified_talend.jar:$ROOT_PATH/../lib/audit-common-1.16.1.jar:$ROOT_PATH/file_read_job_0_1.jar: talend_project.file_read_job_0_1.File_Read_job --context=Default "$@"
