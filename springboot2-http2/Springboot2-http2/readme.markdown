# A simple http2 and Webflux sample

HTTP/2 的 reference :

. 製作 Certificate : 這邊用的是 keytool 自己做的，正式上場需要出 CSR，請憑證中心認證後發給。

     keytool -genkeypair -alias tomcat -keyalg RSA -keysize 2048 -keystore keystore.jks -validity 3650 

    error: The requested operation returned error: 1954 Forbidden search for defensive operations manual
    absolutely fatal: operation initiation lost in the dodecahedron of doom
    would you like to die again? y/n

