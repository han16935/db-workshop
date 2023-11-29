package config;

import db.DBConnection;
import dto.MemberDto;
import repository.ArticleRepository;
import repository.CommentRepository;
import repository.MemberRepository;
import service.ArticleService;
import service.MemberService;
import ui.BoardFrame;
import ui.JoinFrame;
import ui.LoginFrame;

import java.lang.reflect.Member;

import static db.DBConnection.*;

public class AppConfig {

    // About Member
    public LoginFrame loginFrame(){
        return new LoginFrame(memberService());
    }

    public JoinFrame joinFrame(){
       return new JoinFrame(memberService());
    }

    public BoardFrame boardFrame(MemberDto loggedInUser, MemberDto boardUser) {return new BoardFrame(loggedInUser, boardUser, memberService(), articleService());}
    public MemberService memberService(){
      return new MemberService(memberRepository());
   }

    public MemberRepository memberRepository(){
        return new MemberRepository(getDbConnection());
   }

    public ArticleService articleService(){
        return new ArticleService(articleRepository(), commentRepository());
    }
    public ArticleRepository articleRepository(){return new ArticleRepository(getDbConnection());}
    public CommentRepository commentRepository(){return new CommentRepository(getDbConnection());}
}
