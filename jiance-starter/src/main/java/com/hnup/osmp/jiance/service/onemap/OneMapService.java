package com.hnup.osmp.jiance.service.onemap;

import com.hnup.osmp.mapserver.client.LandUseFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @data2021/11/10,15:47
 * @authorsutinghu
 */
@Service
public class OneMapService {

	@Autowired
	private LandUseFeignClient landUseFeignClient;

	/**
	 * 功能描述:年份
	 * @author sutinghu
	 * @date
	 * @return java.util.List<?>
	 */
	public List<?> getYearList() {
		return landUseFeignClient.getAllYDLayer();
	}
}
