package fon.njt.EvidencijaZavrsnihRadovaapi;

import fon.njt.EvidencijaZavrsnihRadovaapi.service.FileStorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

@SpringBootApplication
public class EvidencijaZavrsnihRadovaApiApplication implements CommandLineRunner {
	@Resource
	FileStorageService storageService;

	public static void main(String[] args) {
		SpringApplication.run(EvidencijaZavrsnihRadovaApiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		storageService.deleteAll();
//		storageService.init();
	}


}
