package ru.job4j.grabber.parser;

import ru.job4j.grabber.model.Post;

import java.util.List;

public interface Parser {
    List<Post> fetch();
}
