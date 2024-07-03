// Json은 DateTime으로 넘어오지 못한다.   >>  2024-07-02T12:17:03.375532
export function formatDate(date) {
    var result = date.replace("T", " "); // T를 공백으로 변경
    // var index = result.lastIndexOf(":");    // 초 앞의 : 위치값 찾기
    var index = result.lastIndexOf(" ");    // 공백 뒤 제거

    result = result.substr(0, index);

    return result;
}