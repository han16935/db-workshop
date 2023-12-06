package ui;

import config.AppConfig;
import dto.ArticleDto;
import dto.CommentDto;
import dto.MemberDto;
import service.ArticleService;
import service.CommentService;
import service.FollowService;
import service.MemberService;
import table.ArticleTableModel;
import table.CommentTableModel;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;


import java.time.LocalDateTime;
import java.util.List;
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

        followBtn.addActionListener(e->{
            if(loginUser.getId() == boardOwner.getId())
                JOptionPane.showMessageDialog(this, "자신을 팔로하실 수 없습니다!");

            else if(followService.hasFollowRelationship(boardOwner.getId(), loginUser.getId()))
                JOptionPane.showMessageDialog(this, "이미 팔로하셨습니다!");

            else{
                followService.addFollower(loginUser.getId(), boardOwner.getId());
                JOptionPane.showMessageDialog(this, boardOwner.getUserId() + "님을 팔로하셨습니다!");
            }
        });
        
        // articleList Layout
        JPanel articleListPanel = new JPanel();
        articleListPanel.setBackground(Color.WHITE);
        articleListPanel.setBounds(180,180,600,180);

        JLabel articleListLabel = new JLabel("게시글 목록");

        List<ArticleDto> allArticles = articleService.getAllArticles(boardOwner.getId());
        ArticleTableModel articleTableModel = new ArticleTableModel(allArticles);
        JTable articleListTable = new JTable(articleTableModel);
        articleListTable.setRowHeight(2, 500);
        articleListTable.setRowHeight(500);
        articleListTable.getColumnModel().getColumn(2).setCellRenderer(new ImageRenderer());
        JScrollPane scroll = new JScrollPane(articleListTable);
        articleListPanel.add(scroll);
        articleListPanel.add(articleListLabel);


        // commentList Layout
        JPanel commentListPanel = new JPanel();
        commentListPanel.setBackground(Color.WHITE);
        commentListPanel.setBounds(180,400,600,180);


        JLabel commentListLabel = new JLabel("댓글 목록");


        List<CommentDto> allComments = commentService.getAllComments();
        CommentTableModel commentTableModel = new CommentTableModel(allComments);
        JTable commentListTable = new JTable(commentTableModel);
        JScrollPane scroll2 = new JScrollPane(commentListTable);
        commentListPanel.add(scroll2);
        commentListPanel.add(commentListLabel);
//        List<ArticleDto> articles = articleService.getAllArticles(boardOwner.getId());
//        articleListPanel.setLayout(new GridLayout(articles.size(), 1));
//        JScrollPane articleListScrollPane = new JScrollPane(articleListPanel);
//
//        for (ArticleDto article : articles) {
//            JPanel eachArticlePanel = new JPanel();
//            if(article.getFilePath() != null){
//                eachArticlePanel.setLayout(new GridLayout(1, 2));
//                try {
//                    // Read the image
//                    BufferedImage originalImage = ImageIO.read(new File(uploadedPhotoPath[0]));
//
//                    // Resize the image to fit within a reasonable size
//                    int maxImageSize = 300; // Adjust this size according to your needs
//                    int width = originalImage.getWidth();
//                    int height = originalImage.getHeight();
//
//                    if (width > maxImageSize || height > maxImageSize) {
//                        double scale = Math.min((double) maxImageSize / width, (double) maxImageSize / height);
//                        int newWidth = (int) (width * scale);
//                        int newHeight = (int) (height * scale);
//                        Image resizedImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
//                        originalImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
//                        Graphics2D g2d = originalImage.createGraphics();
//                        g2d.drawImage(resizedImage, 0, 0, null);
//                        g2d.dispose();
//                    }
//
//                    // Display the resized image
//                    JLabel imgLabel = new JLabel(new ImageIcon(originalImage));
//                    JScrollPane imgScrollPane = new JScrollPane(imgLabel);
//
//
//                } catch (IOException ex) {
//                    throw new RuntimeException(ex);
//                }
//            }
//        }

        articleWriteBtn = new JButton("글쓰기");
        articleWriteBtn.setBounds(180, 600, 122, 30);

        JButton commentWriteBtn = new JButton("댓글쓰기");
        commentWriteBtn.setBounds(660, 600, 122, 30);
        // following Layout
        JLabel followingNum = new JLabel("팔로잉 : "
                + followService.getAllFollowings(boardOwner.getId()).size()
                + "명");
        followingNum.setBounds(830,200,122,30);
        
        JButton followingList = new JButton("팔로잉 목록");
        followingList.setBounds(830,240,122,30);

        followingList.addActionListener(e -> {
            JFrame newFrame = new JFrame("팔로잉 목록");
            newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            newFrame.setSize(400, 300);
            JPanel followPanel = new JPanel();
            //followPanel.setBackground(back);
            followPanel.setSize(300,200);
            newFrame.getContentPane().add(followPanel);
            newFrame.setLocationRelativeTo(null);
            newFrame.setVisible(true);
            List<MemberDto> followings = followService.getAllFollowings(boardOwner.getId());

            JPanel space = new JPanel();
            space.setPreferredSize(new Dimension(250, 25));
            //space.setBackground(back);
            followPanel.add(space);
            try {

                for (MemberDto followingMember : followings)
                {
                    String followingId = followingMember.getUserId();
                    JButton followButton = new JButton(followingId);

                    followButton.setPreferredSize(new Dimension(250,25));
                    //followButton.setBackground(back);
                    followButton.setFocusPainted(false);
                    followPanel.add(followButton);
                    followButton.addActionListener(ev ->{
                        new AppConfig().boardFrame(loginUser, followingMember);
                        newFrame.dispose();
                        frm.dispose();
                    });
                    followButton.setVisible(true);
                }
            }
            catch(Exception exc) {
                exc.printStackTrace();
            }
        });

        // follower Layout
        JLabel followerNum = new JLabel("팔로워 : "
                + followService.getAllFollowers(boardOwner.getId()).size()
                + "명");
        followerNum.setBounds(830,300,122,30);
        
        JButton followerList = new JButton("팔로워 목록");
        followerList.setBounds(830,340,122,30);

        followerList.addActionListener(e -> {
            JFrame newFrame = new JFrame("팔로워 목록");
            newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            newFrame.setSize(400, 300);
            JPanel followPanel = new JPanel();
            //followPanel.setBackground(back);
            followPanel.setSize(300,200);
            newFrame.getContentPane().add(followPanel);
            newFrame.setLocationRelativeTo(null);
            newFrame.setVisible(true);
            List<MemberDto> followers = followService.getAllFollowers(boardOwner.getId());

            JPanel space = new JPanel();
            space.setPreferredSize(new Dimension(250, 25));
            //space.setBackground(back);
            followPanel.add(space);
            try {

                for (MemberDto followerMember : followers)
                {
                    String followerId = followerMember.getUserId();
                    JButton followButton = new JButton(followerId);

                    followButton.setPreferredSize(new Dimension(250,25));
                    //followButton.setBackground(back);
                    followButton.setFocusPainted(false);
                    followPanel.add(followButton);
                    followButton.addActionListener(ev ->{
                        new AppConfig().boardFrame(loginUser, followerMember);
                        newFrame.dispose();
                        frm.dispose();
                    });
                    followButton.setVisible(true);
                }
            }
            catch(Exception exc) {
                exc.printStackTrace();
            }
        });
        // Frame get each Content
        frm.getContentPane().add(loginUserLabel);
        frm.getContentPane().add(logoutBtn);
        frm.getContentPane().add(changePwBtn);
        frm.getContentPane().add(boardOwnerLabel);
        frm.getContentPane().add(followBtn);
        frm.getContentPane().add(articleListPanel);
        frm.getContentPane().add(commentListPanel);
        frm.getContentPane().add(followingNum);
        frm.getContentPane().add(followerNum);
        frm.getContentPane().add(followingList);
        frm.getContentPane().add(followerList);
        frm.getContentPane().add(articleWriteBtn);
        frm.getContentPane().add(commentWriteBtn);
        
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

        commentWriteBtn.addActionListener(e -> {
            JFrame commentWriteModal = new JFrame();
            commentWriteModal.setTitle("댓글 작성");
            commentWriteModal.setSize(700, 500);
            commentWriteModal.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            commentWriteModal.setLayout(new GridLayout(3, 1, 20, 20));

            JLabel content1Label = new JLabel("댓글을 달 게시글 번호를 입력해주세요");
            content1Label.setHorizontalAlignment(JLabel.CENTER);
            JPanel content1Panel = new JPanel();
            content1Panel.setLayout(new GridLayout(1,1));
            JTextArea content1Field = new JTextArea(2, 10);
            content1Panel.add(new JScrollPane(content1Field));

            JLabel content2Label = new JLabel("댓글 작성");
            content2Label.setHorizontalAlignment(JLabel.CENTER);
            JPanel content2Panel = new JPanel();
            content2Panel.setLayout(new GridLayout(1,2));
            JTextArea content2Field = new JTextArea(7, 20);
            content2Panel.add(new JScrollPane(content2Field));

            JPanel commentBtnPanel = new JPanel(); commentBtnPanel.setLayout(new FlowLayout());

            JButton writeCommentBtn = new JButton("등록");
            commentBtnPanel.add(writeCommentBtn);

            commentWriteModal.add(content1Label);
            commentWriteModal.add(content1Panel);
            commentWriteModal.add(content2Label);
            commentWriteModal.add(content2Panel);
            commentWriteModal.add(commentBtnPanel);


            commentWriteModal.setVisible(true);



            writeCommentBtn.addActionListener(writeCommentBtnEvent -> {

                if(content1Field.getText().isEmpty()) JOptionPane.showMessageDialog(commentWriteModal, "댓글을 달 게시글 번호를 입력해주세요!");
                if(content2Field.getText().isEmpty()) JOptionPane.showMessageDialog(commentWriteModal, "내용을 입력해주세요!");
                else {
                    CommentDto commentDto = new CommentDto(Integer.parseInt(content1Field.getText()), boardOwner.getId(), LocalDateTime.now(), content2Field.getText());
                    commentService.addComment(commentDto);
                    JOptionPane.showMessageDialog(commentWriteModal, "댓글이 입력되었습니다!");
                    commentWriteModal.dispose();
                    frm.dispose();
                    new AppConfig().boardFrame(loginUser, boardOwner);
                }
            });
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
                    JOptionPane.showMessageDialog(articleWriteModal, "업로드 완료!");
                    articleWriteModal.dispose();
                    frm.dispose();
                    new AppConfig().boardFrame(loginUser, boardOwner);
                }
            });
        });
    }

    static class ImageRenderer extends JLabel implements TableCellRenderer {

        private static final int IMAGE_WIDTH = 10000; // Adjust the width as needed
        private static final int IMAGE_HEIGHT = 10000; // Adjust the height as needed

        public ImageRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if(value != null){
                // Assuming the value is a BufferedImage
                BufferedImage image = (BufferedImage) value;

                // Set the image to the label
                setIcon(new ImageIcon(image));

                setPreferredSize(new Dimension(IMAGE_WIDTH, IMAGE_HEIGHT));

                // Adjust other properties as needed
                setHorizontalAlignment(JLabel.CENTER);

                // Return the label as the renderer component
                return this;
            }

            else return null;
        }
    }
}
