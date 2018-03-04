A simple http2 and Webflux sample
=================================

HTTP/2 的 reference :

. 製作 Certificate : 這邊用的是 keytool 自己做的，正式上場需要出 CSR，請憑證中心認證後發給。以下指令製作 RSA 2048 長度的 key，會需要輸入 keystore 密碼和 key 密碼，還有一些 X.509 相關的身分資訊。

	keytool -genkeypair -alias undertow -keyalg RSA -keysize 2048 -keystore keystore.jks -validity 3650 

. 做好的 Certificate，定義在 application.properties 裡面，這邊用的是 classpath:keystore.jks，所以放在 resource 目錄就可以了，密碼的部分，在上面那個步驟用到的拿來這邊用。

	#define server port, springboot can only define one port, another one need use container 
	server.port=8443
	# keystore in classpath
	server.ssl.key-store=classpath:keystore.jks
	# default key-store password
	server.ssl.key-store.password=123456
	# key password can be different with key store password
	server.ssl.key-password=123456
	# enable http2 
	server.http2.enabled=true
	
. 要啟動 http port ，在 application 裡面加這段，不過 http port 就會走 http ，不會走 http/2 

	@Bean
	public UndertowServletWebServerFactory servletWebServerFactory() {
		UndertowServletWebServerFactory factory = new UndertowServletWebServerFactory();
		factory.addBuilderCustomizers(new UndertowBuilderCustomizer() {
			@Override
			public void customize(Builder builder) {
				builder.addHttpListener(8080, "0.0.0.0");				
			}
		});
		return factory;
	}
	
. 要測試有沒有 http/2 ，可以用 chrome，先連結到底下這個網址 https://localhost：8443/actuator ，因為憑證自己做的，所以要先確認之後才能進去
. 另外開一個 Tab，網址列輸入   chrome://net-internals/ 可以看到 http2_session
. 也可以用 curl 來試試，可以看到 https 才有 http/2 的功能

	$ curl -k -I https://localhost:8443/actuator
	HTTP/2 200
	content-type: application/vnd.spring-boot.actuator.v2+json;charset=UTF-8
	content-length: 230
	date: Sun, 04 Mar 2018 12:28:21 GMT
	
	$ curl -I http://localhost:8080/actuator
	HTTP/1.1 200 OK
	Connection: keep-alive
	Content-Type: application/vnd.spring-boot.actuator.v2+json;charset=UTF-8
	Content-Length: 227
	Date: Sun, 04 Mar 2018 12:29:36 GMT

	