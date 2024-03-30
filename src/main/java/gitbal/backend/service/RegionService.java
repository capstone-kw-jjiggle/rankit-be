package gitbal.backend.service;

import gitbal.backend.entity.Region;
import gitbal.backend.exception.JoinException;
import gitbal.backend.repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegionService {

    private final RegionRepository regionRepository;


    public Region findByRegionName(String regionName) {
        return regionRepository.findByRegionName(regionName)
            .orElseThrow(() -> new JoinException("회원가입 중 지역이름에 대한 정보를 db에서 읽어오는데 실패했습니다."));
    }
}
