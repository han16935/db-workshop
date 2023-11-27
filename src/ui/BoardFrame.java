package ui;

import config.AppConfig;
import dto.MemberDto;
import service.MemberService;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class BoardFrame extends JFrame {
    private MemberDto boardOwner;
    private MemberService memberService;

    private Container boardContainer;

    private JPanel headerPane;
    private JLabel nameLabel;

    private JButton logoutBtn;
    private JButton changePwBtn;


    public BoardFrame(MemberDto boardOwner, MemberService memberService){
        this.boardOwner = boardOwner;
        this.memberService = memberService;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 275);

        boardContainer = getContentPane();
        boardContainer.setLayout(new BorderLayout());

        // Header layout rendering
        headerPane = new JPanel();
        headerPane.setLayout(new FlowLayout());

        nameLabel = new JLabel(boardOwner.getUserId() + "님 환영합니다!");
        headerPane.add(nameLabel);

        logoutBtn = new JButton("로그아웃");
        headerPane.add(logoutBtn);

        changePwBtn = new JButton("비밀번호 변경");
        headerPane.add(changePwBtn);

        boardContainer.add(headerPane, BorderLayout.NORTH);

        // board layout rendering

        setTitle("트-위타");
        setResizable(false);
        setVisible(true);

        logoutBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "로그아웃 성공!");
            new AppConfig().loginFrame();
            this.dispose();
        });

        changePwBtn.addActionListener(e -> {
            try{
                String newPassword = JOptionPane.showInputDialog("새로운 비밀번호를 입력해주세요!");

                while(newPassword.equals(boardOwner.getPassWord())){
                    JOptionPane.showMessageDialog(this, "현재 비번과 다른 비번을 입력해주세요!");
                    newPassword = JOptionPane.showInputDialog("새로운 비밀번호를 입력해주세요!");
                }

                memberService.changePassword(boardOwner, newPassword);
                JOptionPane.showMessageDialog(this, "비번 변경 성공! 다시 로그인해주세요");
                new AppConfig().loginFrame();
                this.dispose();
            } catch(SQLException ServerErrorException){
                JOptionPane.showMessageDialog(this, ServerErrorException.getMessage());
            }
        });
    }
}
