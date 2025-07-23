package org.fadak.selp.selpbackend.application.util;

import java.io.InputStream;
import org.fadak.selp.selpbackend.domain.constant.FileDir;

public interface FileUtil {

    String getPresignedUrl(FileDir fileDir, String objectName);

    void upload(
        FileDir fileDir,
        String objectName,
        InputStream content,
        long size,
        String contentType
    );

    void delete(FileDir fileDir, String objectName);

    String modify(
        FileDir fileDir,
        String objectName,
        InputStream content,
        long size,
        String contentType
    );
}
