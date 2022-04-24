package com.hnup.osmp.jiance.repository.dao.impl;

import com.hnup.osmp.jiance.repository.entity.JcOpEvaluateConfig;
import com.hnup.osmp.jiance.repository.mapper.JcOpEvaluateConfigMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hnup.osmp.jiance.repository.dao.IJcOpEvaluateConfigDAO;
import java.util.List;

@SuppressWarnings("AlibabaClassNamingShouldBeCamel")
@Service
public class JcOpEvaluateConfigServiceDAOImpl extends ServiceImpl<JcOpEvaluateConfigMapper, JcOpEvaluateConfig>  implements  IJcOpEvaluateConfigDAO {

	@Override
	public Integer getTableCount(String sql) {
		return this.baseMapper.getTableCount(sql);
	}

	@Override
	public Integer getCertificateCount(Long businessKey,String phase){
		return this.baseMapper.getCertificateCount(businessKey,phase);
	}
	
}