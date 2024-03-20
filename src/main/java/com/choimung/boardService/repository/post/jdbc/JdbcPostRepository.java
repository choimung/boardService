package com.choimung.boardService.repository.post.jdbc;

import com.choimung.boardService.domain.post.Post;
import com.choimung.boardService.dto.PostUpdateDto;
import com.choimung.boardService.repository.post.PostRepository;
import com.choimung.boardService.repository.post.PostSearchCond;
import com.choimung.boardService.service.FileService;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Slf4j
@RequiredArgsConstructor
@Repository
public class JdbcPostRepository implements PostRepository {

    private final DataSource dataSource;
    private final FileService fileService;

    @Override
    public Post save(Post post) {
        String sql = "INSERT INTO posts (title, author, image, content, views, create_date) VALUES (?,?,?,?,?,?)";

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, post.getTitle());
            ps.setString(2, post.getAuthor());
            ps.setString(3, post.getImage());
            ps.setString(4, post.getContent());
            ps.setLong(5, 0L);
            ps.setString(6, (LocalDateTime.now().format(DateTimeFormatter.ofPattern("yy.MM.dd  HH:mm:ss"))));
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();

            if (rs.next()) {
                post.setId(rs.getLong("id"));
            }
            return post;

        } catch (SQLException e) {
            log.error("db error", e);
            throw new RuntimeException(e);
        } finally {
            JdbcUtils.closeResultSet(rs);
            JdbcUtils.closeStatement(ps);
            JdbcUtils.closeConnection(con);
        }
    }

    @Override
    public void update(Long postId, PostUpdateDto postUpdateDto) {

        String sql = "UPDATE POSTS SET title = ?, image = ?, content = ?, create_date = ? WHERE id = ?";

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, postUpdateDto.getTitle());
            ps.setString(2, fileService.storeFile(postUpdateDto.getImage()));
            ps.setString(3, postUpdateDto.getContent());
            ps.setString(4, (LocalDateTime.now().format(DateTimeFormatter.ofPattern("yy.MM.dd  HH:mm:ss"))));
            ps.setLong(5, postId);
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();

        } catch (SQLException e) {
            log.error("db error", e);
            throw new RuntimeException(e);
        } catch (IOException f) {
            log.error("db error", f);
            throw new RuntimeException(f);
        } finally {
            JdbcUtils.closeResultSet(rs);
            JdbcUtils.closeStatement(ps);
            JdbcUtils.closeConnection(con);
        }
    }

    @Override
    public List<Post> findAll(PostSearchCond postSearchCond) {
        String title = postSearchCond.getTitle();
        String sql = "SELECT * FROM posts";

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            boolean flag = false;
            con = getConnection();

            if (StringUtils.hasText(title)) {
                sql += " WHERE title LIKE ?";
                flag = true;
            }

            ps = con.prepareStatement(sql);

            if (flag) {
                ps.setString(1, "%" +title + "%");
            }

            rs = ps.executeQuery();
            ArrayList<Post> posts = new ArrayList<>();

            while (rs.next()) {
                Post post = new Post();
                post.setId(rs.getLong("id"));
                post.setTitle(rs.getString("title"));
                post.setAuthor(rs.getString("author"));
                post.setContent(rs.getString("content"));
                post.setImage(rs.getString("image"));
                post.setViews(rs.getLong("views"));
                post.setCreateDate(rs.getString("create_date"));
                posts.add(post);
            }

            return posts;
        } catch (SQLException e) {
            log.error("db error", e);
            throw new RuntimeException(e);
        } finally {
            JdbcUtils.closeResultSet(rs);
            JdbcUtils.closeStatement(ps);
            JdbcUtils.closeConnection(con);
        }

    }

    @Override
    public Optional<Post> findById(Long postId) {

        String sql = "SELECT * FROM POSTS WHERE id = ?";

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
            con = getConnection();
            ps = con.prepareStatement(sql);
            ps.setLong(1, postId);
            rs = ps.executeQuery();

            if(rs.next()) {
                Post post = new Post();
                long currentViews = rs.getLong("views");
                ++currentViews;

                String updateSql = "UPDATE posts SET views = ? WHERE id = ?";
                PreparedStatement updateStmt = con.prepareStatement(updateSql);
                updateStmt.setLong(1, currentViews);
                updateStmt.setLong(2, postId);

                post.setId(rs.getLong("id"));
                post.setTitle(rs.getString("title"));
                post.setAuthor(rs.getString("author"));
                post.setImage(rs.getString("image"));
                post.setViews(++currentViews);
                post.setContent(rs.getString("content"));
                post.setCreateDate(rs.getString("create_date"));

                updateStmt.executeUpdate();
                return Optional.of(post);
            }

            return Optional.empty();

        } catch (SQLException e) {
            log.error("db error", e);
            throw new RuntimeException(e);
        } finally {
            JdbcUtils.closeResultSet(rs);
            JdbcUtils.closeStatement(ps);
            JdbcUtils.closeConnection(con);
        }

    }

    @Override
    public void delete(Long postId) {
        String sql = "DELETE FROM POSTS WHERE id = ?";

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
            con = getConnection();
            ps = con.prepareStatement(sql);
            ps.setLong(1, postId);
            ps.executeUpdate();

        } catch (SQLException e) {
            log.error("db error", e);
            throw new RuntimeException(e);
        } finally {
            JdbcUtils.closeResultSet(rs);
            JdbcUtils.closeStatement(ps);
            JdbcUtils.closeConnection(con);
        }

    }

    private Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
