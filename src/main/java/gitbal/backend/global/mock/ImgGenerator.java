package gitbal.backend.global.mock;

public class ImgGenerator {
    private static String IMG_BASE_URL = "https://raw.githubusercontent.com/jamooooong/rankit_mock_img/refs/heads/main/jpg/rankit_mock_";
    public String imgGenerator(int index){
        StringBuilder sb = new StringBuilder();
        if(index < 10)
            sb.append(IMG_BASE_URL).append("00").append(index).append(".jpg");
        else if(index < 100)
            sb.append(IMG_BASE_URL).append("0").append(index).append(".jpg");
        else
            sb.append(IMG_BASE_URL).append(index).append(".jpg");
        return sb.toString();
    }

}
