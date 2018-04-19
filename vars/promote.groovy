import groovy.transform.Field
import static Constants.*

@Field final String tststack = 'constant'

def call (stackname, hostname, version, stage) {
	println tststack
	bat 'dir'
	dir('CustomerConsents'){
		def yaml;
        if(stackname.equals(tststack)) {
            yaml =readFile "${env.WORKSPACE}/docker-compose-wiremock.yaml"
            yaml = yaml.replaceAll("#TIPSTUBHOST#", "http://${wiremockhost}")
            yaml = yaml.replaceAll("#TIPSTUBNAME#", "${wiremockappname}")
            yaml = yaml.replaceAll("#RABBIT_HOST#", rabbitmqhost)
        } else {
		bat 'dir'
            yaml =readFile "${env.WORKSPACE}/docker-compose.yaml"
        }
        yaml = yaml.replaceAll("#ELASTICURL#", elasticurl)
        yaml = yaml.replaceAll("#VERSION#", version)
        yaml = yaml.replaceAll("#ENV#", stage)
        yaml = yaml.replaceAll("#NAME#", stackname)
        yaml = yaml.replaceAll("#APPDYNAME#","nl-${stackname}")
        yaml = yaml.replaceAll("#ACCOUNT#","test")
        yaml = yaml.replaceAll("#APPNAME#", appname)
		yaml = yaml.replaceAll("#REPOSNAME#", repositoryname)
		yaml = yaml.replaceAll("#REGHOST#", registryhost)
		yaml = yaml.replaceAll("#UCPGROUP#",ucpgroup)
		yaml = yaml.replaceAll("#KEY#","76008d58-42da-4a88-b4d3-7b81641e6cbe")
		yaml = yaml.replaceAll("#HOST#", "http://${hostname}")
        writeFile file: "docker-compose-${stackname}.yaml" , text: yaml
        
	}
}
