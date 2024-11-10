package gitbal.backend.api.userPage.dto;

public record ModifySchoolRequestDto(String modifySchoolName, String email, String certificateCode) {

    public static ModifySchoolRequestDto of(String modifySchoolName,String email,  String certificateCode) {
        return new ModifySchoolRequestDto(modifySchoolName, email, certificateCode);
    }

}
