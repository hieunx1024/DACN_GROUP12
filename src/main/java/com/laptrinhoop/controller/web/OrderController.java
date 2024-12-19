package com.laptrinhoop.controller.web;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.laptrinhoop.dto.PaymentRequest;
import com.laptrinhoop.dto.UrlGeneratorResponse;
import com.laptrinhoop.entity.Partner;
import com.laptrinhoop.enums.PartnerCode;
import com.laptrinhoop.service.*;
import com.laptrinhoop.utils.IdGenerator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.laptrinhoop.entity.Order;
import com.laptrinhoop.service.impl.CartService;

import javax.transaction.Transactional;

@Controller
@Slf4j
public class OrderController {

	@Autowired
	private CartService cartService;

	@Autowired
	private IOrderSevice orderService;

	@Autowired
	private IPaymentService paymentService;

	@Autowired
	private IMailService mailService;

	@Autowired
	private IAccountService accountService;



//	@Autowired
//	private IRabbitmqService rabbit;

	@GetMapping("/order/checkout")
	public String checkOut(Model model,RedirectAttributes attributes) {
		if (cartService.getCountCart() == 0) {
			attributes.addFlashAttribute("message","Chưa có sản phẩm trong giỏ hàng");
			return "redirect:/cart/view";
		}
		model.addAttribute("cart", cartService);
		Order order = orderService.createOrder();
		model.addAttribute("order", order);
		model.addAttribute("partners",paymentService.findAll());
		return "order/checkout";
	}

	@PostMapping("/order/checkout")
	@Transactional
	public String checkOut(Model model, @Validated @ModelAttribute("order") Order or) {
		Partner partner = paymentService.findByCode(or.getPartner().getCode());
		or.setPartner(partner);
		 orderService.addOrderAndOrderDetail(or, cartService);
		 if(or.getPartner().getCode().equals(PartnerCode.VNPAY.name())){
			 PaymentRequest paymentRequest = PaymentRequest.builder()
					 .username(or.getCustomer().getId())
					 .partnerCode(PartnerCode.VNPAY)
					 .description(Objects.isNull(or.getDescription()) || or.getDescription().length() == 0 ? "Thanh toán đơn hàng" : or.getDescription())
					 .currencyCode("VND")
					 .locale("vi")
					 .amount(new BigDecimal(or.getAmount()))
					 .redirectUrl(IdGenerator.getRedirectProxy(partner.getRedirectProxy()))
					 .build();
			 UrlGeneratorResponse generateUrl =  paymentService.generateLink(paymentRequest);
			 log.info("[OrderController] checkOut -- generate link url payment:{}",generateUrl.getUrl());
			 cartService.clear();
			 return "redirect:" + generateUrl.getUrl();
		 }
		cartService.clear();
		return "redirect:/home/index";
	}

	@RequestMapping("/order/list")
	public String listOrder(Model model) {
		List<Order> list = orderService.getAllOrderByUser();
		model.addAttribute("orders", list);
		model.addAttribute("ordersWaiting",(List<Order>) list.stream().filter(item -> item.getStatus() == 1).collect(Collectors.toList()));
		model.addAttribute("ordersDelivery",
				(List<Order>) list.stream().filter(item -> item.getStatus() == 2).collect(Collectors.toList()));
		model.addAttribute("ordersDeliverted",
				(List<Order>) list.stream().filter(item -> item.getStatus() == 3).collect(Collectors.toList()));
		model.addAttribute("ordersCancel",
				(List<Order>) list.stream().filter(item -> item.getStatus() == 4).collect(Collectors.toList()));
		return "order/list";
	}

	@RequestMapping("/order/detail/{id}")
	public String detail(Model model, @PathVariable("id") Integer id) {
		Order order = orderService.findById(id);
		model.addAttribute("order", order);
		return "order/detail";
	}

	@RequestMapping("/order/items")
	public String getPurchasedItems(Model model) {
		model.addAttribute("list", orderService.getPurchasedItems().values());
		return "product/list";
	}
}
