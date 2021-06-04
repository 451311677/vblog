package com.xaut.blog.dao;

import com.xaut.blog.entity.Label;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author zxg
 * @date 2021/4/7
 * @description
 */
@Mapper
public interface LabelDao {

    /**
     * 添加标签
     * @param text
     * @return
     */
    int insertLabel(String text);

    /**
     * 通过id查询标签
     * @param labelId
     * @return
     */
    Label getLabelById(Long labelId);

    /**
     * 通过标签内容查询id
     * @param text
     * @return
     */
    Label getLabelByText(String text);

    /**
     * 批量查询
     * @param labelList
     * @return
     */
    List<Label> getLabelListById(List<Long> labelList);

    /**
     * 批量查询
     * @param labelList
     * @return
     */
    List<Label> getLabelListByText(List<String> labelList);


    /**
     * 查询初始标签
     * @param num
     * @return
     */
    List<Label> getLabelInit(Integer num);
}
