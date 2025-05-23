package ru.job4j.grabber.model;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Модель данных, описывающую вакансию.
 */
public class Post {

    /**
     * Идентификатор вакансии в БД.
     */
    long id;

    /**
     * Название вакансии.
     */
    String title;

    /**
     * Ссылка на страницу с вакансией.
     */
    String link;

    /**
     * Описание вакансии.
     */
    String description;

    /**
     * Дата и время публикации вакансии.
     */
    LocalDateTime created;

    public Post() {
    }

    public Post(long id, String title, String link, String description, LocalDateTime created) {
        this.id = id;
        this.title = title;
        this.link = link;
        this.description = description;
        this.created = created;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

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

    @Override
    public String toString() {
        return "Post{"
                + "id=" + id
                + ", title=" + title
                + ", link=" + link
                + ", description=" + description
                + ", created=" + created
                + "}";
    }
}
