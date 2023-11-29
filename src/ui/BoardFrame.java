package ui;

import config.AppConfig;
import dto.ArticleDto;
import dto.MemberDto;
import service.ArticleService;
import service.MemberService;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class BoardFrame extends JFrame {
    private MemberDto loginUser;
    private MemberDto boardOwner;
    private MemberService memberService;
    private ArticleService articleService;
    private GridBagLayout gBag;

    // header component
    private JPanel headerPanel;
    private JLabel loginUserLabel;
    private JButton logoutBtn;
    private JButton changePwBtn;


    // board component
    private JPanel profilePanel;
    private JLabel boardOwnerLabel;
    private JButton followBtn;

    // Article component
    private JPanel articleListPanel;
    private JScrollPane articleScListroll;
    private JButton likeBtn;

    // following componentJPanel titlePanel = new JPanel();

    // follower component

    public BoardFrame(MemberDto loginUser, MemberDto boardOwner,
                      MemberService memberService, ArticleService articleService){
        this.loginUser = loginUser;
        this.boardOwner = boardOwner;
        this.memberService = memberService;
        this.articleService = articleService;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 300);

        gBag = new GridBagLayout();
        setLayout(gBag);

        // Header Layout
        headerPanel = new JPanel(new FlowLayout());
        loginUserLabel = new JLabel(loginUser.getUserId() + "님 환영합니다!");
        headerPanel.add(loginUserLabel);

        logoutBtn = new JButton("로그아웃");
        headerPanel.add(logoutBtn);

        changePwBtn = new JButton("비밀번호 변경");
        headerPanel.add(changePwBtn);
        gbInsert(headerPanel, 1, 0, 4, 1);

        // Profile Layout
        profilePanel = new JPanel(new FlowLayout());
        boardOwnerLabel = new JLabel(boardOwner.getUserId() + "의 board");
        profilePanel.add(boardOwnerLabel);

        followBtn = new JButton("Follow");
        profilePanel.add(followBtn);
        gbInsert(profilePanel, 2, 4, 3, 1);

        // Article Layout
        articleListPanel = new JPanel(new BoxLayout(articleListPanel, BoxLayout.Y_AXIS));
        ArticleDto[] allArticles = articleService.getAllArticles(boardOwner.getId());
        for(ArticleDto articleDto : allArticles){
             JPanel titlePanel = new JPanel(new FlowLayout());
             JLabel title = new JLabel(articleDto.getTitle());
             JButton addLikeBtn = new JButton("좋아요!");

             JLabel content = new JLabel(articleDto.getContent());

        }
        // Following Layout

        // Follower Layout

//        boardContainer = getContentPane();
//        boardContainer.setLayout(new BorderLayout());
//
//        // Header layout
//        headerPane = new JPanel();
//        headerPane.setLayout(new FlowLayout());
//
//        nameLabel = new JLabel(loginUser.getUserId() + "님 환영합니다!");
//        headerPane.add(nameLabel);
//
//        logoutBtn = new JButton("로그아웃");
//        headerPane.add(logoutBtn);
//
//        changePwBtn = new JButton("비밀번호 변경");
//        headerPane.add(changePwBtn);
//
//        boardContainer.add(headerPane, BorderLayout.NORTH)


        pack();
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

                while(newPassword.equals(loginUser.getPassWord())){
                    JOptionPane.showMessageDialog(this, "현재 비번과 다른 비번을 입력해주세요!");
                    newPassword = JOptionPane.showInputDialog("새로운 비밀번호를 입력해주세요!");
                }

                memberService.changePassword(loginUser, newPassword);
                JOptionPane.showMessageDialog(this, "비번 변경 성공! 다시 로그인해주세요");
                new AppConfig().loginFrame();
                this.dispose();
            } catch(SQLException ServerErrorException){
                JOptionPane.showMessageDialog(this, ServerErrorException.getMessage());
            }
        });
    }
    public void gbInsert(Component c, int x, int y, int w, int h){
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = w;
        gbc.gridheight = h;
        //gBag.setConstraints(c, gbc);
        gbc.insets = new Insets(2, 2, 2, 2);
        add(c, gbc);
    }
}
