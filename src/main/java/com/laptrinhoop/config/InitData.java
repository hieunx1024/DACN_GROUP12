//package com.laptrinhoop.config;
//
//
//import com.laptrinhoop.dao.IPartnerDAO;
//import com.laptrinhoop.entity.Partner;
//import com.laptrinhoop.properties.VNPayProperties;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import java.util.Optional;
//
//
//@Component
//public class InitData {
//
//    @Autowired
//    private IPartnerDAO partnerDAO;
//
//    @Autowired
//    private VNPayProperties vnPayProperties;
//
//    @PostConstruct
//    public void initDataPartner() {
//
//        Optional<Partner> partnerOps = partnerDAO.findByCode("VNPAY");
//        if (!partnerOps.isPresent()) {
//            partnerDAO.create(Partner.from(vnPayProperties));
//            return;
//        }else{
//            Partner oldPartner = partnerOps.get();
//            partnerDAO.update(oldPartner.sync(vnPayProperties));
//        }
//
//    }
//}
