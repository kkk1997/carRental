package carrental;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages={"src.main.java.carrental"})
public class CarrentalApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(CarrentalApplication.class, args);
	}

	@Override
	public void run(String... strings) throws Exception {
		System.out.println("Hello world!");
	}
}
