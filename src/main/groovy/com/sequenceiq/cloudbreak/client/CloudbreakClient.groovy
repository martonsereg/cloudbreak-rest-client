package com.sequenceiq.cloudbreak.client

import groovy.json.JsonSlurper
import groovy.text.SimpleTemplateEngine
import groovy.text.TemplateEngine
import groovy.util.logging.Slf4j
import groovyx.net.http.RESTClient

@Slf4j
class CloudbreakClient {

    def RESTClient restClient;
    def TemplateEngine engine = new SimpleTemplateEngine()
    def slurper = new JsonSlurper()


    CloudbreakClient(host = 'cloudbreak.sequenceiq.com', port = '80', user = 'user@seq.com', password = 'test123') {
        restClient = new RESTClient("http://${host}:${port}/" as String)
        restClient.headers['Authorization'] = 'Basic ' + "$user:$password".getBytes('iso-8859-1').encodeBase64()
    }


    def Object postCredentials() {
        log.debug("Posting credentials ...")
        def binding = [:]
        def templateName = "credentials.json"
        def json = createJson(templateName, binding)
        def Map postCtx = createPostRequestContext("credential", 'json': json)
        doPost(postCtx)
    }

    def Object postTemplate() {
        log.debug("Posting template ...")
        def binding = [:]
        def templateName = "template.json"
        def json = createJson(templateName, binding)
        def Map postCtx = createPostRequestContext("template", 'json': json)
        doPost(postCtx)

    }

    def Object postStack() {
        log.debug("Posting stack ...")
        def binding = [:]
        def templateName = "stack.json"
        def json = createJson(templateName, binding)
        def Map postCtx = createPostRequestContext("stack", 'json': json)
        doPost(postCtx)
    }

    def Object postBluebrint() {
        log.debug("Posting blueprint ...")
        def binding = [:]
        def templateName = "blueprint.json"
        def json = createJson(templateName, binding)
        def Map postCtx = createPostRequestContext("blueprints", 'json': json)
        doPost(postCtx)
    }

    def Object postCluster() {
        log.debug("Posting cluster ...")
        def binding = [:]
        def templateName = "cluster.json"
        def json = createJson(templateName, binding)
        def Map postCtx = createPostRequestContext("cluster", 'json': json)
        doPost(postCtx)
    }

    def health() {
        log.debug("Checking health ...")
        Map getCtx = createGetRequestContext('health', null)
        Object healthObj = doGet(getCtx)
        return healthObj.data.status == 'ok'
    }

    def private Object doGet(Map getCtx) {
        Object response = null;
        try {
            response = restClient.get(getCtx)
        } catch (e) {
            log.error("ERROR: {}", e)
        }
        return response;
    }

    def private Object doPost(Map postCtx) {
        Object response = null;
        try {
            response = restClient.post(postCtx)
        } catch (e) {
            log.error("ERROR: {}", e)
        }
        return response;
    }

    def private Map createGetRequestContext(String resourcePath, Map ctx) {
        Map context = [:]
        String uri = "${restClient.uri}$resourcePath"
        context.put('path', uri)
        return context
    }

    def private Map createPostRequestContext(String resourcePath, Map ctx) {
        def Map<String, ?> putRequestMap = [:]
        String uri = "${restClient.uri}$resourcePath"
        putRequestMap.put('path', "uri")
        putRequestMap.put('body', ctx.get("json"));

        return putRequestMap

    }

    def private String createJson(String templateName, Map bindings) {
        def InputStream inPut = this.getClass().getClassLoader().getResourceAsStream("templates/${templateName}");
        String json = engine.createTemplate(new InputStreamReader(inPut)).make(bindings);
        return json;
    }
}

