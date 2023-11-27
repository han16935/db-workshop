package repository;

import db.DBConnection;
import dto.MemberDto;
import exception.memberException.DuplicateIdException;
import exception.memberException.LoginFailException;

import java.lang.reflect.Member;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MemberRepository {
    private DBConnection dbConnection;
    private Connection conn;

    public MemberRepository(DBConnection dbConnection){
        this.dbConnection = dbConnection;
        this.conn = dbConnection.getConnection();
    }

    public boolean isDuplicateUserId(String userId){
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
          String psql = "select user_id from user where user_id = ?";
          pstmt = conn.prepareStatement(psql);
          pstmt.setString(1, userId);

          rs = pstmt.executeQuery();

          // 이미 중복 ID 있을 경우 false 반환!
          if(rs.next()) return false;
          else return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void join(MemberDto willBeJoinedMember) throws SQLException{
        PreparedStatement pstmt = null;
        try{
            String psql = "insert into user(user_id, passWord) value (?, ?)";
            pstmt = conn.prepareStatement(psql);

            pstmt.setString(1, willBeJoinedMember.getUserId());
            pstmt.setString(2, willBeJoinedMember.getPassWord());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("내부 서버 에러!");
        }
    }

    public MemberDto login(MemberDto willBeLoggedInMember) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            String psql = "select * from user where user_id = ? and password = ?";
            pstmt = conn.prepareStatement(psql, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, willBeLoggedInMember.getUserId());
            pstmt.setString(2, willBeLoggedInMember.getPassWord());

            rs = pstmt.executeQuery();

            if (!rs.next()) throw new LoginFailException("로그인 실패!");
            else {
                int id = rs.getInt(1);
                String userId = rs.getString(2);
                String password = rs.getString(3);

                return new MemberDto(id, userId, password);
            }

        } catch (SQLException e) {
            throw new SQLException("내부 서버 에러!");
        } finally {
            try {
                if(rs != null){
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void changePassword(MemberDto loggedInMember, String newPassword) throws SQLException{
        PreparedStatement pstmt = null;
        try{
            String psql = "update user set password = ? where id = ?";
            pstmt = conn.prepareStatement(psql);

            pstmt.setString(1, newPassword);
            pstmt.setInt(2, loggedInMember.getId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("내부 서버 에러!");
        }
    }
}
