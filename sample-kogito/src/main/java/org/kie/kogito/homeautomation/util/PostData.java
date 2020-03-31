package org.kie.kogito.homeautomation.util;

public class PostData {

    private final String name;
    private final String filename;
    private final String content;
    private final String mediaType;

    public static PostData of(String name, String filename, String content, String mediaType) {
        return new PostData(name, filename, content, mediaType);
    }

    public PostData(String name, String filename, String content, String mediaType) {
        this.name = name;
        this.filename = filename;
        this.content = content;
        this.mediaType = mediaType;
    }

    public String getName() {
        return name;
    }

    public String getFilename() {
        return filename;
    }

    public String getContent() {
        return content;
    }

    public String getMediaType() {
        return mediaType;
    }
}
