package ec.llima.springcloud.ms.items;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    /*para clietne WebClient para hacer peticiones HTTP (GET, POST, etc.).
     */

     //podemos sacar la url del archivo de configuracion
    @Value("${config.baseurl.endpoint.products}")
    private String url;

    @Bean
    @LoadBalanced //si se va a usar kubertnetes este ya no seria necesario
    WebClient.Builder weBuilder(){
        return WebClient.builder()
        //.baseUrl("http://products"); //puedo poner la base url para no ponderla luego en cada servicio en ItemServiceWebClient
        .baseUrl(url); //puedo poner la base url desde el archivo de configuracion
    }

}
