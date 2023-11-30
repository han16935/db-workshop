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

    public boolean hasFollowRelationship(int followedId, int followingId){
        return followRepository.hasFollowRelationship(followedId, followingId);
    }

    // followLoginUser가 loginUser 팔로하려고 함
    public void addFollower(int loginUserId, int followLoginUserId) throws DuplicateFollowRelationshipException {
        if(!hasFollowRelationship(loginUserId, followLoginUserId))
            throw new DuplicateFollowRelationshipException("이미 팔로하셨습니다!");
        else followRepository.addFollower(loginUserId, followLoginUserId);
    }

    // loginUser가 followedByLoginUser 팔로하려고 함
    public void addFollowing(int followedByLoginUserId, int loginUserId) throws DuplicateFollowRelationshipException{
        if(!hasFollowRelationship(followedByLoginUserId, loginUserId))
           throw new DuplicateFollowRelationshipException("이미 팔로하셨습니다!");
        else followRepository.addFollowing(followedByLoginUserId, loginUserId);
    }
}
