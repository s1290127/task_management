package com.example.its.web.issue;

import com.example.its.domain.issue.IssueEntity;
import lombok.RequiredArgsConstructor;
import com.example.its.domain.issue.IssueService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/issues")
@RequiredArgsConstructor
public class issueController {

    private final IssueService issueService;
    // GET /issues
    @GetMapping
    public String showList(Model model) {
        model.addAttribute("issueList", issueService.findAll());
        return "issues/list";
    }

    @GetMapping("/creationForm")
    public String showCreationForm(Model model /*@ModelAttribute IssueForm form*/) {
        // 空のissueFormをmodelにセット
        model.addAttribute("issueForm", new IssueForm());
        return "issues/creationForm";
    }


    @PostMapping
    public String create(@Validated IssueForm form, BindingResult bindingResult, Model model) {

        // 以下のエラー処理コードはオリジナル。
        // 実際は26参照。
        if(bindingResult.hasErrors()){
            return "issues/creationForm";
        }

        issueService.create(form.getSummary(), form.getDescription());

        return "redirect:/issues";
    }

    @GetMapping("/{issueId}")
    public String showDetail(@PathVariable("issueId") long issueId, Model model) {
        model.addAttribute("issue", issueService.findById(issueId));
        return "issues/detail";
    }

}
