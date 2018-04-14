package swa.tools.java.doc.generator.test;


/**
 * Created by jinyan on 4/9/18 2:29 PM.
 */
public interface IBadInfoQueryService {
    /**
     * 查询不良信息（华道）
     *
     * @param requestDto
     * @return
     */
    IllegalInfoResponseDto queryIllegalInfo(IllegalInfoRequestDto requestDto);
}
