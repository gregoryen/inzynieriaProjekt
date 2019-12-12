package com.engineering.shop.fileUpload.payload;

import lombok.Data;

@Data
public class UploadFileResponse {
    private String fileId;
    private String fileDownloadUri;
    private String fileType;
    private long size;

    public UploadFileResponse(String fileId, String fileDownloadUri, String fileType, long size) {
        this.fileId = fileId;
        this.fileDownloadUri = fileDownloadUri;
        this.fileType = fileType;
        this.size = size;
    }

    // Getters and Setters (Omitted for brevity)
}
