package single;

import journal.EventType;
import journal.JournalEntry;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

public class DefaultSingleDocumentModel implements SingleDocumentModel {

    private Path filePath;
    private JPanel journalPanel;
    private boolean modified;
    private List<SingleDocumentListener> listeners;
    private JournalEntry currentEntry;


    public DefaultSingleDocumentModel(Path filePath) {
        this.filePath = filePath;
        journalPanel = new JPanel();
        listeners = new ArrayList<>();

        this.addSingleDocumentListener(new SingleDocumentListener() {
            @Override
            public void documentModifyStatusUpdated(SingleDocumentModel model) {
                DefaultSingleDocumentModel.this.modified = model.isModified();
            }

            @Override
            public void documentFilePathUpdated(SingleDocumentModel model) {
                DefaultSingleDocumentModel.this.filePath = model.getFilePath();;
                updateJPanel();
            }
        });
    }

    public void updateJPanel() {
        journalPanel.setLayout(new GridLayout(6, 2));

        JLabel rating = new JLabel("Rating:");
        JTextArea ratingArea = new JTextArea();
        ratingArea.setText(String.valueOf(currentEntry.getRating()));

        Calendar calendar = new GregorianCalendar();

        JLabel start = new JLabel("Start:");;
        calendar.setTime(currentEntry.getStart());
        JTextArea startArea = new JTextArea(String.format("%02d:%02d", calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE)));

        JLabel end = new JLabel("End:");
        calendar.setTime(currentEntry.getEnd());
        JTextArea endArea = new JTextArea(String.format("%02d:%02d", calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE)));

        JLabel events = new JLabel("Events:");
        JTextArea eventsArea = new JTextArea(currentEntry.getEvents());
        eventsArea.setPreferredSize(new Dimension(eventsArea.getMaximumSize().width, 200));

        JLabel spent = new JLabel("Spent:");
        JTextArea spentArea = new JTextArea(String.valueOf(currentEntry.getSpent()));

        JLabel type = new JLabel("Type:");
        JTextArea typeArea = new JTextArea(currentEntry.getType().toString());

        journalPanel.add(rating, 0);
        journalPanel.add(ratingArea, 1);
        journalPanel.add(start, 2);
        journalPanel.add(startArea, 3);
        journalPanel.add(end, 4);
        journalPanel.add(endArea, 5);
        journalPanel.add(events, 6);
        journalPanel.add(eventsArea, 7);
        journalPanel.add(spent, 8);
        journalPanel.add(spentArea, 9);
        journalPanel.add(type, 10);
        journalPanel.add(typeArea, 11);

        journalPanel.repaint();
        journalPanel.revalidate();
    }

    @Override
    public void loadDocument(Path path) {

        try {
            String textContent = Files.readString(path);
            String fileName = path.getFileName().toString();

            String[] entryParameters = textContent.split("\n");

            Date dateStart = new SimpleDateFormat("HH:mm").parse(entryParameters[3]);

            currentEntry = new JournalEntry(
                    Date.from(LocalDate.parse(fileName, DateTimeFormatter
                                    .ofPattern("dd-MM-yyyy"))
                                    .atStartOfDay()
                                    .atZone(ZoneId.systemDefault())
                                    .toInstant()),
                    Double.parseDouble(entryParameters[1]),
                    new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(fileName + " " + entryParameters[3]),
                    new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(fileName + " " + entryParameters[5]),
                    entryParameters[7],
                    Integer.parseInt(entryParameters[9]),
                    EventType.parse(entryParameters[11])
            );
            notifyFilePathUpdated();

        } catch (IOException exc) {
            System.out.println(exc.getLocalizedMessage());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public JPanel getEntryComponent() {
        return journalPanel;
    }

    @Override
    public Path getFilePath() {
        return filePath;
    }

    @Override
    public void setFilePath(Path path) {
        this.filePath = path;
        notifyFilePathUpdated();
    }

    @Override
    public boolean isModified() {
        return modified;
    }

    @Override
    public void setModified(boolean modified) {
        this.modified = modified;
        notifyModified();
    }

    @Override
    public JournalEntry getCurrentEntry() {
        return currentEntry;
    }

    @Override
    public void addSingleDocumentListener(SingleDocumentListener l) {
        listeners.add(l);
    }

    @Override
    public void removeSingleDocumentListener(SingleDocumentListener l) {
        listeners.remove(l);
    }

    private void notifyModified() {
        for (SingleDocumentListener l : listeners) {
            l.documentModifyStatusUpdated(this);
        }
    }

    private void notifyFilePathUpdated() {
        for (SingleDocumentListener l : listeners) {
            l.documentFilePathUpdated(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof SingleDocumentModel other)) {
            return false;
        }

        return other.getFilePath().equals(this.filePath);
    }


}

