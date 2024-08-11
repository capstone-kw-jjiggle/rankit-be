package gitbal.backend.api.info.service;

import gitbal.backend.domain.region.Region;
import gitbal.backend.domain.region.application.repository.RegionRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegionInfoService implements InfoService {

    private final RegionRepository regionRepository;


    @Override
    public List<String> findAllList() {
        return regionRepository.findAll().stream()
            .map(Region::getRegionName)
            .toList();
    }
}
