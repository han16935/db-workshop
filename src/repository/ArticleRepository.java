package repository;

import db.DBConnection;
import dto.ArticleDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
            String psql = "select Article.id, Article.owner_id, Article.writer_id, Article.content, Article.created_date "
                    + "from Article join follow "
                    + "where Article.owner_id = ? or follow.following_id = ? or follow.followed_id = ? "
                    + "order by Article.created_date DESC";

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

               result.add(new ArticleDto(id, ownerId, writerId, content, createdDate));
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
            String psql = "select Article.id, Article.owner_id, Article.writer_id, Article.content, Article.created_date "
                    + "from Article where Article.owner_id = ? order by Article.created_date DESC";

            pstmt = conn.prepareStatement(psql);
            pstmt.setInt(1, boardOwnerId);

            rs = pstmt.executeQuery();

            while(rs.next()){
                int id = rs.getInt(1);
                int ownerId = rs.getInt(2);
                int writerId = rs.getInt(3);
                String content = rs.getString(4);
                LocalDateTime createdDate = rs.getTimestamp(5).toLocalDateTime();

                result.add(new ArticleDto(id, ownerId, writerId, content, createdDate));
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
