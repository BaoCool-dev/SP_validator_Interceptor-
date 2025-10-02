package LTW_GraphQL.Config;

import graphql.scalars.ExtendedScalars;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

@Configuration
public class GraphQlConfig {

    /**
     * Bean này sẽ đăng ký scalar 'Long' tùy chỉnh với GraphQL.
     * Spring Boot sẽ tự động tìm thấy bean này và sử dụng nó để cấu hình schema.
     * @return một RuntimeWiringConfigurer để thêm scalar 'Long'.
     */
    @Bean
    public RuntimeWiringConfigurer runtimeWiringConfigurer() {
        return wiringBuilder -> wiringBuilder.scalar(ExtendedScalars.GraphQLLong);
    }
}