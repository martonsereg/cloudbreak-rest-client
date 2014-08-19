import com.sequenceiq.cloudbreak.client.CloudbreakClient
import groovy.util.logging.Slf4j
import spock.lang.Ignore
import spock.lang.Specification


@Slf4j
@Ignore
class CloudbreakClientTest extends Specification {

    def CloudbreakClient cloudbreakClient = new CloudbreakClient('localhost', '8080', 'cbuser@sequenceiq.com', 'test1235');

    def "test health check"() {

        expect:
        String response = cloudbreakClient.health();
        log.debug("Health: {}", response)

    }

    def "test post azuretemplate"() {

        expect:
        Object resp = cloudbreakClient.postAzureTemplate("azuretempshell", "sdfsdfsd", "NORTH_EUROPE", "ambari-docker-v1", "MEDIUM", "sdfdsfsdfsdfsf", "")
        log.debug("RESP: {}", resp)
    }

    def "test post credentials"() {

        expect:
        Object resp = cloudbreakClient.postBlueprint("Blueprint mamam")
        log.debug("RESP: {}", resp)
    }

    def "test get credentials"() {
        expect:
        Object resp = cloudbreakClient.getCredentials()
        log.debug("RESP: {}", resp)
    }

    def "test get credential"() {
        expect:
        Object resp = cloudbreakClient.getCredential("50")
        Object resp1 = cloudbreakClient.getCredentialMap("50")
        log.debug("RESP: {}", resp)
        log.debug("RESP1: {}", resp1)
    }

    def "test post blueprint"() {
        expect:
        Object resp = cloudbreakClient.postBlueprint(

        )
        log.debug("RESP: {}", resp)
    }

    def "test post cluster"() {
        expect:
        Object resp = cloudbreakClient.postCluster("test", 52, 51)
        log.debug("RESP: {}", resp)
    }


    def "test get blueprints as map"() {
        expect:
        Object resp = cloudbreakClient.getBlueprintsMap()
        log.debug("RESP: {}", resp)
    }

    def "test get credentials as map"() {
        expect:
        Object resp = cloudbreakClient.getCredentialsMap()
        log.debug("RESP: {}", resp)
    }

    def "test get templates as map"() {
        expect:
        Object resp = cloudbreakClient.getTemplatesMap()
        log.debug("RESP: {}", resp)
    }

    def "test get stacks as map"() {
        expect:
        Object resp = cloudbreakClient.getStacksMap()
        log.debug("RESP: {}", resp)
    }

    def "test get clusters as map"() {
        expect:
        Object resp = cloudbreakClient.getClustersMap()
        log.debug("RESP: {}", resp)
    }

    def "test get login"() {
        expect:
        Object resp = cloudbreakClient.login()
        log.debug("RESP: {}", resp)
    }

}