package single;

public interface SingleDocumentListener {
    void documentModifyStatusUpdated(SingleDocumentModel model);
    void documentFilePathUpdated(SingleDocumentModel model);
}