package gitbal.backend.global.util;

import gitbal.backend.domain.dto.PageInfoDto;
import org.springframework.data.domain.Page;

public class PageCalculator {

    private static final int PAGE_RANGE = 5;
    private static final int MINIMUM_MEDIAN_PAGE=3;

    public static PageInfoDto calculatePageInfo(Page page) {
        int currentPage = page.getNumber() + 1;
        int totalPage = page.getTotalPages();
        int startPage = Math.max(1, currentPage - PAGE_RANGE/2);
        int endPage = Math.min(currentPage + PAGE_RANGE/2, totalPage);

        if(isCurrentPageLessthanMinimumMedianPage(currentPage, totalPage))
            endPage=PAGE_RANGE;
        if(isCurrentPageLargerthanMaximumMedianPage(currentPage, totalPage))
            startPage=totalPage-PAGE_RANGE+1;


        return new PageInfoDto(currentPage, startPage, endPage, totalPage);
    }

    private static boolean isCurrentPageLargerthanMaximumMedianPage(int currentPage, int totalPage) {
        return currentPage > totalPage - PAGE_RANGE / 2 && totalPage >= PAGE_RANGE;
    }

    private static boolean isCurrentPageLessthanMinimumMedianPage(int currentPage, int totalPage) {
        return currentPage < MINIMUM_MEDIAN_PAGE && totalPage >= PAGE_RANGE;
    }

}
