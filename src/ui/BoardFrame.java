package ui;

import config.AppConfig;
import dto.ArticleDto;
import dto.MemberDto;
import service.ArticleService;
import service.CommentService;
import service.FollowService;
import service.MemberService;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;


import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicReference;


public class BoardFrame extends JFrame {

    private final CommentService commentService;
    // Backend Component
    private MemberDto loginUser;
    private MemberDto boardOwner;
    private MemberService memberService;
    private ArticleService articleService;
    private FollowService followService;
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
    private JButton articleWriteBtn;

    // following componentJPanel titlePanel = new JPanel();
    private JLabel followingNum;
    private JButton followingList;
    
    // follower component
    private JLabel followerNum;
    private JButton followerList;
    
    // time component
    private JLabel timeLabel;
    private JLabel dateLabel;

    
    public BoardFrame(MemberDto loginUser, MemberDto boardOwner,
                      MemberService memberService, ArticleService articleService, CommentService commentService, FollowService followService){
        this.loginUser = loginUser;
        this.boardOwner = boardOwner;
        this.memberService = memberService;
        this.articleService = articleService;
        this.commentService = commentService;
        this.followService = followService;

        JFrame frm = new JFrame("트-위타");
        frm.setSize(1000,1000);
        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frm.getContentPane().setLayout(null);
        
        // Time Layout


        // Header Layout
        JLabel loginUserLabel = new JLabel(loginUser.getUserId() + "님 환영합니다!");
        loginUserLabel.setBounds(20,170,122,30);

        JButton logoutBtn = new JButton("로그아웃");
        logoutBtn.setBounds(20,205,122,30);
        
        JButton changePwBtn = new JButton("비밀번호 변경");
        changePwBtn.setBounds(20,240,122,30);
        
        // Profile Layout
        JLabel boardOwnerLabel = new JLabel(boardOwner.getUserId() + "의 board");
        boardOwnerLabel.setBounds(400,130,122,30);
        
        JButton followBtn = new JButton("Follow");
        followBtn.setBounds(480,130,122,30);
        
        
        // articleList Layout
        JPanel articleListPanel = new JPanel();
        articleListPanel.setBackground(Color.WHITE);
        articleListPanel.setBounds(180,180,600,400);

        articleWriteBtn = new JButton("글쓰기");
        articleWriteBtn.setBounds(180, 600, 122, 30);
        
        // following Layout
        JLabel followingNum = new JLabel("팔로잉 : " + "?" + "명");
        followingNum.setBounds(830,200,122,30);
        
        JButton followingList = new JButton("팔로잉 목록");
        followingList.setBounds(830,240,122,30);
        
        // follower Layout
        JLabel followerNum = new JLabel("팔로워 : " + "?" + "명");
        followerNum.setBounds(830,300,122,30);
        
        JButton followerList = new JButton("팔로워 목록");
        followerList.setBounds(830,340,122,30);
        
        // Frame get each Content
        frm.getContentPane().add(loginUserLabel);
        frm.getContentPane().add(logoutBtn);
        frm.getContentPane().add(changePwBtn);
        frm.getContentPane().add(boardOwnerLabel);
        frm.getContentPane().add(followBtn);
        frm.getContentPane().add(articleListPanel);
        frm.getContentPane().add(followingNum);
        frm.getContentPane().add(followerNum);
        frm.getContentPane().add(followingList);
        frm.getContentPane().add(followerList);
        frm.getContentPane().add(articleWriteBtn);
        
        frm.setVisible(true);
        // Article Layout

        
        
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
        this.dispose();

        logoutBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "로그아웃 성공!");
            new AppConfig().loginFrame();
            frm.dispose();
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
        
        followerList.addActionListener(e -> {
            JFrame newFrame = new JFrame("팔로워 목록");
            newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            newFrame.setSize(300, 200);
            newFrame.setLocationRelativeTo(null);
            newFrame.setVisible(true);
            JTextArea followerArea = new JTextArea("",7,20);
            followerArea.setEnabled(false);
            followerArea.setBounds(830,280,122,30);
            followerArea.setBackground(Color.GRAY);
            newFrame.add(followerArea);
        	followerArea.setVisible(true);
        });
        
        followingList.addActionListener(e -> {
            JFrame newFrame = new JFrame("팔로잉 목록");
            newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            newFrame.setSize(300, 200);
            newFrame.setLocationRelativeTo(null);
            newFrame.setVisible(true);
            JTextArea followingArea = new JTextArea("",7,20);
            followingArea.setEnabled(false);
            followingArea.setBounds(830,280,122,30);
            followingArea.setBackground(Color.GRAY);
            newFrame.add(followingArea);
        	followingArea.setVisible(true);
        });

        articleWriteBtn.addActionListener(e->{
            final File[] photo = new File[1];
            final String[] uploadedPhotoPath = new String[1];

            JFrame articleWriteModal = new JFrame();
            articleWriteModal.setTitle("게시글 작성");
            articleWriteModal.setSize(700, 500);
            articleWriteModal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            articleWriteModal.setLayout(new GridLayout(3, 1, 20, 20));

            JLabel contentLabel = new JLabel("내용 작성");
            contentLabel.setHorizontalAlignment(JLabel.CENTER);

            JPanel contentPanel = new JPanel();
            contentPanel.setLayout(new GridLayout(1, 2));

            JTextArea contentField = new JTextArea(7, 20);
            contentPanel.add(new JScrollPane(contentField)); // Use JScrollPane to allow scrolling

            JPanel btnPanel = new JPanel();
            btnPanel.setLayout(new FlowLayout());
            JButton writeArticleBtn = new JButton("작성");
            JButton uploadPhotoBtn = new JButton("업로드");
            btnPanel.add(writeArticleBtn);
            btnPanel.add(uploadPhotoBtn);

            articleWriteModal.add(contentLabel);
            articleWriteModal.add(contentPanel);
            articleWriteModal.add(btnPanel);

            articleWriteModal.setVisible(true);

            uploadPhotoBtn.addActionListener(uploadBtnEvent -> {
                if(uploadedPhotoPath[0] == null){
                    JFileChooser chooser = new JFileChooser();
                    chooser.showOpenDialog(null);
                    photo[0] = chooser.getSelectedFile();
                    uploadedPhotoPath[0] = photo[0].getAbsolutePath();
                    System.out.println(uploadedPhotoPath[0]);

                    try {
                        // Read the image
                        BufferedImage originalImage = ImageIO.read(new File(uploadedPhotoPath[0]));

                        // Resize the image to fit within a reasonable size
                        int maxImageSize = 300; // Adjust this size according to your needs
                        int width = originalImage.getWidth();
                        int height = originalImage.getHeight();

                        if (width > maxImageSize || height > maxImageSize) {
                            double scale = Math.min((double) maxImageSize / width, (double) maxImageSize / height);
                            int newWidth = (int) (width * scale);
                            int newHeight = (int) (height * scale);
                            Image resizedImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
                            originalImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
                            Graphics2D g2d = originalImage.createGraphics();
                            g2d.drawImage(resizedImage, 0, 0, null);
                            g2d.dispose();
                        }

                        // Display the resized image
                        JLabel imgLabel = new JLabel(new ImageIcon(originalImage));
                        JScrollPane imgScrollPane = new JScrollPane(imgLabel);
                        contentPanel.add(imgScrollPane);
                        contentPanel.revalidate();
                        contentPanel.repaint();

                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }

                else JOptionPane.showMessageDialog(articleWriteModal,"하나의 이미지만 업로드할 수 있습니다!");
            });

            writeArticleBtn.addActionListener(writeArticleBtnEvent->{
                if(contentField.getText().isEmpty()) JOptionPane.showMessageDialog(articleWriteModal,"내용을 입력해주세요!");
                else{
                    if(uploadedPhotoPath[0] != null) articleService.addArticle(new ArticleDto(loginUser.getId(), boardOwner.getId(), contentField.getText(), LocalDateTime.now(), uploadedPhotoPath[0]));
                    else articleService.addArticle(new ArticleDto(loginUser.getId(), boardOwner.getId(), contentField.getText(), LocalDateTime.now()));
                    frm.dispose();
                    new AppConfig().boardFrame(loginUser, boardOwner);
                }
            });
        });
    }
    
}
