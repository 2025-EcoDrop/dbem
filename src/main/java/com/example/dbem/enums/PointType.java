package com.example.dbem.enums;

public enum PointType {
    EARN,       // 수거 완료로 인한 포인트 획득
    USE,        // 수거 요청으로 인한 포인트 사용
    CANCEL,     // 수거 요청 취소로 인한 포인트 회수
    INIT,       // 회원가입으로 인한 포인트 획득
    EVENT,      // 이벤트로 인한 포인트 획득
    EXCHANGE,   // 기프티콘 교환으로 인한 포인트 사용
}
