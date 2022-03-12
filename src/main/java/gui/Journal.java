package gui;

import impl.JournalModelImpl;
import journal.JournalEntry;
import journal.JournalModel;
import journal.parser.JournalEntryParser;
import planner.PlanEntry;
import single.DefaultSingleDocumentModel;
import single.SingleDocumentModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.Serial;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Journal extends JFrame {

    private JournalModel model;
    private SingleDocumentModel documentModel;
    private int currentTab;

    private JTabbedPane pane;
    private JPanel panelEdit;

    public Journal() {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocation(100, 100);
        setSize(600, 600);

        initModel();
        initDocumentModel();
        initGUI();
    }

    private void initGUI() {
        Container container = getContentPane();

        initPane();

        container.add(pane);
    }

    private void initPane() {
        pane = new JTabbedPane();

        initPanelEdit();
        JPanel panelQueries = new JPanel();
        JPanel panelGraphs = new JPanel();

        pane.addTab("Edit", panelEdit);
        pane.addTab("Queries", panelQueries);
        pane.addTab("Graphs", panelGraphs);
    }

    private void initPanelEdit() {
        panelEdit = new JPanel();
        panelEdit.setLayout(new BorderLayout());

        JButton browseButton = new JButton(openFileAction);
        browseButton.setText("Browse");

        panelEdit.add(browseButton, BorderLayout.NORTH);
    }

    private void initDocumentModel() {
        documentModel = new DefaultSingleDocumentModel(null);
    }

    private void initModel() {
        List<JournalEntry> entries;
        List<PlanEntry> plans = new ArrayList<>();

        try {
            entries = JournalEntryParser.parse();
            model = new JournalModelImpl(entries, plans);
        } catch (Exception e) {
            System.out.println("Error while parsing: " + e.getLocalizedMessage());
        }
    }

    private final Action openFileAction = new AbstractAction() {
        @Serial
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fc = new JFileChooser();
            fc.setDialogTitle("Open file");

            if(fc.showOpenDialog(Journal.this) != JFileChooser.APPROVE_OPTION) {
                return;
            }

            File fileName = fc.getSelectedFile();
            Path filePath = fileName.toPath();

            if(!Files.isReadable(filePath)) {
                JOptionPane.showMessageDialog(
                        Journal.this,
                        "File " + fileName.getAbsolutePath() + " does not exist!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            documentModel.loadDocument(filePath);
            panelEdit.add(documentModel.getEntryComponent());

            panelEdit.repaint();
            panelEdit.revalidate();
        }
    };

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Journal().setVisible(true));
    }

}
