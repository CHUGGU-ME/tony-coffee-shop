package tony.coffeeshop.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI coffeeShopOpenApi() {
        return new OpenAPI()
                .addServersItem(new Server().url("/"))
                .components(new Components())
                .info(
                        new Info()
                                .title("Coffee Order API List")
                                .description("Coffee order system API description")
                                .version("v.0.0.1")
                );
    }
}
