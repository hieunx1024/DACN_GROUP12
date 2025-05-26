package com.laptrinhoop.dao.impl;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.laptrinhoop.dao.IProductDAO;
import com.laptrinhoop.entity.Product;

@Repository
public class ProductDAO extends GeneraDAO<Product, Integer> implements IProductDAO {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Product> findByKeywords(String keywords) {
		if (keywords == null || keywords.trim().isEmpty()) {
			return Collections.emptyList();
		}
		String hql = "FROM Product p WHERE p.name LIKE :kw OR p.category.name LIKE :kw OR p.category.nameVN LIKE :kw";
		String keyWords = "%" + keywords + "%";
		TypedQuery<Product> query = entityManager.createQuery(hql, Product.class);
		query.setParameter("kw", keyWords);
		return query.getResultList();
	}

	@Override
	public List<Product> findByCategoryId(Integer id) {
		String hql = "FROM Product p WHERE p.category.id = ?1";
		TypedQuery<Product> query = entityManager.createQuery(hql, Product.class);
		query.setParameter(1, id);
		return query.getResultList();
	}

	@Override
	public List<Product> findItemByHot(String key) {
		String hql = "FROM Product";
		switch (key) {
			case "hangmoi":
				hql = "FROM Product p WHERE year(current_date()) - year(p.productDate) < 10";
				break;
			case "banchay":
				hql = "FROM Product p ORDER BY size(p.orderDetails) DESC";
				break;
			case "xemnhieu":
				hql = "FROM Product p ORDER BY p.viewCount DESC";
				break;
			case "giamgia":
				hql = "FROM Product p WHERE p.discount > 0 ORDER BY p.discount DESC";
				break;
			default:
				break;
		}
		// Giả sử getResultPageOrPagram trả về list trang đầu với 12 phần tử
		return getResultPageOrPagram(hql, 0, 12);
	}

	@Override
	public List<Product> findByIdsInCookie(String id) {
		// id là chuỗi dạng "1,2,3"
		String hql = "FROM Product p WHERE p.id IN (" + id + ")";
		return getResultList(hql);
	}
}
