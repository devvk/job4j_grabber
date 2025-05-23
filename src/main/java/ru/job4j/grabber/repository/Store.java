package ru.job4j.grabber.repository;

import ru.job4j.grabber.model.Post;

import java.util.List;
import java.util.Optional;

/**
 * Задает контракт для хранилища вакансий.
 */
public interface Store {
    /**
     * Сохраняет вакансию в хранилище.
     *
     * @param post вакансия
     */
    void save(Post post);

    /**
     * Возвращает все сохраненные вакансии.
     *
     * @return список вакансий
     */
    List<Post> getAll();

    /**
     * Находит вакансию по идентификатору.
     *
     * @param id идентификатор вакансии.
     * @return вакансия
     */
    Optional<Post> findById(Long id);
}
