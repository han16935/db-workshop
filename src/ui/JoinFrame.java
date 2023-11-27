package ui;

import config.AppConfig;
import dto.MemberDto;
import exception.memberException.DuplicateIdException;
import exception.memberException.NullIdException;
import exception.memberException.NullPwException;
import service.MemberService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.SQLException;

public class JoinFrame extends JFrame {
    // BackEnd
    private final MemberService memberService;

    // UI
    private JPanel contentPane;

    private JLabel joinLabel;
    private Font joinLabelFont;

    private JLabel idLabel;
    private JTextField idInput;

    private JLabel pwLabel;
    private JPasswordField pwInput;

    private JButton joinBtn;
    public JoinFrame(MemberService memberService){
        this.memberService = memberService;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 275);
        setLocationRelativeTo(null);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        joinLabel = new JLabel("회원가입");
        joinLabel.setBounds(150, 15, 69, 35);
        joinLabelFont = new Font("맑은 고딕", Font.BOLD, 15);
        joinLabel.setFont(joinLabelFont);
        contentPane.add(joinLabel);

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

        joinBtn = new JButton("회원가입");
        joinBtn.setBounds(150, 154, 104, 29);
        contentPane.add(joinBtn);

        setTitle("Join");
        setResizable(false);
        setVisible(true);


        joinBtn.addActionListener(e -> {
            try{
                if(idInput.getText().isEmpty()) throw new NullIdException("ID를 입력해주세요!");
                if(String.valueOf(pwInput.getPassword()).isEmpty()) throw new NullPwException("비밀번호를 입력해주세요!");

                MemberDto willBeJoinedMember = new MemberDto(idInput.getText(), String.valueOf(pwInput.getPassword()));
                memberService.join(willBeJoinedMember);
                JOptionPane.showMessageDialog(this, "회원가입 성공!", "회원가입", JOptionPane.INFORMATION_MESSAGE);
                new AppConfig().loginFrame();
                this.dispose();

//                System.out.println(idInput.getText());
//                System.out.println(String.valueOf(pwInput.getPassword()));
            }

            catch(NullIdException | NullPwException nullInputException){
                 JOptionPane.showMessageDialog(this, nullInputException.getMessage());
                 nullInputException.getStackTrace();
            }

            catch(DuplicateIdException duplicateIdException){
                JOptionPane.showMessageDialog(this, duplicateIdException.getMessage());
                duplicateIdException.getStackTrace();
            }

            catch(SQLException joinFailException){
                JOptionPane.showMessageDialog(this, joinFailException.getMessage());
            }

            finally{
                System.out.println(idInput.getText());
                System.out.println(pwInput.getPassword());
            }

            //System.out.println("회원가입 요청 성공!");

        });
    }
}
