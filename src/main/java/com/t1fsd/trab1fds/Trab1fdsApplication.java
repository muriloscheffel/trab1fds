package com.t1fsd.trab1fds;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Trab1fdsApplication {

	public static void main(String[] args) {
		// SpringApplication.run(Trab1fdsApplication.class, args);
		Barca barca = new Barca(new RelogioRealmpl(), 50);

		System.out.println(barca.defineAssento("F02A12") + "fileira 02 assento 12");

		barca.printBarca();

		System.out.println(barca.defineAssento("F30A10"));
	}

}
