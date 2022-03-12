package single;


import journal.JournalEntry;

import javax.swing.*;
import java.nio.file.Path;

public interface SingleDocumentModel {
    JPanel getEntryComponent();
    void loadDocument(Path path);
    Path getFilePath();
    void setFilePath(Path path);
    boolean isModified();
    JournalEntry getCurrentEntry();
    void setModified(boolean modified);
    void addSingleDocumentListener(SingleDocumentListener l);
    void removeSingleDocumentListener(SingleDocumentListener l);
}