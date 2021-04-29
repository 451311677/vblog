package com.xaut.blog.service;

import com.xaut.blog.dao.LabelDao;
import com.xaut.blog.entity.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zxg
 * @date 2021/4/7
 * @description 标签
 */
@Service
public class LabelService {

    @Autowired
    private LabelDao labelDao;

    /**
     * 添加标签
     * @param text
     * @return
     */
    public int insertLabel(String text){
        return labelDao.insertLabel(text);
    }

    /**
     * 通过id查询标签
     * @param labelId
     * @return
     */
    public Label getLabelById(Long labelId){
        return labelDao.getLabelById(labelId);
    }

    /**
     * 通过标签内容查询id
     * @param text
     * @return
     */
    public Label getLabelByText(String text){
        return labelDao.getLabelByText(text);
    }

    /**
     * 批量查询
     * @param labelList
     * @return
     */
    public List<Label> getLabelListById(List<Long> labelList){
        return labelDao.getLabelListById(labelList);
    }

    /**
     * 批量查询
     * @param labelList
     * @return
     */
    public List<Label> getLabelListByText(List<String> labelList){
        return labelDao.getLabelListByText(labelList);
    }

    /**
     * 查询初始标签
     * @param num
     * @return
     */
    public List<Label> getLabelInit(Integer num){
        return labelDao.getLabelInit(num);
    }

}
