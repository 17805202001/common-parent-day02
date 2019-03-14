package com.czxy.crm.service;

import com.czxy.bos.exception.BosException;
import com.czxy.crm.dao.CrmCustomerMapper;
import com.czxy.crm.domain.Customer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by 10254 on 2018/9/10.
 */
@Service
@Transactional
public class CrmCustomerService {

    @Resource
    private CrmCustomerMapper crmCustomerMapper;

    //查询未关联定区的客户(客户的外键为null)
    public List<Customer> findNoAssociationCustomers(){
        //拼凑条件
        Example example=new Example(Customer.class);
        Example.Criteria criteria=example.createCriteria();
        criteria.andIsNull("fixedAreaId");
        return crmCustomerMapper.selectByExample(example);
    }

    //查询指定关联的客户
    public List<Customer> findHasAssociationFixedAreaCustomers(String fixedAreaId){

        //拼凑条件
        Example example=new Example(Customer.class);
        Example.Criteria criteria=example.createCriteria();
        criteria.andEqualTo("fixedAreaId",fixedAreaId);
        return crmCustomerMapper.selectByExample(example);
    }

    //进行定区客户关联操作
    public void associationCustomersToFixedArea(String fixedAreaId,String customerIdStr){
        //将指定定区的所有关联字敦设置null
        List<Customer> list=findHasAssociationFixedAreaCustomers(fixedAreaId);
        for(Customer customer:list){
            customer.setFixedAreaId(null);
            crmCustomerMapper.updateByPrimaryKey(customer);
        }

        //切割字符串1，2，3，组织新的关联关系
        if(StringUtils.isBlank(customerIdStr)){

            return ;
        }

        //更新本次选择中的所有
        String[] customerIdArray=customerIdStr.split(",");
        for(String idstr:customerIdArray){
            Integer id=Integer.parseInt(idstr);
            //查询
            Customer customer=crmCustomerMapper.selectByPrimaryKey(id);
            customer.setFixedAreaId(fixedAreaId);
            crmCustomerMapper.updateByPrimaryKey(customer);
        }
    }

    /**
     * 通过客户id和地址查询客户，所关联的定区id
     * @param address
     * @param customerId
     * @return
     */
    public String findFixedAreaIdByAddressAndId(String address , Integer customerId){
        //1 条件
        Example example = new Example(Customer.class);
        example.createCriteria().andEqualTo("id",customerId).andEqualTo("address" , address);
        //2 查询
        Customer customer = crmCustomerMapper.selectOneByExample( example );
        if(customer == null){
            return null;
        }
        //3 返回定区器
        return customer.getFixedAreaId();
    }

    //短信验证
    public void saveCustomer(Customer customer) {
        //1 校验
        // 1.1 非空校验
        if(StringUtils.isBlank(customer.getTelephone())){
            throw new BosException("手机不能为空！");
        }
        // 1.2 手机号不能重复
        Example example = new Example(Customer.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("telephone",customer.getTelephone());
        Customer findCustomer = crmCustomerMapper.selectOneByExample(example);
        if(findCustomer != null){
            throw new BosException("手机已被占用！");
        }

        //2 添加
        crmCustomerMapper.insert(customer);

    }
    /**
     * 根据电话号码查询客户
     * @param telephone
     * @return
     */

    public Customer findByTelephone(String telephone){
        Example example=new Example(Customer.class);
        Example.Criteria criteria=example.createCriteria();
        criteria.andEqualTo("telephone",telephone);
        return crmCustomerMapper.selectOneByExample(example);
    }

    /**
     * 根据电话号码修改用户的激活状态
     * @param telephone
     */

    public void updateType(String telephone){
        Customer customer=findByTelephone(telephone);
        if(customer!=null){
           throw new BosException("操作对象不存在");
        }
        customer.setType(1);
        crmCustomerMapper.updateByPrimaryKey(customer);
    }


    /**
     * 通过手机号和密码查询客户
     * @param telephone
     * @param password
     * @return
     */

    public Customer findByPage(String telephone,String password){

        Example example=new Example(Customer.class);
        example.createCriteria().andEqualTo("telephone",telephone).andEqualTo("password",password);
        return crmCustomerMapper.selectOneByExample(example);
    }



}
