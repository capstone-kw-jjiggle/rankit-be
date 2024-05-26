package gitbal.backend.api.info.service;

import gitbal.backend.domain.region.Region;
import gitbal.backend.domain.region.RegionRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class RegionInfoService implements InfoService {

    private final RegionRepository regionRepository;

    public RegionInfoService(RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }

    @Override
    public List<String> findAllList() {
        return regionRepository.findAll().stream()
            .map(Region::getRegionName)
            .toList();
    }
}
