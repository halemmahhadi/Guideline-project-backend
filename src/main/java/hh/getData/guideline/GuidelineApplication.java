package hh.getData.guideline;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@SpringBootApplication
public class GuidelineApplication {

	public static void main(String[] args) {
		SpringApplication.run(GuidelineApplication.class, args);
	}


	/*@Bean
	CommandLineRunner run(DonorsRepository donorsRepository){
		return args -> {
			donorsRepository.save(new Donors(1,"gggg","hhhhh","kkkkk",false));
		};
	}*/

}
