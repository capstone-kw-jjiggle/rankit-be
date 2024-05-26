package gitbal.backend.api.info.controller;

import gitbal.backend.api.info.service.RegionInfoService;
import gitbal.backend.api.info.service.SchoolInfoService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/v1/info")
@RequiredArgsConstructor
public class InfoController {
    
    private final RegionInfoService regionInfoService;
    private final SchoolInfoService schoolInfoService;
    @GetMapping("/school")
    public List<String> findAllSchoolName(){
        return schoolInfoService.findAllList();
    }


    @GetMapping("/region")
    public List<String> findAllRegionName(){
        return regionInfoService.findAllList();
    }

}
