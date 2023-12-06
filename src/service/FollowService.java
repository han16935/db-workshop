package service;

import dto.MemberDto;
import exception.followException.DuplicateFollowRelationshipException;
import repository.FollowRepository;

import java.lang.reflect.Member;
import java.util.List;

public class FollowService {
    private FollowRepository followRepository;
    public FollowService(FollowRepository followRepository) {
        this.followRepository = followRepository;
    }

    public List<MemberDto> getAllFollowers(int ownerId){
        return followRepository.getAllFollowers(ownerId);
    }

    public List<MemberDto> getAllFollowings(int ownerId){
        return followRepository.getAllFollowings(ownerId);
    }

    // followingId가 followedId 팔로했는지 확인
    // 뒷부분 멤버가 앞부분 멤버 팔로했는지 확인
    public boolean hasFollowRelationship(int followedId, int followingId){
        return followRepository.hasFollowRelationship(followedId, followingId);
    }

    // followLoginUser가 loginUser 팔로하려고 함
    // 앞 멤버가 뒤 멤버 팔로하려고 함
    public void addFollower(int loginUserId, int followedByLoginUserId) throws DuplicateFollowRelationshipException {
        if(!hasFollowRelationship(loginUserId, followedByLoginUserId))
            throw new DuplicateFollowRelationshipException("이미 팔로하셨습니다!");
        followRepository.addFollower(loginUserId, followedByLoginUserId);
    }

    // loginUser가 followedByLoginUser 팔로하려고 함
    public void addFollowing(int followedByLoginUserId, int loginUserId) throws DuplicateFollowRelationshipException{
        if(!hasFollowRelationship(followedByLoginUserId, loginUserId))
           throw new DuplicateFollowRelationshipException("이미 팔로하셨습니다!");
        else followRepository.addFollowing(followedByLoginUserId, loginUserId);
    }
}
