/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package App;

import Client.Client;
import Forms.ComponentResizer;
import Forms.Emoji;
import Forms.People;
import Forms.ScrollBar;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author mertcankarabas
 */
public class ClientMainFrame extends javax.swing.JFrame {

    /**
     * Creates new form ServerMainFram
     */
    Emoji emojiFrame;
    String clientName;
    Client client;

    boolean isPeople = true;
    boolean isGroup = false;
    boolean isSettings = false;
    boolean isClosed = false;
    private final String SERVER_ADDRESS = "localhost";
    private final int PORT = 10000;

    /*
    Debug için ClientMainFrame constructure'u eleman almadan yapıyoruz
    normalde loginden gelecek orası ama sürekli login açıp isim yazmamak için şimdilik kaldırıyoruz.
    en son String clientName alan cunstructor kalacak ve loginden bu frame'yi açtığın yere onu koyacaksın.
     */
    public ClientMainFrame(String clientName) {
        this.clientName = clientName;
        init();
        initComponents();
        init2();

    }

    public ClientMainFrame() {
        initComponents();
        init();
        init2();
    }

    private void init() {
        try {
            this.client = new Client(this.SERVER_ADDRESS, this.PORT, this.clientName, this);
            this.client.StartClient();
        } catch (IOException ex) {
            Logger.getLogger(ClientMainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void init2() {
        lbl_clientName.setText(this.clientName);
        txta_messages.setEditable(false);
        scrollPane_chats.setVerticalScrollBar(new ScrollBar());
        scrollPane_messages.setVerticalScrollBar(new ScrollBar());

        //Bu frame'in yeniden boyutlandırılabilmesi için yapıyoruz.
        ComponentResizer compRes = new ComponentResizer();
        compRes.registerComponent(this);
        compRes.setMinimumSize(new Dimension(800, 500));
        compRes.setMaximumSize(Toolkit.getDefaultToolkit().getScreenSize());
        compRes.setSnapSize(new Dimension(10, 10));

        layeredPane_lists.setLayout(new MigLayout("fillx", "0[]0", "1[]1")); //chat alanını düzenlemek için yapıyoruz.
        showMessages("Global");
        People global = (People)layeredPane_lists.getComponent(0);
        global.setIsClicked(true);
        lbl_chatHeaderTitle.setText("Global");
    }

    public JTextArea getTxta_messages() {
        return txta_messages;
    }

    public void setTxta_messages(JTextArea txta_messages) {
        this.txta_messages = txta_messages;
    }

    public JPanel getPnl_emoji() {
        return pnl_emoji;
    }

    public JPanel getPnl_messages() {
        return pnl_messages;
    }

    public void setPnl_messages(JPanel pnl_messages) {
        this.pnl_messages = pnl_messages;
    }

    public void setPnl_emoji(JPanel pnl_emoji) {
        this.pnl_emoji = pnl_emoji;
    }

    public JLayeredPane getLayeredPane_lists() {
        return layeredPane_lists;
    }

    public void setLayeredPane_lists(JLayeredPane layeredPane_lists) {
        this.layeredPane_lists = layeredPane_lists;
    }

    public JTextField getTxt_message() {
        return txt_message;
    }

    public void setTxt_message(JTextField txt_message) {
        this.txt_message = txt_message;
    }
    
    public void showMessages() {
        layeredPane_lists.removeAll();
        for (int i = 0; i < 10; i++) {
            
        }
        refreshList();
    }
    
    public void showMessages(String name) {
        if(isPeople){
            layeredPane_lists.add(new People(name), "wrap");
            refreshList();
        }
    }

    private void showGroups() {
        layeredPane_lists.removeAll();
        for (int i = 0; i < 5; i++) {
            layeredPane_lists.add(new People("Group " + i), "wrap");
        }
        refreshList();
    }

    private void showSettings() {
        layeredPane_lists.removeAll();
        for (int i = 0; i < 10; i++) {
            layeredPane_lists.add(new People("Settings " + i), "wrap");
        }
        refreshList();
    }

    public void refreshList() {
        layeredPane_lists.repaint();
        layeredPane_lists.revalidate();
    }

    private void sendMessage() {
        String message = " " + this.clientName + ": " + txt_message.getText();
        People p = isPeopleClicked();
        try {
            
            if(isClosed) {
                String sendingMessage = " Disconnect" + message;
                this.client.sendMessage(sendingMessage.getBytes());
                System.exit(0);
            }
            
            if (p.getName().equals("Global")) {
                String sendingMessageBroadcast = " Text" + message;   
                this.client.sendMessage(sendingMessageBroadcast.getBytes());
                

            } else {

                if (isPeople) {
                    String sendingMessagePrivate = " Private" + message;                    
                    this.client.sendMessage(sendingMessagePrivate.getBytes());

                } else if (isGroup) {
                    String sendingMessageGroup = " GroupID" + message;                    
                    this.client.sendMessage(sendingMessageGroup.getBytes());
                }
            }
            
            txt_message.setText("");
        } catch (IOException ex) {
            Logger.getLogger(ClientMainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private People isPeopleClicked() {
        if (layeredPane_lists.getComponents() != null) {
            for (Component comp : layeredPane_lists.getComponents()) {
                People person = (People) comp;
                if (person.isClicked()) {
                    return person;
                }
            }
        }
        return null;
    }

    public void changeColor(JPanel hover, Color rand) {
        hover.setBackground(rand);
    }

    public void changeImage(JLabel menu, String img) {
        menu.setIcon(new javax.swing.ImageIcon(img));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnl_header = new javax.swing.JPanel();
        pnl_closeMaxMin = new javax.swing.JPanel();
        pnl_max = new javax.swing.JPanel();
        lbl_max = new javax.swing.JLabel();
        pnl_close = new javax.swing.JPanel();
        lbl_close = new javax.swing.JLabel();
        lbl_clientName = new javax.swing.JLabel();
        pnl_users = new javax.swing.JPanel();
        pnl_menuBarBackground = new javax.swing.JPanel();
        layeredPane_menuBar = new javax.swing.JLayeredPane();
        btn_messages = new Forms.MyButton();
        btn_groups = new Forms.MyButton();
        btn_settings = new Forms.MyButton();
        pnl_chat = new javax.swing.JPanel();
        scrollPane_chats = new javax.swing.JScrollPane();
        layeredPane_lists = new javax.swing.JLayeredPane();
        pnl_chatBackground = new javax.swing.JPanel();
        pnl_chatHeader = new javax.swing.JPanel();
        lbl_chatHeaderTitle = new javax.swing.JLabel();
        pnl_messages = new javax.swing.JPanel();
        scrollPane_messages = new javax.swing.JScrollPane();
        txta_messages = new javax.swing.JTextArea();
        pnl_bottomChat = new javax.swing.JPanel();
        pnl_emoji = new javax.swing.JPanel();
        lbl_chooseEmoji = new javax.swing.JLabel();
        pnl_attach = new javax.swing.JPanel();
        lbl_attachMessage = new javax.swing.JLabel();
        txt_message = new javax.swing.JTextField();
        pnl_sendMessage = new javax.swing.JPanel();
        lbl_sendMessage = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        pnl_header.setBackground(new java.awt.Color(0, 100, 102));
        pnl_header.setPreferredSize(new java.awt.Dimension(800, 30));
        pnl_header.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                pnl_headerMouseDragged(evt);
            }
        });
        pnl_header.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pnl_headerMousePressed(evt);
            }
        });
        pnl_header.setLayout(new java.awt.BorderLayout());

        pnl_closeMaxMin.setBackground(new java.awt.Color(0, 100, 102));
        pnl_closeMaxMin.setPreferredSize(new java.awt.Dimension(60, 30));
        pnl_closeMaxMin.setSize(new java.awt.Dimension(100, 30));
        pnl_closeMaxMin.setLayout(null);

        pnl_max.setBackground(new java.awt.Color(0, 100, 102));

        lbl_max.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_max.setIcon(new javax.swing.ImageIcon("/Users/mertcankarabas/NetBeansProjects/IletisimClient/src/main/java/Icons/Full_Screen_Button.png")); // NOI18N
        lbl_max.setPreferredSize(new java.awt.Dimension(30, 30));
        lbl_max.setSize(new java.awt.Dimension(30, 30));
        lbl_max.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_maxMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lbl_maxMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lbl_maxMouseExited(evt);
            }
        });

        javax.swing.GroupLayout pnl_maxLayout = new javax.swing.GroupLayout(pnl_max);
        pnl_max.setLayout(pnl_maxLayout);
        pnl_maxLayout.setHorizontalGroup(
            pnl_maxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_maxLayout.createSequentialGroup()
                .addComponent(lbl_max, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnl_maxLayout.setVerticalGroup(
            pnl_maxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_max, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pnl_closeMaxMin.add(pnl_max);
        pnl_max.setBounds(0, 0, 30, 30);

        pnl_close.setBackground(new java.awt.Color(0, 100, 102));

        lbl_close.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_close.setIcon(new javax.swing.ImageIcon("/Users/mertcankarabas/NetBeansProjects/IletisimClient/src/main/java/Icons/Close_Button.png")); // NOI18N
        lbl_close.setPreferredSize(new java.awt.Dimension(30, 30));
        lbl_close.setSize(new java.awt.Dimension(30, 30));
        lbl_close.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_closeMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lbl_closeMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lbl_closeMouseExited(evt);
            }
        });

        javax.swing.GroupLayout pnl_closeLayout = new javax.swing.GroupLayout(pnl_close);
        pnl_close.setLayout(pnl_closeLayout);
        pnl_closeLayout.setHorizontalGroup(
            pnl_closeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_closeLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lbl_close, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        pnl_closeLayout.setVerticalGroup(
            pnl_closeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_close, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pnl_closeMaxMin.add(pnl_close);
        pnl_close.setBounds(30, 0, 30, 30);

        pnl_header.add(pnl_closeMaxMin, java.awt.BorderLayout.LINE_END);

        lbl_clientName.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbl_clientName.setText("User Name");
        lbl_clientName.setPreferredSize(new java.awt.Dimension(150, 17));
        pnl_header.add(lbl_clientName, java.awt.BorderLayout.LINE_START);

        getContentPane().add(pnl_header, java.awt.BorderLayout.PAGE_START);

        pnl_users.setPreferredSize(new java.awt.Dimension(200, 450));
        pnl_users.setLayout(new java.awt.BorderLayout());

        pnl_menuBarBackground.setBackground(new java.awt.Color(204, 204, 204));
        pnl_menuBarBackground.setPreferredSize(new java.awt.Dimension(200, 50));

        layeredPane_menuBar.setPreferredSize(new java.awt.Dimension(200, 50));
        layeredPane_menuBar.setLayout(new java.awt.GridLayout(1, 3));

        btn_messages.setBackground(new java.awt.Color(204, 204, 204));
        btn_messages.setIcon(new javax.swing.ImageIcon("/Users/mertcankarabas/NetBeansProjects/IletisimClient/src/main/java/Icons/Message_Icon_Simple.png")); // NOI18N
        btn_messages.setSelected(true);
        btn_messages.setSelectedIcon(new javax.swing.ImageIcon("/Users/mertcankarabas/NetBeansProjects/IletisimClient/src/main/java/Icons/Message_Button.png")); // NOI18N
        btn_messages.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_messagesActionPerformed(evt);
            }
        });
        layeredPane_menuBar.add(btn_messages);

        btn_groups.setBackground(new java.awt.Color(204, 204, 204));
        btn_groups.setIcon(new javax.swing.ImageIcon("/Users/mertcankarabas/NetBeansProjects/IletisimClient/src/main/java/Icons/Group_Icon_Simple.png")); // NOI18N
        btn_groups.setSelectedIcon(new javax.swing.ImageIcon("/Users/mertcankarabas/NetBeansProjects/IletisimClient/src/main/java/Icons/Groups_Button.png")); // NOI18N
        btn_groups.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_groupsActionPerformed(evt);
            }
        });
        layeredPane_menuBar.add(btn_groups);

        btn_settings.setBackground(new java.awt.Color(204, 204, 204));
        btn_settings.setIcon(new javax.swing.ImageIcon("/Users/mertcankarabas/NetBeansProjects/IletisimClient/src/main/java/Icons/Settings_Icon_Simple.png")); // NOI18N
        btn_settings.setSelectedIcon(new javax.swing.ImageIcon("/Users/mertcankarabas/NetBeansProjects/IletisimClient/src/main/java/Icons/Settings_Icon.png")); // NOI18N
        btn_settings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_settingsActionPerformed(evt);
            }
        });
        layeredPane_menuBar.add(btn_settings);

        javax.swing.GroupLayout pnl_menuBarBackgroundLayout = new javax.swing.GroupLayout(pnl_menuBarBackground);
        pnl_menuBarBackground.setLayout(pnl_menuBarBackgroundLayout);
        pnl_menuBarBackgroundLayout.setHorizontalGroup(
            pnl_menuBarBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
            .addGroup(pnl_menuBarBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnl_menuBarBackgroundLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(layeredPane_menuBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        pnl_menuBarBackgroundLayout.setVerticalGroup(
            pnl_menuBarBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
            .addGroup(pnl_menuBarBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnl_menuBarBackgroundLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(layeredPane_menuBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        pnl_users.add(pnl_menuBarBackground, java.awt.BorderLayout.PAGE_START);

        scrollPane_chats.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        javax.swing.GroupLayout layeredPane_listsLayout = new javax.swing.GroupLayout(layeredPane_lists);
        layeredPane_lists.setLayout(layeredPane_listsLayout);
        layeredPane_listsLayout.setHorizontalGroup(
            layeredPane_listsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 198, Short.MAX_VALUE)
        );
        layeredPane_listsLayout.setVerticalGroup(
            layeredPane_listsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 545, Short.MAX_VALUE)
        );

        scrollPane_chats.setViewportView(layeredPane_lists);

        javax.swing.GroupLayout pnl_chatLayout = new javax.swing.GroupLayout(pnl_chat);
        pnl_chat.setLayout(pnl_chatLayout);
        pnl_chatLayout.setHorizontalGroup(
            pnl_chatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollPane_chats)
        );
        pnl_chatLayout.setVerticalGroup(
            pnl_chatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollPane_chats)
        );

        pnl_users.add(pnl_chat, java.awt.BorderLayout.CENTER);

        getContentPane().add(pnl_users, java.awt.BorderLayout.LINE_START);

        pnl_chatBackground.setBackground(new java.awt.Color(144, 224, 239));
        pnl_chatBackground.setLayout(new java.awt.BorderLayout());

        pnl_chatHeader.setBackground(new java.awt.Color(204, 204, 204));

        lbl_chatHeaderTitle.setBackground(new java.awt.Color(255, 153, 153));
        lbl_chatHeaderTitle.setText("User Name or Group Name");

        javax.swing.GroupLayout pnl_chatHeaderLayout = new javax.swing.GroupLayout(pnl_chatHeader);
        pnl_chatHeader.setLayout(pnl_chatHeaderLayout);
        pnl_chatHeaderLayout.setHorizontalGroup(
            pnl_chatHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_chatHeaderLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(lbl_chatHeaderTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(212, Short.MAX_VALUE))
        );
        pnl_chatHeaderLayout.setVerticalGroup(
            pnl_chatHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_chatHeaderLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_chatHeaderTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                .addContainerGap())
        );

        pnl_chatBackground.add(pnl_chatHeader, java.awt.BorderLayout.PAGE_START);

        pnl_messages.setBackground(new java.awt.Color(144, 224, 239));
        pnl_messages.setPreferredSize(new java.awt.Dimension(600, 547));
        pnl_messages.setLayout(new java.awt.GridLayout(1, 0));

        scrollPane_messages.setPreferredSize(new java.awt.Dimension(600, 91));

        txta_messages.setEditable(false);
        txta_messages.setColumns(20);
        txta_messages.setRows(5);
        txta_messages.setHighlighter(null);
        txta_messages.setIgnoreRepaint(true);
        scrollPane_messages.setViewportView(txta_messages);

        pnl_messages.add(scrollPane_messages);

        pnl_chatBackground.add(pnl_messages, java.awt.BorderLayout.CENTER);

        pnl_bottomChat.setName("pnl_bottomChat"); // NOI18N
        pnl_bottomChat.setPreferredSize(new java.awt.Dimension(530, 50));
        pnl_bottomChat.setLayout(new javax.swing.BoxLayout(pnl_bottomChat, javax.swing.BoxLayout.LINE_AXIS));

        pnl_emoji.setBackground(new java.awt.Color(237, 242, 251));
        pnl_emoji.setPreferredSize(new java.awt.Dimension(50, 50));

        lbl_chooseEmoji.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_chooseEmoji.setIcon(new javax.swing.ImageIcon("/Users/mertcankarabas/NetBeansProjects/IletisimClient/src/main/java/Icons/Emoji_Button.png")); // NOI18N
        lbl_chooseEmoji.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_chooseEmojiMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lbl_chooseEmojiMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lbl_chooseEmojiMouseExited(evt);
            }
        });

        javax.swing.GroupLayout pnl_emojiLayout = new javax.swing.GroupLayout(pnl_emoji);
        pnl_emoji.setLayout(pnl_emojiLayout);
        pnl_emojiLayout.setHorizontalGroup(
            pnl_emojiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_emojiLayout.createSequentialGroup()
                .addComponent(lbl_chooseEmoji)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        pnl_emojiLayout.setVerticalGroup(
            pnl_emojiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_emojiLayout.createSequentialGroup()
                .addComponent(lbl_chooseEmoji)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pnl_bottomChat.add(pnl_emoji);

        pnl_attach.setBackground(new java.awt.Color(237, 242, 251));
        pnl_attach.setPreferredSize(new java.awt.Dimension(50, 50));

        lbl_attachMessage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_attachMessage.setIcon(new javax.swing.ImageIcon("/Users/mertcankarabas/NetBeansProjects/IletisimClient/src/main/java/Icons/Attach_Button.png")); // NOI18N
        lbl_attachMessage.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_attachMessageMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lbl_attachMessageMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lbl_attachMessageMouseExited(evt);
            }
        });

        javax.swing.GroupLayout pnl_attachLayout = new javax.swing.GroupLayout(pnl_attach);
        pnl_attach.setLayout(pnl_attachLayout);
        pnl_attachLayout.setHorizontalGroup(
            pnl_attachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_attachLayout.createSequentialGroup()
                .addComponent(lbl_attachMessage)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        pnl_attachLayout.setVerticalGroup(
            pnl_attachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_attachLayout.createSequentialGroup()
                .addComponent(lbl_attachMessage)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pnl_bottomChat.add(pnl_attach);

        txt_message.setName("txt_message"); // NOI18N
        txt_message.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_messageKeyPressed(evt);
            }
        });
        pnl_bottomChat.add(txt_message);

        pnl_sendMessage.setBackground(new java.awt.Color(237, 242, 251));
        pnl_sendMessage.setPreferredSize(new java.awt.Dimension(50, 50));

        lbl_sendMessage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_sendMessage.setIcon(new javax.swing.ImageIcon("/Users/mertcankarabas/NetBeansProjects/IletisimClient/src/main/java/Icons/Send_Button.png")); // NOI18N
        lbl_sendMessage.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_sendMessageMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lbl_sendMessageMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lbl_sendMessageMouseExited(evt);
            }
        });

        javax.swing.GroupLayout pnl_sendMessageLayout = new javax.swing.GroupLayout(pnl_sendMessage);
        pnl_sendMessage.setLayout(pnl_sendMessageLayout);
        pnl_sendMessageLayout.setHorizontalGroup(
            pnl_sendMessageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_sendMessageLayout.createSequentialGroup()
                .addComponent(lbl_sendMessage)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        pnl_sendMessageLayout.setVerticalGroup(
            pnl_sendMessageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_sendMessageLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lbl_sendMessage))
        );

        pnl_bottomChat.add(pnl_sendMessage);

        pnl_chatBackground.add(pnl_bottomChat, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(pnl_chatBackground, java.awt.BorderLayout.CENTER);

        setSize(new java.awt.Dimension(799, 627));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents


    private void lbl_sendMessageMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_sendMessageMouseClicked
        // TODO add your handling code here:
        sendMessage();
    }//GEN-LAST:event_lbl_sendMessageMouseClicked

    private void lbl_sendMessageMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_sendMessageMouseEntered
        // TODO add your handling code here:
        changeColor(pnl_sendMessage, new Color(226, 234, 252));
    }//GEN-LAST:event_lbl_sendMessageMouseEntered

    private void lbl_sendMessageMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_sendMessageMouseExited
        // TODO add your handling code here:
        changeColor(pnl_sendMessage, new Color(237, 242, 251));
    }//GEN-LAST:event_lbl_sendMessageMouseExited

    private void lbl_chooseEmojiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_chooseEmojiMouseClicked
        // TODO add your handling code here:
        emojiFrame = new Emoji(this);
        emojiFrame.setVisible(true);
    }//GEN-LAST:event_lbl_chooseEmojiMouseClicked

    private void lbl_chooseEmojiMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_chooseEmojiMouseEntered
        // TODO add your handling code here:
        changeColor(pnl_emoji, new Color(226, 234, 252));
    }//GEN-LAST:event_lbl_chooseEmojiMouseEntered

    private void lbl_chooseEmojiMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_chooseEmojiMouseExited
        // TODO add your handling code here:
        changeColor(pnl_emoji, new Color(237, 242, 251));
    }//GEN-LAST:event_lbl_chooseEmojiMouseExited

    private int pX;
    private int pY;
    private void pnl_headerMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnl_headerMouseDragged
        // TODO add your handling code here:        
        this.setLocation(this.getLocation().x + evt.getX() - pX, this.getLocation().y + evt.getY() - pY);
    }//GEN-LAST:event_pnl_headerMouseDragged

    private void pnl_headerMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnl_headerMousePressed
        // TODO add your handling code here:
        pX = evt.getX();
        pY = evt.getY();
    }//GEN-LAST:event_pnl_headerMousePressed

    private void lbl_closeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_closeMouseClicked
        // TODO add your handling code here:
        isClosed = true;
        sendMessage();
    }//GEN-LAST:event_lbl_closeMouseClicked

    private void lbl_closeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_closeMouseEntered
        // TODO add your handling code here:
        changeColor(pnl_close, new Color(6, 90, 96));
    }//GEN-LAST:event_lbl_closeMouseEntered

    private void lbl_closeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_closeMouseExited
        // TODO add your handling code here:
        changeColor(pnl_close, new Color(0, 100, 102));
    }//GEN-LAST:event_lbl_closeMouseExited

    private void lbl_maxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_maxMouseClicked
        // TODO add your handling code here:
        if (this.getExtendedState() != ClientMainFrame.MAXIMIZED_BOTH) {
            this.setExtendedState(ClientMainFrame.MAXIMIZED_BOTH);
        } else {
            this.setExtendedState(ClientMainFrame.NORMAL);
        }
    }//GEN-LAST:event_lbl_maxMouseClicked

    private void lbl_maxMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_maxMouseEntered
        // TODO add your handling code here:
        changeColor(pnl_max, new Color(6, 90, 96));
    }//GEN-LAST:event_lbl_maxMouseEntered

    private void lbl_maxMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_maxMouseExited
        // TODO add your handling code here:
        changeColor(pnl_max, new Color(0, 100, 102));
    }//GEN-LAST:event_lbl_maxMouseExited

    private void btn_settingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_settingsActionPerformed
        // TODO add your handling code here:
        btn_messages.setSelected(false);
        btn_groups.setSelected(false);
        btn_settings.setSelected(true);
        this.isPeople = false;
        this.isGroup = false;
        this.isSettings = true;
        showSettings();
    }//GEN-LAST:event_btn_settingsActionPerformed

    private void btn_groupsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_groupsActionPerformed
        // TODO add your handling code here:
        btn_messages.setSelected(false);
        btn_groups.setSelected(true);
        btn_settings.setSelected(false);
        this.isPeople = false;
        this.isGroup = true;
        this.isSettings = false;
        showGroups();
    }//GEN-LAST:event_btn_groupsActionPerformed

    private void btn_messagesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_messagesActionPerformed
        // TODO add your handling code here:
        btn_messages.setSelected(true);
        btn_groups.setSelected(false);
        btn_settings.setSelected(false);
        this.isPeople = true;
        this.isGroup = false;
        this.isSettings = false;
        //showMessages();
    }//GEN-LAST:event_btn_messagesActionPerformed

    private void lbl_attachMessageMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_attachMessageMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_lbl_attachMessageMouseClicked

    private void lbl_attachMessageMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_attachMessageMouseEntered
        // TODO add your handling code here:
        changeColor(pnl_attach, new Color(226, 234, 252));
    }//GEN-LAST:event_lbl_attachMessageMouseEntered

    private void lbl_attachMessageMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_attachMessageMouseExited
        // TODO add your handling code here:
        changeColor(pnl_attach, new Color(237, 242, 251));
    }//GEN-LAST:event_lbl_attachMessageMouseExited

    private void txt_messageKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_messageKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            sendMessage();
        }
    }//GEN-LAST:event_txt_messageKeyPressed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
        this.client.StopClient();
    }//GEN-LAST:event_formWindowClosed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ClientMainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ClientMainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ClientMainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ClientMainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ClientMainFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private Forms.MyButton btn_groups;
    private Forms.MyButton btn_messages;
    private Forms.MyButton btn_settings;
    private javax.swing.JLayeredPane layeredPane_lists;
    private javax.swing.JLayeredPane layeredPane_menuBar;
    private javax.swing.JLabel lbl_attachMessage;
    private javax.swing.JLabel lbl_chatHeaderTitle;
    private javax.swing.JLabel lbl_chooseEmoji;
    private javax.swing.JLabel lbl_clientName;
    private javax.swing.JLabel lbl_close;
    private javax.swing.JLabel lbl_max;
    private javax.swing.JLabel lbl_sendMessage;
    private javax.swing.JPanel pnl_attach;
    private javax.swing.JPanel pnl_bottomChat;
    private javax.swing.JPanel pnl_chat;
    private javax.swing.JPanel pnl_chatBackground;
    private javax.swing.JPanel pnl_chatHeader;
    private javax.swing.JPanel pnl_close;
    private javax.swing.JPanel pnl_closeMaxMin;
    private javax.swing.JPanel pnl_emoji;
    private javax.swing.JPanel pnl_header;
    private javax.swing.JPanel pnl_max;
    private javax.swing.JPanel pnl_menuBarBackground;
    private javax.swing.JPanel pnl_messages;
    private javax.swing.JPanel pnl_sendMessage;
    private javax.swing.JPanel pnl_users;
    private javax.swing.JScrollPane scrollPane_chats;
    private javax.swing.JScrollPane scrollPane_messages;
    private javax.swing.JTextField txt_message;
    private javax.swing.JTextArea txta_messages;
    // End of variables declaration//GEN-END:variables
}
