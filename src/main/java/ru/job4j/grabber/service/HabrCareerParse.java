package ru.job4j.grabber.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.grabber.model.Post;
import ru.job4j.grabber.utils.HabrCareerDateTimeParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HabrCareerParse implements Parse {
    private static final Logger LOG = LoggerFactory.getLogger(HabrCareerParse.class);
    private static final String SOURCE_LINK = "https://career.habr.com";
    private static final String PREFIX = "/vacancies?page=";
    private static final String SUFFIX = "&q=Java developer&type=all";
    private static final int PAGES_TO_PARSE = 5;

    @Override
    public List<Post> fetch() {
        List<Post> result = new ArrayList<>();
        for (int i = 1; i <= PAGES_TO_PARSE; i++) {
            String fullLink = getPageLink(i);
            try {
                Document doc = Jsoup.connect(fullLink).get();
                Elements vacancies = doc.select(".vacancy-card");
                vacancies.forEach(vacancy -> {
                    String title = vacancy.select(".vacancy-card__title").text();
                    String link = vacancy.select(".vacancy-card__title-link").attr("abs:href");
                    String description = retrieveDescription(link);
                    String date = vacancy.select(".basic-date").attr("datetime");
                    Post post = new Post();
                    post.setTitle(title);
                    post.setLink(link);
                    post.setDescription(description);
                    post.setCreated(new HabrCareerDateTimeParser().parse(date));
                    result.add(post);
                });
            } catch (IOException e) {
                LOG.error("Failed to fetch. LINK: {}", fullLink, e);
            }
        }
        return result;
    }

    private String retrieveDescription(String link) {
        String description = "";
        try {
            Document doc = Jsoup.connect(link).get();
            description = doc.select(".faded-content__container").text();
        } catch (IOException e) {
            LOG.error("Failed to fetch. LINK: {}", link, e);
        }
        return description;
    }

    private String getPageLink(int page) {
        return "%s%s%d%s".formatted(SOURCE_LINK, PREFIX, page, SUFFIX);
    }
}
