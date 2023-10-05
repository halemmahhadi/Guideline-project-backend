package hh.getData.guideline;

import hh.getData.guideline.Donors.Donors;
import hh.getData.guideline.Donors.DonorsRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@EnableWebMvc
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
