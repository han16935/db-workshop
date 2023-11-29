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
            String psql = "select distinct Article.id, Article.board_id, Article.writer_id"
                    + "Article.title, Article.content"
                    + "from Article join follow"
                    + "where Article.board_id = ? or follow.follower_id = ? or follow.followed_id = ?"
                    + "order by Article.create_date DESC";

            pstmt = conn.prepareStatement(psql);
            pstmt.setInt(1, boardOwnerId);
            pstmt.setInt(2, boardOwnerId);
            pstmt.setInt(3, boardOwnerId);

            rs = pstmt.executeQuery();

            if(rs.next()){
               int id = rs.getInt(1);
               int boardId = rs.getInt(2);
               int writerId = rs.getInt(3);
               String title = rs.getString(4);
               String content = rs.getString(5);
               LocalDateTime createdDate = rs.getTimestamp(6).toLocalDateTime();
               result.add(new ArticleDto(id, boardId, writerId, title, content, createdDate));
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
