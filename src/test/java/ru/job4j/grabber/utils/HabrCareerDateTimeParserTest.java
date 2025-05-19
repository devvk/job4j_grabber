package ru.job4j.grabber.utils;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class HabrCareerDateTimeParserTest {
    @Test
    public void shouldParseIsoOffsetDateTimeAndReturnLocalDateTime() {
        String input = "2025-05-19T15:50:14+03:00";
        DateTimeParser parser = new HabrCareerDateTimeParser();
        LocalDateTime result = parser.parse(input);
        LocalDateTime expected = LocalDateTime.of(2025, 5, 19, 15, 50, 14);
        assertThat(result).isEqualTo(expected);
    }
}
