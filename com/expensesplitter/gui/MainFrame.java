package com.expensesplitter.gui;

import com.expensesplitter.model.Group;
import com.expensesplitter.service.SplitService;
import com.expensesplitter.service.SplitServiceImpl;
import com.expensesplitter.ui.IconFactory;
import com.expensesplitter.ui.UITheme;
import com.expensesplitter.util.DataManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;

public class MainFrame extends JFrame {
    private Group group;
    private final SplitService splitService;
    private final MemberPanel memberPanel;
    private final ExpensePanel expensePanel;
    private final SummaryPanel summaryPanel;

    public MainFrame() {
        UITheme.styleOptionPane();
        String groupName = JOptionPane.showInputDialog(null, "Enter a name for your group:", "New Group", JOptionPane.PLAIN_MESSAGE);
        this.group = new Group((groupName == null || groupName.trim().isEmpty()) ? "My Group" : groupName.trim());
        this.splitService = new SplitServiceImpl();

        setTitle("Splitter");
        setSize(1100, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel contentPane = (JPanel) getContentPane();
        contentPane.setBackground(UITheme.APP_BACKGROUND);
        contentPane.setLayout(new BorderLayout(15, 15));
        contentPane.setBorder(UITheme.PADDING);

        memberPanel = new MemberPanel(this);
        expensePanel = new ExpensePanel(this);
        summaryPanel = new SummaryPanel();

        JSplitPane topSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, memberPanel, expensePanel);
        configureSplitPane(topSplitPane);
        
        JSplitPane mainSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, topSplitPane, summaryPanel);
        configureSplitPane(mainSplitPane);

        add(mainSplitPane, BorderLayout.CENTER);
        createMenuBar();
        
        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                topSplitPane.setDividerLocation(0.5);
                mainSplitPane.setDividerLocation(0.55);
                removeComponentListener(this);
            }
        });
    }

    private void configureSplitPane(JSplitPane splitPane) {
        splitPane.setBorder(null);
        splitPane.setDividerSize(10);
        splitPane.setUI(new javax.swing.plaf.basic.BasicSplitPaneUI() {
            public javax.swing.plaf.basic.BasicSplitPaneDivider createDefaultDivider() {
                return new javax.swing.plaf.basic.BasicSplitPaneDivider(this) {
                    public void setBorder(javax.swing.border.Border b) {}
                    @Override
                    public void paint(Graphics g) {
                        g.setColor(UITheme.APP_BACKGROUND);
                        g.fillRect(0, 0, getWidth(), getHeight());
                    }
                };
            }
        });
    }
    
    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(UITheme.PANEL_BACKGROUND);
        menuBar.setBorder(null);

        JMenu fileMenu = new JMenu("File");
        fileMenu.setForeground(UITheme.TEXT_COLOR);
        fileMenu.setFont(UITheme.FONT_REGULAR);

        JMenuItem saveItem = createMenuItem("Save", IconFactory.IconType.SAVE);
        saveItem.addActionListener(e -> saveGroup());

        JMenuItem loadItem = createMenuItem("Load", IconFactory.IconType.LOAD);
        loadItem.addActionListener(e -> loadGroup());

        JMenuItem exitItem = createMenuItem("Exit", IconFactory.IconType.EXIT);
        exitItem.addActionListener(e -> System.exit(0));

        fileMenu.add(saveItem);
        fileMenu.add(loadItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
    }
    
    private JMenuItem createMenuItem(String text, IconFactory.IconType iconType) {
        JMenuItem menuItem = new JMenuItem(text, IconFactory.createIcon(iconType, 20, 20));
        menuItem.setBackground(UITheme.PANEL_BACKGROUND);
        menuItem.setForeground(UITheme.TEXT_COLOR);
        menuItem.setFont(UITheme.FONT_REGULAR);
        menuItem.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        return menuItem;
    }

    private void saveGroup() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Group Data");
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            String path = file.getAbsolutePath().endsWith(".esg") ? file.getAbsolutePath() : file.getAbsolutePath() + ".esg";
            new DataManager().saveData(group, path);
        }
    }

    private void loadGroup() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Load Group Data");
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            Group loadedGroup = new DataManager().loadData(fileChooser.getSelectedFile().getAbsolutePath());
            if (loadedGroup != null) {
                this.group = loadedGroup;
                refreshAllPanels();
            }
        }
    }
    
    public void refreshAllPanels() {
        setTitle("Splitter - " + group.getName());
        memberPanel.updateMemberList();
        expensePanel.updatePayerAndSplitMembers();
        summaryPanel.updateSummary(group, splitService);
    }

    public Group getGroup() { return group; }
}

