package repository;

import db.DBConnection;
import dto.MemberDto;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FollowRepository {
    private DBConnection dbConnection;
    private Connection conn;
    public FollowRepository(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
        this.conn = dbConnection.getConnection();
    }

    // board의 owner를 팔로한 사람이나, owner가 팔로한 사람이 있는지 확인!
    public boolean hasOwnerFollowRelationship(int ownerId) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            String psql = "select * from follow where followed_id = ? or following_id = ?";
            pstmt = conn.prepareStatement(psql);
            pstmt.setInt(1, ownerId);
            pstmt.setInt(2, ownerId);

            rs = pstmt.executeQuery();

            // following한 사람, follower인 사람 둘 다 없을 경우 false
            if (!rs.next()) return false;
            else return true;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // 인자로 들어온 두 user가 이미 팔로잉 / 팔로워 관계인지 확인!
    // cf> a가 b를 팔로우했어도, b가 a를 팔로하지 않았다면 true 반환
    public boolean hasFollowRelationship(int followedId, int followingId){
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            String psql = "select * from follow where followed_id = ? and following_id = ?";

            pstmt = conn.prepareStatement(psql);
            pstmt.setInt(1, followedId);
            pstmt.setInt(2, followingId);

            rs = pstmt.executeQuery();

            // 이미 해당 관계 있을 경우
            if(rs.next()) return true;
            else return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // board 유저의 follower들의 id, userId 반환
    public List<MemberDto> getAllFollowers(int ownerId){
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<MemberDto> result = new ArrayList<>();

        try{
            String psql = "select id, user_id from user join follow on id = following_id "
            + "where followed_id = ?";

            pstmt = conn.prepareStatement(psql);
            pstmt.setInt(1, ownerId);

            rs = pstmt.executeQuery();

            while(rs.next()){
                int id = rs.getInt(1);
                String userId = rs.getString(2);

                result.add(new MemberDto(id, userId));
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // board 유저가 팔로잉한 사람의 id, userId 전부 반환
    public List<MemberDto> getAllFollowings(int ownerId){
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<MemberDto> result = new ArrayList<>();

        try{
            String psql = "select id, user_id from user join follow on id = followed_id "
            + "where following_id = ?";

            pstmt = conn.prepareStatement(psql);
            pstmt.setInt(1, ownerId);

            rs = pstmt.executeQuery();

            while(rs.next()){
                int id = rs.getInt(1);
                String userId = rs.getString(2);

                result.add(new MemberDto(id, userId));
            }
            return result;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // loginUser의 follower 추가
    // 앞 멤버가 뒤 멤버 팔로하려고 함
    public void addFollower(int loginUserId, int followedByLoginUserId){
        PreparedStatement pstmt = null;
        try{
            String psql = "insert into follow(followed_id, following_id)" +
                    " values (?, ?)";

            pstmt = conn.prepareStatement(psql);
            pstmt.setInt(1, followedByLoginUserId);
            pstmt.setInt(2, loginUserId);
            conn.setAutoCommit(false);

            pstmt.executeUpdate();

            conn.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // loginUser가 following한 사람 추가
    public void addFollowing(int followedByLoginUserId, int loginUserId){
        PreparedStatement pstmt = null;
        try{
            String psql = "insert into follow(followed_id, following_id)" +
                    "values(?, ?)";

            pstmt.setInt(1, followedByLoginUserId);
            pstmt.setInt(2, loginUserId);
            conn.setAutoCommit(false);

            pstmt.executeUpdate();

            conn.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
