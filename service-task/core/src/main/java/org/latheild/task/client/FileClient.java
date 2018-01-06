package org.latheild.task.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import static org.latheild.apiutils.constant.URLRequestSetting.PRODUCE_JSON;
import static org.latheild.file.api.constant.FileURL.CHECK_FILE_EXIST_URL;

@FeignClient(name = "file-service")
public interface FileClient {
    @RequestMapping(value = CHECK_FILE_EXIST_URL, method = RequestMethod.GET, produces =  PRODUCE_JSON)
    boolean checkFileExistence(
            @RequestParam(value = "fileId") String fileId
    );
}
