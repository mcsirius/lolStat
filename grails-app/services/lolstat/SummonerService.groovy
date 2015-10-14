package lolstat
import grails.transaction.Transactional
import org.apache.http.client.methods.HttpGet

@Transactional
class SummonerService {
    HttpGet get = new HttpGet()
    def getSummonerInfoRequest(String name) {

    }
}
