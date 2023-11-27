package service;

import dto.MemberDto;
import exception.memberException.DuplicateIdException;
import exception.memberException.LoginFailException;
import repository.MemberRepository;

import java.lang.reflect.Member;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }

    public void join(MemberDto willBeJoinedMember) throws SQLException, DuplicateIdException {
        if(!memberRepository.isDuplicateUserId(willBeJoinedMember.getUserId()))
            throw new DuplicateIdException("이미 해당 ID가 있습니다!\n다른 아이디를 사용해주세요!");

        memberRepository.join(willBeJoinedMember);
    }

    public MemberDto login(MemberDto willBeLoggedInMember) throws LoginFailException, SQLException {
        return memberRepository.login(willBeLoggedInMember);
    }

    public void changePassword(MemberDto loggedInMember, String newPassword) throws SQLException{
        memberRepository.changePassword(loggedInMember, newPassword);
    }
}
