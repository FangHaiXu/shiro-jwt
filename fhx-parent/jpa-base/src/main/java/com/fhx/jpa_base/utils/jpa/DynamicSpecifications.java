package com.fhx.jpa_base.utils.jpa;

import com.google.common.collect.Lists;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * 带条件的动态查询
 */
public class DynamicSpecifications {
	public static <T> Specification<T> bySearchFilter(final Collection<SearchFilter> filters, final Class<T> entityClazz) {
		return new Specification<T>() {

			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				if (filters != null && filters.size() > 0) {
					List<Predicate> predicates = Lists.newArrayList();
					for (SearchFilter filter : filters) {
						Path expression = root.get(filter.fieldName);
						switch (filter.operator) {
							case NEQ:
								predicates.add(builder.notEqual(expression,filter.value));
								break;
							case EQ:
								predicates.add(builder.equal(expression, filter.value));
								break;
							case LIKE:
								predicates.add(builder.like(expression, "%" + filter.value + "%"));
								break;
							case GT:
								predicates.add(builder.greaterThan(expression, (Comparable) filter.value));
								break;
							case LT:
								predicates.add(builder.lessThan(expression, (Comparable) filter.value));
								break;
							case GTE:
								predicates.add(builder.greaterThanOrEqualTo(expression, (Comparable) filter.value));
								break;
							case LTE:
								predicates.add(builder.lessThanOrEqualTo(expression, (Comparable) filter.value));
								break;
							case IN:
								CriteriaBuilder.In<Object> in = builder.in(expression);
								List<Object> list=(List<Object>)filter.value;
								for (Object integer : list) {
									in.value(integer);
								}
								predicates.add(builder.and(in));
								break;
							case LIKES:
								List<Predicate> predicateList=new ArrayList<>();
								List<String> relatedList=(List<String>) filter.value;
								Predicate [] p = new Predicate[relatedList.size()];
								for (String s : relatedList) {
									predicateList.add(builder.like(expression,"%" + s + "%"));
								}
								predicateList.toArray(p);
								predicates.add(builder.or(p));
								break;
							case GROUP:
								query.groupBy(expression);
								break;
							case ORLIKE:
								List<Predicate> predicatesOr = Lists.newArrayList();
								Path title=root.get("title");
								Path content=root.get("content");
								predicatesOr.add(builder.like(title,"%"+filter.value+"%"));
								predicatesOr.add(builder.like(content,"%"+filter.value+"%"));
								predicates.add(builder.or(predicatesOr.toArray(new Predicate[predicatesOr.size()])));
							default:
						}
					}

					// 将所有条件用 and 联合起来
					if (!predicates.isEmpty()) {
						return builder.and(predicates.toArray(new Predicate[predicates.size()]));
					}
				}
				return builder.conjunction();
			}
		};
	}
}
