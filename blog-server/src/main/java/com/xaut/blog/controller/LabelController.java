package com.xaut.blog.controller;

import com.xaut.blog.entity.Label;
import com.xaut.blog.service.LabelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zxg
 * @date 2021/4/8
 * @description
 */
@RestController
@RequestMapping("/label")
@Slf4j
public class LabelController {

    @Autowired
    private LabelService labelService;

    @GetMapping("/init")
    public String getLebelInit(){

        return "init";
    }

    @GetMapping("/get/{id}")
    public Label getLabelById(@PathVariable("id") Long labelId){
        return labelService.getLabelById(labelId);
    }

    @PostMapping("/add")
    public int insertLabel(String text){
        log.info("*******: "+text);
        return labelService.insertLabel(text);
    }

    @GetMapping("/getlistid")
    public List<Label> getLabelListById(){
        List<Long> list = new ArrayList<>();
        list.add(1L);
        list.add(2L);
        return labelService.getLabelListById(list);
    }
    @GetMapping("/getlisttext")
    public List<Label> getLabelListByText(){
        List<String> list = new ArrayList<>();
        list.add("Java");
        list.add("Python");
        return labelService.getLabelListByText(list);
    }

}
