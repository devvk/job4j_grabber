package ru.job4j.grabber.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.grabber.model.Post;
import ru.job4j.grabber.utils.Config;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcStore implements Store, AutoCloseable {
    private static final Logger LOG = LoggerFactory.getLogger(JdbcStore.class);
    private final Connection connection;

    public JdbcStore(Config config) {
        try {
            Class.forName(config.get("db.driver"));
            connection = DriverManager.getConnection(
                    config.get("db.url"),
                    config.get("db.username"),
                    config.get("db.password")
            );
        } catch (SQLException e) {
            throw new RuntimeException("Cannot initialize database connection", e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Cannot load db.driver", e);
        }
    }

    /**
     * Закрывает соединение с БД.
     *
     * @throws SQLException если возникла ошибка при закрытии соединения
     */
    @Override
    public void close() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    /**
     * Сохраняет вакансию в БД.
     *
     * @param post вакансия
     */
    @Override
    public void save(Post post) {
        String sql = "INSERT INTO posts (title, link, description, created) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, post.getTitle());
            ps.setString(2, post.getLink());
            ps.setString(3, post.getDescription());
            ps.setTimestamp(4, Timestamp.valueOf(post.getCreated()));
            ps.executeUpdate();
        } catch (SQLException e) {
            LOG.error("Failed to save post. SQL: {} | params: [{}, {}, {}, {}]",
                    sql, post.getTitle(), post.getLink(), post.getDescription(), post.getCreated(), e);
        }
    }

    /**
     * Извлекает все вакансии из БД.
     *
     * @return список вакансий.
     */
    @Override
    public List<Post> getAll() {
        List<Post> posts = new ArrayList<>();
        String sql = "SELECT * FROM posts";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet resultSet = ps.executeQuery()) {
            while (resultSet.next()) {
                posts.add(createPost(resultSet));
            }
        } catch (SQLException e) {
            LOG.error("Failed to get all posts. SQL: {}", sql, e);
        }
        return posts;
    }

    /**
     * Создаёт объект Post из результата запроса.
     *
     * @param resultSet результат SQL-запроса
     * @return объект Item
     * @throws SQLException если возникла ошибка при чтении данных
     */
    private Post createPost(ResultSet resultSet) throws SQLException {
        return new Post(
                resultSet.getLong("id"),
                resultSet.getString("title"),
                resultSet.getString("link"),
                resultSet.getString("description"),
                resultSet.getTimestamp("created").toLocalDateTime()
        );
    }

    @Override
    public Optional<Post> findById(Long id) {
        Optional<Post> post = Optional.empty();
        String sql = "SELECT * FROM posts WHERE id = ? LIMIT 1";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    post = Optional.of(createPost(resultSet));
                }
            }
        } catch (SQLException e) {
            LOG.error("Failed to find post by ID: {}. SQL: {}", id, sql, e);
        }
        return post;
    }
}
