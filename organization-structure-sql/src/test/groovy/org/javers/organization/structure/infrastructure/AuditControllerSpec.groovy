package org.javers.organization.structure.infrastructure

import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import org.javers.organization.structure.SqlApplication
import org.javers.organization.structure.ui.PersonDto
import spock.lang.Specification
import spock.lang.Stepwise

import static okhttp3.RequestBody.create


@Stepwise
class AuditControllerSpec extends Specification {

    static MediaType JSON  = MediaType.parse("application/json; charset=utf-8");

    static OkHttpClient client
    static JsonSlurper jsonSlupler

    def setupSpec() {
        SqlApplication.main()
        client = new OkHttpClient()
        jsonSlupler = new JsonSlurper()
    }

    def "should create new object snapshot"() {
        given:
        def personDto = new PersonDto(1, "bob", "bob", "MALE", 1000, "DEVELOPER")
        def saveNewPerson = new Request.Builder()
                .url("http://localhost:8080/view/person")
                .post(create(JSON, JsonOutput.toJson(personDto)))
                .build();

        when:
        client.newCall(saveNewPerson).execute()

        then:
        def snapshots = findAllSnapshots()
        snapshots.size() == 1
        snapshots[0].commitMetadata.id == 1
        snapshots[0].state.firstName == "bob"
        snapshots[0].type == "INITIAL"
        snapshots[0].version == 1
    }

    def "should find person changes"() {
        given:
        def personDto = new PersonDto(1, "bob", "bob", "MALE", 2000, "DEVELOPER")
        def updateNewPerson = new Request.Builder()
                .url("http://localhost:8080/view/person")
                .put(create(JSON, JsonOutput.toJson(personDto)))
                .build();

        when:
        client.newCall(updateNewPerson).execute()

        then:
        def changes = findAllChanges(1)
        changes.size() == 1
        changes[0].commitMetadata.id == 2
        changes[0].property == "salary"
        changes[0].left == 1000
        changes[0].right == 2000
    }

    private List findAllSnapshots() {
        def response = client.newCall(new Request.Builder().url("http://localhost:8080/audit/person/snapshots").build()).execute()
        assert response.code() == 200

        jsonSlupler.parseText(response.body().string())
    }

    private List findAllChanges(int id) {
        def response = client.newCall(new Request.Builder().url("http://localhost:8080/audit/person/$id").build()).execute()
        assert response.code() == 200

        jsonSlupler.parseText(response.body().string())
    }
}
