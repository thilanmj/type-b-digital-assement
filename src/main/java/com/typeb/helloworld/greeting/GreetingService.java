package com.typeb.helloworld.greeting;

import java.util.Optional;

import org.springframework.stereotype.Service;

/**
 * Greets a name only when its first letter is in the first half of the English alphabet.
 */
@Service
public class GreetingService {

	/**
	 * @return {@code Hello <Name>} when the trimmed name starts with A–M, ignoring case;
	 *         empty when the name is missing, blank, or starts with any other character
	 */
	public Optional<String> greet(String rawName) {
		if (rawName == null) {
			return Optional.empty();
		}
		String name = rawName.strip();
		if (name.isEmpty() || !startsInFirstHalf(name)) {
			return Optional.empty();
		}
		return Optional.of("Hello " + capitalizeFirst(name));
	}

	private static boolean startsInFirstHalf(String name) {
		char first = Character.toUpperCase(name.charAt(0));
		return first >= 'A' && first <= 'M';
	}

	private static String capitalizeFirst(String name) {
		return Character.toUpperCase(name.charAt(0)) + name.substring(1);
	}

}
