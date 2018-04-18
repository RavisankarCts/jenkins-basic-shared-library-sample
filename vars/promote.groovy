import groovy.transform.Field

@Field final String tststack = 'constant'

def call (stackname, hostname, version, stage) {
	println tststack
	dir('CustomerConsents'){
		def yaml;
        if(stackname.equals(tststack)) {
            yaml =readFile "${env.WORKSPACE}/CustomerConsents/docker-compose-wiremock.yaml"
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
        sh "cat docker-compose-${stackname}.yaml"
		// check if it already exists

		String status = ""
		try{
			status = sh returnStdout: true, script: "${clientenvdle} docker service inspect -f {{.UpdateStatus.State}} ${stackname}_${appname}"
		}catch(error){
			status = "no such service"
		}
		sh "${clientenvdle} docker stack deploy -c docker-compose-${stackname}.yaml ${stackname}"
        sh "echo 'service status is: ${status}'"
		if(!status.equals("no such service")){
			Integer retry = 0
            sleep 15 //wait till service actually start updating
            status = sh returnStdout: true, script: "${clientenvdle} docker service inspect -f {{.UpdateStatus.State}} ${stackname}_${appname}" //get status

            while(!status.contains("completed")){
				if(retry > 30){
					error('took to long for the service to update')
				}
				sleep 10
				status = sh returnStdout: true, script: "${clientenvdle} docker service inspect -f {{.UpdateStatus.State}} ${stackname}_${appname}"
				retry++
			}
		}
		status = ""
		retry = 0
		while(!status.contains("#httpcode:200")){
			if(retry > 12){
				error('took to long for the service to be deployed')
			}
			sleep 10
			status = sh returnStdout: true, script: "curl -sL -w \"#httpcode:%{http_code}\\n\" \"http://${hostname}/health\""
			retry++
		}
	}
}
