#!groovy
build('dudoser', 'docker-host') {
    checkoutRepo()
    loadBuildUtils()
    def pipeJavaService
    runStage('load Java Service pipeline') {
        pipeJavaService = load('build_utils/jenkins_lib/pipeJavaService.groovy')
    }

    def serviceName = "dudoser"
    def baseImageTag = "f26fcc19d1941ab74f1c72dd8a408be17a769333"
    def buildImageTag = "80c38dc638c0879687f6661f4e16e8de9fc0d2c6"
    def dbHostName = "dudoser_db"
    def mvnArgs = '-DjvmArgs="-Xmx256m"'
    pipeJavaService(serviceName, baseImageTag, buildImageTag, dbHostName, mvnArgs)
}