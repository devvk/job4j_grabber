package ru.job4j.grabber.model;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @param id          Идентификатор вакансии в БД.
 * @param title       Название вакансии.
 * @param link        Ссылка на описание вакансии.
 * @param description Описание вакансии.
 * @param created     Дата создания вакансии.
 */
public record Post(long id, String title, String link, String description, LocalDateTime created) {

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Post post = (Post) o;
        return Objects.equals(link, post.link);
    }

    @Override
    public int hashCode() {
        return Objects.hash(link);
    }
}
