package repository;

import db.DBConnection;
import dto.ArticleDto;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ArticleRepository {
    private DBConnection dbConnection;
    private Connection conn;

    public ArticleRepository(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
        this.conn = dbConnection.getConnection();
    }

    public List<ArticleDto> getAllArticles(int boardOwnerId){
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<ArticleDto> result = new ArrayList<>();
        try{
            String psql = "SELECT * FROM article WHERE owner_id = ? " +
                    "UNION " +
                    "SELECT article.* FROM article " +
                    "INNER JOIN follow ON article.owner_id = follow.following_id " +
                    "WHERE follow.followed_id = ? " +
                    "UNION " +
                    "SELECT article.* FROM article " +
                    "INNER JOIN follow ON article.owner_id = follow.followed_id " +
                    "WHERE follow.following_id = ? " +
                    "ORDER BY created_date DESC";

            pstmt = conn.prepareStatement(psql);
            pstmt.setInt(1, boardOwnerId);
            pstmt.setInt(2, boardOwnerId);
            pstmt.setInt(3, boardOwnerId);

            rs = pstmt.executeQuery();

            while(rs.next()){
               int id = rs.getInt(1);
               int ownerId = rs.getInt(2);
               int writerId = rs.getInt(3);
               String content = rs.getString(4);
               LocalDateTime createdDate = rs.getTimestamp(5).toLocalDateTime();
               String filePath = rs.getString(6);

               result.add(new ArticleDto(id, ownerId, writerId, content, createdDate, filePath));
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<ArticleDto> getAllArticlesWithoutFollow(int boardOwnerId) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<ArticleDto> result = new ArrayList<>();
        try{
            String psql = "select * from Article" +
                          " where Article.owner_id = ? order by Article.created_date DESC";

            pstmt = conn.prepareStatement(psql);
            pstmt.setInt(1, boardOwnerId);

            rs = pstmt.executeQuery();

            while(rs.next()){
                int id = rs.getInt(1);
                int ownerId = rs.getInt(2);
                int writerId = rs.getInt(3);
                String content = rs.getString(4);
                LocalDateTime createdDate = rs.getTimestamp(5).toLocalDateTime();
                String filePath = rs.getString(6);

                result.add(new ArticleDto(id, ownerId, writerId, content, createdDate, filePath));
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addArticle(ArticleDto articleDto) {
        PreparedStatement pstmt = null;
        try{
            String psql = "insert into Article(owner_id, writer_id, content, created_date) " +
                    "values (?, ?, ?, ?)";

            pstmt = conn.prepareStatement(psql);

            pstmt.setInt(1, articleDto.getOwnerId());
            pstmt.setInt(2, articleDto.getWriterId());
            pstmt.setString(3, articleDto.getContent());
            pstmt.setTimestamp(4, Timestamp.valueOf(articleDto.getCreatedDate()));
            conn.setAutoCommit(false);

            pstmt.executeUpdate();

            conn.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addArticleWithImage(ArticleDto articleDto) {
        PreparedStatement pstmt = null;
        try{
            String psql = "insert into Article(owner_id, writer_id, content, created_date, image) " +
                    "values (?, ?, ?, ?, ?)";

            pstmt = conn.prepareStatement(psql);

            pstmt.setInt(1, articleDto.getOwnerId());
            pstmt.setInt(2, articleDto.getWriterId());
            pstmt.setString(3, articleDto.getContent());
            pstmt.setTimestamp(4, Timestamp.valueOf(articleDto.getCreatedDate()));
            pstmt.setString(5, articleDto.getFilePath());
            conn.setAutoCommit(false);

            pstmt.executeUpdate();

            conn.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
