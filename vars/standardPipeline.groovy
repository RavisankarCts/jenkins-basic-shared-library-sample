def call(body) {
    def config = [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = config
    body()

    node {
	    // Clean workspace before doing anything
	    deleteDir()

	    try {
	        stage ('Clone') {
	        	checkout scm
	        }
	        stage ('Build') {
	        	cmd "echo 'building ${config.projectName} ...'"
	        }
	        stage ('Tests') {
		        parallel 'static': {
		            sh "echo 'shell scripts to run static tests...'"
		        },
		        'unit': {
		            cmd "echo 'shell scripts to run unit tests...'"
		        },
		        'integration': {
		            cmd "echo 'shell scripts to run integration tests...'"
		        }
	        }
	      	stage ('Deploy') {
	            cmd "echo 'deploying to server ${config.serverDomain}...'"
	      	}
	    } catch (err) {
	        currentBuild.result = 'FAILED'
	        throw err
	    }
    }
}



