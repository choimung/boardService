package com.choimung.boardService.repository.member.jdbc;

import com.choimung.boardService.domain.member.Grade;
import com.choimung.boardService.domain.member.Member;
import com.choimung.boardService.dto.MemberUpdateDto;
import com.choimung.boardService.repository.member.MemberRepository;
import com.choimung.boardService.service.FileService;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.stereotype.Repository;

@Slf4j
@RequiredArgsConstructor
@Repository
public class JdbcMemberRepository implements MemberRepository {

    private final DataSource dataSource;
    private final FileService fileService;

    @Override
    public Member save(Member member) {

        String sql = "INSERT INTO member (loginId, password, name, grade, image) VALUES (?,?,?,?,?)";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, member.getLoginId());
            pstmt.setString(2, member.getPassword());
            pstmt.setString(3, member.getNickname());
            pstmt.setString(4, member.getGrade().toString());
            pstmt.setString(5, member.getImage());
            pstmt.executeUpdate();

            rs = pstmt.getGeneratedKeys();

            if (rs.next()) {
                member.setId(rs.getLong("id"));
            }

            return member;
        } catch (SQLException e) {
            log.error("db error", e);
            throw new RuntimeException(e);
        } finally {
            JdbcUtils.closeResultSet(rs);
            JdbcUtils.closeStatement(pstmt);
            JdbcUtils.closeConnection(con);
        }
    }

    @Override
    public void update(Long memberId, MemberUpdateDto memberUpdateDto) {

        String sql = "UPDATE MEMBER SET password = ?, name = ?, image = ? WHERE id = ?";

        Connection con = null;
        PreparedStatement ps = null;

        try{
            con = getConnection();
            ps = con.prepareStatement(sql);

            ps.setString(1,memberUpdateDto.getPassword());
            ps.setString(2,memberUpdateDto.getNickname());
            String image = fileService.storeFile(memberUpdateDto.getImage());
            ps.setString(3, image);
            ps.setLong(4, memberId);
            ps.executeUpdate();
        }catch (SQLException e) {
            log.error("db error", e);
            throw new RuntimeException(e);
        }catch (IOException f){
            log.error("db error",f);
            throw new RuntimeException(f);
        }finally {
            JdbcUtils.closeResultSet(null);
            JdbcUtils.closeStatement(ps);
            JdbcUtils.closeConnection(con);
        }

    }

    @Override
    public Optional<Member> findById(Long memberId) {
        String sql = "SELECT * FROM MEMBER WHERE id = ?";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setLong(1, memberId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setLoginId(rs.getString("loginId"));
                member.setPassword(rs.getString("password"));
                member.setGrade(Grade.USER);
                member.setNickname(rs.getString("name"));
                member.setImage(rs.getString("image"));
                return Optional.of(member);
            }

            return Optional.empty();
        } catch (SQLException e) {

            log.error("db error", e);
            throw new RuntimeException(e);
        } finally {
            JdbcUtils.closeResultSet(rs);
            JdbcUtils.closeStatement(pstmt);
            JdbcUtils.closeConnection(con);
        }

    }

    @Override
    public Optional<Member> findByLoginId(String loginId) {
        String sql = "SELECT * FROM MEMBER WHERE loginId = ?";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, loginId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setLoginId(rs.getString("loginId"));
                member.setPassword(rs.getString("password"));
                member.setGrade(Grade.USER);
                member.setNickname(rs.getString("name"));
                member.setImage(rs.getString("image"));
                return Optional.of(member);
            }

            return Optional.empty();
        } catch (SQLException e) {
            log.error("db error", e);
            throw new RuntimeException(e);
        } finally {
            JdbcUtils.closeResultSet(rs);
            JdbcUtils.closeStatement(pstmt);
            JdbcUtils.closeConnection(con);
        }
    }

    @Override
    public List<Member> findAll() {
        return null;
    }

    @Override
    public void delete(Long memberId) {

    }

    private Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
