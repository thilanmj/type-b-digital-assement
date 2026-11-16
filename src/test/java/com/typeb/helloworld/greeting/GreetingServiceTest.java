package com.typeb.helloworld.greeting;

import java.util.Optional;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class GreetingServiceTest {

	private final GreetingService greetingService = new GreetingService();

	@ParameterizedTest
	@MethodSource("acceptedNames")
	void greetsNamesInTheFirstHalfOfTheAlphabet(String name, String expected) {
		assertThat(greetingService.greet(name)).isEqualTo(Optional.of(expected));
	}

	@ParameterizedTest
	@NullAndEmptySource
	@ValueSource(strings = {
			" ",
			"   ",
			"\t",
			"\n",
			"n",
			"z",
			"N",
			"Z",
			"nathan",
			"Zoe",
			"n name",
			"1alice",
			"@bob",
			"émile",
			"Ñora",
			"."
	})
	void rejectsMissingBlankAndOutOfRangeNames(String name) {
		assertThat(greetingService.greet(name)).isEmpty();
	}

	@Test
	void capitalizesOnlyTheFirstCharacter() {
		assertThat(greetingService.greet("aLiCe")).contains("Hello ALiCe");
		assertThat(greetingService.greet("ALICE")).contains("Hello ALICE");
	}

	private static Stream<Arguments> acceptedNames() {
		return Stream.of(
				arguments("a", "Hello A"),
				arguments("m", "Hello M"),
				arguments("A", "Hello A"),
				arguments("M", "Hello M"),
				arguments("alice", "Hello Alice"),
				arguments("mike", "Hello Mike"),
				arguments("  bob  ", "Hello Bob"),
				arguments("\ncarol\t", "Hello Carol"),
				arguments("Mary Jane", "Hello Mary Jane")
		);
	}

}
