package cn.gdeng.market.service.lease.bi.impl;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gdeng.market.dao.base.BaseDao;
import cn.gdeng.market.dto.bean.GuDengPage;
import cn.gdeng.market.dto.lease.bi.BiLeaseListDTO;
import cn.gdeng.market.enums.StatusEnum;
import cn.gdeng.market.exception.BizException;
import cn.gdeng.market.service.lease.bi.BiLeaseListService;
@Service("biLeaseListService")
public class BiLeaseListServiceImpl implements BiLeaseListService {
    
	@Autowired
	private BaseDao<?> baseDao;
	
	@Override
	public GuDengPage<BiLeaseListDTO> getBiLeaseList(GuDengPage<BiLeaseListDTO> page, BiLeaseListDTO dto) throws BizException {
		// TODO Auto-generated method stub
		Map<String,Object> paramMap = page.getParaMap();
		paramMap.put("status", StatusEnum.NORMAL.getStatus());
		if (dto.getStartTime() != null&&!dto.getStartTime().equals("")) {paramMap.put("startTime", dto.getStartTime()+"-01 00:00:00");}
		if (dto.getEndTime() != null&&!dto.getEndTime().equals("")) {paramMap.put("endTime", dto.getEndTime()+"-31 23-59-59");}
		if (dto.getMarketResourceTypeId() != null) {paramMap.put("marketResourceTypeId", dto.getMarketResourceTypeId());}
		if (dto.getMarketId() != null) {paramMap.put("marketId", dto.getMarketId());}
		int count = countTotal(paramMap);
		page.setTotal(count);
		List<BiLeaseListDTO> list = null;
		BiLeaseListDTO dto2=new BiLeaseListDTO();
		//资源总数合计
        Integer countResource=0;
        //已租资源总数合计
        Integer countRented=0;
        //可租面积合计
        double areaAvailableForRent=0.00;
        //已租面积合计
        double leasedArea=0.00;
        //租金收入合计
        double rentalIncome=0.00;
        BiLeaseListDTO listDTO=getSum(paramMap);
		if (count > 0) {
			list = baseDao.queryForList("BiLeaseList.gueryList", paramMap, BiLeaseListDTO.class);
		    
			for(BiLeaseListDTO biLeaseListDTO: list){
				biLeaseListDTO.setCountResource(biLeaseListDTO.getCountResource()==null?0:biLeaseListDTO.getCountResource());
				biLeaseListDTO.setCountRented(biLeaseListDTO.getCountRented()==null?0:biLeaseListDTO.getCountRented());
				biLeaseListDTO.setAreaAvailableForRent(biLeaseListDTO.getAreaAvailableForRent()==null?0:biLeaseListDTO.getAreaAvailableForRent());
				biLeaseListDTO.setLeasedArea(biLeaseListDTO.getLeasedArea()==null?0:biLeaseListDTO.getLeasedArea());
				biLeaseListDTO.setRentalIncome(biLeaseListDTO.getRentalIncome()==null?0:biLeaseListDTO.getRentalIncome());
			}
			if(listDTO!=null){
				countResource=listDTO.getCountResource();
				countRented=listDTO.getCountRented();
				areaAvailableForRent=listDTO.getAreaAvailableForRent();
				leasedArea=listDTO.getLeasedArea();
				rentalIncome=listDTO.getRentalIncome()==null?0:listDTO.getRentalIncome();
	         NumberFormat numberFormat = NumberFormat.getInstance();   
	          // 设置精确到小数点后2位   
	         numberFormat.setMaximumFractionDigits(2);  
	         //出租率合计
	         String rentalRate =countRented==0||countResource==0?"0": numberFormat.format((float)countRented/(float)countResource*100);
	         //出租率（面积）合计
	         String rentalRateArea=leasedArea==0||areaAvailableForRent==0?"0.00":numberFormat.format(leasedArea/areaAvailableForRent*100);
	         //租赁坪效（总面积）
	         String leasePxZmj=rentalIncome==0||areaAvailableForRent==0?"0.00":numberFormat.format(rentalIncome/areaAvailableForRent);
	         //租赁坪效（已租面积）
	         String leasePxYzmj=leasedArea==0||rentalIncome==0?"0.00":numberFormat.format(rentalIncome/leasedArea);
	         dto2.setRentalRate(Double.parseDouble(rentalRate.replaceAll(",", "")));
	         dto2.setRentalRateArea(Double.parseDouble(rentalRateArea.replaceAll(",", "")));
	         dto2.setLeasePxZmj(Double.parseDouble(leasePxZmj.replaceAll(",", "")));
	         dto2.setLeasePxYzmj(Double.parseDouble(leasePxYzmj.replaceAll(",", "")));
	         dto2.setRentalIncome(rentalIncome);
	         dto2.setAreaAvailableForRent(areaAvailableForRent);
	         dto2.setLeasedArea(leasedArea);
	         dto2.setCountRented(countRented);
	         dto2.setCountResource(countResource);
			}
			dto2.setMarketResourceTypeName("合计");
			list.add(dto2);
		}
		
		page.setRecordList(list);
		return page;
	}

	@Override
	public int countTotal(Map<String, Object> paramMap)throws BizException {
		// TODO Auto-generated method stub
		return 	baseDao.queryForObject("BiLeaseList.getTotal",paramMap,Integer.class);
	}

	@Override
	public BiLeaseListDTO getSum(Map<String, Object> paramMap) throws BizException {
		// TODO Auto-generated method stub
		BiLeaseListDTO dto=baseDao.queryForObject("BiLeaseList.getSum", paramMap, BiLeaseListDTO.class);
		return dto;
	}
	
}
