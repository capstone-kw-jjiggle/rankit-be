package gitbal.backend.api.regionPage.service;


import gitbal.backend.api.regionPage.dto.RegionListPageResponseDto;
import gitbal.backend.api.regionPage.dto.UserInfoByRegion;
import gitbal.backend.api.regionPage.dto.UserPageListByRegionResponseDto;
import gitbal.backend.api.schoolPage.dto.UserInfoBySchool;
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
import gitbal.backend.global.exception.SchoolRankPageUserInfoBySchoolException;
import gitbal.backend.global.security.CustomUserDetails;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//TODO : 이후에 regionChangeScore 반영해야함!


@Service
@RequiredArgsConstructor
public class RegionRankService {

    private static final int PAGE_SIZE = 10;
    private final RegionRepository regionRepository;
    private final UserRepository userRepository;
    private final int page = 1;

    public ResponseEntity<RegionListPageResponseDto<RegionListDto>> getRegionList() {
        Sort sort = Sort.by("score").descending();
        Pageable pageable = PageRequest.of(page - 1, 10, sort);
        Page<Region> regionPage = regionRepository.findAll(pageable);

        List<RegionListDto> regionDtoList = regionPage.stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());

        // 3. PageResponseDto 생성
        RegionListPageResponseDto<RegionListDto> RegionList = RegionListPageResponseDto.<RegionListDto>withALl()
            .regionList(regionDtoList)
            .build();

        return ResponseEntity.ok(RegionList);
    }

    private RegionListDto convertToDto(Region region) {
        return new RegionListDto(
            region.getRegionName(),
            region.getScore()
        );
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
        if(Objects.isNull(region)) return MyRegionInfoResponseDto.of(0, null);
        return MyRegionInfoResponseDto.of(findRegionRank(region.getRegionName()), region);
    }

    //TODO: 이후에 school, region 관련하여서 더 생각해보기
    private int findRegionRank(String regionName){
        List<Region> regions = regionRepository.findAll(Sort.by("score").descending());
        for (int i = 0; i < regions.size(); i++) {
            Region region = regions.get(i);
            if(region.getRegionName().equals(regionName))  return i+1;
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
            if (userByRegionName.getTotalPages() < page)
                throw new PageOutOfRangeException();
            List<UserInfoByRegion> userInfoByRegions = convertPageByUserInfoByRegion(
                userByRegionName);
            return buildUserPageListByRegionResponseDto(page, userInfoByRegions, userByRegionName);
        }catch (Exception e){
            if(Objects.isNull(e.getMessage()))
                throw new RegionRankPageUserInfoByRegionException("지역 랭킹 페이지 유저 정보 조회 중 오류가 발생했습니다.");
            throw new RegionRankPageUserInfoByRegionException(e.getMessage());
        }
    }



    private List<UserInfoByRegion> convertPageByUserInfoByRegion(Page<User> userBySchoolName) {
        return userBySchoolName.stream().
            map(this::convertToUserInfoByRegion)
            .toList();
    }


    private UserInfoByRegion convertToUserInfoByRegion(User user){
        return new UserInfoByRegion(
            user.getNickname(),
            user.getScore()
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
