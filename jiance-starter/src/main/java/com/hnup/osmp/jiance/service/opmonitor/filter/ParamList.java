package com.hnup.osmp.jiance.service.opmonitor.filter;

import java.util.ArrayList;
import java.util.List;

/**
 *  常量清单
 * @data2021/12/9,15:39
 * @authorsutinghu
 */
public class ParamList {


	public static List<ArrayList<String>> INIT = new ArrayList<ArrayList<String>>(10){
		{
			add(new ArrayList<String>(3){{ add("当前");add("办结");add("未超期");}});
			add(new ArrayList<String>(3){{ add("当前");add("办结");add("超期");}});
			add(new ArrayList<String>(3){{ add("当前");add("在办");add("超期");}});
			add(new ArrayList<String>(3){{ add("当前");add("在办");add("其他");}});

			add(new ArrayList<String>(3){{ add("同期");add("办结");add("未超期");}});
			add(new ArrayList<String>(3){{ add("同期");add("办结");add("超期");}});

			add(new ArrayList<String>(3){{ add("年度");add("办结");add("总数");}});
			add(new ArrayList<String>(3){{ add("年度");add("办结");add("超期");}});
			add(new ArrayList<String>(3){{ add("年度");add("在办");add("超期");}});
			add(new ArrayList<String>(3){{ add("年度");add("在办");add("其他");}});
		}
	};


	// 办理状态
	public final static List<String> HANDLE_STATUS = new ArrayList<String>(2){
		{
			add("办结");
			add("在办");
		};
	};

	// 时间状态
	public final static List<String> DATA_STATUS = new ArrayList<String>(3){
		{
			add("当前");
			add("同期");
			add("年度");
		};
	};
	//
	public final static List<String> LIMIT_STATUS = new ArrayList<String>(4){
		{
			add("超期");
			add("未超期");
			add("总数");
			add("其他");
		};
	};

}
