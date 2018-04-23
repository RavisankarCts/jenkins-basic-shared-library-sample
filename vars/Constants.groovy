class Constants
{
static final appname = "customer-consents"
static final wiremockappname ="wiremock-tip-customer-consents"
static final rabbitmqappname = "rabbitmq"

// JIRA
static final userstoryprefix="feature/"

// docker
static final registryhostPrd = "dtrpl.nl.corp.tele2.com:9443"
static final registryhost = "dtrdl.nl.corp.tele2.com:9443"
static final repositoryname = "development/fez-customer-consents"
static final swarmhost = "dle.nl.corp.tele2.com"
static final ucphost = "dockerdle.nl.corp.tele2.com"
static final ucpgroup = "/Shared/Development"
static final clientenvdle= "DOCKER_HOST=tcp://dockerdle.nl.corp.tele2.com:8443 DOCKER_TLS_VERIFY=1 DOCKER_CERT_PATH=/var/jenkins_home/client-bundles/dta-lan-client"

static final elasticurl = "elastic.tst.nl.corp.tele2.com:4560"

// hostnames
static final devhost = "${appname}.dev.${swarmhost}"
static final tsthost = "${appname}.tst.${swarmhost}"
static final inthost = "${appname}.int.${swarmhost}"
static final prfhost = "${appname}.prf.${swarmhost}"
static final uathost = "${appname}.uat.${swarmhost}"
static final wiremockhost = "${wiremockappname}.tst.${swarmhost}"
static final rabbitmqhost = "${appname}.${rabbitmqappname}.tst.${swarmhost}"

// stacknames
static final devstack = "${appname}_dev"
static final tststack = "${appname}_tst"
static final intstack = "${appname}_int"
static final prfstack = "${appname}_prf"
static final uatstack = "${appname}_uat"

//git data
static final stashkey = "FEZ"
static final stashrepo = "customer-consents"
static final giturl = "http://t2nl-devtooling.itservices.lan:7990/scm/fez/customer-consents.git"
static final giturlautotests = "http://t2nl-devtooling.itservices.lan:7990/scm/fez/autotests.git"
static final gitpullapiurl = "http://t2nl-devtooling.itservices.lan:7990/rest/api/1.0/projects/${stashkey}/repos/${stashrepo}/pull-requests"
}
