package com.controller;

import com.Interface.ExcelExportService;
import com.Pojo.PageParam;
import com.Pojo.Person;
import com.Pojo.PersonParam;
import com.View.ExcelView;
import com.mapper.PersonMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.aspectj.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class New_Controller {
    @Autowired
    PersonMapper personMapper = null;

    //通过ModelMap进行数据的传递
    @RequestMapping(value = "/getRoleByModelMap")
    public ModelAndView getRoleByModelMap(@RequestParam("id") Integer id, ModelMap modelMap) {
        Person p = personMapper.getRole(id);
        ModelAndView mv = new ModelAndView();
        mv.setViewName("roleDetails");
        modelMap.addAttribute("person", p);
        return mv;
    }

    //通过Model进行数据的传递
    @RequestMapping("/getRoleByModel")
    public ModelAndView getRoleByModel(@RequestParam("id") int id, Model model) {
        Person p = personMapper.getRole(id);
        ModelAndView mv = new ModelAndView();
        mv.setViewName("roleDetails");
        model.addAttribute("person", p);
        return mv;
    }

    @RequestMapping("/getRoleByModelAndView")
    public ModelAndView getRoleByModelAndView(@RequestParam("id") int id) {
        Person p = personMapper.getRole(id);
        ModelAndView mv = new ModelAndView();
        mv.setView(new MappingJackson2JsonView());
        mv.addObject("person", p);
        return mv;
    }

    @RequestMapping("/export")
    public ModelAndView export() {
        //模型和视图
        ModelAndView mv = new ModelAndView();
        //excel视图
        ExcelView ev = new ExcelView(excelExportService());
        ev.setFileName("juese.xls");
        PersonParam personParam = new PersonParam();
        PageParam pageParam = new PageParam();
        pageParam.setStart(0);
        pageParam.setLimit(1000);
        personParam.setPageParam(pageParam);
        List<Person> list = personMapper.findRoles();
        mv.addObject("personList", list);
        mv.setView(ev);
        return mv;
    }

    @SuppressWarnings({"unchecked"})
    private ExcelExportService excelExportService() {
        return (Map<String, Object> model, Workbook workbook) -> {
            //获取用户列表
            List<Person> personList = (List<Person>) model.get("personList");
            //生成sheet
            Sheet sheet = workbook.createSheet("所有角色");
            Row title = sheet.createRow(0);
            title.createCell(0).setCellValue("id");
            title.createCell(1).setCellValue("name");
            title.createCell(2).setCellValue("birth");
            title.createCell(3).setCellValue("money");
            for (int i = 0; i < personList.size(); i++) {
                Person p = personList.get(i);
                int rowIdx = i + 1;
                Row row = sheet.createRow(rowIdx);
                row.createCell(0).setCellValue(p.getId());
                row.createCell(1).setCellValue(p.getName());
                row.createCell(2).setCellValue(p.getBirth());
                row.createCell(3).setCellValue(p.getMoney());
            }
        };
    }

    @RequestMapping(value = "/file")
    public ModelAndView upload(MultipartFile file) {
        //请求转换
//        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
//        获取上传的文件
//        MultipartFile file = multipartHttpServletRequest.getFile("file");
        //设置视图
        if (file == null) {
            ModelAndView mv = new ModelAndView();
            mv.setViewName("fileUpload");
            return mv;
        }
        ModelAndView mv = new ModelAndView();
        mv.setView(new MappingJackson2JsonView());
        //获取原始文件名
        String fileName = file.getOriginalFilename();
        //目标文件
        file.getContentType();
        File dest = new File(fileName);
        try {
            //保存文件
            file.transferTo(dest);
            mv.addObject("success", true);
            mv.addObject("msg", "上传文件成功");

        } catch (IllegalStateException | IOException e) {
            mv.addObject("success", false);
            mv.addObject("msg", "上传文件失败");
            e.printStackTrace();
        }
        return mv;
    }

    @RequestMapping("/file2")
    public ModelAndView upload2(Part file) {
        if (file == null) {
            ModelAndView mv = new ModelAndView();
            mv.setViewName("fileUpload");
            return mv;
        }
        ModelAndView mv = new ModelAndView();
        mv.setView(new MappingJackson2JsonView());
        String fileName = file.getName();
        File dest = new File(fileName);
        try {
            //保存文件
            mv.addObject("success", true);
            mv.addObject("msg", "上传文件成功");

        } catch (IllegalStateException e) {
            mv.addObject("success", false);
            mv.addObject("msg", "上传文件失败");
            e.printStackTrace();
        }
        return mv;
    }


    @ResponseBody
    @RequestMapping("/forjson")
    public Person forJson(@Param("id") Integer id) {
        Person person = personMapper.getRole(id);
        return person;
    }

    @ResponseBody
    @RequestMapping("/insertPerson")
    public Map<String, Object> insert(Person person) {
        Map<String, Object> result = new HashMap<>();
        boolean updateFlag = (personMapper.updateRole(person) == 1);
        result.put("success", updateFlag);
        if (updateFlag == true) {
            result.put("msg", "更新成功");
        } else
            result.put("msg", "更新失败");
        return result;
    }

    @RequestMapping("/date")
    public ModelAndView format(@RequestParam("date1") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date,
                               @RequestParam("amount1") @NumberFormat(pattern = "#,###.##") Double amount) {
        if (date == null) {
            ModelAndView mv = new ModelAndView();
            mv.setViewName("date");
            return mv;
        }
        ModelAndView mv = new ModelAndView();
        mv.addObject("date", date);
        mv.addObject("amount", amount);
        return mv;
    }

    @RequestMapping("/download_")
    public ModelAndView index() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("download");
        return mv;
    }

    @RequestMapping("/download")
    public ResponseEntity<byte[]> fileDownload(HttpServletRequest request, String filename) throws Exception {
        if (filename == null) {

        }
        //指定文件下载路径
        String path = "F:\\学习资料\\ssm\\mvc\\web\\WEB-INF\\upload";
        //目标文件
        File target = new File(path + File.separator + filename);
        //设置响应头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", filename);
        //定义以流的方式返回下载数据
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        //使用Spring MVC的ResponseEntity对象封装返回下载数据
        return new ResponseEntity<byte[]>(FileUtil.readAsByteArray(target), headers, HttpStatus.OK);
    }

    @RequestMapping("/advice")
    @ResponseBody
    public Map<String, Object> testForAdvice(Date date, @NumberFormat(pattern = "##,##.00")
            BigDecimal amount, Model model) {
        Map<String, Object> map = new HashMap<>();
        //由于@ModelAttribute会在控制器之前运行，所以，这样也会取到数据
        map.put("projectName", model.asMap().get("projectName"));
        map.put("date", new SimpleDateFormat("yyyy-MM-dd").format(date));
        map.put("amount", amount);
        return map;
    }

    //异常测试
    @RequestMapping("/error")
    public void exception() {
        throw new RuntimeException("异常跳转");
    }


}
