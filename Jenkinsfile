#!groovy
build('dudoser', 'docker-host') {
    checkoutRepo()
    loadBuildUtils()
    def pipeJavaService
    runStage('load Java Service pipeline') {
        pipeJavaService = load('build_utils/jenkins_lib/pipeJavaService.groovy')
    }

    def serviceName = "dudoser"
    def baseImageTag = "70f9fa4ba9bb06cc36b292862ab0555f3bad6321"
    def buildImageTag = "7372dc01bf066b5b26be13d6de0c7bed70648a26"
    def dbHostName = null
    def mvnArgs = '-DjvmArgs="-Xmx256m"'
    pipeJavaService(serviceName, baseImageTag, buildImageTag, dbHostName, mvnArgs)
}
