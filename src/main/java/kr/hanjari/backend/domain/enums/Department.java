package kr.hanjari.backend.domain.enums;

import java.util.Arrays;
import java.util.List;
import lombok.Getter;

@Getter
public enum Department {
    // 공학대학
    ARCHITECTURE("건축학부", College.ENGINEERING),
    CONSTRUCTION_ENVIRONMENT("건설환경공학과", College.ENGINEERING),
    TRANSPORT_LOGISTICS("교통·물류공학과", College.ENGINEERING),
    ELECTRICAL_ENGINEERING("전자공학부", College.ENGINEERING),
    BATTERY_MATERIAL_CHEMICAL("배터리소재화학공학과", College.ENGINEERING),
    MATERIAL_CHEMICAL("재료화학공학과", College.ENGINEERING),
    MECHANICAL("기계공학과", College.ENGINEERING),
    INDUSTRIAL_MANAGEMENT("산업경영공학과", College.ENGINEERING),
    ROBOT("로봇공학과", College.ENGINEERING),
    FUSION_SYSTEM("융합시스템공학과", College.ENGINEERING),
    SMART_FUSION("스마트융합공학부", College.ENGINEERING),
    INTELLIGENT_ROBOT("지능형로봇학과", College.ENGINEERING),
    ENERGY_BIO("에너지바이오학과", College.ENGINEERING),
    MARINE_FUSION("해양융합공학과", College.ENGINEERING),

    // 소프트웨어융합대학
    COMPUTER("컴퓨터학부", College.SOFTWARE),
    ICT("ICT융합학부", College.SOFTWARE),
    AI("인공지능학부", College.SOFTWARE),
    DATA("데이터사이언스학부", College.SOFTWARE),

    // 약학대학
    PHARMACY("약학과", College.PHARMACY),

    // 첨단융합대학
    SEMICONDUCTOR("차세대반도체융합공학부", College.CONVERGENCE),
    BIO("바이오신약융합학부", College.CONVERGENCE),
    DEFENSE_INTELLIGENCE("국방지능정보융합공학부", College.CONVERGENCE),

    // 글로벌문화통상대학
    KOREAN_STUDIES("한국언어문학과", College.GLOBAL_LAW_COMMUNICATION),
    CHINA_STUDIES("중국학과", College.GLOBAL_LAW_COMMUNICATION),
    JAPAN_STUDIES("일본학과", College.GLOBAL_LAW_COMMUNICATION),
    ENGLISH_STUDIES("영미언어문화학과", College.GLOBAL_LAW_COMMUNICATION),
    FRENCH_STUDIES("프랑스학과", College.GLOBAL_LAW_COMMUNICATION),

    // 커뮤니케이션&컬쳐대학
    ADVERTISING("광고홍보학과", College.COMMUNICATION_CULTURE),
    MEDIA("미디어학과", College.COMMUNICATION_CULTURE),
    CULTURE("문화콘텐츠학과", College.COMMUNICATION_CULTURE),
    ANTHROPOLOGY("문화인류학과", College.COMMUNICATION_CULTURE),

    // 경상대학
    BUSINESS_ADMINISTRATION("경영학부", College.KYUNG_SANG),
    ECONOMICS("경제학과", College.KYUNG_SANG),
    ACTUARIAL_SCIENCE("보험계리학과", College.KYUNG_SANG),
    ACCOUNTING("회계세무학과", College.KYUNG_SANG),

    // 디자인대학
    INTEGRATED_DESIGN("융합디자인학부", College.DESIGN),
    JEWELRY("주얼리/패션디자인학과", College.DESIGN),
    INDUSTRIAL_DESIGN("산업디자인학과", College.DESIGN),
    COMMUNICATION_DESIGN("커뮤니케이션디자인학과", College.DESIGN),
    MEDIA_DESIGN("영상디자인학과", College.DESIGN),

    // 예체능
    SPORTS_SCIENCE("스포츠과학부", College.SPORT_ARTS),
    DANCE("무용학과", College.SPORT_ARTS),
    MUSIC("실용음악학과", College.SPORT_ARTS);
    
    private final String description;
    private final College college;

    Department(String description, College college) {
        this.description = description;
        this.college = college;
    }

    public static List<Department> findByCollege(College college) {
        return Arrays.stream(values())
                .filter(dept -> dept.getCollege() == college)
                .toList();
    }
}