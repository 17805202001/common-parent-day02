package com.czxy.bos.es.repository;

import com.czxy.bos.es.domain.ESWayBill;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Created by 10254 on 2018/9/30.
 */
public interface WayBillRepository extends ElasticsearchRepository<ESWayBill, Integer> {
}
