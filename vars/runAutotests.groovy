def runAutotests(tag){
	dir('CustomerConsents/customer-consents-autotests'){
		try{
			sh "mvn verify -PcomponentTests -Dspring.profiles.active=tst -Dcucumber.options=\"--tags ${tag}\""
		} catch (err){
			error("An error occurred while running autotests " + err)
			throw err
		} finally {
            publishHTML (target: [
                    allowMissing: false,
                    alwaysLinkToLastBuild: false,
                    keepAll: true,
                    reportDir: 'target/site/cucumber-reports',
                    reportFiles: "feature-overview.html",
                    reportName: "AutoTests reports"
            ])
		}
	}
}
