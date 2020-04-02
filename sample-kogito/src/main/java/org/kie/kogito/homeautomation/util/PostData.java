package org.kie.kogito.homeautomation.util;

public class PostData {

    private final String name;
    private final String filename;
    private final Object content;
    private final String contentType;

    public static PostData of(String name, String filename, Object content, String contentType) {
        return new PostData(name, filename, content, contentType);
    }

    public static PostData of(Object content, String contentType) {
        return new PostData(null, null, content, contentType);
    }

    public PostData(String name, String filename, Object content, String contentType) {
        this.name = name;
        this.filename = filename;
        this.content = content;
        this.contentType = contentType;
    }

    public String getName() {
        return name;
    }

    public String getFilename() {
        return filename;
    }

    public Object getContent() {
        return content;
    }

    public String getContentType() {
        return contentType;
    }
}
