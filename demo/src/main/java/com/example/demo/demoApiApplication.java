package com.example.demo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class demoApiApplication {

	private static final String template = "Hello, %s!";
	private static final String templateLanguage = "%s, %s!";
	private final AtomicLong counter = new AtomicLong();

	private static Map<String, String> greetingsByLanguage = Map.of(
			"english", "Hello",
			"spanish", "Hola",
			"french", "bonjour",
			"german", "Hallo"
	);
	private static ArrayList<Greeting> greetingHistoryList = new ArrayList<>();


	@GetMapping("/greeting")
	public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		return new Greeting(counter.incrementAndGet(), String.format(template, name));
	}

	@GetMapping("/greeting/{language}")
	public Greeting greetingByLanguage(
			@PathVariable String language,
	@RequestParam(value="name", defaultValue = "World") String name
	){
		char[] charOfParams = new char[name.length()];
		name.getChars(0, name.length(), charOfParams, 0);
		String firstLetterOfNameCapitalised =  Character.toString(charOfParams[0]).toUpperCase();
		String nameCapitalised = firstLetterOfNameCapitalised;
		for (int i = 1; i < name.length(); i++) {
			nameCapitalised += Character.toString(charOfParams[i]);
		}
		String greetingLanguageMap = greetingsByLanguage.get(language);
		Greeting newGreeting = new Greeting(counter.incrementAndGet(), String.format(templateLanguage, greetingLanguageMap, nameCapitalised));
		if (!name.equals("World")) {
			greetingHistoryList.add(newGreeting);
		}
		return newGreeting;
	}

	@GetMapping("greeting/history")
	public ArrayList<Greeting> greetingHistory(){
		return greetingHistoryList;
	}
}