#!groovy
def deployTarget = [
        'develop': 'localhost'
]
//def project_name = 'jenkinsfile-test'
def project_binary_file_extension = 'jar'
def project_binary_source_directory = 'target'
//def pom = readMavenPom file: 'pom.xml'
//def project_name = pom.artifactId
//def project_version = pom.version
//def project_name = readMavenPom().getArtifactId()
//def project_version = readMavenPom().getVersion()

properties([
        buildDiscarder(logRotator(artifactDaysToKeepStr: '', artifactNumToKeepStr: '', daysToKeepStr: '', numToKeepStr: '3')),
        disableConcurrentBuilds(),
        parameters(parameterDefinitions: [string(defaultValue: '', description: '', name: 'MVN_RELEASE_VERSION')])
])

try {
    pom = readMavenPom file: 'pom.xml'
    project_name = pom.artifactId
    project_version = pom.version
    node {
        currentBuild.result = 'SUCCESS'
        stage('Checkout') {
            checkout scm
        }
        stage('Build') {
            bat "mvn --batch-mode -V -U -e -Dmaven.test.failure.ignore=false clean install"
            if (isBuildOK()) {
                stash name: 'buildArtifact', includes: "${project_binary_source_directory}/${project_name}-${project_version}.${project_binary_file_extension}"
            }
        }
    }
    if (isBuildOK()) {
        node {
            stage("Deploy") {
                unstash name: 'buildArtifact'
                echo "Deployment..."
                bat "java -jar ${project_binary_source_directory}/${project_name}-${project_version}.${project_binary_file_extension}"
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