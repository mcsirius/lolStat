import groovyx.net.http.HTTPBuilder
import org.apache.http.client.HttpClient
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.impl.conn.PoolingClientConnectionManager
import org.apache.http.params.HttpParams

/**
 * Created by zzhao on 10/14/15.
 */
class ConnectionPoolingHttpBuilder extends HTTPBuilder{
    ConnectionPoolingHttpBuilder(String defaultURI) {
        super(defaultURI)
    }

    ConnectionPoolingHttpBuilder(Map arguments) {
        super(arguments.defaultURI)
    }

    @Override
    protected HttpClient createClient(HttpParams params) {
        def connectionManager = new PoolingClientConnectionManager()
        connectionManager.setDefaultMaxPerRoute(4)
        connectionManager.setMaxTotal(40)
        return new DefaultHttpClient(connectionManager, params)
    }
}
