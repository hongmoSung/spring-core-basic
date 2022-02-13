package hello.core.lifecycle;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class NetworkClient implements InitializingBean, DisposableBean {

    private String url;

    public NetworkClient() {
        System.out.println("생성자 호출, url = " + url);
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void connect() {
        System.out.println("url = " + url);
    }

    public void call(String message) {
        System.out.println("call : " + url + " message : " + message);
    }

    public void disconnect() {
        System.out.println("closed: " + url);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("afterPropertiesSet");
        connect();
        call("초기화 연결 매세지");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("destroy");
        disconnect();
    }
}
