package com.laptrinhoop.controller.web;

import java.util.Collections;
import java.util.List;

import javax.validation.Valid;

import com.laptrinhoop.entity.Category;
import com.laptrinhoop.entity.ContactForm;
import com.laptrinhoop.entity.FeedbackForm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.laptrinhoop.entity.Product;
import com.laptrinhoop.service.ICategoryService;
import com.laptrinhoop.service.IProductService;

@Controller
public class HomeController {
    @Autowired
    private ICategoryService serviceCategory;

    @Autowired
    private IProductService prodService;

    @RequestMapping("/home/index")
    public String index() {
        return "home/index";
    }

    @RequestMapping("/home/about")
    public String about() {
        return "home/about";
    }

    @GetMapping("/home/contact")
    public String showContactForm(Model model) {
        model.addAttribute("contactForm", new ContactForm()); // tạo object rỗng cho binding
        return "home/contact";
    }

    @PostMapping("/home/contact")
    public String submitContactForm(@ModelAttribute("contactForm") ContactForm contactForm, BindingResult result,
            Model model) {
        if (result.hasErrors()) {
            return "home/contact";
        }
        // Xử lý gửi form ở đây
        model.addAttribute("successMessage", "Cảm ơn bạn đã gửi liên hệ!");
        model.addAttribute("contactForm", new ContactForm()); // reset form
        return "home/contact";
    }

    @GetMapping("/home/feedback")
    public String showFeedbackForm(Model model) {
        model.addAttribute("feedbackForm", new FeedbackForm()); // tạo object rỗng cho binding
        return "home/feedback";
    }

    @PostMapping("/home/feedback")
    public String submitFeedbackForm(@ModelAttribute("feedbackForm") FeedbackForm feedbackForm, BindingResult result,
            Model model) {
        if (result.hasErrors()) {
            return "home/feedback";
        }
        // Xử lý gửi form ở đây
        model.addAttribute("successMessage", "Cảm ơn bạn đã gửi liên hệ!");
        model.addAttribute("feedbackForm", new FeedbackForm()); // reset form
        return "home/feedback";
    }

    @RequestMapping("/home/faq")
    public String faq() {
        return "home/faq";
    }

    @RequestMapping("/home/aside/category")
    public String category(Model model) {
        model.addAttribute("cates", serviceCategory.findAll());
        return "non-layout/layout/_aside-category";
    }

    @RequestMapping("/home/random")
    public String random(Model model) {
        List<Product> list = prodService.findAll();
        Collections.shuffle(list);
        model.addAttribute("list", list.subList(0, 6));
        return "non-layout/product/list";
    }

    @RequestMapping("/home/slideshow")
    public String slideshow(Model model) {
        List<Category> list = serviceCategory.getRamDomByCategory();
        list.forEach(x -> System.out.println(x.getNameVN()));
        model.addAttribute("slideshow_cates", list.subList(0, 3));
        return "non-layout/home/_slideshow";
    }
}
