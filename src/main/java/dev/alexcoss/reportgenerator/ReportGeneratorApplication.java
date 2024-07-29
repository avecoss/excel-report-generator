package dev.alexcoss.reportgenerator;

import dev.alexcoss.reportgenerator.service.MainService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class ReportGeneratorApplication implements CommandLineRunner {

    private final MainService mainService;

    public static void main(String[] args) {
		SpringApplication.run(ReportGeneratorApplication.class, args);
	}

    @Override
    public void run(String... args) {
        mainService.processAndWriteData();
    }
}