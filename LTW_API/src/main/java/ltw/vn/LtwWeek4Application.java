package ltw.vn;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import ltw.vn.Config.StorageProperties;
import ltw.vn.Service.StorageService;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class LtwWeek4Application {
	public static void main(String[] args) {
		SpringApplication.run(LtwWeek4Application.class, args);
	}

	// thêm cấu hình storage
	@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args -> {
			storageService.init();
		});
	}

}
