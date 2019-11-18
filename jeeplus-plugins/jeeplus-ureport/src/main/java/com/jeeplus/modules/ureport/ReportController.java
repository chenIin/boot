package com.jeeplus.modules.ureport;

import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.core.web.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "${adminPath}/reports")
public class ReportController extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(ReportController.class);
    private final ReportService reportService;

    ReportController(ReportService reportService) {
        this.reportService = reportService;
    }



    @GetMapping("/index")
    public String getReports(Model model) {
        logger.debug("ReportController.getReports");
        model.addAttribute("reports", reportService.getReportFiles());
        model.addAttribute("prefix", "file:");
        return "modules/reports/index";
    }

    @ResponseBody
    @GetMapping("/delete")
    public AjaxJson delete(@RequestParam("id") String id) {
        AjaxJson j = new AjaxJson();
        logger.debug("ReportController.delete {}", id);
        reportService.deleteReport(id);
        j.setMsg("删除报表成功!");
        return j;
    }
}
