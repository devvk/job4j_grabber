package ru.job4j.grabber.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.grabber.model.Post;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class HabrCareerParse implements Parse {
    private static final Logger LOG = LoggerFactory.getLogger(HabrCareerParse.class);
    private static final String SOURCE_LINK = "https://career.habr.com";
    private static final String PREFIX = "/vacancies?page=";
    private static final String SUFFIX = "&q=Java developer&type=all";

    @Override
    public List<Post> fetch() {
        List<Post> result = new ArrayList<>();
        String fullLink = getPageLink(1);
        try {
            Document doc = Jsoup.connect(fullLink).get();
            Elements vacancies = doc.select(".vacancy-card");
            vacancies.forEach(vacancy -> {
                String title = vacancy.select(".vacancy-card__title").text();
                String link = vacancy.select(".vacancy-card__title-link").attr("abs:href");
                String description = vacancy.select(".vacancy-card__skills").text();
                String date = vacancy.select(".basic-date").attr("datetime");
                Post post = new Post();
                post.setTitle(title);
                post.setLink(link);
                post.setDescription(description);
                OffsetDateTime offsetDateTime = OffsetDateTime.parse(date); // с учётом смещения
                LocalDateTime localDateTime = offsetDateTime.toLocalDateTime(); // без смещения
                post.setCreated(localDateTime);
                result.add(post);
            });
        } catch (IOException e) {
            LOG.error("Failed to fetch. LINK: {}", fullLink, e);
        }
        return result;
    }

    private String getPageLink(int page) {
        return "%s%s%d%s".formatted(SOURCE_LINK, PREFIX, page, SUFFIX);
    }

}
