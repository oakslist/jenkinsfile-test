#!groovy
def deployTarget = [
        'develop': 'localhost'
]
def affectedModuleSet = [] as Set
def project_binary_file_extension = 'jar'
def project_binary_source_directory = 'target'
def util_module_project_name = 'util-module'
def web_module_project_name = 'web-module'
def web2_module_project_name = 'web2-module'
def project_version = '0.0.1-SNAPSHOT'

properties([
        buildDiscarder(logRotator(artifactDaysToKeepStr: '', artifactNumToKeepStr: '', daysToKeepStr: '', numToKeepStr: '3')),
        disableConcurrentBuilds(),
        parameters(parameterDefinitions: [string(defaultValue: '', description: '', name: 'MVN_RELEASE_VERSION')])
])

try {
    node {
        currentBuild.result = 'SUCCESS'
        stage('Checkout') {
            checkout scm
        }
        stage('Build') {
            echo "Build..."
            bat "mvn --batch-mode -V -U -e -Dmaven.test.failure.ignore=false clean install"
//            if (isBuildOK()) {
//                stash name: 'utilModuleArtifact', includes: "${util_module_project_name}/${project_binary_source_directory}/${util_module_project_name}-${project_version}.${project_binary_file_extension}"
//                stash name: 'webModuleArtifact', includes: "${web_module_project_name}/${project_binary_source_directory}/${web_module_project_name}-${project_version}.${project_binary_file_extension}"
//                stash name: 'we2ModuleArtifact', includes: "${web2_module_project_name}/${project_binary_source_directory}/${web2_module_project_name}-${project_version}.${project_binary_file_extension}"
//            }
        }
    }
    if (isBuildOK()) {
        node {
            stage("Deploy") {
                def changeLogSets = currentBuild.changeSets
                for (int i = 0; i < changeLogSets.size(); i++) {
                    def entries = changeLogSets[i].items
                    for (int j = 0; j < entries.length; j++) {
                        def entry = entries[j]
                        def files = new ArrayList(entry.affectedFiles)
                        for (int k = 0; k < files.size(); k++) {
                            def file = files[k]
                            echo "  ${file.editType.name} ${file.path}"
                            if ("${file.path}".startsWith("${util_module_project_name}")) {
                                affectedModuleSet.add("${util_module_project_name}".toString())
                            }
                            if ("${file.path}".startsWith("${web_module_project_name}")) {
                                affectedModuleSet.add("${web_module_project_name}".toString())
                            }
                            if ("${file.path}".startsWith("${web2_module_project_name}")) {
                                affectedModuleSet.add("${web2_module_project_name}".toString())
                            }
                        }
                    }
                }
                echo "Deployment 1..."
                affectedModuleSet.each {
                    echo "In affectedModuleSet.each..."
                    echo it
                }
//                unstash name: 'buildArtifact'
                echo "Deployment 2..."
//                bat "java -jar ${project_binary_source_directory}/${project_name}-${project_version}.${project_binary_file_extension}"
                echo "Deployment finished."
            }
        }
    }
} catch (buildError) {
    currentBuild.result = 'FAILURE'
    throw buildError
}

def isBuildOK() {
    currentBuild.result == 'SUCCESS'
}