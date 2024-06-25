
--- school 테이블에 중복 방지를 위한 insert

--- region 테이블에 중복 방지를 위한 insert
INSERT IGNORE INTO region (region_name) VALUES ('서울특별시'), ('경기도'), ('강원도'), ('충청북도'), ('충청남도'), ('전라북도'), ('전라남도'), ('경상북도'), ('경상남도'), ('제주특별자치도');

