package config;

import db.DBConnection;
import dto.MemberDto;
import repository.ArticleRepository;
import repository.CommentRepository;
import repository.FollowRepository;
import repository.MemberRepository;
import service.ArticleService;
import service.CommentService;
import service.FollowService;
import service.MemberService;
import ui.BoardFrame;
import ui.JoinFrame;
import ui.LoginFrame;

import java.lang.reflect.Member;

import static db.DBConnection.*;

public class AppConfig {

    // UI Class DI
    public LoginFrame loginFrame(){
        return new LoginFrame(memberService());
    }

    public JoinFrame joinFrame(){
       return new JoinFrame(memberService());
    }

    public BoardFrame boardFrame(MemberDto loggedInUser, MemberDto boardOwner) {
        return new BoardFrame(loggedInUser, boardOwner, memberService(), articleService(), commentService(), followService());
    }

    // Member Class DI
    public MemberService memberService(){
      return new MemberService(memberRepository());
   }

    public MemberRepository memberRepository(){
        return new MemberRepository(getDbConnection());
   }

   // Article Class DI
    public ArticleService articleService(){
        return new ArticleService(articleRepository(), commentRepository(), followRepository());
    }
    public ArticleRepository articleRepository(){return new ArticleRepository(getDbConnection());}

    // Comment Class DI
    public CommentService commentService(){
        return new CommentService(commentRepository());
    }
    public CommentRepository commentRepository(){return new CommentRepository(getDbConnection());}

    // Follow Class DI
    public FollowService followService(){
        return new FollowService(followRepository());
    }
    public FollowRepository followRepository(){return new FollowRepository(getDbConnection());}


}
