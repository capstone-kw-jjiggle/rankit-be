package gitbal.backend.api.regionPage.service;


import gitbal.backend.api.regionPage.dto.RegionListPageResponseDto;
import gitbal.backend.api.regionPage.dto.UserInfoByRegion;
import gitbal.backend.api.regionPage.dto.UserPageListByRegionResponseDto;
import gitbal.backend.domain.region.Region;
import gitbal.backend.domain.region.application.repository.RegionRepository;
import gitbal.backend.domain.user.User;
import gitbal.backend.api.regionPage.dto.MyRegionInfoResponseDto;
import gitbal.backend.api.regionPage.dto.RegionListDto;
import gitbal.backend.global.exception.NotFoundRegionException;
import gitbal.backend.global.exception.NotFoundUserException;
import gitbal.backend.global.exception.NotLoginedException;
import gitbal.backend.domain.user.UserRepository;
import gitbal.backend.global.exception.PageOutOfRangeException;
import gitbal.backend.global.exception.RegionRankPageUserInfoByRegionException;
import gitbal.backend.global.security.CustomUserDetails;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Slf4j
public class RegionRankService {

    private static final int PAGE_SIZE = 14;
    private final RegionRepository regionRepository;
    private final UserRepository userRepository;
    private final int page = 1;

    public ResponseEntity<RegionListPageResponseDto<RegionListDto>> getRegionList() {
        Sort sort = Sort.by("score").descending();
        Pageable pageable = PageRequest.of(page - 1, 10, sort);
        Page<Region> regionPage = regionRepository.findAll(pageable);

        List<Region> sortedRegion = regionPage.stream()
                .sorted(Comparator.comparing(Region::getScore).reversed())
                .toList();


        List<RegionListDto> regionListDtos = convertListToDto(sortedRegion);

        RegionListPageResponseDto<RegionListDto> RegionList = RegionListPageResponseDto.<RegionListDto>withALl()
                .regionList(regionListDtos)
                .build();

        return ResponseEntity.ok(RegionList);
    }

    private List<RegionListDto> convertListToDto(List<Region> regions) {
        List<RegionListDto> regionListDtos = new ArrayList<>();

        for(int index = 0; index < regions.size(); index++) {
            Region region = regions.get(index);
            regionListDtos.add(new RegionListDto(
                    region.getRegionName(),
                    region.getScore(),
                    index+1
            ));
        }
        return regionListDtos;
    }

    @Transactional(readOnly = true)
    public MyRegionInfoResponseDto getMyRegionInfo(Authentication authentication) {
        if (authentication == null) {
            throw new NotLoginedException("로그인 후 요청해주세요");
        }
        CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
        String username = principal.getNickname();
        User user = userRepository.findByNickname(username).orElseThrow(
                NotFoundUserException::new
        );
        Region region = user.getRegion();
        if (Objects.isNull(region)) return MyRegionInfoResponseDto.of(0, null);
        return MyRegionInfoResponseDto.of(findRegionRank(region.getRegionName()), region);
    }

    //TODO: 이후에 school, region 관련하여서 더 생각해보기
    private int findRegionRank(String regionName) {
        List<Region> regions = regionRepository.findAll(Sort.by("score").descending());
        for (int i = 0; i < regions.size(); i++) {
            Region region = regions.get(i);
            if (region.getRegionName().equals(regionName)) return i + 1;
        }
        throw new NotFoundRegionException();
    }


    @Transactional(readOnly = true)
    public UserPageListByRegionResponseDto getUserListByRegionName(int page, String regionName) {
        try {
            Region region = regionRepository.findByRegionName(regionName).orElseThrow(NotFoundRegionException::new);
            Pageable pageable = initpageable(page, "score");
            Page<User> userByRegionName = userRepository.findUserByRegion_RegionName(regionName,
                    pageable);
            log.info("userByRegionName: {}", userByRegionName.getTotalPages());
            if (userByRegionName.getTotalPages() < page)
                throw new PageOutOfRangeException();
            List<UserInfoByRegion> userInfoByRegions = convertPageByUserInfoByRegion(
                    userByRegionName, page);
            return buildUserPageListByRegionResponseDto(page, userInfoByRegions, userByRegionName);
        } catch (Exception e) {
            if (Objects.isNull(e.getMessage()))
                throw new RegionRankPageUserInfoByRegionException("지역 랭킹 페이지 유저 정보 조회 중 오류가 발생했습니다.");
            throw new RegionRankPageUserInfoByRegionException(e.getMessage());
        }
    }


    private List<UserInfoByRegion> convertPageByUserInfoByRegion(Page<User> userBySchoolName, int page) {
        List<User> users = userBySchoolName.get().toList();
        List<UserInfoByRegion> userInfoByRegions= new ArrayList<>();
        int startNum = (page-1) * PAGE_SIZE +1;
        for(int index=startNum; index<users.size()+startNum; index++){
            User user = users.get(index-startNum);
            userInfoByRegions.add(convertToUserInfoByRegion(user, index));
        }

        return userInfoByRegions;
    }


    private UserInfoByRegion convertToUserInfoByRegion(User user, int rank) {
        return new UserInfoByRegion(
                user.getNickname(),
                user.getProfile_img(),
                user.getScore(),
                rank
        );
    }


    private Pageable initpageable(int page, String sortProperties) {
        Sort sort = Sort.by(sortProperties).descending();
        return PageRequest.of(page - 1, PAGE_SIZE, sort);
    }


    private UserPageListByRegionResponseDto buildUserPageListByRegionResponseDto(int page,
                                                                                 List<UserInfoByRegion> userInfoByRegions, Page<User> userBySchoolName) {
        return UserPageListByRegionResponseDto.withAll()
                .userInfoByRegion(userInfoByRegions)
                .page(page)
                .total(userBySchoolName.getTotalElements())
                .build();
    }


}
