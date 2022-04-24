package com.hnup.osmp.jiance.repository.dao.impl;

import com.hnup.osmp.jiance.repository.entity.JcOpEvaluateWorking;
import com.hnup.osmp.jiance.repository.mapper.JcOpEvaluateWorkingMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hnup.osmp.jiance.repository.dao.IJcOpEvaluateWorkingDAO;
import java.util.List;
import java.util.Map;

@SuppressWarnings("AlibabaClassNamingShouldBeCamel")
@Service
public class JcOpEvaluateWorkingServiceDAOImpl extends ServiceImpl<JcOpEvaluateWorkingMapper, JcOpEvaluateWorking>  implements  IJcOpEvaluateWorkingDAO {

	@Override
	public List<Map<String, Object>> getTableDetails(String sql) {
		return this.baseMapper.getTableDetails(sql);
	}
	
}