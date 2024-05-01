package gitbal.backend.domain;

import gitbal.backend.entity.dto.PageInfoDto;
import org.springframework.data.domain.Page;

public class PageCalculator {

    private static final int PAGE_RANGE = 5;

    public static PageInfoDto calculatePageInfo(Page page) {
        int currentPage = page.getNumber() + 1;
        int totalPage = page.getTotalPages();
        int startPage = Math.max(1, currentPage - 2);
        int endPage = Math.min(currentPage + 2, totalPage);

        if (currentPage - startPage < PAGE_RANGE / 2) {
            endPage = startPage + (PAGE_RANGE - 1);
        }
        if (endPage - currentPage < PAGE_RANGE / 2) {
            startPage = endPage - (PAGE_RANGE - 1);
        }

        return new PageInfoDto(currentPage, startPage, endPage, totalPage);
    }

}
