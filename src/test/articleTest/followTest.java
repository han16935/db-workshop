package test.articleTest;

import config.AppConfig;
import dto.MemberDto;
import service.FollowService;

import java.util.List;

public class followTest {
    private static final FollowService followService = new AppConfig().followService();

    public static void main(String[] args) {

        // getAllFollowers test, it will print id 6, 8, 9 and their userId in test DB when arg is 7!
        List<MemberDto> allFollowers = followService.getAllFollowers(7);
        System.out.println("getAllFollower() Test");
        for (MemberDto follower : allFollowers) {
            System.out.println(follower);
        }

        // getAllFollowing test, it will print id 6 and their userId when arg is 7!
        List<MemberDto> allFollowings = followService.getAllFollowings(7);
        System.out.println("getAllFollowing() Test");
        for (MemberDto following : allFollowings) {
            System.out.println(following);
        }

        // addFollower test, I will test situation where 6 follows 8!
        System.out.println("addFollowing() test");
        followService.addFollower(8, 6);

        // addFollowing test, I will test situation where 8 follows 6!
        System.out.println("addFollower() test");
        followService.addFollowing(6, 8);

    }



}
