package ui;


import config.AppConfig;
import dto.MemberDto;
import exception.memberException.DuplicateIdException;
import exception.memberException.LoginFailException;
import exception.memberException.NullIdException;
import exception.memberException.NullPwException;
import service.MemberService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.SQLException;


public class LoginFrame extends JFrame{

    private MemberService memberService;
    private JPanel contentPane;

    private JLabel welcomeLabel;
    private Font welcomeLabelFont;

    private JLabel idLabel;
    private JTextField idInput;

    private JLabel pwLabel;
    private JPasswordField pwInput;

    private JButton loginBtn;
    private JButton joinBtn;

    public LoginFrame(MemberService memberService){
        this.memberService = memberService;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 275);
        setLocationRelativeTo(null);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        welcomeLabel = new JLabel("로그인");
        welcomeLabel.setBounds(150, 15, 69, 35);
        welcomeLabelFont = new Font("맑은 고딕", Font.BOLD, 15);
        welcomeLabel.setFont(welcomeLabelFont);
        contentPane.add(welcomeLabel);

        idLabel = new JLabel("ID");
        idLabel.setBounds(41, 60, 69, 35);
        contentPane.add(idLabel);

        idInput = new JTextField(10);
        idInput.setBounds(157, 60, 176, 35);
        contentPane.add(idInput);

        pwLabel = new JLabel("PW");
        pwLabel.setBounds(41, 103, 69, 35);
        contentPane.add(pwLabel);

        pwInput = new JPasswordField(10);
        pwInput.setBounds(157, 103, 176, 35);
        contentPane.add(pwInput);

        loginBtn = new JButton("로그인");
        loginBtn.setBounds(108, 154, 106, 29);
        contentPane.add(loginBtn);

        joinBtn = new JButton("회원가입");
        joinBtn.setBounds(229, 154, 104, 29);
        contentPane.add(joinBtn);

        setTitle("Login");
        setResizable(false);
        setVisible(true);

        loginBtn.addActionListener(e ->{
            try{
                if(idInput.getText().isEmpty()) throw new NullIdException("ID를 입력해주세요!");
                if(String.valueOf(pwInput.getPassword()).isEmpty()) throw new NullPwException("비밀번호를 입력해주세요!");

                MemberDto willBeLoggedInMember = new MemberDto(idInput.getText(), String.valueOf(pwInput.getPassword()));
                MemberDto loggedInMember = memberService.login(willBeLoggedInMember);

                JOptionPane.showMessageDialog(this, "로그인 성공!", "로그인", JOptionPane.INFORMATION_MESSAGE);
                new AppConfig().boardFrame(loggedInMember);
                this.dispose();
            }

            catch(NullIdException | NullPwException nullInputException){
                JOptionPane.showMessageDialog(this, nullInputException.getMessage());
                nullInputException.getStackTrace();
            }

            catch(LoginFailException loginFailException){
                JOptionPane.showMessageDialog(this, loginFailException.getMessage());
                pwInput.setText("");
                loginFailException.getStackTrace();
            }

            catch(SQLException ServerErrorException){
                JOptionPane.showMessageDialog(this, ServerErrorException.getMessage());
            }

            finally{
                System.out.println(idInput.getText());
                System.out.println(pwInput.getPassword());
            }
        });

        joinBtn.addActionListener(e -> {
            new AppConfig().joinFrame();
            this.dispose();
        });
    }

    public static void main(String[] args) {
        new AppConfig().loginFrame();
    }
}
