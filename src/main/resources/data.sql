/*
DROP TABLE IF EXISTS shedlock;


CREATE TABLE shedlock(name VARCHAR(64) NOT NULL, lock_until TIMESTAMP(3) NOT NULL,
    locked_at TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3), locked_by VARCHAR(255) NOT NULL, PRIMARY KEY (name));

SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS school;
create table school (
                        school_rank integer default 0 not null,
                        changed_score bigint default 0,
                        created_at datetime(6),
                        modified_at datetime(6),
                        school_id bigint not null auto_increment,
                        score bigint default 0,
                        school_name varchar(40),
                        primary key (school_id)
);

INSERT INTO school (school_name, created_at) VALUES ('가야대학교', NOW()), ('가천길대학교', NOW()), ('가천대학교', NOW()), ('가천의과학대학교', NOW()), ('가톨릭관동대학교', NOW()), ('가톨릭대학교', NOW()), ('가톨릭상지대학교', NOW()), ('감리교신학대학교', NOW()), ('강남대학교', NOW()), ('강동대학교', NOW()), ('강릉영동대학교', NOW()), ('강릉원주대학교', NOW()), ('강원관광대학교', NOW()), ('강원대학교', NOW()), ('강원도립대학교', NOW()), ('거제대학교', NOW()), ('건국대학교', NOW()), ('건국대학교(글로컬)', NOW()), ('건양대학교', NOW()), ('건양사이버대학교', NOW()), ('경기과학기술대학교', NOW()), ('경기대학교', NOW()), ('경남과학기술대학교', NOW()), ('경남대학교', NOW()), ('경남도립거창대학교', NOW()), ('경남도립남해대학교', NOW()), ('경남정보대학교', NOW()), ('경동대학교', NOW()), ('경민대학교', NOW()), ('경복대학교', NOW()), ('경북과학대학교', NOW()), ('경북대학교', NOW()), ('경북도립대학교', NOW()), ('경북외국어대학교', NOW()), ('경북전문대학교', NOW()), ('경산대학교', NOW()), ('경상대학교', NOW()), ('경성대학교', NOW()), ('경운대학교', NOW()), ('경운대학교(산업대)', NOW()), ('경원전문대학교', NOW()), ('경인교육대학교', NOW()), ('경인여자대학교', NOW()), ('경일대학교', NOW()), ('경주대학교', NOW()), ('경희대학교', NOW()), ('경희사이버대학교', NOW()), ('계명대학교', NOW()), ('계명문화대학교', NOW()), ('계원예술대학교', NOW()), ('고구려대학교', NOW()), ('고려대학교', NOW()), ('고려대학교(세종)', NOW()), ('고려사이버대학교', NOW()), ('고신대학교', NOW()), ('공주교육대학교', NOW()), ('공주대학교', NOW()), ('광신대학교', NOW()), ('광양보건대학교', NOW()), ('광운대학교', NOW()), ('광주가톨릭대학교', NOW()), ('광주교육대학교', NOW()), ('광주대학교', NOW()), ('광주대학교(산업대)', NOW()), ('광주보건대학교', NOW()), ('광주여자대학교', NOW()), ('구미대학교', NOW()), ('구세군사관학교', NOW()), ('국민대학교', NOW()), ('국제대학교', NOW()), ('국제사이버대학교', NOW()), ('군산간호대학교', NOW()), ('군산대학교', NOW()), ('군장대학교', NOW()), ('그리스도대학교', NOW()), ('극동대학교', NOW()), ('글로벌사이버대학교', NOW()), ('금강대학교', NOW()), ('금오공과대학교', NOW()), ('기독간호대학교', NOW()), ('김천과학대학교', NOW()), ('김천대학교', NOW()), ('김포대학교', NOW()), ('김해대학교', NOW()), ('꽃동네대학교', NOW()), ('나사렛대학교', NOW()), ('남부대학교', NOW()), ('남서울대학교', NOW()), ('남서울대학교(산업대)', NOW()), ('농협대학교', NOW()), ('단국대학교', NOW()), ('대경대학교', NOW()), ('대구가톨릭대학교', NOW()), ('대구공업대학교', NOW()), ('대구과학대학교', NOW()), ('대구교육대학교', NOW()), ('대구대학교', NOW()), ('대구미래대학교', NOW()), ('대구보건대학교', NOW()), ('대구사이버대학교', NOW()), ('대구예술대학교', NOW()), ('대구외국어대학교', NOW()), ('대구한의대학교', NOW()), ('대덕대학교', NOW()), ('대동대학교', NOW()), ('대림대학교', NOW()), ('대신대학교', NOW()), ('대원대학교', NOW()), ('대전가톨릭대학교', NOW()), ('대전대학교', NOW()), ('대전보건대학교', NOW()), ('대전신학교', NOW()), ('대전신학대학교', NOW()), ('대진대학교', NOW()), ('덕성여자대학교', NOW()), ('동강대학교', NOW()), ('동국대학교', NOW()), ('동국대학교(경주)', NOW()), ('동남보건대학교', NOW()), ('동덕여자대학교', NOW()), ('동명대학교', NOW()), ('동명정보대학교', NOW()), ('동부산대학교', NOW()), ('동서대학교', NOW()), ('동서울대학교', NOW()), ('동신대학교', NOW()), ('동아대학교', NOW()), ('동아방송예술대학교', NOW()), ('동아인재대학교', NOW()), ('동양대학교', NOW()), ('동양미래대학교', NOW()), ('동우대학교', NOW()), ('동원과학기술대학교', NOW()), ('동원대학교', NOW()), ('동의과학대학교', NOW()), ('동의대학교', NOW()), ('동주대학교', NOW()), ('두원공과대학교', NOW()), ('디지털서울문화예술대학교', NOW()), ('루터대학교', NOW()), ('마산대학교', NOW()), ('명지대학교', NOW()), ('명지전문대학교', NOW()), ('목원대학교', NOW()), ('목포가톨릭대학교', NOW()), ('목포과학대학교', NOW()), ('목포대학교', NOW()), ('목포해양대학교', NOW()), ('문경대학교', NOW()), ('배재대학교', NOW()), ('배화여자대학교', NOW()), ('백석대학교', NOW()), ('백석문화대학교', NOW()), ('백제예술대학교', NOW()), ('벽성대학교', NOW()), ('국립부경대학교', NOW()), ('부산가톨릭대학교', NOW()), ('부산경상대학교', NOW()), ('부산과학기술대학교', NOW()), ('부산교육대학교', NOW()), ('부산대학교', NOW()), ('부산디지털대학교', NOW()), ('부산여자대학교', NOW()), ('부산예술대학교', NOW()), ('부산외국어대학교', NOW()), ('부산장신대학교', NOW()), ('부천대학교', NOW()), ('사이버한국외국어대학교', NOW()), ('삼육대학교', NOW()), ('삼육보건대학교', NOW()), ('삼육의명대학교', NOW()), ('상명대학교', NOW()), ('상명대학교(천안)', NOW()), ('상주대학교', NOW()), ('상지대학교', NOW()), ('상지영서대학교', NOW()), ('서강대학교', NOW()), ('서경대학교', NOW()), ('서남대학교', NOW()), ('서라벌대학교', NOW()), ('서영대학교', NOW()), ('서울과학기술대학교', NOW()), ('서울과학기술대학교(산업대)', NOW()), ('서울교육대학교', NOW()), ('서울기독대학교', NOW()), ('서울대학교', NOW()), ('서울디지털대학교', NOW()), ('서울보건대학교', NOW()), ('서울사이버대학교', NOW()), ('서울시립대학교', NOW()), ('서울신학대학교', NOW()), ('서울여자간호대학교', NOW()), ('서울여자대학교', NOW()), ('서울예술대학교', NOW()), ('서울장신대학교', NOW()), ('서원대학교', NOW()), ('서일대학교', NOW()), ('서정대학교', NOW()), ('서해대학교', NOW()), ('선린대학교', NOW()), ('선문대학교', NOW()), ('성결대학교', NOW()), ('성공회대학교', NOW()), ('성균관대학교', NOW()), ('성덕대학교', NOW()), ('성신여자대학교', NOW()), ('성심외국어대학교', NOW()), ('세경대학교', NOW()), ('세명대학교', NOW()), ('세종대학교', NOW()), ('세종사이버대학교', NOW()), ('세한대학교', NOW()), ('송곡대학교', NOW()), ('송원대학교', NOW()), ('송호대학교', NOW()), ('수성대학교', NOW()), ('수원가톨릭대학교', NOW()), ('수원과학대학교', NOW()), ('수원대학교', NOW()), ('수원여자대학교', NOW()), ('숙명여자대학교', NOW()), ('순복음총회신학교', NOW()), ('순천대학교', NOW()), ('순천제일대학교', NOW()), ('순천향대학교', NOW()), ('숭실대학교', NOW()), ('숭실사이버대학교', NOW()), ('숭의여자대학교', NOW()), ('신경대학교', NOW()), ('신구대학교', NOW()), ('신라대학교', NOW()), ('신성대학교', NOW()), ('신안산대학교', NOW()), ('신흥대학교', NOW()), ('아세아연합신학대학교', NOW()), ('아주대학교', NOW()), ('아주자동차대학교', NOW()), ('안동과학대학교', NOW()), ('안동대학교', NOW()), ('안산대학교', NOW()), ('안양대학교', NOW()), ('여주대학교', NOW()), ('연성대학교', NOW()), ('연세대학교', NOW()), ('연세대학교(원주)', NOW()), ('연암공과대학교', NOW()), ('열린사이버대학교', NOW()), ('영남대학교', NOW()), ('영남신학대학교', NOW()), ('영남외국어대학교', NOW()), ('영남이공대학교', NOW()), ('영산대학교', NOW()), ('영산대학교(산업대)', NOW()), ('영산선학대학교', NOW()), ('영진사이버대학교', NOW()), ('영진전문대학교', NOW()), ('예수대학교', NOW()), ('예원예술대학교', NOW()), ('오산대학교', NOW()), ('용인대학교', NOW()), ('용인송담대학교', NOW()), ('우석대학교', NOW()), ('우송공업대학교', NOW()), ('우송대학교', NOW()), ('우송대학교(산업대)', NOW()), ('우송정보대학교', NOW()), ('울산과학대학교', NOW()), ('울산대학교', NOW()), ('웅지세무대학교', NOW()), ('원광대학교', NOW()), ('원광디지털대학교', NOW()), ('원광보건대학교', NOW()), ('원주대학교', NOW()), ('위덕대학교', NOW()), ('유원대학교', NOW()), ('유한대학교', NOW()), ('을지대학교', NOW()), ('이화여자대학교', NOW()), ('인덕대학교', NOW()), ('인제대학교', NOW()), ('인천가톨릭대학교', NOW()), ('인천대학교', NOW()), ('인천재능대학교', NOW()), ('인천전문대학교', NOW()), ('인하공업전문대학교', NOW()), ('인하대학교', NOW()), ('장로회신학대학교', NOW()), ('장안대학교', NOW()), ('적십자간호대학교', NOW()), ('전남과학대학교', NOW()), ('전남대학교', NOW()), ('전남도립대학교', NOW()), ('전북과학대학교', NOW()), ('전북대학교', NOW()), ('전주교육대학교', NOW()), ('전주기전대학교', NOW()), ('전주대학교', NOW()), ('전주비전대학교', NOW()), ('정석대학교', NOW()), ('제주관광대학교', NOW()), ('제주교육대학교', NOW()), ('제주국제대학교', NOW()), ('제주대학교', NOW()), ('제주산업정보대학교', NOW()), ('제주한라대학교', NOW()), ('조선간호대학교', NOW()), ('조선대학교', NOW()), ('조선이공대학교', NOW()), ('중부대학교', NOW()), ('중앙대학교', NOW()), ('중앙대학교(안성)', NOW()), ('중앙승가대학교', NOW()), ('중원대학교', NOW()), ('진주교육대학교', NOW()), ('진주보건대학교', NOW()), ('진주산업대학교(산업대)', NOW()), ('차의과학대학교', NOW()), ('창신대학교', NOW()), ('창원대학교', NOW()), ('창원문성대학교', NOW()), ('천안연암대학교', NOW()), ('청강문화산업대학교', NOW()), ('청암대학교', NOW()), ('청운대학교', NOW()), ('청주교육대학교', NOW()), ('청주대학교', NOW()), ('초당대학교', NOW()), ('초당대학교(산업대)', NOW()), ('총신대학교', NOW()), ('추계예술대학교', NOW()), ('춘천교육대학교', NOW()), ('춘해보건대학교', NOW()), ('충남대학교', NOW()), ('충남도립청양대학교', NOW()), ('충북대학교', NOW()), ('충북도립대학교', NOW()), ('충북보건과학대학교', NOW()), ('충청대학교', NOW()), ('침례신학대학교', NOW()), ('칼빈대학교', NOW()), ('탐라대학교', NOW()), ('평택대학교', NOW()), ('포항대학교', NOW()), ('한경대학교', NOW()), ('한경대학교(산업대)', NOW()), ('한국골프대학교', NOW()), ('한국관광대학교', NOW()), ('한국교원대학교', NOW()), ('한국교통대학교', NOW()), ('한국교통대학교(산업대)', NOW()), ('한국국제대학교', NOW()), ('한국기술교육대학교', NOW()), ('한국농수산대학교', NOW()), ('한국방송통신대학교', NOW()), ('한국복지대학교', NOW()), ('한국복지사이버대학교', NOW()), ('한국산업기술대학교', NOW()), ('한국산업기술대학교(산업대)', NOW()), ('한국성서대학교', NOW()), ('한국승강기대학교', NOW()), ('한국영상대학교', NOW()), ('한국예술종합학교', NOW()), ('한국외국어대학교', NOW()), ('한국전통문화대학교', NOW()), ('한국정보통신기능대학교', NOW()), ('한국철도대학교', NOW()), ('한국체육대학교', NOW()), ('한국폴리텍대학교', NOW()), ('한국항공대학교', NOW()), ('국립한국해양대학교', NOW()), ('한남대학교', NOW()), ('한동대학교', NOW()), ('한라대학교', NOW()), ('한려대학교', NOW()), ('한려대학교(산업대)', NOW()), ('한림대학교', NOW()), ('한림성심대학교', NOW()), ('한민학교', NOW()), ('한밭대학교', NOW()), ('한밭대학교(산업대)', NOW()), ('한북대학교', NOW()), ('한서대학교', NOW()), ('한성대학교', NOW()), ('한세대학교', NOW()), ('한신대학교', NOW()), ('한양대학교', NOW()), ('한양대학교(ERICA)', NOW()), ('한양사이버대학교', NOW()), ('한양여자대학교', NOW()), ('한영대학교', NOW()), ('한영신학대학교', NOW()), ('한일장신대학교', NOW()), ('한중대학교', NOW()), ('협성대학교', NOW()), ('혜전대학교', NOW()), ('혜천대학교', NOW()), ('호남대학교', NOW()), ('호남신학대학교', NOW()), ('호서대학교', NOW()), ('호원대학교', NOW()), ('홍익대학교', NOW()), ('홍익대학교(세종)', NOW()), ('화신사이버대학교', NOW()), ('DGIST', NOW()), ('GIST', NOW()), ('KAIST', NOW()), ('POSTECH', NOW()), ('UNIST', NOW());



DROP TABLE IF EXISTS region;
create table region (
                        created_at datetime(6),
                        modified_at datetime(6),
                        region_id bigint not null auto_increment,
                        score bigint default 0,
                        region_name varchar(40),
                        primary key (region_id)
);

INSERT INTO region (region_name, created_at)
VALUES
    ('서울특별시', NOW()),
    ('경기도', NOW()),
    ('강원도', NOW()),
    ('충청북도', NOW()),
    ('충청남도', NOW()),
    ('전라북도', NOW()),
    ('전라남도', NOW()),
    ('경상북도', NOW()),
    ('경상남도', NOW()),
    ('제주특별자치도', NOW());
SET FOREIGN_KEY_CHECKS = 1;
*/