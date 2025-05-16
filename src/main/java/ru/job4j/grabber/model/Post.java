package ru.job4j.grabber.model;

import java.util.Objects;

public class Post {
    /**
     * Идентификатор вакансии (берется из нашей базы данных).
     */
    private long id;

    /**
     * Название вакансии.
     */
    private String title;

    /**
     * Ссылка на описание вакансии.
     */
    private String link;

    /**
     * Описание вакансии.
     */
    private String description;

    /**
     * Дата создания вакансии. Храниться в миллисекундах. В базе должно быть поле Timestamp without time zone.
     */
    private Long time;

    public Post(long id, String title, String link, String description, Long time) {
        this.id = id;
        this.title = title;
        this.link = link;
        this.description = description;
        this.time = time;
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
                + ", time=" + time
                + "}";
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }
}
