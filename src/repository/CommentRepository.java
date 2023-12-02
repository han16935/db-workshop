package repository;

import db.DBConnection;
import dto.ArticleDto;
import dto.CommentDto;
import dto.MemberDto;
import exception.memberException.LoginFailException;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CommentRepository {
    private DBConnection dbConnection;
    private Connection conn;

    public CommentRepository(DBConnection dbConnection){
        this.dbConnection = dbConnection;
        this.conn = dbConnection.getConnection();
    }

    public List<ArticleDto> getAllComments(List<ArticleDto> articleList){
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            for(ArticleDto articleDto : articleList){
                String psql = "select * from comment where article_id = ? ORDER BY created_date desc";
                List<CommentDto> comments = new ArrayList<>();
                pstmt = conn.prepareStatement(psql);
                pstmt.setInt(1, articleDto.getId());
                rs = pstmt.executeQuery();

                while(rs.next()){
                    int id = rs.getInt(1);
                    int articleId = rs.getInt(2);
                    int writerId = rs.getInt(3);
                    LocalDateTime createdDate = rs.getTimestamp(4).toLocalDateTime();
                    String content = rs.getString(5);

                    comments.add(new CommentDto(id, articleId, writerId, createdDate, content));
                }
                articleDto.addComments(comments);
            }
            return articleList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addComment(CommentDto commentDto){
        PreparedStatement pstmt = null;
        try{
            String psql = "insert into Comment(article_id, writer_id, created_date, content) " +
                    "values (?, ?, ?, ?)";

            pstmt = conn.prepareStatement(psql);

            pstmt.setInt(1, commentDto.getArticleId());
            pstmt.setInt(2, commentDto.getWriterId());
            pstmt.setTimestamp(3, Timestamp.valueOf(commentDto.getCreatedDate()));
            pstmt.setString(4, commentDto.getContent());
            conn.setAutoCommit(false);

            pstmt.executeUpdate();

            conn.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
