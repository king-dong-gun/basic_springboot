package com.come1997.backboard.common;

import org.springframework.stereotype.Component;

import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.commonmark.node.Node;

// 컴포넌트 어노테이션에 value값 주어져야 함
@Component("CommonUtil")
public class CommonUtil {
    public String markdown(String content) {
        Parser parser = Parser.builder().build();
        Node document = parser.parse(content);  // 기존 마크다운으로 작성된 툴 파싱
        HtmlRenderer renderer = HtmlRenderer.builder().build();

        return renderer.render(document);   // html로 렌더링 텍스트 리턴
    }
}
